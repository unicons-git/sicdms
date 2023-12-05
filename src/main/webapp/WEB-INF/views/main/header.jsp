<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authorize access="isAuthenticated()">
   	<sec:authentication var="loginUserVo" property="principal" />
</sec:authorize>
<script>

$(document).ready(function(){
	headerInvokeEvent();
	$.ajaxSetup({
		beforeSend	:	function(xhr, settings) {
			xhr.setRequestHeader("AJAX", "Yes");
		},
		statusCode : {
			200: function(a,b,c) {
				fnLoadingEnd();
				if(a.state != undefined && b != undefined && a.state() == 'rejected' && b == 'parsererror'){
					g3way.sicims.common.messageBox(null, "로그인 세션 만료", '로그인 세션이 만료되어 홈페이지로 이동합니다.', function(){window.top.location.href="<c:url value='/login/homepage.do'/>";}, null, 'Y');
				}
	        },
			302: function() {
				fnLoadingEnd();
				g3way.sicims.common.messageBox(null, "로그인 세션 만료", '로그인 세션이 만료되어 홈페이지로 이동합니다.', function(){window.top.location.href="<c:url value='/login/homepage.do'/>";}, null, 'Y');
	        },
	        401: function() {
	        	fnLoadingEnd();
	        	//window.top.location.href = "<c:url value='/login/login.do'/>";
	        	g3way.sicims.common.messageBox(null, "로그인 세션 만료", '로그인 세션이 만료되어 홈페이지로 이동합니다. ', function(){window.top.location.href = "<c:url value='/login/homepage.do'/>";}, null, 'N');
	        },
			404: function(a, status, c) {
				fnLoadingEnd();
				var message = "요청한 페이지가 없습니다.";
				if(this.type == "POST"){
					message = "요청 주소가 잘못됐습니다."
				}

				if(this.url == "<c:url value='/sicims000Cm/messageBox.do'/>" && status == "error"){
					$.modal.close();
					alert('서버에서 오류가 발생했습니다. 잠시 후 다시 시도해 주시기 바랍니다.');
				} else {
		    		g3way.sicims.common.messageBox(null, "페이지 오류", message, null);
				}
		    },
		    406: function() {
		    	fnLoadingEnd();
	        	//window.top.location.href = "<c:url value='/login/login.do'/>";
	        	g3way.sicims.common.messageBox(null, "권한없음", "해당 기능에 대한 권한이 없습니다.", null , 'N');

	        },
			500: function() {
				fnLoadingEnd();
				var message = "요청한 페이지에 오류가 발생했습니다.";
				if(this.type == "POST"){
					message = "요청한 페이지에 오류가 발생했습니다."
				}
		    	g3way.sicims.common.messageBox(null, "페이지 오류", message, null);
		    }
	    }
	});
});

//main
function main() {
	window.top.location.href = "<c:url value='/main.do'/>";
}

// logout

function login() {
	window.top.location.href = "<c:url value='/login/homepage.do'/>";
}

function logout() {
	window.location.href = "<c:url value='/login/logout.do'/>";
}

function modifyMember() {
	window.location.href  ="<c:url value='/login/memberInfo/modifyMemberCheck.do'/>";
}


//메인페이지 링크 클릭 이벤트
function menuLinkMove(mMenuId, sMenuId){
	var mMenuId = $(this).data('mMenuId'.toLowerCase());
	var sMenuId = $(this).data('sMenuId'.toLowerCase());
	changeSubmenu(mMenuId, sMenuId);
}

//Header Invoke Event
function headerInvokeEvent() {
	topMenuEventHandler();				// Top Menu

	//로그아웃 버튼 이벤트
	//header.jsp 등록된 이벤트 호출
	$("#btnHeaderLogOut").on('click', logout);

	//개인정보 수정 버튼 이벤트
	//header.jsp 등록된 이벤트 호출
	$("#btnHeaderModifyMember").on('click', modifyMember);

}

//Top Menu Event Handler
function topMenuEventHandler() {
	$('#gnb > li').on({
		mouseenter: function(){
			$('#header,.black__dimmed').addClass('extend')

		},
		mouseleave: function(){
			$('#header,.black__dimmed').removeClass('extend')
		}
	});

	$('.header__menu #gnb > li').each(function() {
		if ($(this).find('ul.depth2').length) {
			$(this).addClass('has-depth2');
		}
	});


	// 클릭한 menu의 메인페이지
	$('.header__menu #gnb > li > a').on('click', function(e) {
		$(this).parent().siblings().children("a").removeClass("on");
		$('.header__menu #gnb > li.has-depth2 > ul.depth2 > li').children().removeClass("on");
		$(this).addClass("on");
		$(this).siblings().children("li").find('a').eq(0).addClass('on')
		var mMenuId = $(this).data("mmenuid");
		var sMenuId = $(this).data("smenuid");

		changeSubmenu(mMenuId, sMenuId);
	});


	// 클릭한 menu로 이동
	$('header #gnb ul.depth2 > li > a').on('click', function(e) {
		$('.header__menu #gnb > li.has-depth2 > a').removeClass("on");
		$(this).parent().parent().parent().siblings().children("a").removeClass("on");
		$(this).parent().parent().parent().children("a").addClass("on");
		$('.header__menu #gnb > li.has-depth2 > ul.depth2 > li').children().removeClass("on");
		$(this).addClass("on");
		var mMenuId = $(this).data("mmenuid");
		var sMenuId = $(this).data("smenuid");

		changeSubmenu(mMenuId, sMenuId);
	});
}

