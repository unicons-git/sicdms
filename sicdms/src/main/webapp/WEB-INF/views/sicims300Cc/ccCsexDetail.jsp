<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>

<div>
	<div>
		<div class="title2 left">자료제출 상세정보</div>
	</div>
	<div class="clear"></div>
	<!-- TIP -->
	<div class="div__tip">
		<h3 class="tip-label">TIP</h3>
		<dl class="tip-list">
			<dt><strong>여러자료를 압축해서 1개의 파일(zip)로 등록합니다.</strong></dt>
			<dt>자료는 '<strong>등록</strong>'만 가능합니다.</dt>
			<dt>(*)은 필수 입력 항목입니다.</dt>
			<dt>문의처 : 담당주무관 ${fn:escapeXml(ccCsmaVo.taskTkcgAoNm)} / 자본금담당주무관 ${fn:escapeXml(ccCsmaVo.cptlTkcgAoNm)}</dt>
		</dl>
	</div>


	<form id="frm" name="frm" method="post">
		<input type="hidden"	name="cirsSn"		value="${fn:escapeXml(info.cirsSn)}" />
		<input type="hidden"	name="exmnDataSn"	value="${fn:escapeXml(ccCsexVo.exmnDataSn)}" />
		<input type="hidden"	name="fileKd"		value="csex" />
		<input type="hidden"	name="fileNm"		value="${fn:escapeXml(ccCsexVo.fileNm)}" />
		<input type="hidden"	name="fileOrgnlNm"	value="${fn:escapeXml(ccCsexVo.fileOrgnlNm)}" />
		<input type="hidden"	name="fileExtn"		value="${fn:escapeXml(ccCsexVo.fileExtn)}" />
		<input type="hidden"	name="filePath"		value="${fn:escapeXml(ccCsexVo.filePath)}" />
		<input type="hidden"	name="fileSz"		value="${fn:escapeXml(ccCsexVo.fileSz)}" />

		<table class="sicims-info-tb">
			<caption>건설업사전자료제출 정보</caption>
			<colgroup>
				<col width="10%" /><col width="90%" />
			</colgroup>
			<thead></thead>
			<tfoot></tfoot>
			<tbody>
				<tr>
					<th>내용(*)</th>
					<td>
						<input type="text" name="exmnDataCn" value="${ccCsexVo.exmnDataCn}" class="required" placeholder="내용을 입력하세요." title="내용을 입력하세요."
							onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.6%;"/>
					</td>
				</tr>
				<tr>
					<th>비고</th>
					<td>
						<input type="text" name="rmrk" value="${ccCsexVo.rmrk}" placeholder="비고를 입력하세요." title="비고를 입력하세요."
							onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99.6%;"/>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<c:if test="${ccCsexVo.exmnDataAtflId ne null and ccCsexVo.exmnDataAtflId ne ''}">
							<div class="file_Download">
								<div class="link_text left">
									<a href="#none" onclick="event.stopPropagation(); g3way.sicims.common.downloadFile('${fn:escapeXml(ccCsexVo.exmnDataAtflId)}');"><c:out value="${fn:escapeXml(ccCsexVo.exmnDataAtflNm)}" /></a>
								</div>
							</div>
						</c:if>
						<c:if test="${ccCsexVo.exmnDataAtflId eq null or ccCsexVo.exmnDataAtflId eq ''}">
						<div id="fileControl" class="left"></div>
						<div style="float:right;margin-top:35px;margin-right:20px;">
							<input type="button" id="btnFileSelect" class="search" value="파일선택"/>
						</div>
						</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<div style="margin-top:10px;">
	<c:if test="${ccCsexVo.exmnDataSn eq null}">
		<div class="btnAction">
			<ul>
				<li><button id="btnInsert" 	class="default1">등록</button></li>
				<li><button id="btnClose" 	class="default2">닫기</button></li>
			</ul>
		</div>
	</c:if>
</div>

<script>

var control = new Object();

$(document).ready(function() {
	g3way.sicims.common.keyEvent();		// 입력 체크
	detailInvokeEvent();			// invoke event

	//<c:if test="${ccCsexVo.exmnDataAtflId eq null or ccCsexVo.exmnDataAtflId eq ''}">
	initInnorix();
	//</c:if>
});


