<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
	<div class="page-head">
		<ul class="page-loca">
			<li class="initPage">Home</li>
			<li class="menuPage" 	data-menuinx="3" data-submenuinx="22" data-title="불법하도급신고서">민원 관리 > 민원관리 </li>
			<li class="menuItem">불법하도급신고서</li>
		</ul>
	</div>
	<div class="content">
		<div class="box-search">
			<div class="box-search-line">
				<label class="blind" for="selRegInstCd">등록기관 선택</label>
				<select id="selRegInstCd" style="width:130px;" title="등록기관 선택" <c:if test="${member.authrtCd eq 'ROLE_SGG'}">disabled</c:if> >
					<option value="">등록기관 선택</option>
				<c:forEach var="item" items="${regInstList}" varStatus="status">
					<option value="<c:out value="${item.instCd}"/>"
						<c:if test="${member.authrtCd eq 'ROLE_SGG' and item.instCd eq member.inst1Cd}">selected</c:if>>
						<c:out value="${item.instNm}"/>
					</option>
				</c:forEach>
				</select>
				<label class="lbl" style="margin-left:20px;">신고기간 : </label>
				<label class="blind" for="fromYmd">신고시작일자</label>
				<input type="text" id="fromYmd" class="w90" title="신고시작일자를 선택하세요." 	placeholder="신고시작일자"/><span style="font-size:13px;">&nbsp;~&nbsp;</span>
				&nbsp;~&nbsp;
				<label class="blind" for="toYmd">신고종료일자</label>
				<input type="text" id="toYmd" class="w90" title="신고종료일자를 선택하세요." 	placeholder="신고종료일자"style="margin:0px;"/>

				<label class="blind" for="keyword">검색어 </label>
				<input type="text" id="keyword" name="keyword" style="width:300px;" placeholder="신고자명을 입력하세요."  title="신고자명을 입력하세요."/>
			</div>
			<div style="float:right;">
				<button id="btnSearch" class="search" style="float:left;">검색</button>
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
					<caption>불법하도급신고 목록</caption>
					<thead>
						<tr>
							<th style="width:60px;">번호</th>
							<th style="width:80px;">등록기관</th>
							<th style="width:85px;">접수번호</th>
							<th style="width:100px">신고자</th>
							<th style="width:110px;">연락처</th>
							<th style="width:80px;">신고일자</th>
							<th style="width:173px;">원도급업체</th>
							<th style="width:173px;">불법하도급을 한 업체</th>
							<th style="width:*;">불법하도급을 받은 업체</th>
						</tr>
					</thead>
					<tfoot class="blind"></tfoot>
					<tbody>
						<tr class="nohover">
							<td colspan="9">&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="divPaginator" class="tc" style="display:none;margin-top:10px;"></div>
		</div>
		<div class="clear"></div>
		<div style="margin-top:5px;">
			<button id="btnAdd" class="append" style="float:right;">추가</button>
		</div>
		<div class="clear"></div>
		<div id="divDetail"></div>
	</div>
</div>

<script>
var _pager 	= null;		// 메인 목록
var _pager9 = null;		// 팝업

var _regInstCd	= "";
var _fromYmd	= "";
var _toYmd		= "";
var _keyword	= "";


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

	// 시작일자 변경
	$("#fromYmd").change(function() {
		$("#toYmd").val(g3way.sicims.common.addMonth($(this).val(), 1));
	});

	// 종료일자 변경
	$("#toYmd").change(function() {
		//$("#fromYmd").val(g3way.sicims.common.addMonth($(this).val(), -1));
	});


	// 엔터 키
	$("#keyword").keypress(function(e){
		 if(event.which == 13) {
			 searchPage(1, 1);
	     }
	});

	// 검색
	$("#btnSearch").click(function() {
		searchPage(1, 1);
	});


	// 불법하도급신고 추가
	$("#btnAdd").click(function() {
		$("#tblList tbody tr").removeClass("selected");
		viewCcIsrdDetail("");
	});
}


function searchPage(page, num) {
	if (num == 1) selectCcIsrdList(page);	// 메인 목록
	if (num == 9) selectKcCcmaList(page);	// 팝업 목록
}