function fnLoadingStart() {
	fnLoadingEnd();
	var backHeight = $(document).height(); //뒷 배경의 상하 폭
	var backWidth = window.document.body.clientWidth; //뒷 배경의 좌우 폭
	var backGroundCover = "<div id='back'></div>"; //뒷 배경을 감쌀 커버
	var loadingBarImage = ''; //가운데 띄워 줄 이미지
	loadingBarImage += "<div id='loadingBar'>";
	loadingBarImage += " <img src='<c:url value='/resources/images/common/loading.gif'/>' width = 100 height =100 />"; //로딩 바 이미지
	loadingBarImage += "</div>";
	$('body').append(backGroundCover).append(loadingBarImage);
	$('#back').css({ 'width': backWidth, 'height': backHeight, 'opacity': '0.3','z-index': '9999'});
	$('#back').show();
	$('#loadingBar').show();
}

function fnLoadingEnd() {
	$('#back, #loadingBar').hide();
	$('#back, #loadingBar').remove();
}

function sicimsLoadingStart() {
	sicimsLoadingEnd();
	var backHeight = $(document).height(); //뒷 배경의 상하 폭
	var backWidth = window.document.body.clientWidth; //뒷 배경의 좌우 폭
	var backGroundCover = "<div id='backgroundCover'></div>"; //뒷 배경을 감쌀 커버
	var loadingBarImage = ''; //가운데 띄워 줄 이미지
	loadingBarImage += "<div id='loadingBarImage'>";
	loadingBarImage += " <img src='<c:url value='/resources/images/common/loading.gif'/>' width = 100 height =100 />"; //로딩 바 이미지
	loadingBarImage += "</div>";
	$('body').append(backGroundCover).append(loadingBarImage);
	//$('body #backgroundCover').append(loadingBarImage);
	$('#backgroundCover').css({ 'width': backWidth, 'height': backHeight, 'opacity': '0.3','z-index': '9999'});
	$('#backgroundCover').show();
	$('#loadingBarImage').show();
}

function sicimsLoadingEnd() {
	$('#backgroundCover, #loadingBarImage').hide();
	$('#backgroundCover, #loadingBarImage').remove();
}

//건설업 빅데이터 통합관리시스템 탭 메뉴 화면 이동.
function changeSubmenu(mMenuId, sMenuId){
	if ( sMenuId == undefined || sMenuId == 0 ) {
		$('#lnb ul#nav' + mMenuId).children('li:eq(0)').children().trigger('click');
	} else if( sMenuId != undefined && sMenuId.toString().length == 2 ) {
		$('#lnb ul#nav' + mMenuId).find('a[data-mmenuid='+mMenuId+'][data-smenuid='+sMenuId+']').trigger('click');
	} else {
		$('#lnb ul#nav' + mMenuId).find('a[data-mmenuid='+mMenuId+'][data-smenuid='+sMenuId+'1]').trigger('click');
	}
}
</script>

<div class="container">
	<h1 class="logo"><a href="javascript:;" onclick="main();" title="홈바로가기"><span>건설업 빅데이터 통합관리시스템</span></a></h1>
	<sec:authorize access="isAuthenticated()">
		<c:if test = "${empty loginYn or loginYn eq 'N'}">
			<div class="header__menu">
				<ul id="gnb">
					<sec:authorize access="hasAnyRole('ROLE_ECMP')">
					<div class="header__menu">
						<ul id="gnb">
							<li><a href="javascript:;" data-mmenuid="6" data-smenuid="1">자료제출</a>
								<ul class="depth2">
									<li><a href="javascript:;" data-mmenuid="6" data-smenuid="1">건설업사전자료제출</a></li>
								</ul>
							</li>
						</ul>
					</div>
					</sec:authorize>
				</ul>
			</div>
		</c:if>
	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
		<c:if test = "${empty loginYn or loginYn eq 'N'}">
			<div class="header__top__util">
		    	<div>
		     		<a href="javascript:;" class="top__profile btn_top_util" title="로그인 정보" ><span class = 'blind'>로그인 정보</span></a>
					<!-- // push 창 -->

					<div class="top__box box__date">
						<div class="box_content">
						    <!-- <div class="datepickerfull"></div> -->
						</div>
					</div>
					<!-- // 달력창 -->

					<div class="top__box box__profile">
						<div class="box_content">
							<div class="avatar__area">
								<div class="avatar__link">
									<span class="img"></span>
									<div class="avatar_detail">
										<p><c:out value = "${loginUserVo.egovUserVO.inst1Nm}"/></p>
										<p class="name"><c:out value = "${loginUserVo.egovUserVO.userNm}"/></p>
									</div>
								</div>

								<div style = "margin-left: 35px; bottom: 40px; position: absolute;">
									<p class="name">마지막 접속일 : ${fn:escapeXml(loginUserVo.egovUserVO.lastLgnYmd)}</p>
								</div>
							</div>
							<div class="profile__links">
							 <!-- 	<a href="javascript:void(0);" id="btnHeaderModifyMember" title="회원정보 수정 페이지 이동" >회원정보 수정</a> -->
								<a href="javascript:void(0);" id="btnHeaderLogOut" title="로그아웃" >로그아웃</a>
							</div>
						</div>
					</div>
					<!-- // 프로필 -->

				</div>
			</div>
		</c:if>
	</sec:authorize>
</div>

<div class="header__gnb__bg"></div>

<div class="header__top"></div>
