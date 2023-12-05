<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="sicims-info">
	<!-- TIP -->
	<div class="div__tip">
		<h3 class="tip-label">TIP</h3>
		<dl class="tip-list">
			<dt>(*)은 필수 입력 항목입니다.</dt>
			<dt>공고번호 조회를 통해서 공고명, 공고일, 개찰일, 예산금액, 발주기관 정보를 불러올수 있습니다.(만일, 공고정보 조회가 안되면 수기 입력하세요)</dt>
			<dt>낙찰정보 갱신 기능을 통해서 낙찰정보가 조회되면,  조사업체 정보들이 초기값으로 자동 입력됩니다.(조사업체명, 대표자, 사업자번호, 주소)</dt>
			<dt>낙찰정보는 전일 수집자료입니다. 개찰일 당일 정보는 나라장터(g2b) 에서 직접 확인 입력해야합니다.</dt>
		</dl>
	</div>

	<form id="frm" method="post">
		<input type="hidden"	name="cirsSn"		value="${fn:escapeXml(ccCsmaVo.cirsSn)}" />
		<input type="hidden"	name="picChgYn"		value="N" />

		<div style="margin-top:5px;">
			<table class="sicims-info-tb">
				<caption>건설업등록기준사전조사 상세정보</caption>
				<colgroup>
					<col width="10%" />
					<col width="20%" />
					<col width="12%" />
					<col width="16%" />
					<col width="12%" />
					<col width="*" />
				</colgroup>
				<thead></thead>
				<tfoot></tfoot>
				<tbody>
					<tr>
						<th>등록기관(*)</th>
						<td>
							<select name="regInstCd" class="required w150" title="등록기관을 선택바랍니다." <c:if test="${member.authrtCd eq 'ROLE_SGG'}">disabled</c:if>>
								<option value="">등록기관 선택</option>
							<c:forEach var="item" items="${regInstList}" varStatus="status">
								<option value="${fn:escapeXml(item.instCd)}" <c:if test="${ccCsmaVo.regInstCd eq item.instCd}">selected</c:if>>${fn:escapeXml(item.instNm)}</option>
							</c:forEach>
							</select>
						</td>
						<th>공고번호</th>
						<td>
							<input type="text" name="pbancNo" value="${fn:escapeXml(ccCsmaVo.pbancNo)}" class="innerSearch isDigit" maxlength="13" readonly placeholder="공고번호를 검색하세요." title="공고번호를 검색하세요."
								 onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'13')" style="width:99.6%;"/>
						</td>
						<th>공사명(*)</th>
						<td>
							<input type="text" name="cstrnNm" value="${fn:escapeXml(ccCsmaVo.cstrnNm)}" class="required" placeholder="공사명을 입력하세요." title="공사명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.6%;"/>
						</td>
					</tr>
					<tr>
						<th>공고일(*)</th>
						<td class = "tl">
							<fmt:parseDate value="${ccCsmaVo.pbancYmd}" 	var="pbancYmd" pattern="yyyyMMdd" />
							<label class="blind" for="pbancYmd">공고일</label>
							<input type="text" id="pbancYmd" name="pbancYmd" class="required w100" title="공고일을 선택하세요." placeholder="공고일" value="<fmt:formatDate value="${pbancYmd}" pattern="yyyy-MM-dd" />" />
						</td>
						<th>개찰일(*)</th>
						<td class = "tl">
							<fmt:parseDate value="${ccCsmaVo.opnbdYmd}" 	var="opnbdYmd" pattern="yyyyMMdd" />
							<label class="blind" for="opnbdYmd">개찰일</label>
							<input type="text" id="opnbdYmd" name="opnbdYmd" class="required w100" title="개찰일을 선택하세요." placeholder="개찰일" value="<fmt:formatDate value="${opnbdYmd}" pattern="yyyy-MM-dd" />" />
						</td>
						<th>공사금액(원)(*)</th>
						<td>
							<input type="text" name="cstrnAmt" value="<fmt:formatNumber value="${ccCsmaVo.cstrnAmt}" pattern="#,###" />"
								class="required isNum" style="width:99.6%;"	placeholder="공사금액(원)를 입력하세요." maxlength="25" title="공사금액(원)을 입력하세요." />
						</td>
					</tr>
					<tr>
						<th>발주기관(*)</th>
						<td>
							<input type="text" name="podrInstNm" value="${fn:escapeXml(ccCsmaVo.podrInstNm)}" class="required" placeholder="발주기관을 입력하세요." title="발주기관을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99.6%;"/>
						</td>
						<th>입찰참가조건</th>
						<td>
							<input type="text" name="bidPrtcptCndtn" value="${fn:escapeXml(ccCsmaVo.bidPrtcptCndtn)}" placeholder="입찰참가조건을 입력하세요." title="입찰참가조건을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99.6%;"/>
						</td>
						<th>입찰참가업체수</th>
						<td>
							<input type="text" name="bidPrtcptBzentyCnt" value="<fmt:formatNumber value="${kcCwgcVo.bidPrtcptBzentyCnt}" pattern="###" />"
								class="isNum" style="width:99.6%;"	placeholder="입찰참가업체수를 입력하세요." maxlength="3" title="입찰참가업체수를 입력하세요." />
						</td>
					</tr>
					<tr>
						<th>낙찰1순위(*)</th>
						<td colspan="5">
							<input type="text" name="qfexmn1rnkBzentyNm" value="${fn:escapeXml(ccCsmaVo.qfexmn1rnkBzentyNm)}" placeholder="낙찰1순위를 입력하세요." title="낙찰1순위를 입력하세요."
									class="required"  onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:212px;"/>
							<input type="button" id="btnBzentySearch" value="낙찰정보갱신" style="width:120px;" title="낙찰정보갱신" <c:if test="${ccCsmaVo.pbancNo eq null or ccCsmaVo.pbancNo eq ''}">disabled</c:if> />
						</td>
					</tr>
					<tr>
						<th>조사업체구분(*)</th>
						<td>
							<select name="exmnBzentySe" class="required w150" title="조사업체구분을 선택바랍니다.">
								<option value="">조사업체구분 선택</option>
							<c:forEach var="item" items="${exmnBzentySeList}" varStatus="status">
								<option value="${fn:escapeXml(item.cmcdCd)}" <c:if test="${ccCsmaVo.exmnBzentySe eq item.cmcdCd}">selected</c:if>>${fn:escapeXml(item.cmcdNm)}</option>
							</c:forEach>
							</select>
						</td>
						<th>조사업체구분명(*)</th>
						<td>
							<input type="text" name="exmnBzentySeNm" value="${fn:escapeXml(ccCsmaVo.exmnBzentySeNm)}" maxlength="10" placeholder="조사업체구분명을 입력하세요." title="조사업체구분명을 입력하세요."
								class="required" onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'10')" style="width:99.6%;"/>
						</td>
						<th>조사업체명(*)</th>
						<td>
							<input type="text" name="exmnBzentyNm" value="${fn:escapeXml(ccCsmaVo.exmnBzentyNm)}" class="required" placeholder="조사업체명을 입력하세요." title="조사업체명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99.6%;"/>
						</td>
					</tr>
					<tr>
						<th>대표자(*)</th>
						<td>
							<input type="text" name="exmnBzentyRprsvNm" value="${fn:escapeXml(ccCsmaVo.exmnBzentyRprsvNm)}" placeholder="대표자를 입력하세요." title="대표자를 입력하세요."
								class="required" onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99.6%;"/>
						</td>
						<th>전문업체구분</th>
						<td>
							<select name="spcoSe" class="required w150" title="전문업체구분을 선택바랍니다.">
								<option value="">전문업체구분 선택</option>
							<c:forEach var="item" items="${spcoSeList}" varStatus="status">
								<option value="${fn:escapeXml(item.cmcdCd)}" <c:if test="${ccCsmaVo.spcoSe eq item.cmcdCd}">selected</c:if>>${fn:escapeXml(item.cmcdNm)}</option>
							</c:forEach>
							</select>
						</td>
						<th>법인등록번호(*)</th>
						<td>
							<input type="text" name="exmnBzentyCrno" value="<c:choose><c:when test="${fn:length(ccCsmaVo.exmnBzentyCrno) == 13}">${fn:substring(ccCsmaVo.exmnBzentyCrno, 0, 6)}-${fn:substring(ccCsmaVo.exmnBzentyCrno, 6, 13)}</c:when><c:otherwise>${fn:escapeXml(ccCsmaVo.exmnBzentyCrno)}</c:otherwise></c:choose>"
								class="required isCrno" style="width:60%;" placeholder="숫자만 입력하세요." title="법인등록번호 숫자만 입력하세요."/>
							<input type="button" id="btnBzentyCrnoSearch" value="KISCON정보" title="KISCON정보 조회" <c:if test="${ccCsmaVo.exmnBzentyCrno eq null or ccCsmaVo.exmnBzentyCrno eq ''}">disabled</c:if>/>
						</td>
					</tr>
					<tr>
						<th>업체주소(*)</th>
						<td colspan="5">
							<input type="text" name="exmnBzentyAddr" value="${fn:escapeXml(ccCsmaVo.exmnBzentyAddr)}" placeholder="업체주소를 입력하세요." title="업체주소를 입력하세요."
								class="required" onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.9%;"/>
						</td>
					</tr>
					<tr>
						<th>주력분야(*)</th>
						<td>
							<select name="mfldCd" class="required w150" title="주력분야를 선택바랍니다.">
								<option value="">주력분야 선택</option>
							<c:forEach var="item" items="${mfldCdList}" varStatus="status">
								<option value="${fn:escapeXml(item.cmcdCd)}" <c:if test="${ccCsmaVo.mfldCd eq item.cmcdCd}">selected</c:if>>${fn:escapeXml(item.cmcdNm)}</option>
							</c:forEach>
							</select>
						</td>
						<th>보유면허(*)</th>
						<td>
							<input type="text" name="pssnLcns" value="${fn:escapeXml(ccCsmaVo.pssnLcns)}" placeholder="보유면허를 입력하세요." title="보유면허를 입력하세요."
								class="required" onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.6%;"/>
						</td>
						<th>상호시장진출구분(*)</th>
						<td>
							<select name="mmadvnSe" class="required w180" title="상호진출(허용/불허)를 선택바랍니다.">
								<option value="">상호시장진출구분 선택</option>
							<c:forEach var="item" items="${mmadvnSeList}" varStatus="status">
								<option value="${fn:escapeXml(item.cmcdCd)}" <c:if test="${ccCsmaVo.mmadvnSe eq item.cmcdCd}">selected</c:if>>${fn:escapeXml(item.cmcdNm)}</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>담당주무관(*)</th>
						<td>
							<input type="text" name="taskTkcgAoNm" value="${fn:escapeXml(ccCsmaVo.taskTkcgAoNm)}" class="required innerSearch"  readonly placeholder="담당주무관을 검색하세요." title="담당주무관을 검색하세요."
								 onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99%;"/>
						</td>
						<th>재무과공사담당자(*)</th>
						<td>
							<input type="text" name="acseCstrnPicNm" value="${fn:escapeXml(ccCsmaVo.acseCstrnPicNm)}" class="required"  placeholder="재무과공사담당자를 입력하세요." title="재무과공사담당자를 입력하세요."
								 onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99%;"/>
						</td>
						<th>자본금담당주무관(*)</th>
						<td>
							<input type="text" name="cptlTkcgAoNm" value="${fn:escapeXml(ccCsmaVo.cptlTkcgAoNm)}" class="required"  placeholder="자본금담당주무관을 입력하세요." title="자본금담당주무관을 입력하세요."
								 onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99.6%;"/>
						</td>
					</tr>
					<tr>
						<th>업체담당자명(*)</th>
						<td>
							<input type="text" name="exmnBzentyPicNm" value="${fn:escapeXml(ccCsmaVo.exmnBzentyPicNm)}" class="required"  placeholder="업체담당자명을 입력하세요." title="업체담당자명을 입력하세요."
								 onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99%;"/>
						</td>
						<th>업체담당자연락처(*)</th>
						<td>
							<input type="text" name="exmnBzentyPicTelno" value="${fn:escapeXml(ccCsmaVo.exmnBzentyPicTelno)}"  maxLength=13 style="width:99.6%;"
							class="required isMblTelno" placeholder="숫자만 입력하세요." title="업체담당자연락처를 입력하세요." />
						</td>
						<th>담당자ID(*)</th>
						<td>
							<input type="text" name="exmnBzentyPicEmlAddr" value="${fn:escapeXml(ccCsmaVo.exmnBzentyPicEmlAddr)}"  maxLength=128 style="width:60%;"
							class="required isEmail" placeholder="이메일 주소를 입력하세요." title="이메일 주소를 입력하세요." />
						<c:if test="${ccCsmaVo.cirsSn ne null and ccCsmaVo.updusrId eq member.userId}">
							<input type="button" id="btnResetPswd" value="비밀번호초기화" title="비밀번호초기화"/>
						</c:if>
						</td>
					</tr>
					<tr>
						<th>자료제출기간(*)</th>
						<td colspan="5" class="tl">
							<fmt:parseDate value="${ccCsmaVo.sbmsnBgngYmd}" var="sbmsnBgngYmd" pattern="yyyyMMdd" />
							<fmt:parseDate value="${ccCsmaVo.sbmsnEndYmd}" 	var="sbmsnEndYmd"  pattern="yyyyMMdd" />
							<label class="blind" for="sbmsnBgngYmd">제출시작일자</label>
							<input type="text" id="sbmsnBgngYmd" name="sbmsnBgngYmd" class="required w100" title="제출시작일자를 선택하세요." placeholder="제출시작일자" value="<fmt:formatDate value="${sbmsnBgngYmd}" pattern="yyyy-MM-dd" />"/>
							&nbsp;~&nbsp;
							<label class="blind" for="sbmsnEndYmd">제출종료일자</label>
							<input type="text" id="sbmsnEndYmd" name="sbmsnEndYmd" class="required w100" title="제출종료일자를 선택하세요." placeholder="제출종료일자" value="<fmt:formatDate value="${sbmsnEndYmd}" pattern="yyyy-MM-dd" />">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>

