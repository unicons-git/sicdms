<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var = "maxUploadSize"><spring:eval expression="@properties['Global.upload.maxSize']"/></c:set>

<div class="sicims-info">
	<!-- TIP -->
	<div class="div__tip">
		<h3 class="tip-label">TIP</h3>
		<dl class="tip-list">
			<dt>(*)은 필수 입력 항목입니다.</dt>
			<dt>첨부파일은 <strong>hwp, .hwpx, pdf, xls, xlsx, zip, egg, avi, mp4의 형식만</strong> 업로드 가능합니다.</dt>
		</dl>
		<button id="btnTemplateDownload" class="default1"  style="position:absolute; margin-top:-40px; margin-left:760px;">신고서 양식 다운로드</button>
	</div>

	<form id="frm" name="frm" method="post" enctype="multipart/form-data">
		<input type="hidden"	name="dclrFileId"	value="${fn:escapeXml(ccIsrdVo.dclrFileId)}" />
		<div style="margin-top:5px;">
			<table class="sicims-info-tb">
				<caption>불법하도급신고 상세정보</caption>
				<colgroup>
					<col width="8%" /><col width="9%" /><col width="7%" /><col width="8%" /><col width="11%" />
					<col width="11%" /><col width="13%" /><col width="13%" /><col width="10%" /><col width="10%" />
				</colgroup>
				<thead></thead>
				<tfoot></tfoot>
				<tbody>
					<tr>
						<th colspan="2">접수번호</th>
						<td colspan="2">
							<input type="text" name="rcptNo" value="${fn:escapeXml(ccIsrdVo.rcptNo)}" class="w120" readonly style="width:99.6%;"/>
						</td>
						<th>등록기관</th>
						<td>
							<select name="regInstCd" style="width:130px;" title="등록기관 선택" <c:if test="${member.authrtCd eq 'ROLE_SGG'}">disabled</c:if> >
								<option value="">등록기관 선택</option>
							<c:forEach var="item" items="${regInstList}" varStatus="status">
								<option value="${fn:escapeXml(item.instCd)}"
									<c:if test="${ccIsrdVo.regInstCd eq item.instCd}">selected</c:if>>${fn:escapeXml(item.instNm)}
								</option>
							</c:forEach>
							</select>
						</td>
						<th>접수일(*)</th>
						<td colspan="3">
							<label class="blind" for="rcptYmd">접수일</label>
							<fmt:parseDate value="${ccIsrdVo.rcptYmd}" 	var="rcptYmd" pattern="yyyyMMdd" />
							<input type="text" id="rcptYmd" name="rcptYmd" class="required w120" title="접수일을 선택하세요." placeholder="접수일" value="<fmt:formatDate value="${rcptYmd}" pattern="yyyy-MM-dd" />" />
						</td>
					</tr>
					<tr>
						<th rowspan="3">신고자</th>
						<th>성명(*)</th>
						<td colspan="4">
							<input type="text" name="dclNm" value="${fn:escapeXml(ccIsrdVo.dclNm)}" class="required" placeholder="성명을 입력하세요." title="성명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99.6%;"/>
						</td>
						<th>생년월일(*)</th>
						<td colspan="3">
							<label class="blind" for="dclBrdt">생년월일</label>
							<fmt:parseDate value="${ccIsrdVo.dclBrdt}" 	var="dclBrdt" pattern="yyyyMMdd" />
							<input type="text" id="dclBrdt" name="dclBrdt" class="required w120" title="생년월일을 선택하세요." placeholder="생년월일" value="<fmt:formatDate value="${dclBrdt}" pattern="yyyy-MM-dd" />" />
						</td>
					</tr>
					<tr>
						<th>주소(*)</th>
						<td colspan="8">
							<input type="text" name="dclAddr" value="${fn:escapeXml(ccIsrdVo.dclAddr)}" class="required" placeholder="주소를 입력하세요." title="주소를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99.8%;"/>
						</td>
					</tr>
					<tr>
						<th>연락처(*)</th>
						<td colspan="4">
							<input type="text" name="dclTelno" value="${fn:escapeXml(ccIsrdVo.dclTelno)}"  maxLength=13 style="width:99.6%;"
							class="required isMblTelno" placeholder="숫자만 입력하세요." title="연락처를 입력하세요." />
						</td>
						<th>e-mail</th>
						<td colspan="3">
							<input type="text" name="dclEmlAddr" value="${fn:escapeXml(ccIsrdVo.dclEmlAddr)}"  maxLength=64 style="width:99.6%;"
							class="isEmail" placeholder="e-mail을 입력하세요." title="e-mail을 입력하세요." />
						</td>
					</tr>
					<tr>
						<th rowspan="4" colspan="2">피신고자</th>
						<th colspan="2">업체구분</th>
						<th colspan="2">KISCON관리번호</th>
						<th colspan="2">업체명(대표자)(*)</th>
						<th colspan="2">현장대리인(연락처)(*)</th>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left;">원도급업체</th>
						<td colspan="2">
							<input type="text" name="gnctrtBzentyKisconMngNo" value="${fn:escapeXml(ccIsrdVo.gnctrtBzentyKisconMngNo)}"
								class="isDigit left"  maxlength="6"
								placeholder="KISCON관리번호" title="원도급업체KISCON관리번호를 입력 한 후 검색하세요." style="width:80%;"/>
							<div class="outerSearch" data-kisconMngNo="gnctrtBzentyKisconMngNo" data-bzentyNm="gnctrtBzentyNm"></div>
						</td>
						<td colspan="2">
							<input type="text" name="gnctrtBzentyNm" value="${fn:escapeXml(ccIsrdVo.gnctrtBzentyNm)}" class="required" placeholder="원도급업체명을 입력하세요." title="원도급업체명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.6%;"/>
						</td>
						<td colspan="2">
							<input type="text" name="gnctrtBzentyPicTelno" value="${fn:escapeXml(ccIsrdVo.gnctrtBzentyPicTelno)}"  maxLength=13 style="width:99.6%;"
							class="required isMblTelno" placeholder="숫자만 입력하세요." title="원도급업체 현장대리인(연락처)를 입력하세요." />
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left;">불법하도급을 한 업체</th>
						<td colspan="2">
							<input type="text" name="gisbeKisconMngNo" value="${fn:escapeXml(ccIsrdVo.gisbeKisconMngNo)}"
								class="isDigit left"   maxlength="6"
								placeholder="KISCON관리번호" title="불법하도급을 한 업체KISCON관리번호를 입력 한 후 검색하세요." style="width:80%;"/>
							<div class="outerSearch" data-kisconMngNo="gisbeKisconMngNo" data-bzentyNm="gisbeNm"></div>
						</td>
						<td colspan="2">
							<input type="text" name="gisbeNm" value="${fn:escapeXml(ccIsrdVo.gisbeNm)}" class="required" placeholder="불법하도급을 한 업체명을 입력하세요." title="불법하도급을 한 업체명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.6%;"/>
						</td>
						<td colspan="2">
							<input type="text" name="gisbeTelno" value="${fn:escapeXml(ccIsrdVo.gisbeTelno)}"  maxLength=13 style="width:99.6%;"
							class="required isMblTelno" placeholder="숫자만 입력하세요." title="불법하도급을 한 업체 현장대리인(연락처)를 입력하세요." />
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left;">불법하도급을 받은 업체</th>
						<td colspan="2">
							<input type="text" name="risbeKisconMngNo" value="${fn:escapeXml(ccIsrdVo.risbeKisconMngNo)}"
								class="isDigit left"  maxlength="6"
								placeholder="KISCON관리번호" title="불법하도급을 받은 업체KISCON관리번호를 입력 한 후 검색하세요." style="width:80%;"/>
							<div class="outerSearch" data-kisconMngNo="risbeKisconMngNo" data-bzentyNm="risbeNm"></div>
						</td>
						<td colspan="2">
							<input type="text" name="risbeNm" value="${fn:escapeXml(ccIsrdVo.risbeNm)}" class="required" placeholder="불법하도급을 받은 업체명을 입력하세요." title="불법하도급을 받은 업체명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.6%;"/>
						</td>
						<td colspan="2">
							<input type="text" name="risbeTelno" value="${fn:escapeXml(ccIsrdVo.risbeTelno)}"  maxLength=13 style="width:99.6%;"
							class="required isMblTelno" placeholder="숫자만 입력하세요." title="불법하도급을 받은 업체 현장대리인(연락처)를 입력하세요." />
						</td>
					</tr>
					<tr>
						<th colspan="10">신고내용</th>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">1. 공사명(*)</th>
						<td colspan="8">
							<input type="text" name="cstrnNm" value="${fn:escapeXml(ccIsrdVo.cstrnNm)}" class="required" placeholder="공사명을 입력하세요." title="공사명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.8%;"/>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">2. 발주기관명(*)</th>
						<td colspan="8">
							<input type="text" name="podrInstNm" value="${fn:escapeXml(ccIsrdVo.podrInstNm)}" class="required" placeholder="발주기관명을 입력하세요." title="발주기관명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99.8%;"/>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">3. 공사현장주소(*)</th>
						<td colspan="8">
							<input type="text" name="cstrnSpotAddr" value="${fn:escapeXml(ccIsrdVo.cstrnSpotAddr)}" class="required" placeholder="공사현장주소를 입력하세요." title="공사현장주소를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99.8%;"/>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">4. 신고내용(*)</th>
						<td colspan="8">
							<textarea  name="dclrCn" class="required" placeholder="신고내용을 입력하세요."  title="신고내용을 입력하세요."
							onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'4000')" style="width:99.9%; height:80px;resize:none;">${fn:escapeXml(ccIsrdVo.dclrCn)}</textarea>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">증거서류(첨부)</th>
						<td colspan="8">
							<textarea  name="evdncDcmnt" placeholder="증거서류(첨부) 내용을 입력하세요."  title="증거서류(첨부) 내용을 입력하세요."
							onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'4000')" style="width:99.9%; height:80px;resize:none;">${fn:escapeXml(ccIsrdVo.evdncDcmnt)}</textarea>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">기타사항</th>
						<td colspan="8">
							<textarea  name="dclrRmrk" placeholder="기타사항을 입력하세요."  title="기타사항을 입력하세요."
							onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'4000')" style="width:99.9%; height:80px;resize:none;">${fn:escapeXml(ccIsrdVo.dclrRmrk)}</textarea>
						</td>
					</tr>
					<tr>
						<th colspan="2" style="text-align:left; padding-left:30px;">첨부파일</th>
						<td colspan="8" style="height:30px;">
						<c:if test="${ccIsrdVo.dclrFileId ne null and ccIsrdVo.dclrFileId ne ''}">
							<div class="file_Download">
								<div class="link_text left">
									<a href="#none" onclick="event.stopPropagation(); g3way.sicims.common.downloadFile('${fn:escapeXml(ccIsrdVo.dclrFileId)}');">${fn:escapeXml(ccIsrdVo.dclrFileNm)}</a>
								</div>
							<c:if test="${ccIsrdVo.updusrId eq member.userId}">
								<div class="left"  style="margin-left:30px;">
									<input id="btnFileDelete" type="button" value="삭제"  style="height:26px;"/>
								</div>
							</c:if>
							</div>
						</c:if>
							<div class="clear"></div>
							<div style="float:left; width:100%; ">
								<div class="left" style="width:90%;">
									<input type="file" name="file" title="파일을 선택하세요." onchange="fileSelected(this);" style="border:0px; width:100%; cursor:pointer;" accept=".hwp,.hwpx,.pdf,.xls,.xlsx,.zip,.egg,.avi,.mp4" />
								</div>
								<div class="left">
									<input id="btnFileCancel" type="button" class="default2" style="display:none;" value="취소"/>
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
	<c:if test="${ccIsrdVo.rcptNo eq null}">
		<div class="btnAction">
			<ul>
				<li><button id="btnInsert" 	class="default1">등록</button></li>
				<li><button id="btnClose" 	class="default2">닫기</button></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${ccIsrdVo.rcptNo ne null}">
		<c:set var="updateYn" value="disabled" />
		<c:if test="${ccIsrdVo.updusrId eq member.userId}">
			<c:set var="updateYn" value="" />
		</c:if>
		<div class="btnAction">
			<ul>
				<li><button id="btnUpdate" 	class="default1" ${updateYn}>수정</button></li>
				<li><button id="btnDelete" 	class="default3" ${updateYn}>삭제</button></li>
				<li><button id="btnPrint"  	class="default2" >출력</button></li>
				<li><button id="btnResult" 	class="default1" ${updateYn}>결과</button></li>
				<li><button id="btnClose" 	class="default2" >닫기</button></li>
			</ul>
		</div>
	</c:if>
