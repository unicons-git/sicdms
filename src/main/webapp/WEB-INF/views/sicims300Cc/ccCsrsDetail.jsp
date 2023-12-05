<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var = "reportingServer"><spring:eval expression="@properties['reportingServer.url']"/></c:set>

<div>
	<div style="margin-top:10px;">
		<div class="title2 left">건설업등록기준사전조사 결과</div>
	</div>
	<form id="frmCcCsrs" method="post">
		<input type="hidden"	name="cirsSn"			value="${fn:escapeXml(info.cirsSn)}" />
		<input type="hidden"	name="ontcAtflId"		value="${fn:escapeXml(ccCsrsVo.ontcAtflId)}" />
		<input type="hidden"	name="rsltRptAtflId"	value="${fn:escapeXml(ccCsrsVo.rsltRptAtflId)}" />
		<input type="hidden"	name="acseNtfctnAtflId"	value="${fn:escapeXml(ccCsrsVo.acseNtfctnAtflId)}" />

		<div style="margin-top:5px;">
			<table class="sicims-info-tb">
				<caption>건설업등록기준사전조사 결과</caption>
				<colgroup>
					<col width="9%" />
					<col width="9%" />
					<col width="15%" />
					<col width="10%" />
					<col width="8%" />
					<col width="8%" />
					<col width="10%" />
					<col width="8%" />
					<col width="10%" />
					<col width="*" />
				</colgroup>
				<thead></thead>
				<tfoot></tfoot>
				<tbody>
					<tr>
						<th colspan="2">방문일(*)</th>
						<td class = "tl">
							<fmt:parseDate value="${ccCsrsVo.vstYmd}" 	var="vstYmd" pattern="yyyyMMdd" />
							<label class="blind" for="vstYmd">방문일</label>
							<input type="text" id="vstYmd" name="vstYmd" class="required w100" title="방문일을 선택하세요." placeholder="방문일" value="<fmt:formatDate value="${vstYmd}" pattern="yyyy-MM-dd" />" />
						</td>
						<th>최종결과(*)</th>
						<td colspan="6">
							<input type="text" name="lastRslt" value="${fn:escapeXml(ccCsrsVo.lastRslt)}" class="required" placeholder="최종결과를 입력하세요." title="최종결과를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99.6%;"/>
						</td>
					</tr>
					<tr>
						<th colspan="2">적합여부(*)</th>
						<td colspan="8">
							<select name="ofcStbltYn" class="required w150" title="사무실적합여부를 선택바랍니다.">
								<option value="">사무실적합여부 선택</option>
								<option value="Y" <c:if test="${ccCsrsVo.ofcStbltYn eq 'Y'}">selected</c:if>>Y</option>
								<option value="N" <c:if test="${ccCsrsVo.ofcStbltYn eq 'N'}">selected</c:if>>N</option>
							</select>
							<select name="tcnbltStbltYn" class="required w150" title="기술능력적합여부를 선택바랍니다.">
								<option value="">기술능력적합여부 선택</option>
								<option value="Y" <c:if test="${ccCsrsVo.tcnbltStbltYn eq 'Y'}">selected</c:if>>Y</option>
								<option value="N" <c:if test="${ccCsrsVo.tcnbltStbltYn eq 'N'}">selected</c:if>>N</option>
							</select>
							<select name="cptlStbltYn" class="required w150" title="자본금적합여부를 선택바랍니다.">
								<option value="">자본금적합여부 선택</option>
								<option value="Y" <c:if test="${ccCsrsVo.cptlStbltYn eq 'Y'}">selected</c:if>>Y</option>
								<option value="N" <c:if test="${ccCsrsVo.cptlStbltYn eq 'N'}">selected</c:if>>N</option>
							</select>
						</td>
					</tr>
					<tr>
						<th rowspan="3">행정처분</th>
						<th>위반내용/<br/>지적사항</th>
						<td colspan="8">
							<input type="text" name="admdspVltnCn" value="${fn:escapeXml(ccCsrsVo.admdspVltnCn)}" placeholder="위반내용/지적사항을 입력하세요." title="위반내용/지적사항을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.6%;"/>
						</td>
					</tr>
					<tr>
						<th>면허</th>
						<td colspan="8">
							<input type="text" name="admdspLcns" value="${fn:escapeXml(ccCsrsVo.admdspLcns)}" placeholder="면허를 입력하세요." title="면허를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.6%;"/>
						</td>
					</tr>
					<tr>
						<th>위반</th>
						<td colspan="8">
							<input type="text" name="admdspVltn" value="${fn:escapeXml(ccCsrsVo.admdspVltn)}" placeholder="위반을 입력하세요." title="위반을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.6%;"/>
						</td>
					</tr>
					<tr>
						<th colspan="2">행정처분기관</th>
						<td colspan="6">
							<input type="text" name="admdspInstNm" value="${fn:escapeXml(ccCsrsVo.admdspInstNm)}" placeholder="행정처분기관을 입력하세요." title="행정처분기관을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99.6%;"/>
						</td>
						<th>행정처분의뢰일</th>
						<td class = "tl">
							<fmt:parseDate value="${ccCsrsVo.admdspRqstYmd}" 	var="admdspRqstYmd" pattern="yyyyMMdd" />
							<label class="blind" for="admdspRqstYmd">행정처분의뢰일</label>
							<input type="text" id="admdspRqstYmd" name="admdspRqstYmd" class="w100" title="행정처분의뢰일을 선택하세요." placeholder="행정처분의뢰일" value="<fmt:formatDate value="${admdspRqstYmd}" pattern="yyyy-MM-dd" />" />
						</td>
					</tr>
					<tr>
						<th colspan="2">행정처분내용</th>
						<td colspan="8">
							<input type="text" name="admdspCn" value="${fn:escapeXml(ccCsrsVo.admdspCn)}" placeholder="행정처분내용을 입력하세요." title="행정처분내용을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99.6%;"/>
						</td>
					</tr>
					<tr>
						<th colspan="2">행정처분시작일</th>
						<td colspan="4" class = "tl">
							<fmt:parseDate value="${ccCsrsVo.admdspBgngYmd}" 	var="admdspBgngYmd" pattern="yyyyMMdd" />
							<label class="blind" for="admdspBgngYmd">행정처분시작일</label>
							<input type="text" id="admdspBgngYmd" name="admdspBgngYmd" class="w100" title="행정처분시작일을 선택하세요." placeholder="행정처분시작일" value="<fmt:formatDate value="${admdspBgngYmd}" pattern="yyyy-MM-dd" />" />
						</td>
						<th>행정처분종료일</th>
						<td colspan="4" class = "tl">
							<fmt:parseDate value="${ccCsrsVo.admdspEndYmd}" 	var="admdspEndYmd" pattern="yyyyMMdd" />
							<label class="blind" for="admdspEndYmd">행정처분종료일</label>
							<input type="text" id="admdspEndYmd" name="admdspEndYmd" class="w100" title="행정처분종료일을 선택하세요." placeholder="행정처분종료일" value="<fmt:formatDate value="${admdspEndYmd}" pattern="yyyy-MM-dd" />" />
						</td>
					</tr>
					<tr>
						<th colspan="2">계약배제여부</th>
						<td colspan="8">
							<input type="text" name="ctrtExcl" value="${fn:escapeXml(ccCsrsVo.ctrtExcl)}" placeholder="계약배제여부를 입력하세요." title="계약배제여부를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99.6%;"/>
						</td>
					</tr>
					<tr>
						<th colspan="2">공문알림</th>
						<td class = "tl">
							<fmt:parseDate value="${ccCsrsVo.ontcYmd}" 	var="ontcYmd" pattern="yyyyMMdd" />
							<label class="blind" for="ontcYmd">공문알림</label>
							<input type="text" id="ontcYmd" name="ontcYmd" class="w100" title="공문알림일을 선택하세요." placeholder="공문알림일" value="<fmt:formatDate value="${ontcYmd}" pattern="yyyy-MM-dd" />" />
						</td>
						<th>첨부파일</th>
						<td colspan="6" style="height:30px;">
						<c:if test="${ccCsrsVo.ontcAtflId ne null and ccCsrsVo.ontcAtflId ne ''}">
							<div class="file_Download">
								<div class="link_text left">
									<a href="#none" onclick="event.stopPropagation(); g3way.sicims.common.downloadFile('${fn:escapeXml(ccCsrsVo.ontcAtflId)}');">${fn:escapeXml(ccCsrsVo.ontcAtflNm)}</a>
								</div>
							<c:if test="${ccCsrsVo.updusrId eq member.userId}">
								<div class="left"  style="margin-left:30px;">
									<input id="btnOntcAtflDelete" type="button" value="삭제"  style="height:26px;"/>
								</div>
							</c:if>
							</div>
						</c:if>
							<div class="clear"></div>
							<div style="float:left; width:100%; ">
								<div class="left" style="width:90%;">
									<input type="file" name="ontcAtfl" title="파일을 선택하세요." onchange="ccCsrsFileSelected(this, 'btnOntcAtflCancel');" style="border:0px; width:100%; cursor:pointer;" accept=".hwp,.hwpx,.pdf,.xls,.xlsx,.zip,.egg,.avi,.mp4" />
								</div>
								<div class="left">
									<input id="btnOntcAtflCancel" type="button" class="default2" style="display:none;" value="취소"/>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th colspan="2">결과보고</th>
						<td class = "tl">
							<fmt:parseDate value="${ccCsrsVo.rsltRptYmd}" 	var="rsltRptYmd" pattern="yyyyMMdd" />
							<label class="blind" for="rsltRptYmd">결과보고일</label>
							<input type="text" id="rsltRptYmd" name="rsltRptYmd" class="w100" title="결과보고일을 선택하세요." placeholder="결과보고일" value="<fmt:formatDate value="${rsltRptYmd}" pattern="yyyy-MM-dd" />" />
						</td>
						<th>첨부파일</th>
						<td colspan="6" style="height:30px;">
						<c:if test="${ccCsrsVo.rsltRptAtflId ne null and ccCsrsVo.rsltRptAtflId ne ''}">
							<div class="file_Download">
								<div class="link_text left">
									<a href="#none" onclick="event.stopPropagation(); g3way.sicims.common.downloadFile('${fn:escapeXml(ccCsrsVo.rsltRptAtflId)}');">${fn:escapeXml(ccCsrsVo.rsltRptAtflNm)}</a>
								</div>
							<c:if test="${ccCsrsVo.updusrId eq member.userId}">
								<div class="left"  style="margin-left:30px;">
									<input id="btnRsltRptAtflDelete" type="button" value="삭제"  style="height:26px;"/>
								</div>
							</c:if>
							</div>
						</c:if>
							<div class="clear"></div>
							<div style="float:left; width:100%; ">
								<div class="left" style="width:90%;">
									<input type="file" name="rsltRptAtfl" title="파일을 선택하세요." onchange="ccCsrsFileSelected(this, 'btnRsltRptAtflCancel');" style="border:0px; width:100%; cursor:pointer;" accept=".hwp,.hwpx,.pdf,.xls,.xlsx,.zip,.egg,.avi,.mp4" />
								</div>
								<div class="left">
									<input id="btnRsltRptAtflCancel" type="button" class="default2" style="display:none;" value="취소"/>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th colspan="2">재무과통지일</th>
						<td class = "tl">
							<fmt:parseDate value="${ccCsrsVo.acseNtfctnYmd}" 	var="acseNtfctnYmd" pattern="yyyyMMdd" />
							<label class="blind" for="acseNtfctnYmd">재무과통지일</label>
							<input type="text" id="acseNtfctnYmd" name="acseNtfctnYmd" class="w100" title="재무과통지일을 선택하세요." placeholder="재무과통지일" value="<fmt:formatDate value="${acseNtfctnYmd}" pattern="yyyy-MM-dd" />" />
						</td>
						<th>첨부파일</th>
						<td colspan="6" style="height:30px;">
						<c:if test="${ccCsrsVo.acseNtfctnAtflId ne null and ccCsrsVo.acseNtfctnAtflId ne ''}">
							<div class="file_Download">
								<div class="link_text left">
									<a href="#none" onclick="event.stopPropagation(); g3way.sicims.common.downloadFile('${fn:escapeXml(ccCsrsVo.acseNtfctnAtflId)}');">${fn:escapeXml(ccCsrsVo.acseNtfctnAtflNm)}</a>
								</div>
							<c:if test="${ccCsrsVo.updusrId eq member.userId}">
								<div class="left"  style="margin-left:30px;">
									<input id="btnAcseNtfctnAtflDelete" type="button" value="삭제"  style="height:26px;"/>
								</div>
							</c:if>
							</div>
						</c:if>
							<div class="clear"></div>
							<div style="float:left; width:100%; ">
								<div class="left" style="width:90%;">
									<input type="file" name="acseNtfctnAtfl" title="파일을 선택하세요." onchange="ccCsrsFileSelected(this, 'btnAcseNtfctnAtflCancel');" style="border:0px; width:100%; cursor:pointer;" accept=".hwp,.hwpx,.pdf,.xls,.xlsx,.zip,.egg,.avi,.mp4" />
								</div>
								<div class="left">
									<input id="btnAcseNtfctnAtflCancel" type="button" class="default2" style="display:none;" value="취소"/>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th colspan="2">비고</th>
						<td colspan="8">
							<input type="text" name="rmrk" value="${fn:escapeXml(ccCsrsVo.rmrk)}" placeholder="비고를 입력하세요." title="비고를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99.6%;"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>

