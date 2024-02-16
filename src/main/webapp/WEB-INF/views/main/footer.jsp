<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script>
$(document).ready(function(){
	foorterInvokeEvent();
});

function foorterInvokeEvent() {
	footerLinkEventHandler();				// Top Menu
}

function footerLinkEventHandler() {
	$('.footer__links .container > ul > li').each(function() {
		$(this).addClass('has-depth1');
	});

	//클릭한 menu로 이동
	$('.footer__links .container > ul.depth1 > li a').on('click', function(e) {
		$('.footer__links .container > li.has-depth1 > a').removeClass("on");
		$(this).addClass("on");
		var mMenuId = $(this).data("mmenuid").toString();
		var sMenuId = $(this).data("smenuid").toString();

		$("#breadcrumb ul.list #depth1 > a").prop("text", "시스템 안내");
		$("#breadcrumb ul.list #depth2 > a").prop("text", $(this).text());

		moveSubmenu(mMenuId + sMenuId);
	});
}
</script>

<footer id="footer" class="footer">
    <!-- <div class="footer__links">
        <div class="container">
            <ul class="depth1">
            	<li><a href="javascript:;" data-mmenuid="10" data-smenuid="11">시스템 소개</a></li>
		        <li><a href="javascript:;" data-mmenuid="10" data-smenuid="21">이용약관</a></li>
		        <li><a href="javascript:;" data-mmenuid="10" data-smenuid="31">개인정보처리방침</a></li>
		        <li><a href="javascript:;" data-mmenuid="10" data-smenuid="41">공공데이터 이용정책</a></li>
            </ul>
        </div>
    </div> -->
    <div class="footer__contents">
        <div class="container">
            <h2 class="footer__logo"><span>서울특별시</span></h2>
            <div class="footer__addr">
               	서울특별시 중구 세종대로 110 서울특별시청 10층 <br>
               	재난안전관리실 건설혁신과  02-2133-8120<br>
            </div>
            <p class="footer__copy">Copyright &copy; 2023 sicims.seoul.go.kr. All Rights Reserved. </p>
        </div>
    </div>
</footer>
