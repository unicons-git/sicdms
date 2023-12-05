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
	<title>건설업 빅데이터 통합관리시스템(외부사용자)</title>

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
	if($("article").length > 1){
		window.location.href = "<c:url value='/login/login.do'/>";
	}

	invokeEvent();			// invoke event
	//<c:if test = "${not empty message}">
	g3way.sicims.common.messageBox(null, "알림", "${fn:escapeXml(message)}" , null);
	//</c:if>
	var userId = g3way.sicims.common.getCookie("userId");	//저장된 쿠기값 가져오기
	if(userId != undefined && userId != ''){
	    $("#frmInput input[name=userId]").val(userId);
        $("#idChk").attr("checked", true); 				// ID 저장하기를 체크 상태로 두기.
	}

});

/*=======================
invoke event
=========================*/
function invokeEvent() {
	$("#frmInput input[name=userId]").keypress(function(event){
	     if(event.which == 13) {
	    	 getUserInfo();
	     }
	});

	$("#frmInput input[name=userPswd]").keypress(function(event){
	     if(event.which == 13) {
	    	 getUserInfo();
	     }
	});

	// 로그인
	$("#btnLogin").click(function() {
		getUserInfo();
	});


	// 홈페이지 / 비밀번호 변경
	$("#btnGoHome, #btnChangePswd").click(function() {
		goMovePage(this.id);
	});

	$("#idChk").change(function(){
		// ID 저장하기 체크 해제 시
        if(!$("#idChk").is(":checked")){
        	g3way.sicims.common.deleteCookie("userId");
        }
    });
}


//페이지 이동
function goMovePage(btnId){
	var url = "";
	// 홈페이지
	if(btnId == 'btnGoHome')		url = "<c:url value='/login/login.do'/>";
	// 비밀번호 변경
	if(btnId == 'btnChangePswd')	url = "<c:url value='/login/join/ecmpChangePswd.do'/>";

	window.location.href = url;
}


//사용자 정보 조회
function getUserInfo() {
	if(loginCheck()){
		// 외부사용자 아이디 : 이메일 형식
		if (!regExEmail.test($("#frmInput input[name=userId]").val())) {
			g3way.sicims.common.messageBox(null, "아이디 유효성 검사 ", "아이디가 이메일 형식에 맞지 않습니다." , null);
			return false;
		}

		//RSA이용한 개인정보 암호화
		g3way.sicims.common.encryptForm("frmInput",  "frmInfo", "RSAModulus", "RSAExponent");

		if($("#idChk").is(":checked")){ // ID 저장하기를 체크한 상태라면,
            var userId = $("#frmInput input[name=userId]").val();
            g3way.sicims.common.setCookie("userId", userId, 7); // 7일 동안 쿠키 보관
        }

		$("#frmInfo").ajaxForm({
			type		: 	"post",
			dataType	: 	"json",
			url			:	"<c:url value='/login/actionLogin.do'/>",
			success 	: 	function(data, textStatus, XMLHttpRequest){
								if (Object.keys(data).length > 0) {
									if (data.status == "success") {
										location.href = "<c:url value='/main.do' />";
									} else {
										if(data.status == 'fail' && data.message == undefined){
											data.message = '해당 계정에 부여 권한이 없습니다. 헬프데스크에 문의하세요.';
										}
										g3way.sicims.common.messageBox(null, "로그인 실패 ", data.message , null);
									}
								} else {
									g3way.sicims.common.messageBox(null, "로그인 실패 ", "로그인에 실패했습니다.\n아이디, 비밀번호를 확인해 주세요." , null);
								}

							},
		}).submit();
	}
}



//아이디/비밀번호 체크
function loginCheck(){
	if($("#frmInput input[name=userId]").val() == ""){
		g3way.sicims.common.messageBox(null, "로그인 실패 ", "아이디를 입력해 주세요" , null);
		return false;
	}else if($("#frmInput input[name=userPswd]").val() == ""){
		g3way.sicims.common.messageBox(null, "로그인 실패 ", "비밀번호를 입력해 주세요" , null);
		return false;
	}
	return true;
}


</script>
<body>
	<header id="header" class="header">
		<c:import url="/main/header.do?loginYn=Y" charEncoding="UTF-8" />
	</header>
	<section class="login">
		<article>
		<div class="wrap cen">
			<!-- 로그인 화면 -->
			<div class="sub_wrap">
				<div class="inner" style="width:1000px;">
					<div class="sub_title">
						<h3>로그인</h3>
					</div>
					<div class="sub_section">
						<div class="login_wrap">
							<div class="login_inner">
								<form id="frmInfo" >
									<input type="hidden" id="RSAModulus" 	value="${fn:escapeXml(RSAModulus)}"/>
									<input type="hidden" id="RSAExponent" 	value="${fn:escapeXml(RSAExponent)}"/>
									<input type="hidden" name="userId"/>
									<input type="hidden" name="userPswd" 	class = "RSA" />
								</form>
								<form id="frmInput" autocomplete="off">
									<div class="login_form">
										<dl>
											<dt>아이디</dt>
											<dd>
												<label for="userId"></label>
												<input type="text" name="userId" value="" >
											</dd>
										</dl>
										<dl>
											<dt>비밀번호</dt>
											<dd>
												<label for="userPswd"></label>
												<input type="password" name="userPswd" value=""  autoComplete="off">
											</dd>
										</dl>
										<button type="button" class="login_btn" id="btnLogin">로그인</button>
										<label for="idChk" class="id_check"><input type="checkbox" id="idChk">아이디 저장</label>
										<div class="clear"></div>
										<div class="loginComment">
											<span>아이디는 이메일 형식입니다.</span>
											<span style="font-weight:bold;">비밀번호 분실 시 문의는 000 주무관(02-2133-8100)</span>
										</div>
									</div>
								</form>
								<div class="login_util">
									<ul>
										<li><button id="btnChangePswd" 	type="button" class="default2">비밀번호 변경</button></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</article>
		<div id="divMessage"	class="modal"></div>
		<div id="divPopup"		class="modal"></div>
	</section>
	<c:import url="/main/footer.do" charEncoding="UTF-8" />
	<div class="black__dimmed"></div>
</body>
</html>