<div style="margin-top:10px;">
	<c:if test="${ccCsmaVo.cirsSn eq null}">
		<div class="btnAction">
			<ul>
				<li><button id="btnInsert" 	class="default1">등록</button></li>
				<li><button id="btnClose" 	class="default2">닫기</button></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${ccCsmaVo.cirsSn ne null}">
		<c:set var="updateYn" value="disabled" />
		<c:if test="${ccCsmaVo.updusrId eq member.userId}">
			<c:set var="updateYn" value="" />
		</c:if>
		<div class="btnAction">
			<ul>
				<li><button id="btnUpdate" 	class="default1" ${updateYn}>수정</button></li>
				<li><button id="btnDelete" 	class="default3" ${updateYn}>삭제</button></li>
				<li><button id="btnClose" 	class="default2" >닫기</button></li>
				<li><button id="btnResult" 	class="default1">결과</button></li>
			</ul>
		</div>
	</c:if>
</div>

<div>
	<div style="margin-top:10px;">
		<div class="title2 left">자료제출 이력</div>
	</div>
	<div>
		<table id="tblCcCsexList" class="sicims-list-tb">
			<caption>자료제출 목록</caption>
			<thead>
				<tr>
					<th style="width:60px;">번호</th>
					<th style="width:100px;">자료제출일</th>
					<th style="width:283px;">내용</th>
					<th style="width:283px;">파일명</th>
					<th style="width:223px;">비고</th>
					<th>첨부파일</th>
				</tr>
			</thead>
			<tfoot class="blind"></tfoot>
			<tbody style="overflow-x:hidden; overflow-y:auto;">
		<c:choose>
			<c:when test="${fn:length(ccCsexList) == 0}">
				<tr class="nohover">
					<td colspan="6" class="tc" style="color:#999999">검색된 정보가 없습니다.</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="item" items="${ccCsexList}" varStatus="status">
					<tr class='nohover'>
						<fmt:parseDate value="${fn:escapeXml(item.exmnDataSbmsnYmd)}" 	var="exmnDataSbmsnYmd" 	pattern="yyyyMMdd"/>
						<td class="tc" style="width:60px;">${fn:escapeXml(status.index + 1)}</td>
						<td class="tc" style="width:100px;"><fmt:formatDate value="${exmnDataSbmsnYmd}" 	pattern="yyyy-MM-dd"/></td>
						<td class="tc" style="width:283px;"><div class="nowrap" style="width:270px;" title="${fn:escapeXml(item.exmnDataCn)}">${fn:escapeXml(item.exmnDataCn)}</div> </td>
						<td class="tc" style="width:283px;"><div class="nowrap" style="width:270px;" title="${fn:escapeXml(item.exmnDataAtflNm)}">${fn:escapeXml(item.exmnDataAtflNm)}</div> </td>
						<td class="tc" style="width:223px;"><div class="nowrap" style="width:210px;" title="${fn:escapeXml(item.rmrk)}">${fn:escapeXml(item.rmrk)}</div> </td>
						<td class='tc' style="width:90px;">
							<a href="#none" onclick="event.stopPropagation(); g3way.sicims.common.downloadFile(${fn:escapeXml(item.exmnDataAtflId)});">
								<img title="첨부파일"	src="<c:url value='/resources/images/sub/ico_Attach.png'/>">
							</a>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
			</tbody>
		</table>
	</div>
