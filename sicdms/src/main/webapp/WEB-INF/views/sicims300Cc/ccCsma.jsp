<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="container">
	<div class="page-head">
		<ul class="page-loca">
			<li class="initPage">Home</li>
			<li class="menuPage" 	data-menuinx="3" data-submenuinx="21" data-title="건설업등록기준 제출 자료관리">민원 관리 > 민원관리 </li>
			<li class="menuItem">건설업등록기준 제출 자료관리</li>
		</ul>
	</div>

	<div class="content">
		<div class="box-search">
			<div class="box-search-line">
				<label class="blind" for="selRegInstCd">등록기관 선택</label>
				<select id="selRegInstCd" style="width:110px;" title="등록기관 선택" <c:if test="${member.authrtCd eq 'ROLE_SGG'}">disabled</c:if> >
					<option value="">등록기관 선택</option>
				<c:forEach var="item" items="${regInstList}" varStatus="status">
					<option value="<c:out value="${item.instCd}"/>"
						<c:if test="${member.authrtCd eq 'ROLE_SGG' and item.instCd eq member.inst1Cd}">selected</c:if>>
						<c:out value="${item.instNm}"/>
					</option>
				</c:forEach>
				</select>
				<label class="blind" for="selMfldCd">분야 선택</label>
				<select id="selMfldCd" style="width:100px;" title="분야 선택">
					<option value="">분야 선택</option>
				<c:forEach var="item" items="${mfldCdList}" varStatus="status">
					<option value="<c:out value="${item.cmcdCd}"/>"><c:out value="${item.cmcdNm}"/></option>
				</c:forEach>
				</select>
				<label class="blind" for="selSbmsnSttsCd">제출상태 선택</label>
				<select id="selSbmsnSttsCd" style="width:110px;" title="제출상태 선택">
					<option value="">제출상태 선택</option>
				<c:forEach var="item" items="${sbmsnSttsCdList}" varStatus="status">
					<option value="<c:out value="${item.cmcdCd}"/>"><c:out value="${item.cmcdNm}"/></option>
				</c:forEach>
				</select>
				<label class="lbl" style="margin-left:10px;">공고기간 : </label>
				<label class="blind" for="fromYmd">공고시작일자</label>
				<input type="text" id="fromYmd" class="w90" title="공고시작일자를 선택하세요." 	placeholder="공고시작일자"/><span style="font-size:13px;">&nbsp;~&nbsp;</span>
				<label class="blind" for="toYmd">공고종료일자</label>
				<input type="text" id="toYmd" class="w90" title="공고종료일자를 선택하세요." 	placeholder="공고종료일자" style="margin:0px;"/>

				<label class="blind" for="keyword">검색어 </label>
				<input type="text" id="keyword" name="keyword" style="width:160px;" placeholder="공사명 또는 조사업체명" title="공사명 또는 조사업체명을 입력하세요."/>
			</div>
			<div style="float:right;">
				<button id="btnSearch" class="search" style="margin:0px;">검색</button>
				<button id="btnPrint"  class="print"  style="margin-left:10px;" disabled>출력</button>
			</div>
		</div>

		<div style="margin-top:10px;">
			<div>
				<div id="divTotalPage" class="left" style="padding-top:10px;"></div>
				<div class="right">
					<div class="right" style="margin-left:20px;">
						<span style="margin-right:5px;">리스트 개수 :</span>
						<label class="blind" for="selRows">리스트 개수</label>
						<select id="selRows"  style="width:120px;" title="리스트 개수">
							<option value="10" selected>10개씩 보기</option>
							<option value="20">20개씩 보기</option>
							<option value="30">30개씩 보기</option>
							<option value="50">50개씩 보기</option>
							<option value="100">100개씩 보기</option>
						</select>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div style="margin-top:5px;">
				<table id="tblList" class="sicims-list-tb">
					<caption>사용자 목록</caption>
					<thead>
						<tr>
							<th style="width:60px;">번호</th>
							<th style="width:80px;">등록기관</th>
							<th style="width:80px;">공고일</th>
							<th style="width:80px;">개찰일</th>
							<th style="width:90px;">분야</th>
							<th style="width:160px;">공사명</th>
							<th style="width:140px;">발주기관</th>
							<th style="width:95px;">공사금액(억원)</th>
							<th style="width:80px;">순위</th>
							<th style="width:80px;">제출상태</th>
							<th style="width:*;">제출일자</th>
						</tr>
					</thead>
					<tfoot class="blind"></tfoot>
					<tbody>
						<tr class="nohover">
							<td colspan="11">&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="divPaginator" style="display:none;margin-top:10px;text-align:center;"></div>
		</div>
		<div class="clear"></div>
		<div style="margin-top:5px;">
			<button id="btnAdd" class="append" style="float:right;">추가</button>
		</div>
		<div class="clear"></div>
		<div id="divDetail" style="margin-top:10px;"></div>
	</div>
