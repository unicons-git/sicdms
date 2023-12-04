<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="sicims-info">
	<!-- TIP -->
	<div class="div__tip">
		<h3 class="tip-label">TIP</h3>
		<dl class="tip-list">
			<dt><strong>등록기관, 통보기관, 통보일자, 법인등록번호, 업체명, 문서접수번호(접수), 위반혐의구분, 완료여부</strong>는 필수 입력 항목입니다.</dt>
			<dt>(*)은 필수 입력 항목입니다.</dt>
		</dl>
	</div>

	<form id="frm" name="frm" method="post" enctype="multipart/form-data">
		<input type="hidden"	name="admdspSn"	value="${ccVcadVo.admdspSn}" />

		<div style="margin-top:5px;">
			<table class="sicims-info-tb">
				<caption>위반업체 행정처분 상세정보</caption>
				<colgroup>
					<col width="11%" />
					<col width="22%" />
					<col width="11%" />
					<col width="22%" />
					<col width="11%" />
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
								<option value="<c:out value="${item.instCd}"/>" <c:if test="${ccVcadVo.regInstCd eq item.instCd}">selected</c:if>><c:out value="${item.instNm}"/></option>
							</c:forEach>
							</select>
						</td>
						<th>통보기관(*)</th>
						<td>
							<input type="text" name="ntfctnInstNm" value="${ccVcadVo.ntfctnInstNm}" class="required" placeholder="통보기관을 입력하세요." title="통보기관을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99.6%;"/>
						</td>
						<th>통보일자(*)</th>
						<td class = "tl">
							<fmt:parseDate value="${ccVcadVo.ntfctnYmd}" 	var="ntfctnYmd" pattern="yyyyMMdd" />
							<label class="blind" for="ntfctnYmd">통보일자</label>
							<input type="text" id="ntfctnYmd" name="ntfctnYmd" class="required w100" title="통보일자를 선택하세요." placeholder="통보일자" value="<fmt:formatDate value="${ntfctnYmd}" pattern="yyyy-MM-dd" />" />
						</td>
					</tr>
					<tr>
						<th>법인등록번호(*)</th>
						<td>
							<input type="text" name="crno" value="<c:choose><c:when test="${fn:length(ccVcadVo.crno) == 13}">${fn:substring(ccVcadVo.crno, 0, 6)}-${fn:substring(ccVcadVo.crno, 6, 13)}</c:when><c:otherwise>${fn:escapeXml(ccVcadVo.crno)}</c:otherwise></c:choose>"
								class="required isCrno w150" style="width:76%;"	placeholder="법인등록번호를 입력하세요." maxlength="13" title="법인등록번호를 입력하세요." />
						</td>
						<th>업체명(*)</th>
						<td>
							<input type="text" name="bzentyNm" value="${ccVcadVo.bzentyNm}" class="required" placeholder="업체명을 입력하세요." title="업체명을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99%;"/>
						</td>
						<th>문서번호(접수)(*)</th>
						<td>
							<input type="text" name="rcptDocNo" value="${ccVcadVo.rcptDocNo}" class="required"   placeholder="문서번호(접수)를 입력하세요." title="문서번호(접수)를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'32')" style="width:99%;"/>
						</td>
					</tr>
					<tr>
						<th>소재지</th>
						<td colspan="5">
							<input type="text" name="lctn" value="${ccVcadVo.lctn}" placeholder="소재지를 입력하세요." title="소재지를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'128')" style="width:99.7%;"/>
						</td>
					</tr>
					<tr>
						<th>위반내용</th>
						<td colspan="3">
							<input type="text" name="vltnCn" value="${ccVcadVo.vltnCn}" placeholder="위반내용을 입력하세요." title="위반내용을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'1024')" style="width:99.6%;"/>
						</td>
						<th>위반혐의구분(*)</th>
						<td>
							<select name="vltnSpcnSe" class="required w150" title="위반혐의구분을 선택바랍니다.">
								<option value="">위반혐의구분 선택</option>
							<c:forEach var="item" items="${vltnSpcnSeList}" varStatus="status">
								<option value="<c:out value="${item.cmcdCd}"/>" <c:if test="${ccVcadVo.vltnSpcnSe eq item.cmcdCd}">selected</c:if>><c:out value="${item.cmcdNm}"/></option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>위반조항</th>
						<td>
							<input type="text" name="vltnArtcl" value="${ccVcadVo.vltnArtcl}" placeholder="위반조항을 입력하세요." title="위반조항을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99%;"/>
						</td>
						<th>문서번호</th>
						<td>
							<input type="text" name="docNo" value="${ccVcadVo.docNo}" placeholder="문서번호를 입력하세요." title="문서번호를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'32')" style="width:99%;"/>
						</td>
						<th>완료여부(*)</th>
						<td>
							<label for="cmptnSe" class="blind">완료여부 선택</label>
							<select name="cmptnSe" class="required w150" title="완료여부를 선택바랍니다.">
								<option value="">완료여부 선택</option>
							<c:forEach var="item" items="${cmptnSeList}" varStatus="status">
								<option value="<c:out value="${item.cmcdCd}"/>" <c:if test="${ccVcadVo.cmptnSe eq item.cmcdCd}">selected</c:if>><c:out value="${item.cmcdNm}"/></option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>담당자</th>
						<td>
							<input type="text" name="picNm" value="${ccVcadVo.picNm}" class="innerSearch"  readonly placeholder="담당자를 검색하세요." title="담당자를 검색하세요."
								 onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'32')" style="width:99%;"/>
						</td>
						<th>진행상황</th>
						<td>
							<select name="prgrsSttsCd" class="w150" title="진행상황을 선택바랍니다.">
								<option value="">진행상황 선택</option>
							<c:forEach var="item" items="${prgrsSttsCdList}" varStatus="status">
								<option value="<c:out value="${item.cmcdCd}"/>" <c:if test="${ccVcadVo.prgrsSttsCd eq item.cmcdCd}">selected</c:if>><c:out value="${item.cmcdNm}"/></option>
							</c:forEach>
							</select>
						</td>
						<th>처분내용</th>
						<td>
							<input type="text" name="dspsCn" value="${ccVcadVo.dspsCn}" placeholder="처분내용을 입력하세요." title="처분내용을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'64')" style="width:99%;"/>
						</td>
					</tr>
					<tr>
						<th>처분근거조항</th>
						<td colspan="3">
							<input type="text" name="dspsBssArtcl" value="${ccVcadVo.dspsBssArtcl}" placeholder="처분근거조항을 입력하세요." title="처분근거조항을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'256')" style="width:99.6%;"/>
						</td>
						<th>처분(종결)일자</th>
						<td class = "tl">
							<fmt:parseDate value="${ccVcadVo.dspsTrmnYmd}" 	var="dspsTrmnYmd" pattern="yyyyMMdd" />
							<label class="blind" for="dspsTrmnYmd">처분(종결)일자</label>
							<input type="text" id="dspsTrmnYmd" name="dspsTrmnYmd" class="w100" title="처분(종결)일자를 선택하세요." placeholder="처분(종결)일자" value="<fmt:formatDate value="${dspsTrmnYmd}" pattern="yyyy-MM-dd" />" />
						</td>
					</tr>
					<tr>
						<th>상세내용</th>
						<td colspan="5">
							<input type="text" name="dtlCn" value="${ccVcadVo.dtlCn}" placeholder="상세내용을 입력하세요." title="상세내용을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'1024')" style="width:99.7%;"/>
						</td>
					</tr>
					<tr>
						<th>처리계획</th>
						<td colspan="5">
							<input type="text" name="prcsPlan" value="${ccVcadVo.prcsPlan}" placeholder="처리계획을 입력하세요." title="처리계획을 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'1024')" style="width:99.7%;"/>
						</td>
					</tr>
					<tr>
						<th>비고</th>
						<td colspan="5">
							<input type="text" name="rmrk" value="${ccVcadVo.rmrk}" placeholder="비고를 입력하세요." title="비고를 입력하세요."
								onKeyUp="javascript:g3way.sicims.common.chkBytes(this,'512')" style="width:99.7%;"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>