<div style="margin-top:10px;">
	<c:if test="${info.updusrId eq member.userId}">
		<div class="btnAction">
			<ul>
				<li><button id="btnCcCsrsSave" 		class="default1">저장</button></li>
				<li><button id="btnCcCsrsCancel" 	class="default2">취소</button></li>
			</ul>
		</div>
	</c:if>
</div>


<script>
//<c:if test = "${not empty maxUploadSize}">
var _maxUploadSize = "${fn:escapeXml(maxUploadSize)}";
//</c:if>

$(document).ready(function() {
	g3way.sicims.common.datePicker("vstYmd");			// 방문일자
	g3way.sicims.common.datePicker("admdspRqstYmd");	// 행정처분의뢰일자
	g3way.sicims.common.datePicker("ontcYmd");			// 공문알림일자
	g3way.sicims.common.datePicker("rsltRptYmd");		// 결과보고일자
	g3way.sicims.common.datePicker("acseNtfctnYmd");	// 재무과통보일자

	g3way.sicims.common.datePickerFormToDate('admdspBgngYmd', 'admdspEndYmd');
	//<c:if test = "${ccCsrsVo.cirsSn eq null or ccCsrsVo.cirsSn == ''}">
	    //From의 초기값을 오늘 날짜로 설정
	    //$('#admdspBgngYmd').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	    //To의 초기값을 오늘 날짜로 설정
	    //$('#admdspEndYmd').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	//</c:if>

	g3way.sicims.common.keyEvent();		// 입력 체크
	csrsDetailInvokeEvent();			// invoke event

});