</div>

<script>
var _pager 	= null;		// 메인 목록
var _pager9 = null;		// 팝업

var _regInstCd		= "";
var _mfldCd			= "";
var _sbmsnSttsCd	= "";
var _keyword		= "";
var _fromYmd	= "";
var _toYmd		= "";


$(document).ready(function() {
	g3way.sicims.common.datePickerFormToDate('fromYmd', 'toYmd');
    //From의 초기값을  한달전 전으로 설정
    $('#fromYmd').datepicker('setDate', '-1M'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
    //To의 초기값을 오늘 날짜로 설정
    $('#toYmd').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)


	g3way.sicims.common.keyEvent();
	invokeEvent();
	searchPage(1, 1);
});


/*=======================
invoke event
=========================*/
function invokeEvent() {
	// 등록기관
	$("#selRegInstCd").change(function() {
		searchPage(1, 1);
	});

	// 주력분야코드
	$("#selMfldCd").change(function() {
		searchPage(1, 1);
	});

	// 제출상태코드
	$("#selSbmsnSttsCd").change(function() {
		searchPage(1, 1);
	});

	// 시작일자 변경
	$("#fromYmd").change(function() {
		$("#toYmd").val(g3way.sicims.common.addMonth($(this).val(), 1));
		searchPage(1, 1);
	});

	// 종료일자 변경
	$("#toYmd").change(function() {
		//$("#fromYmd").val(g3way.sicims.common.addMonth($(this).val(), -1));
		searchPage(1, 1);
	});


	// 검색어
	$("#keyword").keypress(function(event){
	     if(event.which == 13) {
	    	 searchPage(1, 1);
	     }
	});

	// 검색
	$("#btnSearch").click(function() {
		searchPage(1, 1);
	});

	// 리스트 개수
	$("#selRows").on('change', function() {
		searchPage(1, 1);
	});

	// 출력
	$("#btnPrint").click(function() {
		var reportName 	= "sicims/SICIMS300Cc_CSMA_T.mrd";
		var param  = "/rp ";
			param += "[" + _regInstCd   + "]"; 		// 등록기관
			param += "[" + _mfldCd  	+ "]"; 		// 주력분야코드
			param += "[" + _sbmsnSttsCd + "]"; 		// 제출상태코드
			param += "[" + g3way.sicims.common.removeDelimChar(_fromYmd) + "]"; 	// 공고시작일자
			param += "[" + g3way.sicims.common.removeDelimChar(_toYmd)   + "]"; 	// 공고종료일자
			param += "[" + _keyword 	+ "]"; 		// 키워드

		var url = "<c:url value='/sicims000Cm/rdPrint.do'/>";
		url += "?reportName=" + encodeURIComponent(reportName);
		url += "&param=" 	  + encodeURIComponent(param);
		url += "&html5=Y";
		url += "&timestamp="  + new Date().getTime();


		g3way.sicims.common.openDialog(url, 1000);
	});


	// 건설업등록기준사전조사 추가
	$("#btnAdd").click(function() {
		$("#tblList tbody tr").removeClass("selected");
		viewCcCsmaDetail("");
	});
}


function searchPage(page, num) {
	if (num == 1) selectCcCsmaList(page);	// 메인 목록
	if (num == 9) selectGbBdntList(page);	// 입찰공고 목록
	if (num ==99) selectCmUserList(page);	// 사용자 목록
}