</div>

<div class="clear"></div>
<div id="divCcIsrrDetail"></div>

<script>
var _maxUploadSize = 1073741824  // 1GB
//<c:if test = "${not empty maxUploadSize}">
_maxUploadSize = Number("${fn:escapeXml(maxUploadSize)}");
//</c:if>


$(document).ready(function() {
	g3way.sicims.common.datePicker("rcptYmd");
	g3way.sicims.common.datePicker("dclBrdt");

	//<c:if test = "${ccIsrdVo.rcptNo eq null or ccIsrdVo.rcptNo == ''}">
    //To의 초기값을 오늘 날짜로 설정
    $('#rcptYmd').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	//</c:if>

	$("#dclBrdt").datepicker("option", "yearRange", "c-100:c+0");

	g3way.sicims.common.keyEvent();		// 입력 체크
	detailInvokeEvent();				// invoke event
});


function detailInvokeEvent() {
	// 신고서양식다운로드
	$("#btnTemplateDownload").click(function() {
		g3way.sicims.common.downloadFileNm("/isrd", "불법하도급 신고서(서울특별시 불법하도급 신고포상금 지급에 관한 조례)", "hwp");
	});


	$("div.outerSearch").click(function() {
		var url = "<c:url value='/sicims110Kc/kcCcmaPopup.do'/>";
			url+= "?cncoMngNo=" 	+ $(this).parent().find("input").val();
			url+= "&kisconMngNo=" 	+ $(this).data("kisconmngno");
			url+= "&bzentyNm=" 		+ $(this).data("bzentynm");
			url+= "&timestamp=" 	+ new Date().getTime();

		g3way.sicims.common.openDialog(url, 820);
	});

	// 파일 삭제
	$("#btnFileDelete").click(function() {
		g3way.sicims.common.messageBox(null, "파일 삭제", "파일을 삭제 하시겠습니까?", deleteCcIsrdFile);
	});


	// 파일선택 초기화
	$("#btnFileCancel").click(function() {
		g3way.sicims.common.cloneFile($("#frm input[name=file]"));
		$(this).hide();
	});

	// 불법하도급신고 정보 등록
 	$("#btnInsert").click(function() {
 		if ( !g3way.sicims.common.excuteFormValidate('#frm')) {
 			return false;
 		}

		g3way.sicims.common.messageBox(null, "불법하도급신고 정보 등록", "불법하도급신고 정보를 등록하시겠습니까?", insertCcIsrd);
	});


	// 불법하도급신고 정보  수정
 	$("#btnUpdate").click(function() {
 		if (!g3way.sicims.common.excuteFormValidate('#frm')) {
 			return false;
 		}

 		g3way.sicims.common.messageBox(null, "불법하도급신고 정보 수정", "불법하도급신고 정보를 수정하시겠습니까?", updateCcIsrd);
	});


	// 불법하도급신고  삭제
 	$("#btnDelete").click(function() {
 		g3way.sicims.common.messageBox(null, "불법하도급신고 정보 삭제", "불법하도급신고 정보를 삭제하시겠습니까?", deleteCcIsrd);
	});

	// 출력
	$("#btnPrint").click(function() {
		var reportName 	= "sicims/SICIMS300Cc_ISRD_T.mrd";
		var param	= "/rp ";
			param  += "[" + $("#frm input[name=rcptNo]").val()	+ "]"; 		// 접수번호

		var url = "<c:url value='/sicims000Cm/rdPrint.do'/>";
		url += "?reportName=" + encodeURIComponent(reportName);
		url += "&param=" 	  + encodeURIComponent(param);
		url += "&html5=Y";
		url += "&timestamp="  + new Date().getTime();

		g3way.sicims.common.openDialog(url, 800);
	});

	// 닫기
 	$("#btnClose").click(function() {
		$("#tblList tbody").find(".selected").removeClass("selected");
		$("div#divDetail").hide();
	});

	// 결과
 	$("#btnResult").click(function() {
 		viewCcIsrrDetail();
	});
}


