<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="container">
	<div class="page-head">
		<ul class="page-loca">
			<li class="initPage">Home</li>
			<li class="menuPage" 	data-menuinx="3" data-submenuinx="21" data-title="위반업체 행정처분 자료관리">민원 관리 > 민원관리 </li>
			<li class="menuItem">위반업체 행정처분 자료관리</li>
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
				<label class="blind" for="selVltnSpcnSe">위반혐의구분 선택</label>
				<select id="selVltnSpcnSe" style="width:150px;" title="위반혐의구분 선택">
					<option value="">위반혐의구분 선택</option>
				<c:forEach var="item" items="${vltnSpcnSeList}" varStatus="status">
					<option value="<c:out value="${item.cmcdCd}"/>"><c:out value="${item.cmcdNm}"/></option>
				</c:forEach>
				</select>
				<label class="blind" for="keyword">검색어 </label>
				<input type="text" id="keyword" name="keyword" style="width:300px;" placeholder="업체명 또는 법인등록번호를 입력하세요."/>
			</div>
			<div style="float:right;">
				<button id="btnSearch" class="search" style="margin:0px;">검색</button>
				<!-- <button id="btnPrint"  class="print"  style="margin-left:20px;" disabled>출력</button> -->
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
							<th style="width:100px;">등록기관</th>
							<th style="width:120px;">법인등록번호</th>
							<th style="width:280px;">업체명</th>
							<th style="width:220px;">통보기관</th>
							<th style="width:100px;">통보일자</th>
							<th style="width:*;">위반혐의구분</th>
						</tr>
					</thead>
					<tfoot class="blind"></tfoot>
					<tbody>
						<tr class="nohover">
							<td colspan="7">&nbsp;</td>
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

var _regInstCd	= "";
var _vltnSpcnSe	= "";
var _keyword	= "";

$(document).ready(function() {
	g3way.sicims.common.keyEvent();		// 입력 체크
	invokeEvent();						// invoke event
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

	// 위반혐의 구분
	$("#selVltnSpcnSe").change(function() {
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
		var reportName 	= "sicims/SICIMS300_VCAD_01.mrd";
		var param  = "/rp ";
			param += "[" + _regInstCd   + "]"; 		// 등록기관
			param += "[" + _vltnSpcnSe  + "]"; 		// 위반혐의구분
			param += "[" + _keyword 	+ "]"; 		// 키워드

		var url = "<c:url value='/sicims000Cm/rdPrint.do'/>";
		url += "?reportName=" + encodeURIComponent(reportName);
		url += "&param=" 	  + encodeURIComponent(param);
		url += "&html5=Y";
		url += "&timestamp="  + new Date().getTime();


		g3way.sicims.common.openDialog(url, 860);
	});


	// 위반업체행정처분 추가
	$("#btnAdd").click(function() {
		$("#tblList tbody tr").removeClass("selected");
		viewCcVcadDetail("");
	});
}


function searchPage(page, num) {
	if (num == 1) selectCcVcadList(page);	// 메인 목록
	if (num ==99) selectCmUserList(page);	// 사용자 목록
}


// AJAX : 사용자 목록 조회
function selectCcVcadList(page) {
	_regInstCd	= $("#selRegInstCd").val();		// 등록기관코드
	_vltnSpcnSe	= $("#selVltnSpcnSe").val();	// 위반혐의구분
	_keyword	= $("#keyword").val();			// 키워드

	$("#divDetail").hide();
	$("#tblList tbody").empty().append(g3way.sicims.common.getWaitImg($("#tblList tbody").width(), 7));

	$.ajax({
		url			: 	"<c:url value='/sicims300Cc/selectCcVcadList.do'/>",
		data		:	{
							regInstCd	: $("#selRegInstCd").val(),					// 등록기관코드
							vltnSpcnSe	: $("#selVltnSpcnSe").val(),				// 위반혐의구분
							keyword		: $("#keyword").val(),						// 검색어
							rows		: $("#selRows").val(),						// 페이지당 항목수
							page		: page
						},
		dataType	:	"json",
		type		:	"post",
		success		:	function(data, textStatus){
							var htmlStr = "";
							if (data.result.length > 0) {
								$.each(data.result,function(index,item){
									var admdspSn	= g3way.sicims.common.maskFormat(item.admdspSn);			// 행정처분일련번호
									var regInstNm	= g3way.sicims.common.maskFormat(item.regInstNm);			// 등록기관명
									var crno		= g3way.sicims.common.maskFormat(item.crno, 'CRNO');		// 법인등록번호
									var bzentyNm	= g3way.sicims.common.maskFormat(item.bzentyNm);			// 업체명
									var ntfctnInstNm= g3way.sicims.common.maskFormat(item.ntfctnInstNm);		// 통보기관명
									var ntfctnYmd	= g3way.sicims.common.maskFormat(item.ntfctnYmd, 'DATE');	// 통보일자
									var vltnSpcnSeNm= g3way.sicims.common.maskFormat(item.vltnSpcnSeNm);		// 위반혐의구분명

									htmlStr += "<tr	";
									htmlStr += "	data-admdspSn='" + admdspSn + "'";
									htmlStr += ">";
									htmlStr += "	<td class='tc'>" 	+ item.seqNo + "</td>";
									htmlStr += "	<td class='tc'>" 	+ regInstNm + "</td>";
									htmlStr += "	<td class='tc'>" 	+ crno + "</td>";
									htmlStr += "	<td class='tl'><div class='nowrap' style='width:280px;' title='" + bzentyNm + "'>" + bzentyNm + "</div></td>";
									htmlStr += "	<td class='tl'><div class='nowrap' style='width:220px;' title='" + ntfctnInstNm + "'>" + ntfctnInstNm + "</div></td>";
									htmlStr += "	<td class='tc'>" 	+ ntfctnYmd + "</td>";
									htmlStr += "	<td class='tc'>" 	+ vltnSpcnSeNm + "</td>";
									htmlStr += "</tr>";
								});
								$("#btnPrint").attr("disabled", false);
							} else {
								htmlStr = "<tr class='nohover'><td colspan='7' class='tc' style='color:#999999'>검색된 정보가 없습니다.</td></tr>";
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

		var admdspSn 	= $(this).data("admdspsn");

		viewCcVcadDetail(admdspSn);
	});
}



function viewCcVcadDetail(admdspSn) {
	var url = "<c:url value='/sicims300Cc/ccVcadDetail.do'/>";
	url += "?admdspSn=" + admdspSn;
	url += "&timestamp=" + new Date().getTime();

	$("#divDetail").show().load(url);
}


</script>
