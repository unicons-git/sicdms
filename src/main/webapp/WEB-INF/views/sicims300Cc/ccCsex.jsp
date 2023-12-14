<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="container">
	<div class="page-head">
		<ul class="page-loca">
			<li class="initPage">Home</li>
			<li class="menuPage" 	data-menuinx="6" data-submenuinx="11" data-title="건설업 사전자료 제출">자료제출 </li>
			<li class="menuItem">건설업 사전자료 제출</li>
		</ul>
	</div>

	<div>
		<div class="titleWelcome">${fn:escapeXml(member.deptNm)}&nbsp;${fn:escapeXml(member.userNm)}&nbsp;님 환영합니다.</div>
	</div>

	<div class="content">
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
					<caption>건설업등록기준사전조사 목록</caption>
					<thead>
						<tr>
							<th style="width:60px;">번호</th>
							<th style="width:100px;">공고일</th>
							<th style="width:100px;">개찰일</th>
							<!-- <th style="width:100px;">분야</th> -->
							<th style="width:300px;">공사명</th>
							<th style="width:260px;">발주기관</th>
							<th style="width:120px;">공사금액(억원)</th>
							<th style="width:*;">순위</th>
						</tr>
					</thead>
					<tfoot class="blind"></tfoot>
					<tbody>
						<tr class="nohover">
							<td colspan="8">&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="divPaginator" style="display:none;margin-top:10px;text-align:center;"></div>
		</div>
		<div class="clear"></div>
		<div style="margin-top:20px;">
			<div>
				<div class="title2">자료제출이력</div>
			</div>
			<div style="margin-top:5px;" >
				<table id="tblCcCsexList" class="sicims-list-tb">
					<caption>건설업사전자료제출 목록</caption>
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
						<tr class="nohover">
							<td colspan="6">&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="clear"></div>
			<div style="margin-top:5px;">
				<button id="btnAdd" class="append" style="float:right;">추가</button>
			</div>
			<div class="clear"></div>
			<div id="divDetail"></div>
		</div>
	</div>
</div>

<script>
var _pager 	= null;		// 메인 목록
var _pager9 = null;		// 팝업

$(document).ready(function() {
	g3way.sicims.common.keyEvent();
	invokeEvent();
	searchPage(1, 1);
});


/*=======================
invoke event
=========================*/
function invokeEvent() {
	// 리스트 개수
	$("#selRows").on('change', function() {
		searchPage(1, 1);
	});

	// 건설업등록기준사전조사 자료제출 추가
	$("#btnAdd").click(function() {
 		$("#tblCcCsexList tbody tr").removeClass("selected");
		var cirsSn = $("#tblList tbody").find(".selected").data("cirssn");
		viewCcCsexDetail(cirsSn, "");
	});
}


function searchPage(page, num) {
	if (num == 1) selectCcCsmaList(page);	// 건설업사전조사 목록
	if (num == 9) selectGbBdntList(page);	// 입찰공고 목록
}



