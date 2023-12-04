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
	<title>건설업 빅데이터 통합관리시스템</title>

	<script src="<c:url value='/resources/framework/jquery/jquery-1.12.4.min.js'/>"></script>
	<script src="<c:url value='/resources/js/g3way.sicims.common.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery-modal/jquery.modal.js'/>"></script>
</head>

<body>
<script>
$(document).ready(function(){
	//<c:if test = "${not empty message}">
	//g3way.sicims.common.messageBox(null, "알림", "${fn:escapeXml(message)}" , pageMove, null, "N");
	window.location.href = "<c:url value='/login/login.do'/>";
	//</c:if>
});
function pageMove(){

}
</script>
<div id="divMessage"	class="modal"></div>
</body>
</html>