// 불법하도급신고  목록 조회
function selectCcIsrdList(page) {
	_regInstCd	= $("#selRegInstCd").val();		// 등록기관코드
	_fromYmd	= $("#fromYmd").val();			// 통보시작일자
	_toYmd		= $("#toYmd").val();			// 통보종료일자
	_keyword	= $("#keyword").val();			// 키워드

	$("#divDetail").hide();

	$("#tblList tbody").empty().append(g3way.sicims.common.getWaitImg($("#tblList tbody").width(), 9));

	$.ajax({
		url			: 	"<c:url value='/sicims300Cc/selectCcIsrdList.do'/>",
		data		:	{
							regInstCd	: $("#selRegInstCd").val(),										// 등록기관코드
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
									var rcptNo			= g3way.sicims.common.maskFormat(item.rcptNo);				// 접수번호
									var regInstNm		= g3way.sicims.common.maskFormat(item.regInstNm);			// 등록기관명
									var dclNm			= g3way.sicims.common.maskFormat(item.dclNm);				// 신고자명
									var dclTelno		= g3way.sicims.common.maskFormat(item.dclTelno);			// 연락처
									var rcptYmd 		= g3way.sicims.common.maskFormat(item.rcptYmd, 'DATE');		// 신고일자(접수일자)
									var gnctrtBzentyNm	= g3way.sicims.common.maskFormat(item.gnctrtBzentyNm );		// 원도급업체명
									var gisbeNm			= g3way.sicims.common.maskFormat(item.gisbeNm );			// 불법하도급지급업체명
									var risbeNm			= g3way.sicims.common.maskFormat(item.risbeNm );			// 불법하도급수령업체명

									htmlStr += "<tr	";
									htmlStr += "	data-rcptNo='" + rcptNo + "'";
									htmlStr += ">";
									htmlStr += "	<td class='tc' style='width:60px;'>" 	+ item.seqNo + "</td>";
									htmlStr += "	<td class='tc' style='width:80px;'>" 	+ regInstNm + "</td>";
									htmlStr += "	<td class='tc' style='width:85px;'>" 	+ rcptNo + "</td>";
									htmlStr += "	<td class='tc' style='width:100px;'>" 	+ dclNm + "</td>";
									htmlStr += "	<td class='tc' style='width:110px;'>" 	+ dclTelno + "</td>";
									htmlStr += "	<td class='tc' style='width:80px;'>" 	+ rcptYmd + "</td>";
									htmlStr += "	<td class='tl' style='width:173px;'><div class='nowrap' style='width:160px;' title='" + gnctrtBzentyNm + "'>" + gnctrtBzentyNm + "</div></td>";
									htmlStr += "	<td class='tl' style='width:173px;'><div class='nowrap' style='width:160px;' title='" + gisbeNm + "'>" + gisbeNm + "</div></td>";
									htmlStr += "	<td class='tl' style='width:178px;'><div class='nowrap' style='width:165px;' title='" + risbeNm + "'>" + risbeNm + "</div></td>";
									htmlStr += "</tr>";
								});
							} else {
								htmlStr = "<tr class='nohover'><td colspan='9' class='tc' style='color:#999999'>검색된 정보가 없습니다.</td></tr>";
							}

							var totalCount = (data.result.length == 0 ? 0 : data.result[0].totalCount);
							_pager = new Pager("divPaginator", "_pager", 1);	// global로 선언해함.  패러미터 :  divPaginator(페이징 div id),  _pager(페이저 객체를 담는 object),  1 (페이저 객체 number)
							_pager.makePaging(page, totalCount, $("#selRows").val(), $("#divTotalPage"));

							$("#tblList tbody").html(htmlStr);

							if (data.result.length > 0) {
								listEventHandler();
							}
						},
	});
}

function listEventHandler() {
	$("#tblList tbody tr").mouseover(function() {
		$(this).css("cursor", "pointer");
	}).click(function() {
		$("#tblList tbody tr").removeClass("selected");
		$(this).addClass("selected");

		var rcptNo	= $(this).data("rcptno");
		viewCcIsrdDetail(rcptNo);
	});
}


// 불법하도급신고  상세 정보 조회
function viewCcIsrdDetail(rcptNo) {
	var url = "<c:url value='/sicims300Cc/ccIsrdDetail.do'/>";
	url += "?rcptNo=" + rcptNo;
	url += "&timestamp=" + new Date().getTime();

	$("#divDetail").show().load(url);
}



</script>