// 건설업등록기준사전조사 목록 조회
function selectCcCsmaList(page) {
	$("#divDetail").hide();
	$("#tblList tbody").empty().append(g3way.sicims.common.getWaitImg($("#tblList tbody").width(), 8));

	$.ajax({
		url			: 	"<c:url value='/sicims300Cc/selectCcCsmaList.do'/>",
		data		:	{
							exmnBzentyPicEmlAddr	: "${fn:escapeXml(member.userId)}",		// 사용자ID
							rows					: $("#selRows").val(),					// 페이지당 항목수
							page					: page
						},
		dataType	:	"json",
		type		:	"post",
		success		:	function(data, textStatus){
							var htmlStr = "";
							if (data.result.length > 0) {
								$.each(data.result,function(index,item){
									var cirsSn			= g3way.sicims.common.maskFormat(item.cirsSn);					// 건설업등록기준일련번호
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
									htmlStr += "	<td class='tc'>" 	+ pbancYmd + "</td>";
									htmlStr += "	<td class='tc'>" 	+ opnbdYmd + "</td>";
									/* htmlStr += "	<td class='tc'>" 	+ mfldNm + "</td>"; */
									htmlStr += "	<td class='tl'><div class='nowrap' style='width:290px;' title='" + cstrnNm + "'>" + cstrnNm + "</div></td>";
									htmlStr += "	<td class='tl'><div class='nowrap' style='width:250px;' title='" + podrInstNm + "'>" + podrInstNm + "</div></td>";
									htmlStr += "	<td class='tr'>" 	+ cstrnAmt + "</td>";
									htmlStr += "	<td class='tc'>" 	+ exmnBzentySeNm + "</td>";
									htmlStr += "</tr>";
								});
							} else {
								htmlStr = "<tr class='nohover'><td colspan='8' class='tc' style='color:#999999'>검색된 정보가 없습니다.</td></tr>";
							}

							var totalCount = (data.result.length == 0 ? 0 : data.result[0].totalCount);
							_pager = new Pager("divPaginator", "_pager", 1);	// global로 선언해함.  패러미터 :  divPaginator(페이징 div id),  _pager(페이저 객체를 담는 object),  1 (페이저 객체 number)
							_pager.makePaging(page, totalCount, $("#selRows").val(), $("#divTotalPage"));

							$("#tblList tbody").html(htmlStr);

							if (data.result.length > 0) {
								listEventHandler();
								$("#btnAdd").show();
							} else {
								$("#btnAdd").hide();
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

		getCcCsexList(cirsSn);
	});
	$("#tblList tbody tr:eq(0)").trigger("click");
}


// 건설업사전자료제출 목록 조회
function getCcCsexList(cirsSn) {
	$("#divDetail").hide();
	$("#tblCcCsexList tbody").empty().append(g3way.sicims.common.getWaitImg($("#tblCcCsexList tbody").width(), 5));

	$.ajax({
		url			: 	"<c:url value='/sicims300Cc/getCcCsexList.do'/>",
		data		:	{
							cirsSn		: cirsSn,		// 건설업등록기준일련번호
						},
		dataType	:	"json",
		type		:	"post",
		success		:	function(data, textStatus){
							var htmlStr = "";
							if (data.result.length > 0) {
								$.each(data.result, function(index,item){
									var cirsSn			= g3way.sicims.common.maskFormat(item.cirsSn);						// 건설업등록기준일련번호
									var exmnDataSn		= g3way.sicims.common.maskFormat(item.exmnDataSn);					// 조사자료일련번호
									var exmnDataSbmsnYmd= g3way.sicims.common.maskFormat(item.exmnDataSbmsnYmd, 'DATE');	// 조사자료제출일자
									var exmnDataCn		= g3way.sicims.common.maskFormat(item.exmnDataCn);					// 조사자료내용
									var exmnDataAtflId	= g3way.sicims.common.maskFormat(item.exmnDataAtflId);				// 조사자료첨부파일ID
									var exmnDataAtflNm	= g3way.sicims.common.maskFormat(item.exmnDataAtflNm);				// 조사자료첨부파일명
									var rmrk			= g3way.sicims.common.maskFormat(item.rmrk);						// 비고

									htmlStr += "<tr";
									htmlStr += "	data-cirsSn='" + cirsSn + "'";
									htmlStr += "	data-exmnDataSn='" + exmnDataSn + "'";
									htmlStr += ">";
									htmlStr += "	<td class='tc' style='width:60px;'>" 	+ (index + 1) + "</td>";
									htmlStr += "	<td class='tc' style='width:100px;'>" 	+ exmnDataSbmsnYmd + "</td>";
									htmlStr += "	<td class='tl' style='width:283x;'><div class='nowrap' style='width:270px;' title='" + exmnDataCn + "'>" + exmnDataCn + "</div></td>";
									htmlStr += "	<td class='tl' style='width:283px;'><div class='nowrap' style='width:270px;' title='" + exmnDataAtflNm + "'>" + exmnDataAtflNm + "</div></td>";
									htmlStr += "	<td class='tl' style='width:223px;'><div class='nowrap' style='width:210px;' title='" + rmrk + "'>" + rmrk + "</div></td>";
									htmlStr += "	<td class='tc' style='width:90px;'>";
									htmlStr += "		<a href='#none' onclick='event.stopPropagation(); g3way.sicims.common.downloadFile(\"" + exmnDataAtflId + "\");'>";
									htmlStr += "			<img title='첨부파일'	src='<c:url value='/resources/images/sub/ico_Attach.png'/>'>";
									htmlStr += "		</a>";
									htmlStr += "	</td>";
									htmlStr += "</tr>";
								});

								$("#tblCcCsexList thead").css("display", "inherit");
								$("#tblCcCsexList tbody").height(281).css("display", "inline-block").html(htmlStr);
								if ($("#tblCcCsexList tbody").hasScrollBar()) {
									$("#tblCcCsexList tbody tr").find("td:last").css("width", "74px");
								} else {
									$("#tblCcCsexList tbody").css("height", "auto");
									$("#tblCcCsexList tbody tr").find("td:last").css("width", "90px");
								}
								ccCsexlistEventHandler();
							} else {
								htmlStr = "<tr class='nohover'><td colspan='6' class='tc' style='color:#999999'>검색된 정보가 없습니다.</td></tr>";
								$("#tblCcCsexList thead").css("display", "");
								$("#tblCcCsexList tbody").css("height", "auto").css("display", "").html(htmlStr);
							}
						},
	});
}



function ccCsexlistEventHandler() {
	$("#tblCcCsexList tbody tr").mouseover(function() {
		$(this).css("cursor", "pointer");
	}).click(function() {
		$("#tblCcCsexList tbody tr").removeClass("selected");
		$(this).addClass("selected");

		var cirsSn 		= $(this).data("cirssn");
		var exmnDataSn 	= $(this).data("exmndatasn");

		viewCcCsexDetail(cirsSn, exmnDataSn);
	});

	$("#tblCcCsexList tbody tr:eq(0)").trigger("click");
}



function viewCcCsexDetail(cirsSn, exmnDataSn) {
	var url = "<c:url value='/sicims300Cc/ccCsexDetail.do'/>";
	url += "?cirsSn=" 		+ cirsSn;
	url += "&exmnDataSn=" 	+ exmnDataSn;
	url += "&timestamp=" 	+ new Date().getTime();

	$("#divDetail").show().load(url);
}


</script>