function initInnorix() {
	// 파일전송 컨트롤 생성
	control = innorix.create({
        el			: '#fileControl', 												// 컨트롤 출력 HTML 객체 ID
		transferMode: 'both', 														// both(업로드/다운로드 혼용), upload(업로드만), download(다운로드만)
		installUrl	: "<c:url value='/resources/innorix/install/install.html'/>", 	// Agent 설치 페이지
        uploadUrl	: "<c:url value='/sicims000Cm/innorix/uploadFile.do'/>",		// 업로드 URL
        maxFileCount : 1,															// 첨부 할 수있는 최대 파일 수 설정
        autoReTransfer : false,														// 오류시 재시도 설정
        width		: '85%',
        height		: '100px',
        allowExtension : ["zip"]
    });

    // 업로드 완료 이벤트
    control.on('uploadComplete', function (p) {
        var f = p.files;

        var fileNm			= f[0].serverFileName;
        var fileOrgnlNm		= f[0].clientFileName;
        var fileExtn		= fileOrgnlNm.substr(fileOrgnlNm.lastIndexOf(".") + 1).toLowerCase();
        var serverFilePath 	= f[0].serverFilePath;
        var filePath		= serverFilePath.substr(0, serverFilePath.lastIndexOf("/") + 1);
        var fileSz			= f[0].fileSize;

        $("#frm input[name=fileKd]").val		("csex");		// 파일종류
        $("#frm input[name=fileNm]").val		(fileNm);		// 파일명
        $("#frm input[name=fileOrgnlNm]").val	(fileOrgnlNm);	// 원본파일명
        $("#frm input[name=fileExtn]").val		(fileExtn);		// 파일확장자
        $("#frm input[name=filePath]").val		(filePath);		// 파일경로
        $("#frm input[name=fileSz]").val		(fileSz);		// 파일크기

		insertCcCsex();
    });
}


function detailInvokeEvent() {
	// 파일추가
 	$("#btnFileSelect").click(function() {
 		control.openFileDialogSingle();
	});

	// 건설업등록기준사전조사자료 등록
 	$("#btnInsert").click(function() {
		g3way.sicims.common.messageBox(null, "건설업등록기준사전조사자료 등록", "건설업등록기준사전조사자료를 등록하시겠습니까?", uploadFile);
	});

	// 취소
 	$("#btnClose").click(function() {
		$("div#divDetail").hide();
	});
}

// innorix uploadFile
function uploadFile() {
	if (!g3way.sicims.common.excuteFormValidate('#frm')) {
		return false;
	}

	control.upload();
}


// 건설업사전자료제출 등록
function insertCcCsex() {
	$("#frm select:disabled").removeAttr('disabled');
	$("#frm input:disabled").removeAttr('disabled');


	fnLoadingStart();

	$("#frm").ajaxForm({
		url			:	"<c:url value='/sicims300Cc/insertCcCsex.do'/>",
		type		: 	"post",
		dataType	: 	"json",
		success 	: 	function(data, textStatus){
							fnLoadingEnd();
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "건설업등록기준사전조사자료 등록 성공", "건설업등록기준사전조사자료가 등록되었습니다.", null);
								getCcCsexList("${fn:escapeXml(info.cirsSn)}");
							} else {
								g3way.sicims.common.messageBox(null, "건설업등록기준사전조사자료 등록  실패", "건설업등록기준사전조사자료 등록에 실패했습니다.", null);
							}
						},
	}).submit();
}




// 불법하도급신고 결과 정보 삭제
function deleteCcIsrrFile() {
	$.ajax({
		url			:	"<c:url value='/sicims300Cc/deleteCcIsrrFile.do'/>",
		data		:	{
							rcptNo		: $("#frm input[name=rcptNo]").val(),
							rsltFileId	: $("#frm input[name=rsltFileId]").val(),
						},
		dataType	:	"json",
		type		:	"POST",
		success		: 	function(data, textStatus, XMLHttpRequest){
							if (data.result.status == "success") {
								g3way.sicims.common.messageBox(null, "파일 삭제 성공", "파일이 삭제되었습니다.", null);
							} else {
								g3way.sicims.common.messageBox(null, "파일 삭제 실패", "파일을 삭제하는데 실패했습니다.", null);
							}
						},
	});
}
</script>