</div>

<div id="divCcCsrsDetail"></div>


<script>

$(document).ready(function() {
	g3way.sicims.common.datePicker("pbancYmd");
	g3way.sicims.common.datePicker("opnbdYmd");

	g3way.sicims.common.datePickerFormToDate('sbmsnBgngYmd', 'sbmsnEndYmd');
	//<c:if test = "${ccCsmaVo.cirsSn eq null or ccCsmaVo.cirsSn == ''}">
	    //From의 초기값을 1년 전으로 설정
	    $('#sbmsnBgngYmd').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	    //To의 초기값을 오늘 날짜로 설정
	    $('#sbmsnEndYmd').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	//</c:if>

	//<c:if test="${fn:length(ccCsexList) > 0}">
	$("#tblCcCsexList thead").css("display", "inherit");
	$("#tblCcCsexList tbody").height(281).css("display", "inline-block");
	if ($("#tblCcCsexList tbody").hasScrollBar()) {
		$("#tblCcCsexList tbody tr").find("td:last").css("width", "74px");
	} else {
		$("#tblCcCsexList tbody").css("height", "auto");
		$("#tblCcCsexList tbody tr").find("td:last").css("width", "90px");
	}
	//</c:if>

	g3way.sicims.common.keyEvent();		// 입력 체크
	detailInvokeEvent();			// invoke event

});


