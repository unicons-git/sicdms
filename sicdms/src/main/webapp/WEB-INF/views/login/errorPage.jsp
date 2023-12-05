<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no, address=no, email=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>건설업 빅데이터 통합관리시스템</title>
	<script src="<c:url value='/resources/framework/RSA/rsa.js'/>"></script>
	<script src="<c:url value='/resources/framework/RSA/jsbn.js'/>"></script>
	<script src="<c:url value='/resources/framework/RSA/prng4.js'/>"></script>
	<script src="<c:url value='/resources/framework/RSA/rng.js'/>"></script>
</head>

<body>
<script>
$(document).ready(function(){
	invokeEvent();			// invoke event
});

/*=======================
invoke event
=========================*/
function invokeEvent() {

	// 아이디 찾기
	$("#btnBackPageMove").click(function() {
		window.top.location.href = "<c:url value='/login/login.do'/>";
	});

	// 아이디 찾기
	$("#btnFindUserId").click(function() {
		window.top.location.href = "<c:url value='/login/join/findUserId.do'/>";
	});

	// 패스워드 찾기
	$("#btnFindUserPw").click(function() {
		window.top.location.href = "<c:url value='/login/join/findUserPw.do'/>";
	});
}

</script>
	<section class="login">
		<div class="wrap cen">
			<!-- 로그인 화면 -->
			<div class="sub_wrap">
				<div class="inner">
					<div class="sub_title">
						<h3>이미 가입된 사용자</h3>
					</div>
					<div class="sub_section">
						<div class="login_notice">
							<ul>
								<li>이미 가입된 사용자 입니다.</li>
							</ul>
							<ul>
								<li>아이디 찾기를 통해 가입된 회원정보를 확인해 주시기 바랍니다.</li>
							</ul>
						</div>
						<div class="login_wrap">
							<div class="login_inner">
								<div class = "btnKd01 tc"  >
									<button type="button" id = "btnBackPageMove" title="로그인페이지" class = "white">로그인페이지</button>
									<button type="button" id = "btnFindUserId" title="아이디 찾기">아이디 찾기</button>
									<button type="button" id = "btnFindUserPw" title="패스워드 찾기">패스워드 찾기</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
