<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="pmMessage">
	<div class="modal-title">
		<div id="msgTitle"><h4></h4></div>
	</div>
	<div class="modal-content">
		<label id="msgContent">&nbsp;</label>
	</div>
	<div class="modal-btn">
		<button type="button" 	id="btnPopupMsgConfirm" title="확인">확인</button>
		<button type="button" 	id="btnPopupMsgCancel" 	title="취소">취소</button>
	</div>
</div>

<script>
$(document).ready(function(){
	$("#pmMessage #msgTitle h4").html(g3way.sicims.common.msgTitle);
	$("#pmMessage #msgContent").html(g3way.sicims.common.msgContent);

	$("#pmMessage #btnPopupMsgConfirm").focus();

	if (g3way.sicims.common.callback == null || g3way.sicims.common.canselYn == "N") {
		$("#pmMessage #btnPopupMsgCancel").hide();
	} else {
		$("#pmMessage #btnPopupMsgCancel").show();
	}
	msgBoxInvokeEvent();
});


/*=======================
msgBox invoke event
=========================*/
function msgBoxInvokeEvent() {
	// 확인
 	$("#pmMessage #btnPopupMsgConfirm").click(function() {
 		//$.modal.close();
		//$("#divPopupMessage").empty().hide();
		$(".current").remove();

 		if (g3way.sicims.common.editObj != null) {
 			g3way.sicims.common.editObj.focus();
 		}

 		if (typeof g3way.sicims.common.callback == 'function') {
 			g3way.sicims.common.callback();
 		}
 	});

	// 취소
 	$("#pmMessage #btnPopupMsgCancel").click(function() {
 		//$.modal.close();
 		//$("#divPopupMessage").empty().hide();
 		$(".current").remove();
	});

}
</script>
