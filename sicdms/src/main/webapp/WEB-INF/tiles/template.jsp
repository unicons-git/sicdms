<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"  prefix="tiles"%>

<!doctype html>
<html lang="ko">
<tiles:insertAttribute name="include"/>
<body>
	<header id="header" class="header">
		<tiles:insertAttribute name="header"/>
	</header>
	<section>
		<h2 class="blind">건설업 빅데이터 통합관리시스템</h2>
		<div class="wrap">
			<nav>
				<tiles:insertAttribute name="nav"/>
			</nav>
			<article aria-label="article">
				<tiles:insertAttribute name="article"/>
			</article>

			<div id="divMessage"	class="modal"></div>
			<div id="divPopup"		class="modal"></div>
		</div>
	</section>
	<tiles:insertAttribute name="footer"/>
	<div class="black__dimmed"></div>
	<!-- 카카오맵 -->
	<div class="kakaoContainer"></div>
</body>
</html>