<div style="margin-top:10px;">
	<c:if test="${ccVcadVo.admdspSn eq null}">
		<div class="btnAction">
			<ul>
				<li><button id="btnInsert" 	class="default1">등록</button></li>
				<li><button id="btnClose" 	class="default2">닫기</button></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${ccVcadVo.admdspSn ne null}">
		<c:set var="updateYn" value="disabled" />
		<c:if test="${ccVcadVo.updusrId eq member.userId}">
			<c:set var="updateYn" value="" />
		</c:if>
		<div class="btnAction">
			<ul>
				<li><button id="btnUpdate" 	class="default1" ${updateYn}>수정</button></li>
				<li><button id="btnDelete" 	class="default3" ${updateYn}>삭제</button></li>
				<li><button id="btnClose" 	class="default2">닫기</button></li>
			</ul>
		</div>
	</c:if>
</div>

<script>

$(document).ready(function() {
	g3way.sicims.common.datePicker("ntfctnYmd");
	g3way.sicims.common.datePicker("dspsTrmnYmd");

	//<c:if test = "${ccVcadVo.admdspSn eq null or ccVcadVo.admdspSn == ''}">
    //To의 초기값을 오늘 날짜로 설정
    $('#ntfctnYmd').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	//</c:if>

	g3way.sicims.common.keyEvent();		// 입력 체크
	vcadDetailInvokeEvent();			// invoke event

});