function detailInvokeEvent() {
	// 공고번호호 입찰공고 정보 조회
	$("input[name=pbancNo]").click(function() {
		var url = "<c:url value='/sicims120Gb/gbBdntPopup.do'/>";
		g3way.sicims.common.openDialog(url, 900);
	});

	// 공고번호에 따른 낙찰자1순위 정보 조회
	$("input[name=pbancNo]").change(function() {
		if ($(this).val() == "") {
			$("#btnBzentySearch").attr("disabled", true);
		} else {
			$("#btnBzentySearch").attr("disabled", false);
		}
	});

	// 조사업체 구분이 1순위이면 조사업체 구분명에 1순위, 기타면 직접 입력
	$("select[name=exmnBzentySe]").change(function() {
		if ($(this).val() == "0001") {
			$("input[name=exmnBzentySeNm]").val($("option:selected", this).text()).attr("disabled", true);
		} else {
			$("input[name=exmnBzentySeNm]").attr("disabled", false);
		}
	});

	// 낙찰정보 조회
	$("#btnBzentySearch").click(function() {
		$.ajax({
			url			: 	"<c:url value='/sicims120Gb/getGbScbdInfo.do'/>",
			data		:	{
								pbancNo	: $("input[name=pbancNo]").val(),			// 입찰공고번호
							},
			dataType	:	"json",
			type		:	"post",
			success		:	function(data, textStatus){
								if (data.result.bidPbancNo != null && data.result.bidPbancNo != "") {
									$("input[name=qfexmn1rnkBzentyNm]").val(data.result.lastScsbdBzentyNm);

									var cncoNm		= g3way.sicims.common.maskFormat(data.result.cncoNm);				// 건설업체명
									var cncoCrno	= g3way.sicims.common.maskFormat(data.result.cncoCrno, 'CRNO');		// 건설업체법인등록번호
									var cncoRprsvNm	= g3way.sicims.common.maskFormat(data.result.cncoRprsvNm);			// 건설업체대표자명
									var cncoLctnAddr= g3way.sicims.common.maskFormat(data.result.cncoLctnAddr);			// 건설업체소재지주소
									var spcoSe		= g3way.sicims.common.maskFormat(data.result.spcoSe);				// 전문업체구분

									if (cncoCrno != null && cncoCrno.cncoBrno != "") {
										$("select[name=exmnBzentySe]").val("0001").trigger("change");;
										$("input[name=exmnBzentyNm]").val(cncoNm);
										$("input[name=exmnBzentyRprsvNm]").val(cncoRprsvNm);
										$("select[name=spcoSe]").val(spcoSe);
										$("input[name=exmnBzentyCrno]").val(cncoCrno).trigger("keyup");
										$("input[name=exmnBzentyAddr]").val(cncoLctnAddr);
									}
									g3way.sicims.common.messageBox(null, "낙찰정보 조회", "낙찰정보가 조회되었습니다.", null);
								} else {
									g3way.sicims.common.messageBox(null, "낙찰정보 조회", "해당 입찰공고에 대한 낙찰정보가 없습니다.", null);
								}
							}
		});
	});

	// 법인등록번호에 따른 KISCON 계약정보 조회
	$("input[name=exmnBzentyCrno]").keyup(function() {
		if ($(this).val().length >= 13) {
			$("#btnBzentyCrnoSearch").attr("disabled", false);
		} else {
			$("#btnBzentyCrnoSearch").attr("disabled", true);
		}
	});

	// 법인등록번호에 따른 KISCON 계약정보 조회
	$("#btnBzentyCrnoSearch").click(function() {
		var url = "<c:url value='/sicims110Kc/kcCcmaInfoPopup.do'/>";
		url += "?cncoCrno=" + g3way.sicims.common.removeDelimChar($("input[name=exmnBzentyCrno]").val());
		url += "&timestamp=" + new Date().getTime();

		g3way.sicims.common.openDialog(url, 1000);
	});


	// 담당주무관 정보 조회
	$("input[name=taskTkcgAoNm]").click(function() {
		var url = "<c:url value='/sicims910User/cmUserPopup.do'/>";
		url += "?cbObjNm=taskTkcgAoNm";
		url += "&timestamp=" + new Date().getTime();

		g3way.sicims.common.openDialog(url, 600);
	});

	// 비밀번호 초기화
	$("#btnResetPswd").click(function() {
		g3way.sicims.common.messageBox(null, "비밀번호 초기화", "비밀번호를 초기화 하시겠습니까?",
 			function() {
 	 		$("#frm").ajaxForm({
 	 			type		: 	"post",
 	 			dataType	: 	"json",
 	 			url			:	"<c:url value='/sicims300Cc/resetPswd.do'/>",
 	 			beforeSend	:	function(XMLHttpRequest) { XMLHttpRequest.setRequestHeader("AJAX", "Yes");},
 	 			success 	: 	function(data, textStatus){
 	 								if (data.result > 0) {
 	 									g3way.sicims.common.messageBox(null, "비밀번호 초기화 성공", "비밀번호가 초기화되었습니다.", null);
 	 								} else {
 	 									g3way.sicims.common.messageBox(null, "비밀번호 초기화 실패", "비밀번호 초기화에 실패했습니다.", null);
 	 								}
 	 							},
 	 			}).submit();
 			}
 		);
	});


	// 건설업등록기준사전조사 등록
 	$("#btnInsert").click(function() {
 		if ( !g3way.sicims.common.excuteFormValidate('#frm')) {
 			return false;
 		}

		g3way.sicims.common.messageBox(null, "건설업등록기준사전조사 등록", "건설업등록기준사전조사 정보를 등록하시겠습니까?", insertCcCsma);
	});


	// 건설업등록기준사전조사  수정
 	$("#btnUpdate").click(function() {
 		if (!g3way.sicims.common.excuteFormValidate('#frm')) {
 			return false;
 		}

 		g3way.sicims.common.messageBox(null, "건설업등록기준사전조사 수정", "건설업등록기준사전조사 정보를 수정하시겠습니까?", updateCcCsma);
	});


	// 건설업등록기준사전조사  삭제
 	$("#btnDelete").click(function() {
 		g3way.sicims.common.messageBox(null, "건설업등록기준사전조사 삭제", "건설업등록기준사전조사 정보를 삭제하시겠습니까?", deleteCcCsma);
	});


	// 닫기
 	$("#btnClose").click(function() {
		$("#tblList tbody").find(".selected").removeClass("selected");
		$("div#divDetail").hide();
	});

	// 건설업등록기준사전조사결과
 	$("#btnResult").click(function() {
 		var url = "<c:url value='/sicims300Cc/ccCsrsDetail.do'/>";
 		url += "?cirsSn=" + $("#frm input[name=cirsSn]").val();
 		url += "&updusrId=${fn:escapeXml(ccCsmaVo.updusrId)}";
 		url += "&timestamp=" + new Date().getTime();

 		$("#divCcCsrsDetail").show().load(url);
	});

}



