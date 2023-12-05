<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var = "maxUploadSize"><spring:eval expression="@properties['Global.upload.maxSize']"/></c:set>

<div>
	<div>
		<div class="title2 left">불법하도급 처리결과</div>
	</div>

	<form id="frmCcIsrr" name="frmCcIsrr" method="post" enctype="multipart/form-data">
		<input type="hidden"	name="rcptNo"		value="${fn:escapeXml(info.rcptNo)}" />
		<input type="hidden"	name="rsltFileId"	value="${fn:escapeXml(ccIsrrVo.rsltFileId)}" />

		<div style="margin-top:5px;">
			<table class="sicims-info-tb">
				<caption>불법하도급 처리결과</caption>
				<colgroup>
					<col width="8%" /><col width="9%" /><col width="7%" /><col width="8%" /><col width="11%" />
					<col width="11%" /><col width="13%" /><col width="13%" /><col width="10%" /><col width="10%" />
				</colgroup>
				<thead></thead>
				<tfoot></tfoot>
				<tbody>
					<tr>
						<th rowspan="3">불법하도급<br/>유형</th>
						<th>내용</th>
						<td colspan="8">
							<input type="text" name="ilgsctCn" value="${fn:escapeXml(ccIsrrVo.ilgsctCn)}" class="required" placeholder="내용을 입력하세요." title="내용을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'1024')" style="width:99.6%;"/>
						</td>
					</tr>
					<tr>
						<th>거래자</th>
						<td colspan="8">
							<input type="text" name="ilgsctTrdr" value="${fn:escapeXml(ccIsrrVo.ilgsctTrdr)}" class="required" placeholder="거래자를 입력하세요." title="거래자를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'1024')" style="width:99.8%;"/>
						</td>
					</tr>
					<tr>
						<th>단계</th>
						<td colspan="8">
							<input type="text" name="ilgsctStg" value="${fn:escapeXml(ccIsrrVo.ilgsctStg)}" class="required" placeholder="단계를 입력하세요." title="단계를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'1024')" style="width:99.8%;"/>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">위반업체</th>
						<td colspan="8">
							<input type="text" name="vltnBzentyNm" value="${fn:escapeXml(ccIsrrVo.vltnBzentyNm)}" class="required" placeholder="위반업체명을 입력하세요." title="위반업체명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99.8%;"/>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">발주기관</th>
						<td colspan="8">
							<input type="text" name="podrInstNm" value="${fn:escapeXml(ccIsrrVo.podrInstNm)}" class="required" placeholder="발주기관명을 입력하세요." title="발주기관명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99.8%;"/>
						</td>

					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">처분기관</th>
						<td colspan="8">
							<input type="text" name="dspsInstNm" value="${fn:escapeXml(ccIsrrVo.dspsInstNm)}" class="required" placeholder="처분기관명을 입력하세요." title="처분기관명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99.8%;"/>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">신고내용</th>
						<td colspan="8">
							<textarea  name="dclrCn" class="required" placeholder="신고내용을 입력하세요."  title="신고내용을 입력하세요."
							onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'4000')" style="width:99.9%; height:80px;resize:none;">${fn:escapeXml(ccIsrrVo.dclrCn)}</textarea>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">처리결과</th>
						<td colspan="8">
							<textarea  name="prcsRslt" class="required" placeholder="처리결과를 입력하세요."  title="처리결과를 입력하세요."
							onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'4000')" style="width:99.9%; height:80px;resize:none;">${fn:escapeXml(ccIsrrVo.prcsRslt)}</textarea>
						</td>

					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">기타사항<br/>(포상금지급 등)</th>
						<td colspan="8">
							<textarea  name="rsltRmrk" placeholder="기타사항(포상금지급 등)을 입력하세요."  title="기타사항(포상금지급 등)을 입력하세요."
							onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'4000')" style="width:99.9%; height:80px;resize:none;">${fn:escapeXml(ccIsrrVo.rsltRmrk)}</textarea>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">첨부파일</th>
						<td colspan="8" style="height:30px;">
						<c:if test="${ccIsrrVo.rsltFileId ne null and ccIsrrVo.rsltFileId ne ''}">
							<div class="file_Download">
								<div class="link_text left">
									<a href="#none" onclick="event.stopPropagation(); g3way.sicims.common.downloadFile('${fn:escapeXml(ccIsrrVo.rsltFileId)}');"><c:out value="${fn:escapeXml(ccIsrrVo.rsltFileNm)}" /></a>
								</div>
							<c:if test="${ccIsrrVo.updusrId eq member.userId}">
								<div class="left"  style="margin-left:30px;">
									<input id="btnCcIsrrFileDelete" type="button" value="삭제"  style="height:26px;"/>
								</div>
							</c:if>
							</div>
						</c:if>
							<div class="clear"></div>
							<div style="float:left; width:100%; ">
								<div class="left" style="width:90%;">
									<input type="file" name="file" title="파일을 선택하세요." onchange="ccIsrrFileSelected(this);" style="border:0px; width:100%; cursor:pointer;" accept=".hwp,.hwpx,.pdf,.xls,.xlsx,.zip,.egg,.avi,.mp4" />
								</div>
								<div class="left">
									<input id="btnCcIsrrFileCancel" type="button" class="default2" style="display:none;" value="취소"/>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="clear"></div>
		</div>
	</form>
