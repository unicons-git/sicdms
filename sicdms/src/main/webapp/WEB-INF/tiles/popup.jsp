<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"  prefix="tiles"%>

<!doctype html>
<html lang="ko">
<%-- <tiles:insertAttribute name="include"/> --%>
<body>
	<section style="min-height:auto;">
		<h2 class="blind">건설업 빅데이터 통합관리시스템</h2>
		<article aria-label="article">
			<tiles:insertAttribute name="article"/>
		</article>
		<div id="divPopupMessage"	class="popupModal"></div>
	</section>
</body>
</html>
