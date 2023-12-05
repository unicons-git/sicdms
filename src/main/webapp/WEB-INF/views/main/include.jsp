<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
	<c:set var = "kakaoMapApiKey"><spring:eval expression="@properties['kakaoMap.apiKey']"/></c:set>
	<c:set var = "reportingServer"><spring:eval expression="@properties['reportingServer.url']"/></c:set>
	<c:set var = "releaseYmd">20230831</c:set>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=0,maximum-scale=10,user-scalable=yes">
    <!--[if lt IE 7]><meta http-equiv="imagetoolbar" content="no"><![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<link rel="shortcut icon" href="<c:url value='/resources/images/icons/favicon.ico'/>" type="image/x-icon"><!-- 브라우져 파비콘 -->

	<title>건설업 빅데이터 통합관리시스템</title>

    <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui.css'/>" >
    <link rel="stylesheet" href="<c:url value='/resources/css/g3way.sicims.main.css?ver=${fn:escapeXml(releaseYmd)}'/>" >
    <link rel="stylesheet" href="<c:url value='/resources/framework/jquery/jquery-modal/jquery.modal.css?ver=${fn:escapeXml(releaseYmd)}'/>" >
	<link rel="stylesheet" href="<c:url value='/resources/innorix/innorix.css' />">

	<script src="<c:url value='/resources/innorix/innorix.js?ver=${fn:escapeXml(releaseYmd)}'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery-3.3.1.min.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery-ui.js' />"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery.metadata.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery.form.min.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery-modal/jquery.modal.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/slick.js' />"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery.serialize-object.js'/>"></script>
	<script src="<c:url value='/resources/framework/jquery/jquery.dialogextend.js'/>"></script>

	<script src="<c:url value='/resources/js/g3way.sicims.common.js?ver=20210915'/>"></script>
	<script src="<c:url value='/resources/js/g3way.sicims.paginator.js'/>"></script>
	<script src="<c:url value='/resources/js/g3way.sicims.main.js' />"></script>
	<script src="<c:url value='/resources/js/g3way.sidms.innorixFileupload.js?ver=${fn:escapeXml(releaseYmd)}'/>"></script>
</head>
