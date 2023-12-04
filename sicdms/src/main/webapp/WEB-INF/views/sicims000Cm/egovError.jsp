<%
	if( "GET".equals(request.getMethod())){
%>
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
		window.location.href = "<c:url value='/sicims000Cm/egovError.do'/>";
	}
});

</script>
<body>
	<section>
		<h2 class="blind">오류 안내</h2>
		<div style="margin-top: 100px;">
			<article class="login">
				<h3 class="blind">오류 안내</h3>
				<div class="wrap cen" style="width:900px;border: 1px solid rgb(62, 134, 200);">
					<div style="margin:50px;text-align: center; vertical-align: middle;">
						<div style="font-size: 30px; font-weight: bold;">
							<p class="login-txt">해당 페이지 로드중 오류가 발생하였습니다.</p>
							<br/>
							<p class="login-txt">잠시 후 다시 시도해 주시기 바랍니다.</p>
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
<%
	}
	else {
%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.HashMap"%>
<%
	HashMap<String, String> result = new HashMap<String, String>();
	result.put("status", "fail");
	result.put("message", "오류가 발생하였습니다.");
    // json 형태로 리턴하기 위한 json객체 생성
    JSONObject jobj = new JSONObject();
	jobj.put("status", "fail");
	jobj.put("message", "오류가 발생하였습니다.");

	jobj.put("result", result);

    // 응답시 json 타입이라는 걸 명시 ( 안해주면 json 타입으로 인식하지 못함 )
	response.setContentType("application/json");
	out.print(jobj.toJSONString()); // json 형식으로 출력
%>
<%
	}
%>
