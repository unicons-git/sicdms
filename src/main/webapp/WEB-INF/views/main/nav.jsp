<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script>
$(document).ready(function() {
	subMenuChangeEventHandler();
	menuInvokeEvent();
});


function subMenuChangeEventHandler() {

	// Navigation Bar
	$("#lnb ul.depth2 > li > a").on("click", function(){
		var lnbLiCrt = $(this).parent('li');
		var lnbLiPrv = lnbLiCrt.siblings('li.on');

		if ($(this).parent('li').children('ul.depth3').length != 0) {
			lnbLiCrt.find('ul.depth3 li').removeClass('on');
			lnbLiCrt.find('ul.depth3 li:eq(0)').addClass('on');
			$(this).parent('li').children('ul.depth3').slideDown(300, function() {
				$(this).parent('li').addClass('on');
				$(this).parent('li').siblings('li.on').removeClass('on');
			});
		} else {
			$('#lnb ul.depth3').hide();

			$(this).parent('li').addClass('on');
			$(this).parent('li').siblings('li.on').removeClass('on');
		}

		if ($(this).parent('li').siblings('li.on').children('ul.depth3').length != 0) {
			$(this).parent('li').siblings('li.on').children('ul.depth3').slideUp(300);
		}

		var mMenuId = $(this).data("mmenuid");
		var sMenuId = $(this).data("smenuid");


		moveSubmenu($(this).data("url"));
	});

	$('#lnb ul.depth3 > li > a').on("click", function() {
		$(this).parent('li').addClass('on');
		$(this).parent('li').siblings('li.on').removeClass('on');

		var mMenuId = $(this).data("mmenuid");
		var sMenuId = $(this).data("smenuid");

		moveSubmenu($(this).data("url"));
	});
}



//건설업 빅데이터 통합관리시스템 탭 메뉴 화면 이동.
function moveSubmenu(url){
	location.href = url;
}


function menuInvokeEvent() {
	var mMenuId = "";
	var sMenuId = "";
	var depth3Yn = "N";

	var targetsel = $(".depth2 a[data-url='" + this.location.pathname + "']");

	if(targetsel.length == 0){
		$("#lnb").hide();
	} else {
		mMenuId = $(targetsel).data('mmenuid');
		sMenuId = $(targetsel).data('smenuid');
		depth3Yn = $(targetsel).closest("ul").is('.depth3') ? "Y": "N";

		topMenuset(mMenuId, sMenuId, depth3Yn);
	}
}


//상위 메뉴를 이용하지 않고 화면 이동시 상단 네비게이션 설정함수
function topMenuset(mMenuId, sMenuId, depth3Yn){
	// header부분의 메인 클릭 시
	if ( mMenuId != undefined ) {
		// header부분 on 변경
		$(".header__menu ul#gnb li").find("a").removeClass("on");
		$(".header__menu ul#gnb li").find("a[data-mmenuid='" + mMenuId + "']").eq(0).addClass("on");
	}

 	// header부분의 메인 클릭 시
	if ( mMenuId != undefined && sMenuId != undefined) {
		// header부분 on 변경
		$(".header__menu ul#gnb li.has-depth2 ul.depth2 li").find("a").removeClass("on");
		$(".header__menu ul#gnb li.has-depth2 ul.depth2 li").find("a[data-mmenuid='" + mMenuId + "'][data-smenuid='" + parseInt(sMenuId.toString().substr(0,1)) + "']").addClass("on");
	}

	var title	= $('header ul#gnb > li:eq(' + (mMenuId -1) + ') > a').text();

	if (title == "") {
	 	if(mMenuId == 5) title = "커뮤니티";
	 	if(mMenuId == 6) title = "자료제출";
	 	if(mMenuId == 9) title = "관리자";
	}

	$("#lnb ul#nav" + mMenuId).show();
 	$("#lnb div.title").text(title);

 	$("#lnb ul#nav" + mMenuId + " > li").find("a[data-mmenuid='" + mMenuId + "'][data-smenuid^='" + sMenuId.toString().substr(0, 1) + "']").eq(0).parent("li").addClass("on");

 	// Depth 3
 	$("#lnb ul#nav" + mMenuId + " > li").find("a[data-mmenuid='" + mMenuId + "'][data-smenuid='" + sMenuId + "']").parent("li").addClass("on");

}


//main
function main() {
	window.top.location.href = "<c:url value='/main.do'/>";
}
</script>
<div id="lnb">
	<div class="title h2"></div>
	<!-- 6. 자료제출 -->
	<ul id="nav6" class="depth2" style="display:none;">
		<li>
			<a href="javascript:;" data-mmenuid="6" data-smenuid="11" style="background:none;"  data-url ="<c:url value='/sicims300Cc/ccCsex.do'/>">건설업사전자료제출</a>
		</li>
	</ul>

</div>

