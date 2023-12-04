<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>


<div>
	<div id="divReport" style="display:none;">
		<div id="divCrownixViewer" style="position:relative;width:100%;height:720px;"></div>
	</div>
	<div style="margin-top:10px;"></div>
	<div class="btnAction">
		<ul>
			<li><button type="button" id="btnConfirm"  class="default1" title="확인">확인</button></li>
		</ul>
	</div>
</div>

<script>
$(document).ready(function(){
	popupInvokeEvent();
	print();
});


/*=======================
invoke event
=========================*/
function popupInvokeEvent() {
	$("#btnConfirm").click(function(event){
		$.modal.close();
	});
}

// 출력
function print() {
	var html5 		= decodeURIComponent("${fn:escapeXml(info.html5)}");
	var reportName 	= decodeURIComponent("${fn:escapeXml(info.reportName)}");
	var param		= decodeURIComponent("${fn:escapeXml(info.param)}");

	$("#divReport").show();
	var viewer = new m2soft.crownix.Viewer("${fn:escapeXml(reportingServer)}/service", 'divCrownixViewer',{enableToolbarNavigator:true});
	if (html5 == "Y") {
		viewer.openFile(reportName, param);
	} else {
		viewer.setStateLessMode(false);
		viewer.openFile(reportName, param, {textOnCanvas:true,timeout:600}); 	// html5 랜더링시 시간이 많이 걸릴 경우 */
	}
}

</script>
