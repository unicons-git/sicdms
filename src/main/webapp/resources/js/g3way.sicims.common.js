﻿﻿﻿﻿var g3way = {
		sicims	: {},
};


g3way.sicims.common = {
		contextPath	: "",
		imageWindow	: null,
		editObj		: null,		// input, select object
		msgTitle	: null,		// 알림창  제목
		msgContent	: null,		// 알림창  내용
		callback	: null,		// 알림창 - callback 함수
		time		: 0,		// 타이머 시간

		init : function() {
			this.contextPath = "/" + window.location.pathname.split("/")[1];
			if (this.contextPath == "/" || this.contextPath.indexOf("/login") >=0 || this.contextPath.indexOf("/main") >=0) {
				this.contextPath = "";
			}

			this.contextMenuEventHandler();
			this.avoidBackspaceKey();
			this.afterAjaxStop();
			this.movePageEventHandler();
		},

		// 컨텍스트 메뉴
		contextMenuEventHandler : function () {
			$(document)[0].oncontextmenu = function() {
				return false;
			};
		},

		// BackSpace 키 방지 이벤트
		avoidBackspaceKey : function() {
			// BackSpace 키 방지 이벤트
			$(document).keydown(function(e){
			    var backspace = 8;
			    var t = document.activeElement;

			    if (e.keyCode == backspace) {
			        if (t.tagName == "SELECT")
			            return false;

			        if (t.tagName == "INPUT" && (t.getAttribute("readonly") == true || t.getAttribute("readonly") == "readonly"))
			            return false;
			    }
			});
		},

		// Ajax 후 처리
		afterAjaxStop : function() {
			// AJAX 통신 후 Mybatis 칼럼 값 처리
			$(document).ajaxStop(function(){
				$('article').find('td:contains("undefined")').each(function(inx) {
					//테이블의 안에 조회하는경우 예외처리
					if(this.childElementCount ==  0){
						$(this).html("");
					}
				});
				$('article').find('td:contains("null")').each(function(inx) {
					//테이블의 안에 조회하는경우 예외처리
					if(this.childElementCount ==  0){
						$(this).html("");
					}
				});
			});
		},

		// 페이지 이동 이벤트 핸들러
		movePageEventHandler : function () {
			// 홈페이지
			$(".initPage").click(function() {
				changeSubmenu(0, 1, 0, "Home");
			});

			// Top 메뉴
			$(".menuPage").click(function() {
				changeSubmenu($(this).data("menuinx"), $(this).data("submenuinx"), 0, $(this).data("title"));
			});

			// 서브 메뉴
//			$(".subMenuPage").click(function() {
//				changeSubmenu($(this).data("menuinx"), $(this).data("submenuinx"), 2, $(this).data("title"));
//			});

		},

		datePicker : function (id) {
			$("#" + id).attr('readOnly', 'readOnly');
			var dates = $("#" + id).datepicker(	{
				showOn 			: "both",
				buttonImage 	: this.contextPath + "/resources/images/icons/icon_calendar.gif",
				buttonImageOnly : true,
				minDate			: '-100Y',
				prevText 		: '이전 달',
				nextText 		: '다음 달',
				monthNames 		: [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
				monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
				dayNames 		: [ '일', '월', '화', '수', '목', '금', '토' ],
				dayNamesShort 	: [ '일', '월', '화', '수', '목', '금', '토' ],
				dayNamesMin 	: [ '일', '월', '화', '수', '목', '금', '토' ],
				dateFormat 		: 'yy-mm-dd',
				showMonthAfterYear : true,
				//yearRange		: '1980:c+2',
				yearSuffix 		: '년',
				changeYear 		: true,
				changeMonth 	: true
			});
		},

		datePickerEdit : function (id) {
			var dates = $("#" + id).datepicker(	{
				showOn 			: "both",
				buttonImage 	: this.contextPath + "/resources/images/icons/icon_calendar.gif",
				buttonImageOnly : true,
				prevText 		: '이전 달',
				nextText 		: '다음 달',
				monthNames 		: [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
				monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
				dayNames 		: [ '일', '월', '화', '수', '목', '금', '토' ],
				dayNamesShort 	: [ '일', '월', '화', '수', '목', '금', '토' ],
				dayNamesMin 	: [ '일', '월', '화', '수', '목', '금', '토' ],
				dateFormat 		: 'yymmdd',
				showMonthAfterYear : true,
				/*yearRange		: '2016:c+2',*/
				yearSuffix 		: '년',
				changeYear 		: true,
				changeMonth 	: true
			});

		},
		datePickerFormToDate : function(fromDateId, toDateId) {
             //datepicker 한국어로 사용하기 위한 언어설정
             $.datepicker.setDefaults($.datepicker.regional['ko']);

             //시작일.
             $("#"+fromDateId).attr('readOnly', 'readOnly');
             $("#"+fromDateId).datepicker({
            	showOn 			: "both",
 				buttonImage 	: this.contextPath + "/resources/images/icons/icon_calendar.gif",
 				buttonImageOnly : true,
 				prevText 		: '이전 달',
 				nextText 		: '다음 달',
 				monthNames 		: [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
 				monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
 				dayNames 		: [ '일', '월', '화', '수', '목', '금', '토' ],
 				dayNamesShort 	: [ '일', '월', '화', '수', '목', '금', '토' ],
 				dayNamesMin 	: [ '일', '월', '화', '수', '목', '금', '토' ],
 				dateFormat 		: 'yy-mm-dd',
 				showMonthAfterYear : true,
 				/*yearRange		: '2016:c+2',*/
 				yearSuffix 		: '년',
 				changeYear 		: true,
 				changeMonth 	: true,
                onClose			: function( selectedDate ) {
                	// 시작일(fromDate) datepicker가 닫힐때
                	// 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
                	$("#"+toDateId).datepicker( "option", "minDate", selectedDate );
                }
             });
             $("#"+fromDateId).datepicker( "option", "maxDate",  $("#"+toDateId).val() );

             //종료일
             $("#" + toDateId).attr('readOnly', 'readOnly');
             $("#"+toDateId).datepicker({
            	showOn 			: "both",
  				buttonImage 	: this.contextPath + "/resources/images/icons/icon_calendar.gif",
  				buttonImageOnly : true,
  				prevText 		: '이전 달',
  				nextText 		: '다음 달',
  				monthNames 		: [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
  				monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
  				dayNames 		: [ '일', '월', '화', '수', '목', '금', '토' ],
  				dayNamesShort 	: [ '일', '월', '화', '수', '목', '금', '토' ],
  				dayNamesMin 	: [ '일', '월', '화', '수', '목', '금', '토' ],
  				dateFormat 		: 'yy-mm-dd',
  				showMonthAfterYear : true,
  				/*yearRange		: '2016:c+2',*/
  				yearSuffix 		: '년',
  				changeYear 		: true,
  				changeMonth 	: true,
				onClose			: function( selectedDate ) {
					// 종료일(toDate) datepicker가 닫힐때
					// 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정
					$("#"+fromDateId).datepicker( "option", "maxDate", selectedDate );
				}
             });
             $("#"+toDateId).datepicker( "option", "minDate", $("#"+fromDateId).val());
         },

		//대표주소 검색 관련 event handler
		keyEvent : function() {
			// 숫자만 입력 가능
			$(".isDigit").keypress(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				if ( (keyID >= 48 && keyID <= 57) || keyID == 8)
					return;
				else
					return false;
			});

			// 숫자만 입력 가능
			$(".isNum").keypress(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				if ( (keyID >= 48 && keyID <= 57) || keyID == 8)
					return;
				else
					return false;
			}).keyup(function(event){
				var v = event.target.value;
				v = v.replace(/[^0-9]/gi, ""); // 숫자
				var regExp = /([+-]?\d+)(\d{3})(\.\d+)?/; // 정규식
				while ( regExp.test(v) ) v = v.replace( regExp, "$1" + "," + "$2" + "$3" ); // 쉼표 삽입.
				event.target.value = v;
			});

			// 숫자만 입력 가능
			$(".isNumNoComma").keypress(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				if ( (keyID >= 48 && keyID <= 57) ||  keyID == 8 )
					return;
				else
					return false;
			}).keyup(function(event){
				var v = event.target.value;
				v = v.replace(/[^0-9]/gi, ""); // 숫자
				event.target.value = v;
			});


			$(".isDouble").keypress(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				if ( (keyID >= 48 && keyID <= 57) || keyID == 8 || keyID == 46 || keyID == 45)
					return;
				else
					return false;
			}).keyup(function(event){
				event = event || window.event;
				var v = event.target.value;
				v = v.replace(/[^0-9^.^-]/gi, ""); // 숫자와 .

				var iPart = v;
				var fPart = "";
				if (v.indexOf(".") >= 0) {
					iPart = v.substring(0, v.indexOf("."));
					fPart = v.substring(v.indexOf("."));
				}

				var regExp = /([+-]?\d+)(\d{3})(\.\d+)?/; // 정규식
				while ( regExp.test(iPart) ) iPart = iPart.replace( regExp, "$1" + "," + "$2" + "$3" ); // 쉼표 삽입.
				event.target.value = iPart + fPart;
			});

			// 온도
			$(".isTp").keypress(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				if ( (keyID >= 48 && keyID <= 57) || keyID == 8 || keyID == 46)
					return;
				else
					return false;
			}).keyup(function(event){
				event = event || window.event;
				var v = event.target.value;

				if (v.length > 1 && isNaN(v)) {
					event.target.value = v.substring(0,v.length-1)
				} else {
					v = v.replace(/[^0-9^.^-]/gi, ""); // 숫자와 .

					var iPart = v;
					var fPart = "";
					if (v.indexOf(".") >= 0) {
						iPart = v.substring(0, v.indexOf("."));
						fPart = v.substring(v.indexOf("."));
					}
				}
			});

			// 습도
			$(".isHmdty").keypress(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				if ( (keyID >= 48 && keyID <= 57) || keyID == 8 || keyID == 46 )
					return;
				else
					return false;
			}).keyup(function(event){
				event = event || window.event;
				var v = event.target.value;

				if (v.length > 1 && isNaN(v)) {
					event.target.value = v.substring(0,v.length-1)
				} else {
					v = v.replace(/[^0-9^.^-]/gi, ""); // 숫자와 .

					var iPart = v;
					var fPart = "";
					if (v.indexOf(".") >= 0) {
						iPart = v.substring(0, v.indexOf("."));
						fPart = v.substring(v.indexOf("."));
					}
				}
			});

			// 소숫점 이하(-0.999999999 ~ 0.99999999)
			$(".isFactor").keypress(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				if ( (keyID >= 48 && keyID <= 57) || keyID == 8 || keyID == 46 || keyID == 45)
					return;
				else
					return false;
			}).keyup(function(event){
				event = event || window.event;
				var v = event.target.value;
				if (isNaN(v)) {
					event.target.value = v.substring(0,v.length-1)
				} else {
					//v = v.replace(/[^0-9][^.][^-]/gi, ""); // 숫자/./- 제외
					//var regExp = /^[-]?[0,1]?^\d?.?\d{0,9}$/; // 정규식
					var regExp = /^$|^-$|^-?[0]([.]\d{0,9})?$|^-?[1]([.][0]{0,9})?$|^-?[0]([.][0]{0,9})?$/; // 정규식
					if(!regExp.test(v)){
						event.target.value = v.substring(0,v.length-1)
						if(event.target.value > 1 ){
							event.target.value = 1;
						}
						else if( event.target.value < -1){
							event.target.value = -11;
						}
					}
				}
			});

			// 소숫점 이하(0~1)
			$(".isRate").keypress(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				if ( (keyID >= 48 && keyID <= 57) || keyID == 8 || keyID == 46)
					return;
				else
					return false;
			}).keyup(function(event){
				event = event || window.event;
				var v = event.target.value;
				if (isNaN(v)) {
					event.target.value = v.substring(0,v.length-1)
				} else {
					v = v.replace(/[^0-9][^.][^-]/gi, ""); // 숫자/./- 제외
					var regExp = /[]{0,1}^\d?.?\d{0,9}$/; // 정규식
					if(!regExp.test(v)){
						event.target.value = v.substring(0,v.length-1)
					}
				}
			});

			// 영문만 입력 가능
			$(".isEng").keyup(function(event){
				event = event || window.event;
				event.target.value = event.target.value.replace(/[^A-Za-z]/gi, "");
			});

			// 영문대문자만 입력 가능
			$(".isEngUpper").keyup(function(event){
				event = event || window.event;
				event.target.value = event.target.value.replace(/[^A-Z]/gi, "");
			});

			// 약어명 영문대문자|숫자|_
			$(".isStdtAbbr").keyup(function(event){
				event = event || window.event;
				event.target.value = event.target.value.replace(/[^A-Z0-9_]/gi, "");
			});


			// 영문소문자만 입력 가능
			$(".isEngLower").keyup(function(event){
				event = event || window.event;
				event.target.value = event.target.value.replace(/[^a-z]/gi, "");
			});


			// 숫자와 영문만 입력 가능
			$(".isEngOrNum").keyup(function(event){
				event = event || window.event;
				event.target.value = event.target.value.replace(/[^A-Za-z0-9]/gi, "");
			});

			// 영문만 입력 가능
			$(".isEngOrBlank").keyup(function(event){
				event = event || window.event;
				event.target.value = event.target.value.replace(/[^A-Za-z\ ]/gi, "");
			});


			// 년도 - 숫자만 입력 가능
			$(".isYear").keyup(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				event.target.value = event.target.value.replace(/[^0-9]/gi, "");
			});

			// 년월일 - 숫자만 입력가능
			$(".isYmd").keyup(function(event){
				// 먼저 기존에 들어가 있을 수 있는 콜론(-)기호를 제거한다.
			    var replaceTime = this.value.replace(/[^0-9]/gi, "");
			    if(replaceTime.length > 8) {
			    	replaceTime = replaceTime.substr(0,8);
			    }

			    var year = "";
			    var month = "";
			    var day = "";

		        year = replaceTime.substring(0, 4);
			    if(replaceTime.length >= 5 ) {
			    	month = replaceTime.substring(4, 6);
			    }
			    if(replaceTime.length >= 7 ) {
			    	day = replaceTime.substring(6, 8);
			    }

				if(day != '') {
					this.value = year + "-" + month + "-" + day;
				}
				else if(month != '') {
					this.value = year + "-" + month ;
				}
				else {
					this.value = year ;
			    }
			});

			//날짜 직접 입력시 입력 값 체크
			$(".isYmd").on('blur', function(event) {
				var writeTime = this.value.replace(/[^0-9]/gi, "");

				var year = "";
				var month = "";
				var day = "";

				year = writeTime.substring(0,4);
				month = writeTime.substring(4,6);
				day = writeTime.substring(6,8);

				if(month > 12) {
					g3way.sicims.common.messageBox(null, "날짜 형식 오류", "날짜를 다시 입력해주세요.", null);
					this.value = "";
				} else {
					var chkDate = new Date(year, month, 0);
					if(chkDate.getDate() < day) {
						g3way.sicims.common.messageBox(null, "날짜 형식 오류", "날짜를 다시 입력해주세요.", null);
						this.value = "";
					}
				}
			});

			$(".isTime").keyup(function(event){
				// 먼저 기존에 들어가 있을 수 있는 콜론(:)기호를 제거한다.
			    var replaceTime = this.value.replace(/\:/g, "");
			    replaceTime = replaceTime.replace(/[^0-9]/gi, "");
			    if(replaceTime.length == 5) {
			    	replaceTime = replaceTime.substr(0,4);
			    }
			    // 글자수가 4 ~ 5개 사이일때만 동작하게 고정한다.
			    if(replaceTime.length >= 4 && replaceTime.length < 5) {
			    	// 시간을 추출
			        var hours = replaceTime.substring(0, 2);
			        // 분을 추출
			        var minute = replaceTime.substring(2, 4);

			        // 시간은 24:00를 넘길 수 없게 세팅
			        if(hours + minute > 2400) {
			        	g3way.sicims.common.messageBox(null, "시간 입력", "시간은 24시를 넘길 수 없습니다.", null);
			            this.value = "24:00";
			            return false;
			        }

			        // 분은 60분을 넘길 수 없게 세팅
			        if(minute > 60) {
			        	g3way.sicims.common.messageBox(null, "시간 입력", "분은 60분을 넘길 수 없습니다.", null);
			        	this.value = hours + ":00";
			        	return false;
			        }
			        this.value = hours + ":" + minute;
			    }
			});

			// 모바일번호 '-' 와 숫자만 입력 가능
			$(".isMblTelno, .isTelno").keyup(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;

				var num = event.target.value.replace(/[^0-9]/gi, "");
				var type = 1;
			    var formatNum = '';
			    if(num.length > 11){
			    	num = num.substring(0, 11);
			    }

			    if(num.length==11){
			        if(type==0){
			            formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
			        }else{
			        	if(num.indexOf('02')==0){
			        		num = num.substring(0, 10);
			        		formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
			        	}else{
			        		formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
			        	}
			        }
			    }else if(num.length==8){
			        formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
			    }else{
			        if(num.indexOf('02')==0){
			            if(type==0){
			                formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-****-$3');
			            }else{
			            	if(num.length==9){
			            		formatNum = num.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
			            	}
			            	else {
			            		formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
			            	}
			            }
			        }else{
			            if(type==0){
			                formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
			            }else{
			                formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
			            }
			        }
			    }

			    event.target.value  = formatNum;

				/*
				if (event.target.value.length === 10) {
					event.target.value  = event.target.value.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
				}
				if (event.target.value.length > 12) {
					event.target.value  = event.target.value.replace(/-/g, '').substr(0, 11).replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
				}
				*/
			});

			//법인등록번호
			$(".isCrno").keyup(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;

				var num = event.target.value.replace(/[^0-9]/gi, "");
			    var formatNum = '';
			    if(num.length > 13){
			    	num = num.substring(0, 13);
			    }

        		formatNum = num.replace(/(\d{6})(\d{7})/, '$1-$2');

        		event.target.value  = formatNum;
			});


			//사업자번호
			$(".isBrno").keyup(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;

				var num = event.target.value.replace(/[^0-9]/gi, "");
			    var formatNum = '';
			    if(num.length > 10){
			    	num = num.substring(0, 10);
			    }

        		formatNum = num.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');

        		event.target.value  = formatNum;
			});

			//생년월일
			$(".isBirth").keyup(function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;

				var num = event.target.value.replace(/[^0-9]/gi, "");
			    var formatNum = '';
			    if(num.length > 8){
			    	num = num.substring(0, 8);
			    }

        		formatNum = num.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');

        		event.target.value  = formatNum;
			});
		},

		/* 쿠키 설정 */
		setCookie : function(cookieName, value, exdays){
		    var exdate = new Date();
		    exdate.setDate(exdate.getDate() + exdays);
		    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
		    document.cookie = cookieName + "=" + cookieValue;
		},

		deleteCookie : function (cookieName){
		    var expireDate = new Date();
		    expireDate.setDate(expireDate.getDate() - 1);
		    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
		},

		getCookie :function (cookieName) {
		    cookieName = cookieName + '=';
		    var cookieData = document.cookie;
		    var start = cookieData.indexOf(cookieName);
		    var cookieValue = '';
		    if(start != -1){
		        start += cookieName.length;
		        var end = cookieData.indexOf(';', start);
		        if(end == -1)end = cookieData.length;
		        cookieValue = cookieData.substring(start, end);
		    }
		    return unescape(cookieValue);
		},


		maxLengthCheck : function(object){
			if (object.value.length > object.maxLength){
				object.value = object.value.slice(0, object.maxLength);
			}
		},

		// modal dialog
		messageBox : function (eidtObj, msgTitle, msgContent, callback, closeYn, cancelYn) {
			g3way.sicims.common.editObj		= eidtObj;
			g3way.sicims.common.msgTitle	= msgTitle;
			g3way.sicims.common.msgContent	= msgContent;
			g3way.sicims.common.callback	= callback;
			g3way.sicims.common.cancelYn	= cancelYn;
			/*
			//이미 팝업이 호출된경우 현재 팝업을 닫고 새로운 팝업을 호출한다.
			if($.modal.getCurrent() != null && closeYn != 'N' ) {
				$.modal.close()
			}*/

			$("#divMessage").modal({escapeClose: false, clickClose: false, showClose: false, closeExisting: false });
			$("#divMessage").empty().load(this.contextPath + "/sicims000Cm/messageBox.do");
		},


		// modal dialog
		openDialog : function(url, width) {
			$("#divPopup").modal({escapeClose: false, clickClose: false, showClose: false, closeExisting: false});
			$("#divPopup").css("max-width",	width);
			$("#divPopup").css("height",	"");
			$("#divPopup").css("padding", 	"10px 10px");
			$("#divPopup").empty().load(url);
		},

		open : function (url, width, height) {
			var name = "popup";
			//var option = "width=" + (width == undefined ? 800 : width) + "px, height=" + (height == undefined ? 600 : height) + "px,toolbar=no,directories=no,menubar=no,center=yes,resizable=no,status=no,scrollbars=yes,location=no";
			//var	printWin = window.open(url, name, option);
			var	printWin = window.open(url, name);
		},

		// JS TEXT MASK
		maskFormat : function (str, type, opt, replaceYn) {
			var retValue = "";

			if (type == undefined) { type = "TEXT";	}

			if (str == "null")	return '';

			if (type == "TEXT") {
				try {
					if(replaceYn != "N"){
						str = str.toString().replace(/&/gi, '&amp;').replace(/</gi, '&lt;').replace(/>/gi, '&gt;').replace(/"/gi, '&quot;').replace(/'/gi, '&apos;');
					}
					else {
						str = str.toString();
					}
				} catch (e) {
					if (str == null)
						return "";
					return str;
				}

				if (opt != undefined) {
					return str;
				} else {
					return str;
				}
			} else if (type == "DATE") {
				if (opt == undefined) {	opt = 'yyyy-mm-dd';	}
				try {
					// Oracle - Java - Date type에 대한 Json 객체 리턴 대응
					if (str != null && str.time != null) {
						return dateFormat(new Date(str.time), opt);
					}

					// Javascript Date 객체 대응
					if (str != null && (typeof str == Date)) {
						return dateFormat(str, opt);
					}

					// string yyyymmdd 대응
					str = str.toString();

					/* yyyy-mm-dd */
					if (str.length == 10) {
						return dateFormat(new Date(str), opt);
					}
					/* yyyymmdd */
					else if (str.length == 8) {
						return dateFormat(new Date(str.substring(0, 4) + '-'
								+ str.substring(4, 6) + '-' + str.substring(6, 8)), opt);
					}
					/* yyyymmddhh24mi */
					else if (str.length == 12) {
						return dateFormat(new Date(str.substring(0, 4) + '-'
								+ str.substring(4, 6) + '-' + str.substring(6, 8)), opt);
					}
					/* yyyymmddhh24miss  */
					else if (str.length == 14) {
						return dateFormat(new Date(str.substring(0, 4) + '-'
								+ str.substring(4, 6) + '-' + str.substring(6, 8)), opt);
					}
					else if (str.length == 6) {
						return str.substring(0, 4) + '-'+ str.substring(4, 6);
					}
				} catch (e) {
					return "";
				}
			} else if (type == "TELNO") {
				try {
					var num = str.replace(/[^0-9]/gi, "");
					var formatNum = '';
				    if(num.length > 11){
				    	num = num.substring(0, 11);
				    }

				    if(num.length==11){
			        	if(num.indexOf('02')==0){
			        		num = num.substring(0, 10);
			        		formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
			        	}else{
			        		formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
			        	}
				    }else if(num.length==8){
				        formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
				    }else{
				        if(num.indexOf('02')==0){
			            	if(num.length==9){
			            		formatNum = num.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
			            	}
			            	else {
			            		formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
			            	}
				        }else{
			                formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
				        }
				    }
				    return formatNum;
				} catch (e) {
					return "";
				}
			} else if (type == "CRNO") {
				try {
					var num = str.replace(/[^0-9]/gi, "");
					var formatNum = '';
				    if(num.length > 13){
				    	num = num.substring(0, 13);
				    }

				    if(num.length==13){
		        		formatNum = num.replace(/(\d{6})(\d{7})/, '$1-$2');
				    }

				    return formatNum;
				} catch (e) {
					return "";
				}
			} else if (type == "BRNO") {
				try {
					var num = str.replace(/[^0-9]/gi, "");
					var formatNum = '';
				    if(num.length > 10){
				    	num = num.substring(0, 10);
				    }

				    if(num.length==10){
		        		formatNum = num.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');
				    }

				    return formatNum;
				} catch (e) {
					return "";
				}
			} else if (type == "NUMBER") {
				try {
					if (opt != undefined) {
						str = Number(str).toFixed(opt);
					}
					str = str.toString();
				} catch (e) {
					if (str == null)
						return "";
					return str;
				}

				var regMustNumberComma = /^[0-9|,]+$/;
				var regMustNoStartZero = /^[0]/;

				str = str.replace(/,/g, '');
				var tmpArr = str.split('.');

				for (i = 1; i <= tmpArr[0].length; i++) {
					if (i > 1 && (i % 3) == 1)
						retValue = tmpArr[0].charAt(tmpArr[0].length - i) + ","	+ retValue;
					else
						retValue = tmpArr[0].charAt(tmpArr[0].length - i) + retValue;
				}
				if (tmpArr.length > 1)
					retValue += "." + tmpArr[1];

				if (regMustNoStartZero.test(retValue) == true) {
					return retValue;
				}
				if (regMustNumberComma.test(retValue) == false) {
					return retValue;
				}
				if (tmpArr.length > 1) {
					retValue += '.' + tmpArr[1];
				}
				return retValue;
			}
		},


		//========================//
		//          날짜 계산
		//========================//
		// 날짜 x개월 더히기/빼기
		addMonth : function(ymd, val) {
			let d = new Date(ymd);
			d.setMonth(d.getMonth() + val);

		    let year = d.getFullYear();
		    let month = d.getMonth() + 1; // 월은 0에서 시작하기 때문에 +1
		    let day = d.getDate();

		    month 	= g3way.sicims.common.lpad(month + "", 2, '0');
		    day 	= g3way.sicims.common.lpad(day   + "", 2, '0');

			return year + "-" + month + "-" +  day;
		},


		// 날짜 차이 구항기
		getMonthDiff : function(fromYmd, toYmd) {
			const date1 = new Date(fromYmd);
			const date2 = new Date(toYmd);

			let diffDate = date2.getTime() - date1.getTime();

			return Math.round(Math.abs(diffDate / (1000 * 60 * 60 * 24))); // 밀리세컨 * 초 * 분 * 시 = 일
		},


		// 브라우져 타입
		getBrowserType : function(){
			var agent = window.navigator.userAgent.toLowerCase();
			var browserName;

			switch(true) {
				case agent.indexOf("edge") > -1:
					browserName = "Edge";
					break;
				case agent.indexOf("edg/") > -1:
					browserName = "Edge(Chromium base)";
					break;
				case agent.indexOf("opr") > -1 && !!window.opr:
					browserName = "Opera";
					break;
				case agent.indexOf("chrome") > -1 && !!window.chrome:
					browserName = "Chrome";
					break;
				case navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') !== -1 ||agent.indexOf("trident") > -1:
					browserName = "IE";
					break;
				case agent.indexOf("firefox") > -1:
					browserName = "Firefox";
					break;
				case agent.indexOf("safari") > -1:
					browserName = "Safari";
					break;
				case agent.indexOf("whale") > -1:
					browserName = "Whale(Naver)";
					break;
				default:
					browserName = "other";

			}
			return browserName;
		},

		sessionTimeout : function() {
			window.top.location.href = this.contextPath + '/login/sessionTimeout.do';
		},

		cloneFile : function(obj) {
			var browserType = g3way.sicims.common.getBrowserType();
			if (browserType.match(/IE/g)) {	// IE
				$(obj).replaceWith($(obj).clone(true));
			} else {						// Firefox / Chrome
				$(obj).val("");
			}
		},

		// 이미지 파일 확장자 제한
		imageFileSelected : function(obj, targetId, size){
			var uploadFile	= $(obj).val();
			var fileName 	= uploadFile.substr(uploadFile.lastIndexOf("\\")+1);
			var fileExt		= fileName.substr(fileName.lastIndexOf(".")+1).toLowerCase();

			if (fileExt != "jpg" && fileExt != "jpeg" && fileExt != "gif" && fileExt != "png") {
				g3way.sicims.common.cloneFile(obj);
				var messageTxt = "(jpg | jpeg | gif | png) 파일만 업로드 가능합니다.";
				g3way.sicims.common.messageBox(null, "파일업로드", messageTxt, null);
				return false;
			} else {
				return true;
			}
		},


		// 보고서 파일 확장자 제한
		reportFileSelected : function(obj){
			var uploadFile	= $(obj).val();
			var fileName 	= uploadFile.substr(uploadFile.lastIndexOf("\\")+1);
			var fileExt		= fileName.substr(fileName.lastIndexOf(".")+1).toLowerCase();

			if (fileExt != "pdf" && fileExt != "hwp" && fileExt != "doc" && fileExt != "docx" && fileExt != "ppt" && fileExt != "pptx" && fileExt != "xls" && fileExt != "xlsx" && fileExt != "zip") {
				g3way.sicims.common.cloneFile(obj);
				var messageTxt =  "(pdf | hwp | doc | docx | ppt | pptx | xls | xlsx | zip) 파일만 업로드 가능합니다.";
				g3way.sicims.common.messageBox(null, "파일업로드", messageTxt, null);
				return false;
			} else {
				return true;
			}
		},

		// 파일 확장자 제한
		fileSelected : function(obj, maxUploadSize){
			var fileInfo	= {};
			var uploadFile	= $(obj).val();
			var fileName 	= uploadFile.substr(uploadFile.lastIndexOf("\\")+1);
			var fileExt		= fileName.substr(fileName.lastIndexOf(".")+1).toLowerCase();
			fileName		= fileName.substr(0,fileName.lastIndexOf("."));

			var fileSize	= $(obj).get(0).files[0].size;

			if (fileExt != "jpg" && fileExt != "jpeg" && fileExt != "gif" && fileExt != "png" &&
					fileExt != "pdf" && fileExt != "hwp" && fileExt != "doc" && fileExt != "docx" && fileExt != "ppt" && fileExt != "pptx" && fileExt != "xls" && fileExt != "xlsx" && fileExt != "zip") {
					g3way.sicims.common.cloneFile(obj);
					var messageTxt =  "이미지(jpg | jpeg | gif | png)<br />문서(pdf | hwp | doc | docx | ppt | pptx | xls | xlsx | zip) 파일만 업로드 가능합니다.";
					g3way.sicims.common.messageBox(null, "파일업로드", messageTxt, null);
					fileInfo = {fileName : "", fileExt : "", fileSize : ""};
			} else {
				if (fileSize > maxUploadSize) {
					g3way.sicims.common.cloneFile(obj);
					var messageTxt =  "파일 사이즈는 100MB 이내로 등록 가능합니다.";
					g3way.sicims.common.messageBox(null, "파일업로드", messageTxt, null);
					fileInfo = {fileName : "", fileExt : "", fileSize : ""};
				} else {
					fileInfo = {fileName : fileName, fileExt : fileExt, fileSize : fileSize};
				}
			}
			return fileInfo;
		},

		viewImage : function(imgObj, fileId, maxWidth, maxHeight) {
			imgObj.removeAttr("style");
			imgObj.hide();
			imgObj.attr("src", this.contextPath + "/sicims000Cm/getFileStream.do?fileId=" + fileId + "&timestamp=" + new Date().getTime());

			imgObj.load(function () { //이미지가 로딩이 완료 된 후
				g3way.gis.common.imgResize(this, maxWidth, maxHeight);
				$(this).show();
			})

		},

		//이미지 리사이징
		imgResize : function (img, w, h) {
			var resizeWidth = 0;
			var resizeHeight = 0;

			var width = img.width;
			var height = img.height;

			var maxWidth = w;
			var maxHeight = h;

			if (width > maxWidth || height > maxHeight) {
				if (width > height) {
					resizeWidth = maxWidth;
					resizeHeight = Math.round((height * resizeWidth) / width);
					if (resizeHeight > maxHeight) {
						resizeHeight = maxHeight;
						resizeWidth = Math.round((width * resizeHeight) / height);
					}
				} else {
					resizeHeight = maxHeight;
					resizeWidth = Math.round((width * resizeHeight) / height);
					if (resizeWidth > maxWidth) {
						resizeWidth = maxWidth;
						resizeHeight = Math.round((height * resizeWidth) / width);
					}
				}
			} else {
				resizeWidth = width;
				resizeHeight = height;
			}

			img.width = resizeWidth;
			img.height = resizeHeight;
		},


		// 파일 다운로드
		downloadFile : function (fileId) {
			if(fileId == null || fileId == '') {
				g3way.sicims.common.messageBox(null, "다운로드 파일", "다운로드할 파일이 없습니다.", null);
				return false;
			}

			if ($("#divDownload").html()== null) {
				$("body").append("<div id='divDownload'><iframe id='downloadFrame' name='downloadFrame' style='display:none;visibility:hidden;width:0px;height:0px;'></iframe></div>");
			}

			if ($("#downloadFrame").html() == null) {
				$("#divDownload").html("<iframe id='downloadFrame' name='downloadFrame' style='display:none;visibility:hidden;width:0px;height:0px;'></iframe>");
			}

			$("#downloadFrame").attr("src", this.contextPath + "/sicims000Cm/downloadFile.do?fileId=" + fileId + "&timestamp=" + new Date().getTime());

		},

		// 파일 다운로드
		downloadFileNm: function (filePath, fileName, fileExt) {
			if ($("#divDownload").html()== null) {
				$("body").append("<div id='divDownload'><iframe id='downloadFrame' name='downloadFrame' style='display:none;visibility:hidden;width:0px;height:0px;'></iframe></div>");
			}

			if ($("#downloadFrame").html() == null) {
				$("#divDownload").html("<iframe id='downloadFrame' name='downloadFrame' style='display:none;visibility:hidden;width:0px;height:0px;'></iframe>");
			}

			$("#downloadFrame").attr("src", this.contextPath + "/sicims000Cm/downloadFileNm.do?filePath=" + encodeURIComponent(filePath) + "&fileName=" + encodeURIComponent(fileName) + "&fileExt=" + encodeURIComponent(fileExt) + "&timestamp=" + new Date().getTime());
		},


		// excel 파일 다운로드
		downloadExcel : function (excelKorNm, excelEngNm, excelContent) {
			var htmlStr = "";
			if ($("#divDownload").html()== null) {
				var htmlStr  = "<div id='divDownload'>";
				htmlStr 	+= "<form id='excelFrm'  name='excelFrm' method='post' target='excelFrame'>";
				htmlStr 	+= "<input type='hidden' name='excelContent'>";
				htmlStr 	+= "<input type='hidden' name='excelKorNm'>";
				htmlStr 	+= "<input type='hidden' name='excelEngNm'>";
				htmlStr 	+= "</form>";
				htmlStr 	+= "<iframe id='excelFrame' style='display:none;width:0px;height:0px'></iframe>";
				htmlStr 	+= "</div>";
				$("body").append(htmlStr);
			}

			$("[name=excelContent]").val(excelContent);
			$("[name=excelKorNm]").val(excelKorNm);
			$("[name=excelEngNm]").val(excelEngNm);
			$("#excelFrm").attr("action", this.contextPath + "/sicims000Cm/downloadExcel.do");

			$("#excelFrm").submit();
		},

		// excel 파일 다운로드
		downloadExcel2 : function (url, paramData ) {
			$.fileDownload(url,
					{
						httpMethod: "post",
						data: paramData,
						successCallback: function() {
						},
						failCallback: function() {
							g3way.sicims.common.messageBox(null, "엑셀파일 오류", "엑셀파일 다운로드 중 오류가 발생했습니다.", null);
						}
					});
		},


		print : function (content, width, height) {
			var	printWin = window.open("", "Print", "width=" + width + "px, height=" + height + "px,toolbar=no,directories=no,menubar=no,center=yes,resizable=no,status=no,scrollbars=yes,location=no");
				printWin.document.open();
				printWin.document.write("<link rel=\'stylesheet\' href=\'" + this.contextPath + "/resources/css/map/land/g3way.map.map.css\'>" );
				printWin.document.write("<link rel=\'stylesheet\' href=\'" + this.contextPath + "/resources/framework/jquery/theme/jquery-ui.css\'>" );
				printWin.document.write("<link rel=\'stylesheet\' href=\'" + this.contextPath + "/resources/framework/jquery/multiselect/jquery.multiselect.css\'>" );
				printWin.document.write("<link rel=\'stylesheet\' href=\'" + this.contextPath + "/resources/framework/jquery/multiselect/assets/style.css\'>" );
				printWin.document.write("<link rel=\'stylesheet\' href=\'" + this.contextPath + "/resources/framework/jquery/multiselect/assets/prettify.css\'>" );
				printWin.document.write(content);
				printWin.document.close();
				printWin.focus();
				printWin.print();
				window.setTimeout(function () {
					printWin.close();
			    }, 1000);
		},


		nvl : function(val) {
			if (val == null || val == "null" || val == "undefined") {
				return "";
			} else {
				return val;
			}
		},

		getWaitImg : function (width, colspan) {
			var colspanHtml = "";
			if (colspan != null) {
				colspanHtml = " colspan='" + colspan + "' ";
			}
			var htmlStr  = "";
			htmlStr += "<tr  class='nohover'>";
			htmlStr += "	<td class='tc' style='width:" + width + "px;' colspan='" + colspan + "'><img src='" + this.contextPath + "/resources/images/icons/loading_large.gif'   title='waiting.....'  style='width:20px; height:20px; vertical-align:middle;' alt='로딩중' /></td>";
			htmlStr += "</tr>";

			return htmlStr;
		},


		//3자리 마다 (,)를 찍어 표시
		makeCommaSeparate : function (a_Value) {
		    var fl = "";
		    var nPointPos = 0;

			if(isNaN(parseFloat(a_Value)))  {
		    	return a_Value;
		    }

			a_Value = parseFloat(a_Value);
		    if(a_Value == 0) return a_Value;

		    if(a_Value < 0)   {
			    a_Value=a_Value*(-1);
			    fl = "-";
		    }  else if(a_Value == 0)  {
		    	// 처음 입력값이 0부터 시작할때 이것을 제거한다.
		    	a_Value = a_Value*1;
		    }

		    var a_Value = new String(a_Value);
		    var temp = "";
		    var sRemain = "";
		    var co = 3;

		    nPointPos = a_Value.indexOf(".");

			if( nPointPos == -1 )   {
		    	num_len = a_Value.length;
		    }  else   {
		    	// "." 가 포함되어 있을 경우에 재계산
		    	if( parseInt(a_Value.substr(0,nPointPos)) == 0 )  	{
		    		a_Value = "0" + a_Value.substr(nPointPos);
		    		nPointPos = a_Value.indexOf(".");
		    	}
		    	num_len = nPointPos;
		    	sRemain = a_Value.substr(nPointPos);
		    }

		    while (num_len>0)  {
		        num_len = num_len - co;

		        if(num_len<0) {
		        	co=num_len+co;
		        	num_len=0;
		        }

		        temp = "," + a_Value.substr(num_len,co) + temp;
		    }

		    var tp = fl + temp.substr(1) + sRemain;

		    if(tp != null && tp != '' && tp.substr(0,1)=='.')
		    	tp = '0'+tp;

		    return tp;
		},

		addComma : function (n) {
			var reg = /(^[+-]?\d+)(\d{3})/; n += '';
			while (reg.test(n)) {
				n = n.replace(reg, '$1' + ',' + '$2');
			}
			return n;
		},

		removeComma : function (sMessage) {
			var r, re;
			re = /,/g;
			r = sMessage.replace(re,"");
			return(r);
		},

		timeConvert : function (time) {
			hour = parseInt(time/3600);
			min = parseInt((time%3600)/60);
			sec = time%60;

			if (hour == 0) {
				if (min == 0) {
					if (sec == 0) return "-";
					else return leadingZeros(sec,2)+"초";
				} else {
					if (sec == 0) return leadingZeros(min,2)+"분";
					else return leadingZeros(min,2)+"분"+leadingZeros(sec,2)+"초";
				}
			} else {
				if (min == 0) {
					if (sec == 0) return hour+"시간";
					else return hour+"시간"+leadingZeros(sec,2)+"초";
				} else {
					if (sec == 0) return hour+"시간"+leadingZeros(min,2)+"분";
					else return hour+"시간"+leadingZeros(min,2)+"분"+leadingZeros(sec,2)+"초";
				}
			}
		},


		// input box에 숫자만 입력
		onlyNumber : function(event) {
			event = event || window.event;
			var keyID = (event.which) ? event.which : event.keyCode;
			if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 9 || keyID == 46 || keyID == 37 || keyID == 39 )
				return;
			else
				return false;
		},

		// 소숫점 숫자가 아닌 것 remove
		removeChar : function(event) {
			event = event || window.event;
			var keyID = (event.which) ? event.which : event.keyCode;
			if ( keyID == 8 ||keyID == 9 ||  keyID == 46 || keyID == 37 || keyID == 39)
				return;
			else
				event.target.value = event.target.value.replace(/[^0-9^]/g, "");
		},


		// 소숫점 숫자만 입력
		pDecimal : function(event) {
			event = event || window.event;
			var keyID = (event.which) ? event.which : event.keyCode;
			if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 190)
				return;
			else
				return false;
		},

		// 소숫점 숫자가 아닌 것 remove
		pDecimalRemoveChar : function(event) {
			event = event || window.event;
			var keyID = (event.which) ? event.which : event.keyCode;
			if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 190)
				return;
			else
				event.target.value = event.target.value.replace(/[^0-9^.]/g, "");
		},

		// 숫자(-, .) input box에 숫자만 입력
		nDecimal : function(event) {
			event = event || window.event;
			var keyID = (event.which) ? event.which : event.keyCode;
			if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 190 || keyID == 189)
				return;
			else
				return false;
		},

		// 숫자가 아닌 것 remove
		nDecimalRemoveChar : function(event) {
			event = event || window.event;
			var keyID = (event.which) ? event.which : event.keyCode;
			if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 190 || keyID == 189)
				return;
			else
				event.target.value = event.target.value.replace(/[^0-9^.-]/g, "");
		},


		// 숫자가 아닌 것 remove
		checkNumeric : function(obj) {
			if (isNaN($(obj).val())) {
				$(obj).val("");
				$(obj).focus();
			}
		},

		// 컴마있는 문자열을 숫자로 변환
		strToInt : function(str) {
			if (isNaN(parseInt(str.replace(/,/g , ''))))	 {
				return 0;
			} else {
				return parseInt(str.replace(/,/g , ''));
			}
		},

		getBytes: function(value) {
			function characterLength(characters, position) {
				var character = characters.charAt(position);
				if(escape(character).length > 4) {  // 한글일경우 2byte
					return 2;
				} else if (character == '\n') {     // 줄바꿈일경우
					if(characters.charAt(position-1) != '\r') {     // 하지만 밀려서 줄이 바뀐경우가 아닐때
						return 1;
					}
				} else if (character == '<' || character == '>') {  // 특수문자는 4byte
					return 4;
				} else {
					return 1;
				}
			}
			var bytes = 0;
			var characters_length = value.length;
			for (var position=0; position < characters_length; position++) {
				bytes += characterLength(value, position);
			}
			return bytes;
		},

		//Byte 수 체크 제한
		chkBytes : function (obj, maxByte) {
			var str = obj.value;
			var str_len = str.length;

			var rbyte = 0;
			var rlen = 0;
			var one_char = "";
			var str2 = "";

			for(var i=0; i<str_len; i++)     {
				one_char = str.charAt(i);
				if(escape(one_char).length > 4)      {
					rbyte += 2;                                         //한글2Byte
				} else {
					rbyte++;                                            //영문 등 나머지 1Byte
				}

				if(rbyte <= maxByte){
					rlen = i+1;                                          //return할 문자열 갯수
				}
			}

			if(rbyte > maxByte){
				alert(maxByte + "byte를 초과할 수 없습니다.");
				str2 = str.substr(0,rlen);                                  //문자열 자르기
				obj.value = str2;
				return false;
			} else {
				return true;
			}
		},

		// 날짜 유형을 문자열로 변환
		dateToString : function(day) {
			if (day == null || day == "") return "";

			var yyyy	= day.substring(0,4);
			var mm		= day.substring(4,6);
			var dd		= day.substring(6,8);

//			var date 	= new Date( day );
//			var yyyy 	= date.getFullYear();
//			var mm	 	= this.lpad((date.getMonth() + 1) + "", 2, "0");
//			var dd		= this.lpad(date.getDate() + "", 2, "0");

			return yyyy + "-" + mm + "-" + dd;
		},

		// lpad
		lpad : function(str, len, pad) {
			while (str.length < len) {
				str = pad + str;
			}
		    return str;
		},

		// rpad
		rpad : function(str, len, pad) {
			while (str.length < len) {
				str = str + pad;
			}
		    return str;
		},

		// 구분자 삭제(remove delimited characters
		removeDelimChar : function(str, old,  replacement) {
			var result = "";
			if (old != undefined && replacement != undefined) {
				result = str.replace(/old/gi, replacement);
			} else {
				result = str.replace(/-/gi, "");
			}

			return result;
		},


		excuteFormValidate : function(frmId) {
			var $frms = $(frmId);

			var rtnBool			= true;
			var messageTitle	= "";
			var messageTxt		= '';
			var breakObj		= null;

			if ($frms.length) {

				var $ipts = $frms.find("input[type=text], input[type=password], input[type=file], select, textarea");

				var obj;
				if ($ipts.length) {
					for (var tmpI = 0; tmpI < $ipts.length; tmpI++) {
						obj = $ipts.eq(tmpI);
						var sValue = $(obj).val();
						messageTitle = "유효성 검사";
						// case1 : 글자수 체크 (.underMax)
						if ($(obj).attr("maxlength") && $(obj).hasClass("underMax")) {
							var maxLen = $(obj).attr("maxlength")|| $(obj).metadata().maxLength;
							var minLen = $(obj).metadata().minLength || 0;

							if (sValue.getBytes() < minLen	|| sValue.getBytes() > maxLen) {
								breakObj = obj;
								$(obj).focus();
								messageTxt = minLen + " - " + maxLen + "자 이내로 입력해 주세요.";
								rtnBool = false;
								break;
							}
						}

						// case2 : 최소 글자수 체크 (.overMin) 필수체크
						if (sValue.getBytes() > 0 && $(obj).hasClass("overMin")) {
							var minLen = $(obj).metadata().minLength || 2;

							if (sValue.getBytes() < minLen) {
								breakObj = obj;
								$(obj).focus();
								messageTxt = minLen + "자 이상 입력해 주세요.";
								rtnBool = false;
								break;
							}
						}

						// 필수체크
						if ($(obj).hasClass("required")) {
							var minLen = $(obj).metadata().minLength || 1;
							if (sValue.getBytes() < minLen) {
								breakObj = obj;
								$(obj).focus();
								messageTxt = ($(obj).attr('title') ? $(obj).attr('title') : "필수 입력항목입니다.");
								rtnBool = false;
								break;
							}
						}

						// case3 : 영어만 입력 (.isEng)
						if ($(obj).hasClass("isEng") && !regExOnlyEng.test(sValue)) {
							// regExOnlyEng.test(sValue) 값은 영문이 입력되면 true, 아니면 false를 반환
							obj.value = "";
							breakObj = obj;
							$(obj).focus();
							messageTxt = "영문만 입력 가능합니다.";
							rtnBool = false;
							break;
						}

						// case4 : 숫자만 입력 (.isNum)
						if ($(obj).hasClass("isNum")) {
							sValue = g3way.sicims.common.removeComma(sValue);
							if (!regExOnlyNum.test(sValue)) {
								obj.value = "";
								breakObj = obj;
								$(obj).focus();
								messageTxt = "숫자만 입력 가능합니다.";
								rtnBool = false;
								break;
							} else {
								messageTitle = $(obj).attr("title");
								if (isNaN(sValue)) {
									messageTxt		= "정확한 값을 입력바랍니다.";
									$(obj).focus();
									rtnBool = false;
									break;
								} else {
									if ($(obj).data("min") != undefined && $(obj).data("max") != undefined) {
										if (Number(sValue) < Number($(obj).data("min")) || Number(sValue) > Number($(obj).data("max"))) {
											messageTxt	= $(obj).data("min") + "~" + $(obj).data("max") + " 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else if ($(obj).data("min") != undefined && $(obj).data("max") == undefined) {
										if (Number(sValue) < Number($(obj).data("min"))) {
											messageTxt	= $(obj).data("min") + "보다 크거나 같은 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else if ($(obj).data("min") == undefined && $(obj).data("max") != undefined) {
										if (Number(sValue) > Number($(obj).data("min"))) {
											messageTxt	= $(obj).data("max") + "보다 작거나 같은 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else {
										//
									}
								}
							}
						}

						// case4 : 숫자만 입력 (.isNum)
						if ($(obj).hasClass("isNumNoComma")) {
							sValue = g3way.sicims.common.removeComma(sValue);
							if (!regExOnlyNum.test(sValue)) {
								obj.value = "";
								breakObj = obj;
								$(obj).focus();
								messageTxt = "숫자만 입력 가능합니다.";
								rtnBool = false;
								break;
							} else {
								messageTitle = $(obj).attr("title");
								if (isNaN(sValue)) {
									messageTxt		= "정확한 값을 입력바랍니다.";
									$(obj).focus();
									rtnBool = false;
									break;
								} else {
									if ($(obj).data("min") != undefined && $(obj).data("max") != undefined) {
										if (Number(sValue) < Number($(obj).data("min")) || Number(sValue) > Number($(obj).data("max"))) {
											messageTxt	= $(obj).data("min") + "~" + $(obj).data("max") + " 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else if ($(obj).data("min") != undefined && $(obj).data("max") == undefined) {
										if (Number(sValue) < Number($(obj).data("min"))) {
											messageTxt	= $(obj).data("min") + "보다 크거나 같은 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else if ($(obj).data("min") == undefined && $(obj).data("max") != undefined) {
										if (Number(sValue) > Number($(obj).data("min"))) {
											messageTxt	= $(obj).data("max") + "보다 작거나 같은 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else {
										//
									}
								}
							}
						}

						// case4-1 : double 숫자만 입력 (.isDouble)
						if ($(obj).hasClass("isDouble")) {
							sValue = g3way.sicims.common.removeComma(sValue);
							if (!regExOnlyDbl.test(sValue)) {
								$(obj).val("");
								breakObj = obj;
								$(obj).focus();
								messageTxt = "숫자만 입력 가능합니다.";
								rtnBool = false;
								break;
							} else {
								messageTitle 	= $(obj).attr("title");
								if (isNaN(sValue)) {
									messageTxt		= "정확한 값을 입력바랍니다.";
									$(obj).focus();
									rtnBool = false;
									break;
								} else {
									if ($(obj).data("min") != undefined && $(obj).data("max") != undefined) {
										if (Number(sValue) < Number($(obj).data("min")) || Number(sValue) > Number($(obj).data("max"))) {
											messageTxt	= $(obj).data("min") + "~" + $(obj).data("max") + " 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else if ($(obj).data("min") != undefined && $(obj).data("max") == undefined) {
										if (Number(sValue) < Number($(obj).data("min"))) {
											messageTxt	= $(obj).data("min") + "보다 크거나 같은 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else if ($(obj).data("min") == undefined && $(obj).data("max") != undefined) {
										if (Number(sValue) > Number($(obj).data("min"))) {
											messageTxt	= $(obj).data("max") + "보다 작거나 같은 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else {
										//
									}
								}
							}
						}

						// case4-1 : double 숫자(-0.999999999 ~ 0.999999999)만 입력 (.isFactor)
						if ($(obj).hasClass("isFactor")) {
							sValue = g3way.sicims.common.removeComma(sValue);
							if (!regExOnlyFct.test(sValue)) {
								$(obj).val("");
								breakObj = obj;
								$(obj).focus();
								messageTxt = "숫자만 입력 가능합니다.";
								rtnBool = false;
								break;
							} else {
								messageTitle 	= $(obj).attr("title");
								if (isNaN(sValue)) {
									messageTxt		= "정확한 값을 입력바랍니다.";
									$(obj).focus();
									rtnBool = false;
									break;
								} else {
									if (Number(sValue) <= -1.0 || Number(sValue) >= 1.0) {
										messageTxt		= "-1.0보다 크고 1.0보다 작은 수치를 입력바랍니다.";
										$(obj).focus();
										rtnBool = false;
										break;
									}
								}
							}
						}

						// case4-1 : double 숫자(0 ~ 1)만 입력 (.isRate)
						if ($(obj).hasClass("isRate")) {
							sValue = g3way.sicims.common.removeComma(sValue);
							if (!regExOnlyDbl.test(sValue)) {
								$(obj).val("");
								breakObj = obj;
								$(obj).focus();
								messageTxt = "숫자만 입력 가능합니다.";
								rtnBool = false;
								break;
							} else {
								messageTitle 	= $(obj).attr("title");
								if (isNaN(sValue)) {
									messageTxt		= "정확한 값을 입력바랍니다.";
									$(obj).focus();
									rtnBool = false;
									break;
								} else {
									if ($(obj).data("min") != undefined && $(obj).data("max") != undefined) {
										if (Number(sValue) < Number($(obj).data("min")) || Number(sValue) > Number($(obj).data("max"))) {
											messageTxt	= $(obj).data("min") + "~" + $(obj).data("max") + " 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else if ($(obj).data("min") != undefined && $(obj).data("max") == undefined) {
										if (Number(sValue) < Number($(obj).data("min"))) {
											messageTxt	= $(obj).data("min") + "보다 크거나 같은 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else if ($(obj).data("min") == undefined && $(obj).data("max") != undefined) {
										if (Number(sValue) > Number($(obj).data("min"))) {
											messageTxt	= $(obj).data("max") + "보다 작거나 같은 값을  입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									} else {
										if (Number(sValue) <= 0 || Number(sValue) > 1.0) {
											messageTxt	= "0보다 크고 1보다 같거나 작은 수치를 입력바랍니다.";
											$(obj).focus();
											rtnBool = false;
											break;
										}
									}
								}
							}
						}

						// case5 : 영어 혹은 숫자만 입력 (.isEngOrNum)
						if ($(obj).hasClass("isEngOrNum") && !regExEngOrNum.test(sValue)) {
							breakObj = obj;
							$(obj).focus();
							messageTxt = "영어 혹은 숫자만 입력 가능합니다.";
							rtnBool = false;
							break;
						}

						// case6 : 영어 숫자 조합(.isEngAndNum)
						if ($(obj).hasClass("isEngAndNum") && (!regExEng.test(sValue) || !regExNum.test(sValue))) {
							obj.value = "";
							$(obj).focus();
							messageTxt = "영어와 숫자를 조합해서 입력해주세요.";
							rtnBool = false;
							break;
						}

						// case7 : 도메인 타입 체크(.isDomain)
						if ($(obj).hasClass("isDomain") && !regExDomain.test(sValue)) {
							obj.value = "";
							$(obj).focus();
							messageTxt = "도메인 형식에 맞지 않습니다.";
							rtnBool = false;
							break;
						}

						// case8 : 이메일 타입 체크(.isEmail)
						if ($(obj).hasClass("isEmail") && sValue != '' && !regExEmail.test(sValue)) {
							obj.value = "";
							$(obj).focus();
							messageTxt = "이메일 형식에 맞지 않습니다.";
							rtnBool = false;
							break;
						}

						// case9 : 날짜 형식 체크(.isYmd)
						if ($(obj).hasClass("isYmd")) {
							var writeTime = sValue.replace(/[^0-9]/gi, "");

							var year = "";
							var month = "";
							var day = "";

							year = writeTime.substring(0,4);
							month = writeTime.substring(4,6);
							day = writeTime.substring(6,8);

							if(month > 12) {
								messageTxt = "날짜 유효 범위에 맞지 않습니다.";
								rtnBool = false;
								break;
							} else {
								var chkDate = new Date(year, month, 0);
								if(chkDate.getDate() < day) {
									messageTxt = "날짜 유효 범위에 맞지 않습니다.";
									rtnBool = false;
									break;
								}
							}
						}

						// case10 : 패스워드 타입 체크(.isPassword)
						if ($(obj).hasClass("isPassword") && !regExPassword.test(sValue)) {
							obj.value = "";
							$(obj).focus();
							messageTxt = "패스워드 형식에 맞지 않습니다.(최소9 자 최대 16자, 문자, 숫자, 특수문자가 포함되어야 합니다.)";
							rtnBool = false;
							break;
						}
						// case11 : 핸드폰번호 타입 체크(.isMblTelno)
						if ($(obj).hasClass("isMblTelno") && sValue != '' &&!regExMblTelno.test(sValue)) {
							obj.value = "";
							$(obj).focus();
							messageTxt = "모바일번호 형식에 맞지 않습니다.";
							rtnBool = false;
							break;
						}

						// case12 : 전화번호 타입 체크(.isTelno)
						if ($(obj).hasClass("isTelno") && sValue != '' && !regExTelno.test(sValue)) {
							obj.value = "";
							$(obj).focus();
							messageTxt = "전화번호 형식에 맞지 않습니다.";
							rtnBool = false;
							break;
						}

						// case13 : 인사대체키 타입 체크(.isHrscRplcKey)
						if ($(obj).hasClass("isHrscRplcKey") && sValue != '' && !regExHrscRplcKey.test(sValue)) {
							obj.value = "";
							$(obj).focus();
							messageTxt = "인사대체키 형식에 맞지 않습니다.";
							rtnBool = false;
							break;
						}

					}
				}

				if (messageTxt != '') {
					g3way.sicims.common.messageBox($(obj), messageTitle, messageTxt, null);
				}

				return rtnBool;
			}
		},

		/*
		 *
		 * 목록 조회 후 클릭 이벤트 바인딩 함수
		 *
		 * listId : 리스트 id 명
		 * callfn : 클릭 이벤트 함수
		 * dataNm : 로우에 저장된 data 명
		 * 사용법 : 화면의 목록 id 값과 클릭 했을 때 호출될 함수변수와
		 *          목록에 저장된 순번 값에 접근하는 명칭을 셋팅하고 호출한다.
		 */
		ListEventHandler : function(listId , callfn, dataNm, clickEl) {
			if(clickEl == undefined || clickEl == ''){
				clickEl = 'tr';
			}
			$("#"+listId+" tbody "+clickEl).mouseover(function() {
				$(this).css("cursor", "pointer");
			}).click(function() {
				$("#"+listId+" tbody "+clickEl).removeClass("selected");
				$(this).addClass("selected");
				var tr = $(this);
				var dataValue = [];
				dataNm.split(',').forEach(function (dataNameItem, dataNameidx){
					dataValue.push($(tr).data(dataNameItem));
				});
				callfn(dataValue);
			});
		},


		// 체크박스 이벤트 핸들러
		checkboxHandler : function(){
			// tbody
			$("table.sicims-list-tb tbody input:checkbox").on("click", function(event){
				event.stopPropagation();

				var $thCheckbox = $(this).closest("table").children("thead").find("input:checkbox");


				if($(this).closest("tbody").find(":checked").length == $("table.sicims-list-tb tbody tr").length) {
					$thCheckbox.prop("checked", true);
				} else {
					$thCheckbox.prop("checked", false);
				}
			});

			// thead
			$("table.sicims-list-tb thead input:checkbox").on("click", function(event){
				event.stopPropagation();

				var obj = event.target;
				var $tbody = $(obj).closest("table").children("tbody");

				$tbody.find("input[type=checkbox]").prop("checked", function(){
					return $(obj).prop("checked");
				});
			});


		},

		//전송할 form항목을 input의 name을 비교하여 입력한 form 항목의 값을 암호화하여 복사한다.
		encryptForm : function(inputForm, encryptForm, rSAModulus, rSAExponent){
			if(rSAModulus == undefined || rSAModulus == '') {
				rSAModulus = 'RSAModulus'
			}

			if(rSAExponent == undefined || rSAExponent == '') {
				rSAExponent = 'RSAExponent'
			}

			//RSA이용한 개인정보 암호화
			var rSAModulus 	= $("#" + rSAModulus).val();
		    var rSAExponent = $("#" + rSAExponent).val();

		    var rsa = new RSAKey();
		    rsa.setPublic(rSAModulus, rSAExponent);

		    $("#" + encryptForm + " input[name]").each(function (index, item){
		    	var name 	= $(this).attr('name');
		    	var value 	= $(this).val();

		    	if ($("#" + inputForm + " [name=" + name + "]") != undefined ){
		    		if ($("#" + inputForm + " [name=" + name + "]").attr("type") == 'checkBox'){
		    			value = (($("#" + inputForm + " [name=" + name + "]")).is(':checked'))? ($("#" + inputForm + " [name=" + name + "]")).val(): "";
		    		} else {
		    			value = $("#" + inputForm + " [name=" + name + "]").val();
		    		}
		    	}

		    	if($(this).hasClass("RSA") && value != undefined){
		    		$(this).val(rsa.encrypt(value));
		    	} else {
		    		$(this).val(value);
		    	}
		    });
		},


};

g3way.sicims.common.init();

/*
 * Date Format 1.2.3 (c) 2007-2009 Steven Levithan <stevenlevithan.com> MIT
 * license
 *
 * Includes enhancements by Scott Trenda <scott.trenda.net> and Kris Kowal
 * <cixar.com/~kris.kowal/>
 *
 * Accepts a date, a mask, or a date and a mask. Returns a formatted version of
 * the given date. The date defaults to the current date/time. The mask defaults
 * to dateFormat.masks.default.
 */

var dateFormat = function() {
	var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g, timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g, timezoneClip = /[^-+\dA-Z]/g, pad = function(
			val, len) {
		val = String(val);
		len = len || 2;
		while (val.length < len)
			val = "0" + val;
		return val;
	};

	// Regexes and supporting functions are cached through closure
	return function(date, mask, utc) {
		var dF = dateFormat;

		// You can't provide utc if you skip other args (use the "UTC:" mask
		// prefix)
		if (arguments.length == 1
				&& Object.prototype.toString.call(date) == "[object String]"
				&& !/\d/.test(date)) {
			mask = date;
			date = undefined;
		}

		// Passing date through Date applies Date.parse, if necessary
		date = date ? new Date(date) : new Date;
		if (isNaN(date))
			throw SyntaxError("invalid date");

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var _ = utc ? "getUTC" : "get", d = date[_ + "Date"](), D = date[_
				+ "Day"](), m = date[_ + "Month"](), y = date[_ + "FullYear"](), H = date[_
				+ "Hours"](), M = date[_ + "Minutes"](), s = date[_ + "Seconds"]
				(), L = date[_ + "Milliseconds"](), o = utc ? 0 : date
				.getTimezoneOffset(), flags = {
			d : d,
			dd : pad(d),
			ddd : dF.i18n.dayNames[D],
			dddd : dF.i18n.dayNames[D + 7],
			m : m + 1,
			mm : pad(m + 1),
			mmm : dF.i18n.monthNames[m],
			mmmm : dF.i18n.monthNames[m + 12],
			yy : String(y).slice(2),
			yyyy : y,
			h : H % 12 || 12,
			hh : pad(H % 12 || 12),
			H : H,
			HH : pad(H),
			M : M,
			MM : pad(M),
			s : s,
			ss : pad(s),
			l : pad(L, 3),
			L : pad(L > 99 ? Math.round(L / 10) : L),
			t : H < 12 ? "a" : "p",
			tt : H < 12 ? "am" : "pm",
			T : H < 12 ? "A" : "P",
			TT : H < 12 ? "AM" : "PM",
			Z : utc ? "UTC" : (String(date).match(timezone) || [ "" ]).pop()
					.replace(timezoneClip, ""),
			o : (o > 0 ? "-" : "+")
					+ pad(
							Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o)
									% 60, 4),
			S : [ "th", "st", "nd", "rd" ][d % 10 > 3 ? 0
					: (d % 100 - d % 10 != 10) * d % 10]
		};

		return mask.replace(token, function($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();

// Some common format strings
dateFormat.masks = {
	"default" : "ddd mmm dd yyyy HH:MM:ss",
	shortDate : "m/d/yy",
	mediumDate : "mmm d, yyyy",
	longDate : "mmmm d, yyyy",
	fullDate : "dddd, mmmm d, yyyy",
	shortTime : "h:MM TT",
	mediumTime : "h:MM:ss TT",
	longTime : "h:MM:ss TT Z",
	isoDate : "yyyy-mm-dd",
	isoTime : "HH:MM:ss",
	isoDateTime : "yyyy-mm-dd'T'HH:MM:ss",
	isoUtcDateTime : "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
	dayNames : [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sunday",
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ],
	monthNames : [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec", "January", "February", "March",
			"April", "May", "June", "July", "August", "September", "October",
			"November", "December" ]
};

// For convenience...
Date.prototype.format = function(mask, utc) {
	return dateFormat(this, mask, utc);
};

String.prototype.replaceAll = function() {
	var a = arguments, length = a.length;

	if (length == 0) {
		return this;
	}

	var regExp = new RegExp(a[0], "g");

	if (length == 1) {
		return this.replace(regExp, "");
	} else {
		return this.replace(regExp, a[1]);
	}
	return this;
}


String.prototype.getBytes = function() {
	var contents = this;
	var str_character;
	var int_char_count;
	var int_contents_length;

	int_char_count = 0;
	int_contents_length = contents.length;

	for (k = 0; k < int_contents_length; k++) {
		str_character = contents.charAt(k);
		if (escape(str_character).length > 4)
			int_char_count += 2;
		else
			int_char_count++;
	}
	return int_char_count;
}


//포함 문자를 정의한 정규표현식을 저장한 변수
var regExNum = /[0-9]/;
var regExEng = /[a-zA-Z]/;
var regExHan = /[가-힣]/;
var regExOnlyNum = /^[0-9]*$/;
var regExOnlyDbl = /^[0-9.]*$/;
var regExOnlyFct = /^[0-9.-]*$/;
var regExOnlyEng = /^[a-zA-Z]*$/;
var regExEngOrNum = /^[a-zA-Z0-9]*$/;
var regExDomain = /^((?:(?:(?:\w[\.\-\+]?)*)\w)+)((?:(?:(?:\w[\.\-\+]?){0,62})\w)+)\.(\w{2,6})$/;
var regExEmail = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
var regExPassword = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{9,16}$/;	//최소 9 자, 최대 16자 , 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
var regExMblTelno = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
var regExTelno = /^0?([0-9]{1,2})-?([0-9]{3,4})-?([0-9]{4})$/;
var regExHrscRplcKey = /^A?([0-9]{12})$/;



/*
 * 같은 값이 있는 열을 병합함
 * 사용법 : $('#테이블 ID').rowspan(0);
 */
$.fn.rowspan = function(colIdx, isStats) {
	return this.each(function() {
		var that;
		$('tr', this).each(
				function(row) {
					$('td:eq(' + colIdx + ')', this).filter(':visible').each(
							function(col) {
								if ($(this).html() == $(that).html() && (!isStats || isStats
												&& $(this).prev().html() == $(that).prev().html())) {
									rowspan = $(that).attr("rowspan") || 1;
									rowspan = Number(rowspan) + 1;

									$(that).attr("rowspan", rowspan);

									// do your action for the colspan cell here
									// $(this).hide();

									$(this).remove();
									// do your action for the old cell here
								} else {
									that = this;
								}

								// set the that if not already set
								that = (that == null) ? this : that;
							});
				});
	});
};

/*
 *
 * 같은 값이 있는 행을 병합함
 *
 * 사용법 : $('#테이블 ID').colspan (0);
 *
 */
$.fn.colspan = function(rowIdx) {
	return this.each(function() {
		var that;
		$('tr', this).filter(":eq(" + rowIdx + ")").each(function(row) {
			$(this).find('th').filter(':visible').each(function(col) {
				if ($(this).html() == $(that).html()) {
					colspan = $(that).attr("colSpan") || 1;
					colspan = Number(colspan) + 1;

					$(that).attr("colSpan", colspan);
					$(this).hide(); // .remove();
				} else {
					that = this;
				}

				// set the that if not already set
				that = (that == null) ? this : that;

			});
		});
	});
}

// 스크롤바 여부확인
$.fn.hasScrollBar = function() {
    return this.get(0).scrollHeight > Math.round(this.innerHeight());
}