//파일 확장자 제한
function fileSelected(obj){
	var uploadFile	= $(obj).val();
	var fileName 	= uploadFile.substr(uploadFile.lastIndexOf("\\")+1);
	var fileExt		= fileName.substr(fileName.lastIndexOf(".")+1).toLowerCase();
	var fileSize	= $(obj).get(0).files[0].size;

	$("#btnFileCancel").hide();
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
			$("#btnFileCancel").show();
		}
	}
}



// 불법하도급신고 정보 등록
function insertCcIsrd() {
	$("#frm select:disabled").removeAttr('disabled');
	$("#frm input:disabled").removeAttr('disabled');

	fnLoadingStart();
    $.ajax({
		url			:	"<c:url value='/sicims300Cc/insertCcIsrd.do'/>",
		data		:	new FormData(document.getElementById('frm')),
		processData	:	false,
        contentType	:	false,
		type		:	"post",
		success 	: 	function(data){
							fnLoadingEnd();
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "불법하도급신고 정보 등록 성공", "불법하도급신고 정보가 등록되었습니다.", null);
								selectCcIsrdList(1);
							} else {
								g3way.sicims.common.messageBox(null, "불법하도급신고 정보 등록 실패", "불법하도급신고 정보 등록에 실패했습니다.", null);
							}
						},
		});
}


