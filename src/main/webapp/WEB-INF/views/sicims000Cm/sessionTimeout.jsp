<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>건설업 빅데이터 통합관리시스템</title>
	<link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui.css'/>" >
    <link rel="stylesheet" href="<c:url value='/resources/css/g3way.sicims.login.css'/>" >
    <link rel="stylesheet" href="<c:url value='/resources/css/g3way.sicims.main.css'/>" >
	<script src="<c:url value='/resources/framework/jquery/jquery-1.12.4.min.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery-ui.js' />"></script>

</head>
<script>
$(document).ready(function(){
	if($("article").length > 1){
		window.location.href = "<c:url value='/sicims000Cm/sessionTimeout.do'/>";
	}
});

</script>
<body>
	<header id="header" class="header">
		<c:import url="/main/header.do?login=N" charEncoding="UTF-8" />
	</header>
	<section  class="login">
		<h2 class="blind">세션 만료 안내</h2>
		<div style="margin-top: 100px;">
			<article class="login">
				<h3 class="blind">세션 만료 안내</h3>
				<div class="wrap cen" style="border: 1px solid rgb(62, 134, 200);">
					<div style="margin:50px;text-align: center; vertical-align: middle;">
						<div style="font-size: 30px; font-weight: bold;">
							<p class="login-txt">시스템 미사용으로 세션이 만료되었습니다.</p>
							<br/>
							<p class="login-txt">다시 로그인 하시기 바랍니다.</p>
							<br/><br/>
							<span>
								<a href="<c:url value='/login/login.do'/>" style="font-size: 30px; color:#0000ff; font-weight: bold; text-decoration: underline;">홈 페이지 가기</a>
							</span>
						</div>
					</div>
				</div>
			</article>
		</div>
	</section>
	<c:import url="/main/footer.do" charEncoding="UTF-8" />
	<div class="black__dimmed"></div>
</body>
</html>