function vcadDetailInvokeEvent() {
	$("input[name=picNm]").click(function() {
		var url = "<c:url value='/sicims910User/cmUserPopup.do'/>";
		url += "?cbObjNm=picNm";
		url += "&timestamp=" + new Date().getTime();

		g3way.sicims.common.openDialog(url, 600);
	});

	// 위반업체행정처분 등록
 	$("#btnInsert").click(function() {
 		if ( !g3way.sicims.common.excuteFormValidate('#frm')) {
 			return false;
 		}

		g3way.sicims.common.messageBox(null, "위반업체행정처분 등록", "위반업체행정처분을 등록하시겠습니까?", insertCcVcad);
	});


	// 위반업체행정처분  수정
 	$("#btnUpdate").click(function() {
 		if (!g3way.sicims.common.excuteFormValidate('#frm')) {
 			return false;
 		}

 		g3way.sicims.common.messageBox(null, "위반업체행정처분 수정", "위반업체행정처분을 수정하시겠습니까?", updateCcVcad);
	});


	// 위반업체행정처분  삭제
 	$("#btnDelete").click(function() {
 		g3way.sicims.common.messageBox(null, "위반업체행정처분 삭제", "위반업체행정처분을 삭제하시겠습니까?", deleteCcVcad);
	});

	// 닫기
 	$("#btnClose").click(function() {
		$("#tblList tbody").find(".selected").removeClass("selected");
		$("div#divDetail").hide();
	});
}



//위반업체행정처분 등록
function insertCcVcad() {
	$("#frm select:disabled").removeAttr('disabled');
	$("#frm input:disabled").removeAttr('disabled');

	fnLoadingStart();
	$.ajax({
		url			:	"<c:url value='/sicims300Cc/insertCcVcad.do'/>",
		data		:	new FormData(document.getElementById('frm')),
		processData	:	false,
    	contentType	:	false,
		type		:	"post",
		success 	: 	function(data){
							fnLoadingEnd();
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "위반업체행정처분 등록 성공", "위반업체행정처분이 등록되었습니다.", null);
								selectCcVcadList(1);
							} else {
								g3way.sicims.common.messageBox(null, "위반업체행정처분 등록 실패", "위반업체행정처분 등록에 실패했습니다.", null);
							}
						},
		});
}


//위반업체행정처분 수정
function updateCcVcad() {
	$("#frm select:disabled").removeAttr('disabled');
	$("#frm input:disabled").removeAttr('disabled');

	$.ajax({
		url			:	"<c:url value='/sicims300Cc/updateCcVcad.do'/>",
		data		:	new FormData(document.getElementById('frm')),
		processData	:	false,
    	contentType	:	false,
		type		:	"post",
		success 	: 	function(data, textStatus){
							if (data.result > 0) {
								var item = data.ccVcadVo;
								var admdspSn	= g3way.sicims.common.maskFormat(item.admdspSn);				// 행정처분일련번호
								var crno		= g3way.sicims.common.maskFormat(item.crno, 'CRNO');			// 법인등록번호
								var bzentyNm	= g3way.sicims.common.maskFormat(item.bzentyNm);				// 업체명
								var ntfctnInstNm= g3way.sicims.common.maskFormat(item.ntfctnInstNm);			// 통보기관명
								var ntfctnYmd 	= g3way.sicims.common.maskFormat(item.ntfctnYmd, 'DATE');		// 통보일자
								var vltnSpcnSeNm= g3way.sicims.common.dateToString(item.vltnSpcnSeNm);			// 위반혐의구분명

								g3way.sicims.common.messageBox(null, "위반업체행정처분 수정 성공", "위반업체행정처분이 수정되었습니다.", null);
								$("#tblList tbody").find(".selected").find("td:eq(1)").html($("select[name=regInstCd] option:selected").text());
								$("#tblList tbody").find(".selected").find("td:eq(2)").html(crno);
								$("#tblList tbody").find(".selected").find("td:eq(3) div").html(bzentyNm).attr("title", bzentyNm);
								$("#tblList tbody").find(".selected").find("td:eq(4) div").html(ntfctnInstNm).attr("title", ntfctnInstNm);
								$("#tblList tbody").find(".selected").find("td:eq(5)").html(ntfctnYmd);
								$("#tblList tbody").find(".selected").find("td:eq(6)").html($("#frm select[name=vltnSpcnSe] option:selected").text());

								$("#tblList tbody").find(".selected").trigger("click");
							} else {
								g3way.sicims.common.messageBox(null, "위반업체행정처분 수정 실패", "위반업체행정처분 수정에 실패했습니다.", null);
							}
						},
		});
}




//위반업체행정처분 삭제
function deleteCcVcad() {
	$.ajax({
		url			:	"<c:url value='/sicims300Cc/deleteCcVcad.do'/>",
		data		:	{
							admdspSn	: $("#frm input[name=admdspSn]").val(),
						},
		dataType	:	"json",
		type		:	"POST",
		success		: 	function(data, textStatus, XMLHttpRequest){
							if (data.result > 0) {
								g3way.sicims.common.messageBox(null, "위반업체행정처분 삭제 성공", "위반업체행정처분이 삭제되었습니다.", null);
								searchPage(1);
							} else {
								g3way.sicims.common.messageBox(null, "위반업체행정처분 삭제 실패", "위반업체행정처분을 삭제하는데 실패했습니다.", null);
							}
						},
	});
}



</script>