// 불법하도급신고 정보 수정
function updateCcIsrd() {
	$("#frm select:disabled").removeAttr('disabled');
	$("#frm input:disabled").removeAttr('disabled');

	fnLoadingStart();

    $.ajax({
		url			:	"<c:url value='/sicims300Cc/updateCcIsrd.do'/>",
		data		:	new FormData(document.getElementById('frm')),
		processData	:	false,
        contentType	:	false,
		type		:	"post",
		success 	: 	function(data, textStatus){
							fnLoadingEnd();
							if (data.result > 0) {
								var item = data.ccIsrdVo;
								var rcptNo			= g3way.sicims.common.maskFormat(item.rcptNo);			// 접수번호
								var regInstNm		= g3way.sicims.common.maskFormat(item.regInstNm);		// 등록기관명
								var dclNm			= g3way.sicims.common.maskFormat(item.dclNm);			// 신고자명
								var dclTelno		= g3way.sicims.common.maskFormat(item.dclTelno);		// 연락처
								var dclrYmd 		= g3way.sicims.common.maskFormat(item.dclrYmd, 'DATE');	// 신고일자
								var gnctrtBzentyNm	= g3way.sicims.common.dateToString(item.dclrCn );		// 원도급업체명
								var gisbeNm			= g3way.sicims.common.dateToString(item.gisbeNm );		// 불법하도급지급업체명
								var risbeNm			= g3way.sicims.common.dateToString(item.risbeNm );		// 불법하도급수령업체명

								g3way.sicims.common.messageBox(null, "불법하도급신고 정보 수정 성공", "불법하도급신고 정보가 수정되었습니다.", null);
								$("#tblList tbody").find(".selected").find("td:eq(1)").html(regInstNm);
								$("#tblList tbody").find(".selected").find("td:eq(3)").html(dclNm);
								$("#tblList tbody").find(".selected").find("td:eq(4)").html(dclTelno);
								$("#tblList tbody").find(".selected").find("td:eq(5)").html(dclrYmd);
								$("#tblList tbody").find(".selected").find("td:eq(6) div").html(gnctrtBzentyNm).attr("title", gnctrtBzentyNm);
								$("#tblList tbody").find(".selected").find("td:eq(7) div").html(gisbeNm).attr("title", gisbeNm);
								$("#tblList tbody").find(".selected").find("td:eq(8) div").html(risbeNm).attr("title", risbeNm);

								$("#tblList tbody").find(".selected").trigger("click");
							} else {
								g3way.sicims.common.messageBox(null, "불법하도급신고 정보 수정 실패", "불법하도급신고 정보 수정에 실패했습니다.", null);
							}
						},
		});
}