//건설업등록기준사전조사 등록
function insertCcCsma() {
	$("#frm select:disabled").removeAttr('disabled');
	$("#frm input:disabled").removeAttr('disabled');

	fnLoadingStart();
	$("#frm").ajaxForm({
		url			:	"<c:url value='/sicims300Cc/insertCcCsma.do'/>",
		type		: 	"post",
		dataType	: 	"json",
		success 	: 	function(data){
							fnLoadingEnd();
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "건설업등록기준사전조사 등록 성공", "건설업등록기준사전조사 정보가 등록되었습니다.", null);
								selectCcCsmaList(1);
							} else {
								g3way.sicims.common.messageBox(null, "건설업등록기준사전조사 등록 실패", "건설업등록기준사전조사 정보 등록에 실패했습니다.", null);
							}
						},
		}).submit();
}


//건설업등록기준사전조사 수정
function updateCcCsma() {
	if ($("#frm input[name=exmnBzentyPicEmlAddr]").val() == "${fn:escapeXml(ccIsrdVo.exmnBzentyPicEmlAddr)}") {
		$("#frm input[name=picChgYn]").val("N");
	} else {
		$("#frm input[name=picChgYn]").val("Y");
	}

	$("#frm select:disabled").removeAttr('disabled');
	$("#frm input:disabled").removeAttr('disabled');

	$("#frm").ajaxForm({
		type		: 	"post",
		dataType	: 	"json",
		url			:	"<c:url value='/sicims300Cc/updateCcCsma.do'/>",
		beforeSend	:	function(XMLHttpRequest) { XMLHttpRequest.setRequestHeader("AJAX", "Yes");},
		success 	: 	function(data, textStatus){
							if (data.result > 0) {
								//var item = data.ccCsmaVo;

								var cstrnAmt = g3way.sicims.common.strToInt($("input[name=cstrnAmt]").val());	// 공사금액(원)
								cstrnAmt = cstrnAmt / 100000000;												// 공사금액(억원)
								cstrnAmt = g3way.sicims.common.maskFormat(cstrnAmt, 'NUMBER', 2);				// 공사금액(억원)

								g3way.sicims.common.messageBox(null, "건설업등록기준사전조사 수정 성공", "건설업등록기준사전조사 정보가 수정되었습니다.", null);

								$("#tblList tbody").find(".selected").find("td:eq(1)").html($("select[name=regInstCd] option:selected").text());
								$("#tblList tbody").find(".selected").find("td:eq(2)").html($("input[name=pbancYmd]").val());
								$("#tblList tbody").find(".selected").find("td:eq(3)").html($("input[name=opnbdYmd]").val());
								$("#tblList tbody").find(".selected").find("td:eq(4)").html($("select[name=mfldCd] option:selected").text());
								$("#tblList tbody").find(".selected").find("td:eq(5) div").html($("input[name=cstrnNm]").val()).attr("title", $("input[name=cstrnNm]").val());
								$("#tblList tbody").find(".selected").find("td:eq(6) div").html($("input[name=podrInstNm]").val()).attr("title", $("input[name=podrInstNm]").val());
								$("#tblList tbody").find(".selected").find("td:eq(7)").html(cstrnAmt);
								$("#tblList tbody").find(".selected").find("td:eq(8)").html($("input[name=exmnBzentySeNm]").val());

								$("#tblList tbody").find(".selected").trigger("click");
							} else {
								g3way.sicims.common.messageBox(null, "건설업등록기준사전조사 수정 실패", "건설업등록기준사전조사 정보 수정에 실패했습니다.", null);
							}
						},
		}).submit();
}




//건설업등록기준사전조사 삭제
function deleteCcCsma() {
	$.ajax({
		url			:	"<c:url value='/sicims300Cc/deleteCcCsma.do'/>",
		data		:	{
							cirsSn	: $("#frm input[name=cirsSn]").val(),
						},
		dataType	:	"json",
		type		:	"POST",
		success		: 	function(data, textStatus, XMLHttpRequest){
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "건설업등록기준사전조사 삭제 성공", "건설업등록기준사전조사 정보가 삭제되었습니다.", null);
								searchPage(1, 1);
							} else {
								g3way.sicims.common.messageBox(null, "건설업등록기준사전조사 삭제 실패", "건설업등록기준사전조사 정보를 삭제하는데 실패했습니다.", null);
							}
						},
	});
}



</script>