// 건설업등록기준사전조사 목록 조회
function selectCcCsmaList(page) {
	_regInstCd	= $("#selRegInstCd").val();		// 등록기관코드
	_mfldCd		= $("#selMfldCd").val();		// 주력분야코드
	_sbmsnSttsCd= $("#selSbmsnSttsCd").val();	// 제출상태코드
	_keyword	= $("#keyword").val();			// 키워드
	_fromYmd	= $("#fromYmd").val();			// 공고시작일자
	_toYmd		= $("#toYmd").val();			// 공고종료일자

	$("#divDetail").hide();
	$("#tblList tbody").empty().append(g3way.sicims.common.getWaitImg($("#tblList tbody").width(), 11));

	$.ajax({
		url			: 	"<c:url value='/sicims300Cc/selectCcCsmaList.do'/>",
		data		:	{
							regInstCd	: $("#selRegInstCd").val(),										// 등록기관코드
							mfldCd		: $("#selMfldCd").val(),										// 주력분야코드
							sbmsnSttsCd	: $("#selSbmsnSttsCd").val(),									// 제출상태코드
							fromYmd		: g3way.sicims.common.removeDelimChar($("#fromYmd").val()),		// 신고시작일자
							toYmd		: g3way.sicims.common.removeDelimChar($("#toYmd").val()),		// 신고종료일자
							keyword		: $("#keyword").val(),											// 검색어
							rows		: $("#selRows").val(),											// 페이지당 항목수
							page		: page
						},
		dataType	:	"json",
		type		:	"post",
		success		:	function(data, textStatus){
							var htmlStr = "";
							if (data.result.length > 0) {
								$.each(data.result,function(index,item){
									var cirsSn			= g3way.sicims.common.maskFormat(item.cirsSn);					// 건설업등록기준일련번호
									var regInstNm		= g3way.sicims.common.maskFormat(item.regInstNm);				// 등록기관명
									var pbancYmd		= g3way.sicims.common.maskFormat(item.pbancYmd, 'DATE');		// 공고일자
									var opnbdYmd		= g3way.sicims.common.maskFormat(item.opnbdYmd, 'DATE');		// 개찰일자
									var mfldNm			= g3way.sicims.common.maskFormat(item.mfldNm);					// 주력분야명
									var cstrnNm			= g3way.sicims.common.maskFormat(item.cstrnNm);					// 공사명
									var podrInstNm		= g3way.sicims.common.maskFormat(item.podrInstNm);				// 발주기관명
									var cstrnAmt		= g3way.sicims.common.maskFormat(item.cstrnAmt, 'NUMBER', 2);	// 공사금액(원)
									var exmnBzentySeNm	= g3way.sicims.common.maskFormat(item.exmnBzentySeNm);			// 조사업체구분명
									var sbmsnSttsNm		= g3way.sicims.common.maskFormat(item.sbmsnSttsNm);				// 제출상태명
									var sbmsnYmd		= g3way.sicims.common.maskFormat(item.sbmsnYmd, 'DATE');		// 제출일자

									htmlStr += "<tr	";
									htmlStr += "	data-cirsSn='" + cirsSn + "'";
									htmlStr += ">";
									htmlStr += "	<td class='tc'>" 	+ item.seqNo + "</td>";
									htmlStr += "	<td class='tc'>" 	+ regInstNm + "</td>";
									htmlStr += "	<td class='tc'>" 	+ pbancYmd + "</td>";
									htmlStr += "	<td class='tc'>" 	+ opnbdYmd + "</td>";
									htmlStr += "	<td class='tc'>" 	+ mfldNm + "</td>";
									htmlStr += "	<td class='tl'><div class='nowrap' style='width:150px;' title='" + cstrnNm + "'>" + cstrnNm + "</div></td>";
									htmlStr += "	<td class='tl'><div class='nowrap' style='width:130px;' title='" + podrInstNm + "'>" + podrInstNm + "</div></td>";
									htmlStr += "	<td class='tr'>" 	+ cstrnAmt + "</td>";
									htmlStr += "	<td class='tc'>" 	+ exmnBzentySeNm + "</td>";
									htmlStr += "	<td class='tc'>" 	+ sbmsnSttsNm + "</td>";
									htmlStr += "	<td class='tc'>" 	+ sbmsnYmd + "</td>";
									htmlStr += "</tr>";
								});
								$("#btnPrint").attr("disabled", false);
							} else {
								htmlStr = "<tr class='nohover'><td colspan='11' class='tc' style='color:#999999'>검색된 정보가 없습니다.</td></tr>";
								$("#btnPrint").attr("disabled", true);
							}

							var totalCount = (data.result.length == 0 ? 0 : data.result[0].totalCount);
							_pager = new Pager("divPaginator", "_pager", 1);	// global로 선언해함.  패러미터 :  divPaginator(페이징 div id),  _pager(페이저 객체를 담는 object),  1 (페이저 객체 number)
							_pager.makePaging(page, totalCount, $("#selRows").val(), $("#divTotalPage"));

							$("#tblList tbody").html(htmlStr);

							if (data.result.length > 0) {
								listEventHandler();
							}
						}
	});
}


function listEventHandler() {
	$("#tblList tbody tr").mouseover(function() {
		$(this).css("cursor", "pointer");
	}).click(function() {
		$("#tblList tbody tr").removeClass("selected");
		$(this).addClass("selected");

		var cirsSn 	= $(this).data("cirssn");

		viewCcCsmaDetail(cirsSn);
	});
}



function viewCcCsmaDetail(cirsSn) {
	var url = "<c:url value='/sicims300Cc/ccCsmaDetail.do'/>";
	url += "?cirsSn=" + cirsSn;
	url += "&timestamp=" + new Date().getTime();

	$("#divDetail").show().load(url);
}


</script>