</div>

<div style="margin-top:10px;">
	<c:set var="saveYn" value="disabled" />
	<c:if test="${info.updusrId eq member.userId}">
		<c:set var="saveYn" value="" />
	</c:if>
	<div class="btnAction">
		<ul>
			<li><button id="btnCcIsrrSave"   class="default1" ${saveYn}	  >저장</button></li>
			<li><button id="btnCcIsrrPrint"  class="default2" >출력</button></li>
			<li><button id="btnCcIsrrCancel" class="default2" >취소</button></li>
		</ul>
	</div>
</div>

<script>
var _maxUploadSize = 1073741824  // 1GB
//<c:if test = "${not empty maxUploadSize}">
_maxUploadSize = Number("${fn:escapeXml(maxUploadSize)}");
//</c:if>


$(document).ready(function() {
	g3way.sicims.common.keyEvent();		// 입력 체크
	ccIsrrDetailInvokeEvent();			// invoke event
});


function ccIsrrDetailInvokeEvent() {
	// 파일 삭제
	$("#btnCcIsrrFileDelete").click(function() {
		g3way.sicims.common.messageBox(null, "파일 삭제", "파일을 삭제 하시겠습니까?", deleteCcIsrrFile);
	});


	// 파일선택 초기화
	$("#btnCcIsrrFileCancel").click(function() {
		g3way.sicims.common.cloneFile($("#frmCcIsrr input[name=file]"));
		$(this).hide();
	});

	// 불법하도급신고 결과 저장
 	$("#btnCcIsrrSave").click(function() {
 		if (!g3way.sicims.common.excuteFormValidate('#frmCcIsrr')) {
 			return false;
 		}

		g3way.sicims.common.messageBox(null, "불법하도급신고 결과 저장", "불법하도급신고 결과를 저장하시겠습니까?", saveOrUpdateCcIsrr);
	});

	// 불법하도급신고 결과  삭제
 	$("#btnCcIsrrDelete").click(function() {
 		g3way.sicims.common.messageBox(null, "불법하도급신고 결과 삭제", "불법하도급신고 결과를 삭제하시겠습니까?", deleteccIsrr);
	});

	// 취소
 	$("#btnCcIsrrCancel").click(function() {
		$("div#divCcIsrrDetail").hide();
	});


	// 출력
	$("#btnCcIsrrPrint").click(function() {
		var reportName 	= "sicims/SICIMS300Cc_ISRR_T.mrd";
		var param	= "/rp ";
			param  += "[" + $("#frmCcIsrr input[name=rcptNo]").val() + "]"; 		// 접수번호

		var url = "<c:url value='/sicims000Cm/rdPrint.do'/>";
		url += "?reportName=" + encodeURIComponent(reportName);
		url += "&param=" 	  + encodeURIComponent(param);
		url += "&html5=Y";
		url += "&timestamp="  + new Date().getTime();

		g3way.sicims.common.openDialog(url, 800);
	});
}


//파일 확장자 제한
function ccIsrrFileSelected(obj){
	var uploadFile	= $(obj).val();
	var fileName 	= uploadFile.substr(uploadFile.lastIndexOf("\\")+1);
	var fileExt		= fileName.substr(fileName.lastIndexOf(".")+1).toLowerCase();
	var fileSize	= $(obj).get(0).files[0].size;

	$("#btnCcIsrrFileCancel").hide();
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
			$("#btnCcIsrrFileCancel").show();
		}
	}
}



// 불법하도급신고 결과 수정
function saveOrUpdateCcIsrr() {
	$("#frmCcIsrr select:disabled").removeAttr('disabled');
	$("#frmCcIsrr input:disabled").removeAttr('disabled');


	fnLoadingStart();

    $.ajax({
		url			:	"<c:url value='/sicims300Cc/saveOrUpdateCcIsrr.do'/>",
		data		:	new FormData(document.getElementById('frmCcIsrr')),
		processData	:	false,
        contentType	:	false,
		type		:	"post",
		success 	: 	function(data, textStatus){
							fnLoadingEnd();
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "불법하도급신고 결과 저장 성공", "불법하도급신고 결과가 저장되었습니다.", null);

								$("div#divCcIsrrDetail").hide();
							} else {
								g3way.sicims.common.messageBox(null, "불법하도급신고 결과 저장 실패", "불법하도급신고 결과 저장에 실패했습니다.", null);
							}
						},
		});
}




// 불법하도급신고 결과 정보 삭제
function deleteCcIsrrFile() {
	$.ajax({
		url			:	"<c:url value='/sicims300Cc/deleteCcIsrrFile.do'/>",
		data		:	{
							rcptNo		: $("#frmCcIsrr input[name=rcptNo]").val(),
							rsltFileId	: $("#frmCcIsrr input[name=rsltFileId]").val(),
						},
		dataType	:	"json",
		type		:	"POST",
		success		: 	function(data, textStatus, XMLHttpRequest){
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "파일 삭제 성공", "파일이 삭제되었습니다.", null);
							} else {
								g3way.sicims.common.messageBox(null, "파일 삭제 실패", "파일을 삭제하는데 실패했습니다.", null);
							}
						},
	});
}
</script>
