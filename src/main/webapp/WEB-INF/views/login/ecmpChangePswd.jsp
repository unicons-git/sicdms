<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no, address=no, email=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>건설업 빅데이터 통합관리시스템-패스워드 변경</title>

	<link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui.css'/>" >
    <link rel="stylesheet" href="<c:url value='/resources/css/g3way.sicims.login.css'/>" >
    <link rel="stylesheet" href="<c:url value='/resources/css/g3way.sicims.main.css'/>" >
    <link rel="stylesheet" href="<c:url value='/resources/framework/jquery/jquery-modal/jquery.modal.css'/>" >

	<script src="<c:url value='/resources/framework/jquery/jquery-1.12.4.min.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery.fileDownload.js' />"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery-ui.js' />"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery.metadata.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery.form.min.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery-modal/jquery.modal.js'/>"></script>
	<script src="<c:url value='/resources/js/g3way.sicims.common.js'/>"></script>

	<script src="<c:url value='/resources/framework/RSA/rsa.js'/>"></script>
	<script src="<c:url value='/resources/framework/RSA/jsbn.js'/>"></script>
	<script src="<c:url value='/resources/framework/RSA/prng4.js'/>"></script>
	<script src="<c:url value='/resources/framework/RSA/rng.js'/>"></script>
</head>

<script>
$(document).ready(function(){
	invokeEvent();			// invoke event
});

/*=======================
invoke event
=========================*/
function invokeEvent() {
	// 비밀번호 변경
	$("#btnEcmpUpdatePswd").click(function() {
		if ( !g3way.sicims.common.excuteFormValidate('#frmInput')) {
			return false;
		}

		g3way.sicims.common.messageBox(null, "비밀번호 변경", "비밀번호를 변경하시겠습니까?", ecmpUpdatePswd);
	});

	// 이전페이지
	$("#btnBackPageMove").click(function() {
		window.top.location.href = "<c:url value='/login/login.do'/>";
	});

}

// 비밀번호변경
function ecmpUpdatePswd() {
	var userPswd1 = $("#frmInput input[name=userPswd1]").val();
	var userPswd2 = $("#frmInput input[name=userPswd2]").val();

	if(userPswd2 != userPswd2 ){
		$("#frmInput input[name=userPswd1]").focus();
		g3way.sicims.common.messageBox(null, "비밀번호 확인", "새비밀번호와 새비밀번호 확인이 일치하지 않습니다.", null);
		return false;
	}


	//RSA이용한 개인정보 암호화
	g3way.sicims.common.encryptForm("frmInput",  "frmInfo", "RSAModulus", "RSAExponent");

	$("#frmInfo").ajaxForm({
		type		: 	"post",
		dataType	: 	"json",
		url			:	"<c:url value='/login/join/ecmpUpdatePswd.do'/>",
		success 	: 	function(data, textStatus, XMLHttpRequest){
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "비밀번호 변경 성공", "비밀번호 변경에 성공하였습니다. 변경한 비밀번호 다시 로그인 하시기 바랍니다.", moveLogoutPage, null, 'N');
							} else {
								var reusltMsg = "";
								if(data != undefined && data.resultMsg != undefined ){
									reusltMsg = "<br>" + data.resultMsg;
								}
								g3way.sicims.common.messageBox(null, "비밀번호 변경 실패", "비밀번호 변경에 실패하였습니다." + reusltMsg, null);
							}
						},
	}).submit();
}

function moveLogoutPage(){
	//로그아웃 페이지 호출
	location.href = "<c:url value='/login/logout.do' />";
}

</script>
<body>
	<header id="header" class="header">
		<c:import url="/main/header.do?loginYn=Y" charEncoding="UTF-8" />
	</header>
	<section class="login">
		<div class="wrap cen">
			<!-- 로그인 화면 -->
			<div class="sub_wrap">
				<div class="inner" style="width:1000px;">
					<div class="sub_title">
						<h3>비밀번호 변경</h3>
					</div>
					<div class="sub_section">
						<div class="login_notice">
							<div class="tip">TIP</div>
							<ul>
								<li>(*)은 필수 입력 항목입니다.</li>
							</ul>
							<ul>
								<li>비밀번호는 9~16자의 영문, 숫자, 특수문자를  혼합하여 입력해주세요</li>
							</ul>
						</div>
						<div class="login_wrap" style="padding:30px 0; ">
							<form id="frmInfo" >
								<input type="hidden" id="RSAModulus" 	value="${fn:escapeXml(RSAModulus)}"/>
								<input type="hidden" id="RSAExponent" 	value="${fn:escapeXml(RSAExponent)}"/>
								<input type="hidden" name="userId"		class="RSA"/>
								<input type="hidden" name="userPswd"	class="RSA"/>
								<input type="hidden" name="userPswd1"	class="RSA"/>
								<input type="hidden" name="userPswd2"	class="RSA"/>
							</form>
							<form id="frmInput" autocomplete="off">
								<div class="login_inner">
									<div class="password_form">
										<dl>
											<dt>사용자ID(*)</dt>
											<dd>
												<input type="text" name="userId" placeholder="사용자ID 입력"  title="사용자ID를 입력해주세요." autocomplete="off"  maxlength="32" class="required isEmail" />
											</dd>
										</dl>
										<dl>
											<dt>현재비밀번호(*)</dt>
											<dd>
												<input type="password" name="userPswd" placeholder="현재비밀번호 입력"  title="현재 비밀번호를 입력해주세요." autocomplete="off"  maxlength="16" class="required isPassword" />
											</dd>
										</dl>
										<dl>
											<dt>새 비밀번호(*)</dt>
											<dd><input type="password" name="userPswd1" placeholder="새 비밀번호 입력"  title="새 비밀번호를 입력해주세요." autocomplete="off"  maxlength="16" class="required isPassword" />
											</dd>
										</dl>
										<dl>
											<dt>새 비밀번호확인(*)</dt>
											<dd>
												<input type="password" name="userPswd2" placeholder="새 비밀번호 확인 입력"  title="새 비밀번호 확인을 입력해주세요." autocomplete="off"  maxlength="16" class="required isPassword" />
											</dd>
										</dl>
									</div>
									<div class="btnAction"  >
										<ul>
											<li><button type="button" id="btnBackPageMove"		title="이전페이지"	 class="default2">이전페이지</button></li>
											<li><button type="button" id="btnEcmpUpdatePswd" 	title="비밀번호변경" class="default1">비밀번호변경</button></li>
										</ul>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div id="divMessage"	class="modal"></div>
			<div id="divPopup"		class="modal"></div>
		</div>
	</section>
</body>
</html>