function csrsDetailInvokeEvent() {
	// 건설업등록기준사전조사결과 저장
 	$("#btnCcCsrsSave").click(function() {
 		if (!g3way.sicims.common.excuteFormValidate('#frmCcCsrs')) {
 			return false;
 		}

		g3way.sicims.common.messageBox(null, "건설업등록기준사전조사결과 저장", "건설업등록기준사전조사결과를 저장하시겠습니까?", saveOrUpdateCcCsrs);
	});


 	// 건설업등록기준사전조사결과  취소
 	$("#btnCcCsrsCancel").click(function() {
		$("div#divCcCsrsDetail").hide();
	});

	// 공문알림첨부파일 삭제
	$("#btnOntcAtflDelete").click(function() {
		g3way.sicims.common.messageBox(null, "파일 삭제", "파일을 삭제 하시겠습니까?", deleteOntcAtfl);
	});
	// 공문알림첨부파일 선택 초기화
	$("#btnOntcAtflCancel").click(function() {
		g3way.sicims.common.cloneFile($("#frmCcCsrs input[name=ontcAtfl]"));
		$(this).hide();
	});


	// 결과보고첨부파일 삭제
	$("#btnRsltRptAtflDelete").click(function() {
		g3way.sicims.common.messageBox(null, "파일 삭제", "파일을 삭제 하시겠습니까?", deleteRsltRptAtfl);
	});
	// 결과보고첨부파일 선택 초기화
	$("#btnRsltRptAtflCancel").click(function() {
		g3way.sicims.common.cloneFile($("#frmCcCsrs input[name=rsltRptAtfl]"));
		$(this).hide();
	});


	// 재무과통보첨부파일 삭제
	$("#btnAcseNtfctnAtflDelete").click(function() {
		g3way.sicims.common.messageBox(null, "파일 삭제", "파일을 삭제 하시겠습니까?", deleteAcseNtfctnAtfl);
	});
	// 재무과통보첨부파일 선택 초기화
	$("#btnAcseNtfctnAtflCancel").click(function() {
		g3way.sicims.common.cloneFile($("#frmCcCsrs input[name=acseNtfctnAtfl]"));
		$(this).hide();
	});

}