// 불법하도급신고 정보 삭제
function deleteCcIsrd() {
	$.ajax({
		url			:	"<c:url value='/sicims300Cc/deleteCcIsrd.do'/>",
		data		:	{
							rcptNo		: $("#frm input[name=rcptNo]").val(),
							dclrFileId	: $("#frm input[name=dclrFileId]").val(),
						},
		dataType	:	"json",
		type		:	"POST",
		success		: 	function(data, textStatus, XMLHttpRequest){
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "불법하도급신고 정보 삭제 성공", "불법하도급신고 정보가 삭제되었습니다.", null);
								searchPage(1);
							} else {
								g3way.sicims.common.messageBox(null, "불법하도급신고 정보 삭제 실패", "불법하도급신고 정보를 삭제하는데 실패했습니다.", null);
							}
						},
	});
}


// 불법하도급신고 파일 정보 삭제
function deleteCcIsrdFile() {
	$.ajax({
		url			:	"<c:url value='/sicims300Cc/deleteCcIsrdFile.do'/>",
		data		:	{
							rcptNo		: $("#frm input[name=rcptNo]").val(),
							dclrFileId	: $("#frm input[name=dclrFileId]").val(),
						},
		dataType	:	"json",
		type		:	"POST",
		success		: 	function(data, textStatus, XMLHttpRequest){
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "파일 삭제 성공", "파일이 삭제되었습니다.", null);

								$("#tblList tbody").find(".selected").trigger("click");
							} else {
								g3way.sicims.common.messageBox(null, "파일 삭제 실패", "파일을 삭제하는데 실패했습니다.", null);
							}
						},
	});
}



//불법하도급신고  결과 상세 정보 조회
function viewCcIsrrDetail() {
	var url = "<c:url value='/sicims300Cc/ccIsrrDetail.do'/>";
	url += "?rcptNo=" 	 + $("input[name=rcptNo]").val();
	url += "&updusrId=${fn:escapeXml(ccIsrdVo.updusrId)}";
	url += "&timestamp=" + new Date().getTime();

	$("#divCcIsrrDetail").show().load(url);
}
</script>
