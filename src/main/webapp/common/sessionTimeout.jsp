<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>기반시설관리시스템</title>
	<link rel="stylesheet"	href="<c:url value='/resources/sicims/css/g3way.sicims.login.css' />">

	<script src="<c:url value='/resources/framework/jquery/jquery-1.12.4.min.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery-ui.js' />"></script>
</head>

<body>
	<section>
		<h2 class="blind">세션 만료 안내</h2>
		<div class="wrap" style="margin-top: 155px;">
			<article class="login">
				<h3 class="blind">세션 만료 안내</h3>
				<div class="cen" style="border: 2px solid rgb(62, 134, 200);">
					<div class="login-content">
						<div class="login-area">
							<p class="login-txt">시스템 미사용으로 세션이 만료되었습니다.</p>
							<p class="login-txt">다시 로그인 하시기 바랍니다.</p>
							<br /><br />
							<br/>
							<br/>
							<br/>
							<br/>
							<br/>
							<span>
								<a href="<c:url value='/login/login.do'/>" style="font-size: 16px; color:#0000ff; font-weight: bold; text-decoration: underline;">홈 페이지 가기</a>
							</span>
						</div>
					</div>
					<div class="cs">(담당과) 국토교통부 시설안전과 (우)30103 세종특별자치시 도움6로 11 국토교통부<br>
                		헬프데스크 : (시스템담당) 031-910-4050, 031-910-4051<br>
                		<span style="margin-left:50px;">(업무담당) 070-5121-7535, 070-5121-7537</span>
                	</div>
					<div class="ft-logo"><span class="blind">토토 통통</span></div>
				</div>
			</article>
		</div>
	</section>
</body>
</html>



