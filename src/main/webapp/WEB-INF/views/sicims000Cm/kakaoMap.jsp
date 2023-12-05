<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.custom_zoomcontrol {position:absolute;top:20px;left:10px;width:36px;height:80px;overflow:hidden;z-index:1;background-color:#f5f5f5;}
.custom_zoomcontrol span {display:block;width:36px;height:40px;text-align:center;cursor:pointer;}
.custom_zoomcontrol span img {width:15px;height:15px;padding:12px 0;border:none;}
.custom_zoomcontrol span:first-child{border-bottom:1px solid #bfbfbf;}
/* .radius_border{border:1px solid #919191;border-radius:5px;} */
</style>

<div id="rvContainer" style="position:relative; overflow:hidden; width:100%;height:100%;">
	<div id="roadview" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
    <button id="roadviewClose" type="button" class="roadview" style="padding:0px;">
		<span style="color:white;">로드뷰 종료</span>
    </button>

   	<div id="mapContainer" style="overflow:hidden;">
<!-- 			<div id="kakaoMap" style="width:99.5%;height:100%;position:relative;overflow:hidden;"></div> -->
		<div id="kakaoMap" style="width:99.5%;height:100%;"></div>
	    <div class="custom_zoomcontrol radius_border">
	        <span onclick="zoomIn()"><img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_plus.png" alt="확대"></span>
	        <span onclick="zoomOut()"><img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_minus.png" alt="축소"></span>
	    </div>
	</div>
</div>



<script>
var _kakaomap 	= null;
var _roadview	= null;
var _rvClient	= null;
var _rvMarker	= null;
var _mapCenter	= null;

$(function () {
	$("#rvContainer").css("height", window.innerHeight);

	$("#mapContainer").dialog({
		"title" 			: "로드뷰",
		"width" 			: 400,
		"height" 			: 400,
		//"left"				: 0,
		//"bottom"			: 0,
		"resizable" 		: true,
		"draggable"			: true,
		"close"				: function(){
								$(this).remove();
	  						  },
		"resize"			: function(evt, ui){
								_kakaomap.relayout();
							  }
	}).dialogExtend({
		"closable" 			: false,
		"minimizable" 		: true,
		"minimizeLocation" 	: "left",
		"dblclick" 			: "minimize",
		"icons" 			: {
								"minimize" : "ui-icon-minus"
      						  },
		"load"				: function(evt, dlg){
								//$(this).parent().css("height", "360");
								$(this).parent().css("left", 10);
								//$(this).parent().css("top", "");
								//$(this).parent().css("bottom", "0px");
								$(this).parent().css("padding", "0px");
								$(this).parent().css("border-radius", "0px");
								$(this).parent().css("border", "1px solid #285e84");
								$(".ui-widget-header").css("font-weight", "bold");
								$(".ui-dialog-title").each(function() {
									$(this).parent().css("background", "#285e84");
									$(this).parent().css("border", "0px");
									$(this).parent().css("border-radius", "0px");
									$(this).parent().css("padding", "0px");
									$(this).css("color", "#ffffff");
									$(this).css("font-size", "13px");
									$(this).css("padding", "4px 0 4px 10px");
								});
								$(".ui-dialog-titlebar-buttonpane").css("top", "0");
								$(".ui-dialog-titlebar-buttonpane").css("right", "0");
								$(".ui-dialog-titlebar-buttonpane").css("margin-top", "0px");
								$(".ui-icon").css("display", "none");
								$(this).parent().find(".ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default").css("border", "0px");
								$(this).parent().find(".ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default").css("background", "rgba(255, 255, 255, 0)");
								$(".ui-dialog-titlebar-buttonpane button").css("border-radius", "0px");
								$(".ui-dialog-titlebar-buttonpane a").css("border-radius", "0px");
								$(".ui-dialog .ui-dialog-content").css("padding", "0px");
								$(".ui-dialog .ui-dialog-titlebar-close").css("width", "32");
								$(".ui-dialog .ui-dialog-titlebar-close").css("height", "24");
								$(".ui-dialog .ui-dialog-titlebar-close").css("background-image", "url(/sicims/resources/images/btn/btn_pop_close.png)");
								$(".ui-dialog .ui-dialog-titlebar-minimize").css("width", "33");
								$(".ui-dialog .ui-dialog-titlebar-minimize").css("height", "24");
								$(".ui-dialog .ui-dialog-titlebar-minimize").css("background-image", "url(/sicims/resources/images/btn/btn_pop_min.png)");
								$(".ui-dialog .ui-dialog-titlebar-restore").css("width", "33");
								$(".ui-dialog .ui-dialog-titlebar-restore").css("height", "24");
								$(".ui-dialog .ui-dialog-titlebar-restore").css("background-image", "url(/sicims/resources/images/btn/btn_pop_max.png)");
								//$(".ui-dialog .ui-dialog-titlebar-close").hide();

				  },

	});

	initRoadview();
	loadKakaoMap();
	invokeKakaoEvent();
});

//로드뷰
function initRoadview() {
	_roadview = new kakao.maps.Roadview(document.getElementById('roadview'));

	//좌표로부터 로드뷰 파노라마 ID를 가져올 로드뷰 클라이언트 객체를 생성합니다
	_rvClient = new kakao.maps.RoadviewClient();
}

// 카카오맵
function loadKakaoMap() {
	var lat = Number('<c:out value="${info.lat}"/>');
	var lon = Number('<c:out value="${info.lon}"/>');

	_mapCenter = new kakao.maps.LatLng(lat, lon); // 지도의 중심좌표
	_kakaomap = new kakao.maps.Map(document.getElementById('kakaoMap'), {
			center	: _mapCenter, // 지도의 중심좌표
			level	: 5 // 지도의 확대 레벨
		}
	);

	_kakaomap.relayout();
	setRoadviewMaker();

    // 지도 위에 로드뷰 도로 오버레이를 추가합니다
    _kakaomap.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);
    // 지도 위에 마커를 표시합니다
	_rvMarker.setMap(_kakaomap);
    // 마커의 위치를 지도 중심으로 설정합니다
    _rvMarker.setPosition(_kakaomap.getCenter());
    // 로드뷰의 위치를 지도 중심으로 설정합니다
    displayRoadview(_kakaomap.getCenter());
}


//마커표시
function setRoadviewMaker() {
	//마커 이미지를 생성합니다
	var markImage = new kakao.maps.MarkerImage(
		'https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png',
		new kakao.maps.Size(26, 46),
			{
				spriteSize	: new kakao.maps.Size(1666, 168),
			    spriteOrigin: new kakao.maps.Point(705, 114),
			    offset		: new kakao.maps.Point(13, 46)
			}
	);

	//드래그가 가능한 마커를 생성합니다
	_rvMarker = new kakao.maps.Marker({
		image 		: markImage,
		position	: _mapCenter,
		draggable	: true
	});

}



//invoke event
function invokeKakaoEvent() {
	//로드뷰에 좌표가 바뀌었을 때 발생하는 이벤트를 등록합니다
	kakao.maps.event.addListener(_roadview, 'position_changed', function() {
		var rvPosition = _roadview.getPosition();
		_kakaomap.setCenter(rvPosition);

    	_rvMarker.setPosition(rvPosition);
	});


	//마커에 dragend 이벤트를 등록합니다
	kakao.maps.event.addListener(_rvMarker, 'dragend', function(mouseEvent) {
		// 현재 마커가 놓인 자리의 좌표입니다
		var position = _rvMarker.getPosition();

		// 마커가 놓인 위치를 기준으로 로드뷰를 설정합니다
		displayRoadview(position);
	});

	//지도에 클릭 이벤트를 등록합니다
	kakao.maps.event.addListener(_kakaomap, 'click', function(mouseEvent){
		// 클릭한 위치의 좌표입니다
		var position = mouseEvent.latLng;
		// 마커를 클릭한 위치로 옮깁니다
		_rvMarker.setPosition(position);
		// 클락한 위치를 기준으로 로드뷰를 설정합니다
		displayRoadview(position);
	});

	$("#roadviewClose").click(function() {
		closeDialog();
		$("body").css({"overflow":""});	// 원래대로
		$(".kakaoContainer").hide();
	})
}

//전달받은 좌표(position)에 가까운 로드뷰의 파노라마 ID를 추출하여
//로드뷰를 설정하는 함수입니다
function displayRoadview(position){
	_rvClient.getNearestPanoId(position, 30, function(panoId) {
		if (panoId == null) {
	   		// 파노라마 ID가 null 이면 시청으로
	   		panoId = 1050215196
		}

   		_kakaomap.setCenter(position);
    	if (panoId != null) {
        	_roadview.setPanoId(panoId, position);
    	}
	});
}

//지도 확대, 축소 컨트롤에서 확대 버튼을 누르면 호출되어 지도를 확대하는 함수입니다
function zoomIn() {
	_kakaomap.setLevel(_kakaomap.getLevel() - 1);
}

// 지도 확대, 축소 컨트롤에서 축소 버튼을 누르면 호출되어 지도를 확대하는 함수입니다
function zoomOut() {
	_kakaomap.setLevel(_kakaomap.getLevel() + 1);
}

function closeDialog() {
	var dialog = $(".ui-dialog").find(".ui-dialog-content");
	if ( $(dialog).length ) {
		$(dialog).dialog("close");
	}
}

</script>