//파일 확장자 제한
function ccCsrsFileSelected(obj, cancelObj){
	var uploadFile	= $(obj).val();
	var fileName 	= uploadFile.substr(uploadFile.lastIndexOf("\\")+1);
	var fileExt		= fileName.substr(fileName.lastIndexOf(".")+1).toLowerCase();
	var fileSize	= $(obj).get(0).files[0].size;

	$("#" + cancelObj).hide();
	if (fileExt != "hwp" && fileExt != "pdf" && fileExt != "xls" && fileExt != "xlsx" &&
		fileExt != "zip" && fileExt != "egg" && fileExt != "avi" && fileExt != "mp4") {
			g3way.sicims.common.cloneFile(obj);
			var messageTxt =  "hwp | pdf | xls | xlsx | zip | egg | avi | mp4 파일만 업로드 가능합니다.";
			g3way.sicims.common.messageBox(null, "파일업로드", messageTxt, null);
	} else {
		if (fileSize > Number(_maxUploadSize)) {
			g3way.sicims.common.cloneFile(obj);
			var messageTxt =  "파일 사이즈는 1GB 이내로 등록 가능합니다.";
			g3way.sicims.common.messageBox(null, "파일업로드", messageTxt, null);
		} else {
			$("#" + cancelObj).show();
		}
	}
}


//건설업등록기준사전조사결과 결과 저장
function saveOrUpdateCcCsrs () {
	$("#frmCcCsrs select:disabled").removeAttr('disabled');
	$("#frmCcCsrs input:disabled").removeAttr('disabled');


	fnLoadingStart();

	$.ajax({
		url			:	"<c:url value='/sicims300Cc/saveOrUpdateCcCsrs.do'/>",
		data		:	new FormData(document.getElementById('frmCcCsrs')),
		processData	:	false,
		contentType	:	false,
		type		:	"post",
		success 	: 	function(data, textStatus){
							fnLoadingEnd();
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "건설업등록기준사전조사결과 저장 성공", "건설업등록기준사전조사결과가 저장되었습니다.", null);

								$("div#divCcCsrsDetail").hide();
							} else {
								g3way.sicims.common.messageBox(null, "건설업등록기준사전조사결과 저장 실패", "건설업등록기준사전조사결과 저장에 실패했습니다.", null);
							}
						},
		});
}


// 공문알림첨부파일 삭제
function deleteOntcAtfl() {
	var ontcAtflId = $("#frmCcCsrs input[name=ontcAtflId]").val();
	deleteCcCsrsFile(ontcAtflId, "", "");
}

//결과보고첨부파일 삭제
function deleteRsltRptAtfl() {
	var rsltRptAtflId = $("#frmCcCsrs input[name=rsltRptAtflId]").val();
	deleteCcCsrsFile("", rsltRptAtflId, "");
}

//재무과통보첨부파일 삭제
function deleteAcseNtfctnAtfl() {
	var acseNtfctnAtflId = $("#frmCcCsrs input[name=acseNtfctnAtflId]").val();
	deleteCcCsrsFile("", "", acseNtfctnAtflId);
}


//건설업등록기준사전조사결과 파일 삭제
function deleteCcCsrsFile(ontcAtflId, rsltRptAtflId, acseNtfctnAtflId) {
	$.ajax({
		url			:	"<c:url value='/sicims300Cc/deleteCcCsrsFile.do'/>",
		data		:	{
							cirsSn			: $("#frmCcCsrs input[name=cirsSn]").val(),
							ontcAtflId		: ontcAtflId,
							rsltRptAtflId	: rsltRptAtflId,
							acseNtfctnAtflId: acseNtfctnAtflId,
						},
		dataType	:	"json",
		type		:	"POST",
		success		: 	function(data, textStatus, XMLHttpRequest){
							if (data.result > 0) {
								$("#btnResult").trigger("click");
								g3way.sicims.common.messageBox(null, "파일 삭제 성공", "파일이 삭제되었습니다.", null);
							} else {
								g3way.sicims.common.messageBox(null, "파일 삭제 실패", "파일을 삭제하는데 실패했습니다.", null);
							}
						},
	});
}


</script>
