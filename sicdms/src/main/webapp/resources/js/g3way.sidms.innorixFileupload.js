var innorixFileUpload = function(targetObj, objectId, fileSe,  transferMode, callback, acceptFile) {
	this.targetObj 	= 	targetObj;
	this.objectId	=	objectId;
	this.callback	=	callback;
	this.control    = 	null;
	this.fileSe		= 	fileSe;
	this.maxFileSize = 	104857600;	// 한 파일의 최대 크기 100Mb(bytes) 설정

	// 장구분을 설정하면 장 별로 업로드 됨
	// 파일 갯수 최대를 설정하면 최대 갯수만큼만 업로드 가능 (기본 5개까지만 허용)
	this.chptNo		=	null;				// 기본계획 장구분
	this.maxCnt		=	5; 					// 최대 업로드 갯수는 기본 5개

	this.transferMode	= (transferMode != undefined && transferMode != null) ? transferMode :"both"; 	// 이노릭스 모드 설정

	this.btnVisible =	"N";				// 전송버튼 Visiblilty

	this.fileList	=	[];

	this.acceptFile = (acceptFile != undefined && acceptFile != null) ? acceptFile :"pdf,hwp";

	this.uploadUrl = '/sicims000Cm/innorix/uploadFile.do';

	this.init();
};

innorixFileUpload.prototype = {

		init : function() {
			this.contextPath 	= 	"";
			this.makeFileUpload();
		},

		makeFileUpload : function() {
			if ($("#" + this.targetObj).data("maxcnt") != null) {
				this.maxCnt	= $("#" + this.targetObj).data("maxcnt");		// maxCount를 따로 설정하면 변경
			}

			if ($("#" + this.targetObj).data("chptno") != null) {
				this.chptNo	= $("#" + this.targetObj).data("chptno");
			}

			if ($("#" + this.targetObj).data("btnvisible") != null) {
				this.btnVisible	= $("#" + this.targetObj).data("btnvisible");
			}

			if ($("#" + this.targetObj).data("maxfilesize") != null) {
				this.maxFileSize	= $("#" + this.targetObj).data("maxfilesize");	// maxFileSize 따로 설정하면 변경
			}

			if ($("#" + this.targetObj).data("uploadUrl") != null) {
				this.uploadUrl	= $("#" + this.targetObj).data("uploadUrl");	// maxFileSize 따로 설정하면 변경
			}

			var htmlStr = "";
			var fileControlId = 'fileControl' + (this.chptNo != undefined && this.chptNo != null ) ? "_" + this.chptNo :"";
			var limitExtension = ["exe", "bat", "sh", "jar", "sys", "cmd", "isp"];
			var acceptFileList = this.acceptFile.split(',')

			htmlStr += "<div>";
			htmlStr += "<a href='javascript:;' onclick='"+this.objectId+".toggleFileUpload(this);' title='파일첨부 열기' class = 'attachedFile fl_right'><img src='/sidms/resources/images/icons/icon_arrow_down.png' width='26' height='26' alt=' 파일첨부 열기 아이콘' /></a>";
			//htmlStr += "	<button type='button' class='btn_basic fl_right' onClick='"+this.objectId+".toggleFileUpload(this);'>파일업로드 <i class='fas fa-chevron-down'></i><i class='fas fa-chevron-up' style='display:none;'></i></button>";
			// 장구분이 있는 경우
			if(this.chptNo != null) {
				htmlStr += "		<div>";
				htmlStr += "			<label for='attachFileList_"+this.chptNo+"' class='label_file fl_right'>파일선택</label>";
				htmlStr += "			<input type='file' id='attachFileList_"+this.chptNo+"' name='attachFileList_"+this.chptNo+"' multiple='multiple'";
				if(acceptFileList != undefined){
					htmlStr += " title='";
					for(var idx = 0; idx < acceptFileList.length; idx++){
						htmlStr += acceptFileList[idx]
						if(idx < acceptFileList.length -1 ){
							htmlStr += '|';
						}
					}
					htmlStr += " 파일 선택'";

					htmlStr += " accept='";
					for(var idx = 0; idx < acceptFileList.length; idx++){
						htmlStr += '.'+acceptFileList[idx];
						if(idx < acceptFileList.length -1 ){
							htmlStr += ', ';
						}
					}
					htmlStr += "'";
				}
				else {
					htmlStr += "title='pdf|hwp 파일 선택' accept='.pdf, .hwp'";
				}
				htmlStr += "			/>";
				htmlStr += "		</div>";
			} else {
				htmlStr += "		<div>";
				htmlStr += "			<label for='attachFileListUpload' class='label_file fl_right'>업로드</label>";
				htmlStr += "			<input type='button' id='attachFileListUpload' name='' class='disPlayNone' onclick='"+this.objectId+".control.upload();' />";
				htmlStr += "			<label for='attachFileList' class='label_file fl_right' style= 'margin-right : 10px !important'>파일선택</label>";
				htmlStr += "			<input type='button' id='attachFileList' name='' class='disPlayNone' onclick='"+this.objectId+".control.openFileDialog(this);' />";
				htmlStr += "		</div>";
			}
			htmlStr += "	<div class='div-file-body div-innorix-file-body' style='display:none;'>";
			htmlStr += "		<div id = '"+fileControlId+"' class=''>";
			htmlStr += "		</div>";
			htmlStr += "	</div>";
			htmlStr += "</div>";

			$("#" + this.targetObj).append(htmlStr);

			this.control = innorix.create({
		         	el: "#"+fileControlId, 					// 컨트롤 출력 HTML 객체 ID
					transferMode : this.transferMode,		// 업로드, 다운로드 혼합사용
					maxFileCount : this.maxCnt,				// 첨부 할 수있는 최대 파일 수 설정
		     	    maxFileSize	 : this.maxFileSize,		// 한 파일의 최대 크기 (bytes) 설정
		     	    //maxTotalSize : 1000,					// 첨부 가능한 파일의 총 크기 (bytes) 설정
					allowExtension : acceptFileList,		// 허용한 첨부파일 확장자명
					limitExtension : limitExtension,		// 제한된 첨부파일 확장자명
					autoReTransfer : false,					// 오류시 재시도 설정
					folderIntact : false, 					// 폴더 첨부 불가
					maximumErrorCount : 0,					// 최대에러 허용 수
					width  : 1120,                    		// 컨트롤 출력 너비(pixel)
                    height : 250,                    		// 컨트롤 출력 높이(pixel)
                    installPopupWindow : false,				//설치화면 팝업 호출 여부
		         	installUrl: g3way.sidms.common.contextPath + '/innorix/install/install.html', // Agent 설치 페이지
		         	uploadUrl: g3way.sidms.common.contextPath + this.uploadUrl // 업로드 URL
		     });

			var postObj = new Object();
			 postObj.file_se = "sy";
			 postObj.file_path = "/"+postObj.file_se;
			 postObj.chptNo = this.chptNo;

			this.control.setPostData(postObj);

			//첨부파일 추가 전 이벤트
			this.control.on('beforeAddFile', this.beforeAddFile.bind(this));
			//업로드 완료 이벤트
			this.control.on('uploadComplete', this.uploadComplete.bind(this));
			//업로드 에러 이벤트
			this.control.on('uploadError', this.uploadError.bind(this));
			//첨부파일 추가 후 이벤트
			this.control.on('afterAddFiles', this.fileUploadShow.bind(this));
		},

		beforeAddFile : function(param){
			var fileSe = $("#fileSe").val();

			if(param.fileSize == 0){
				const fileOrgnlNm = param.filePath.replace(param.basePath+"\\", "");
				g3way.sidms.common.messageBox(null, "첨부파일 추가 오류", "파일 사이즈가 0인 파일은 업로드 할 수 없습니다.", null, "N");
				return false;
			} else if(fileSe == "sp" && param.fileSize > 52428800) {
				const fileOrgnlNm = param.filePath.replace(param.basePath+"\\", "");
				g3way.sidms.common.messageBox(null, "첨부파일 추가 오류", "파일 사이즈가 50MB를 초과하는 파일은 업로드 할 수 없습니다.", null, "N");
				return false;
			}
		},
		uploadComplete : function(parma){
	         this.fileList	= [];
	         var f = parma.files;
	         var r = "Upload complete\n\n";
	         for (var i = 0; i < f.length; i++ ) {
	             r += f[i].controlId + " " + f[i].clientFileName + " " + f[i].fileSize + "\n";
	             var obj = new Object();
	         	//String _action            // 동작 플래그
	 			//String _orig_filename     // 원본 파일명
	 			//String _new_filename      // 저장 파일명
	 			//String _filesize          // 파일 사이즈
	 			//String _start_offset      // 파일저장 시작지점
	 			//String _end_offset        // 파일저장 종료지점
	 			//String _filepath          // 파일 저장경로
	 			//String _el                // 컨트롤 엘리먼트 ID
	 			//String _type              // 커스텀 정의 POST Param 1
	 			//String _part              // 커스텀 정의 POST Param 2
	 			//String _file_se           // 커스텀 정의 POST Param 1
	 			//String _file_path         // 커스텀 정의 POST Param 1
	 			//String _chptNo           	// 커스텀 정의 POST Param 1
	 			//String _transferId		// TransferId

	             obj.rowID 		= f[i].rowID;				/* 파일 로우 ID */
	             obj.fileSe 	= this.fileSe;				/* 파일구분 */
	             obj.fileNm		= f[i].serverFileName;		/* 서버에저장된이름 */
	             obj.fileOrgnlNm	= f[i].clientFileName;	/* 원본파일이름 */
	             obj.fileSz 	= f[i].fileSize;			/* 파일사이즈 */
	             obj.fileExtn	= f[i].fileExtn; 			/* 확장자이름 */
	             if(this.fileChptNo != null ){
	            	 obj.fileChptNo = this.chptNo;			/* 챕터번호 */
	             }
	             this.fileList.push(obj);
	         }
	         //콜백함수가 있는경우 업로드 후 콜백함수 실행
	         if(this.callback != undefined && typeof(this.callback) == 'function'){
            	 this.callback();
             }
		},

		uploadError : function(parma){
	         var f = parma.files;
	         var r = "Upload uploadError\n\n";
	         for (var i = 0; i < f.length; i++ ) {
	             r += f[i].controlId + " " + f[i].clientFileName + " " + f[i].fileSize + "\n";
	             var fileOrgnlNm = f[i].clientFileName;	/* 원본파일이름 */
	             g3way.sidms.common.messageBox(null, "업로드 오류", "'"+fileOrgnlNm +"' 파일 업로드중 오류가 발생했습니다.", null, "N");
		         //전송창 닫기
		         this.control.closeTransferWindow();
		         return;
	         }
		},

		toggleFileUpload : function(object){

			if($(object).hasClass('active')){
				$(object).removeClass('active');
				$(object).siblings('.div-innorix-file-body').hide();
				$(object).children('img').attr('src', g3way.sidms.common.contextPath+'/resources/images/icons/icon_arrow_down.png');
			}
			else {
				$(object).addClass('active');
				$(object).siblings('.div-innorix-file-body').show();
				$(object).children('img').attr('src', g3way.sidms.common.contextPath+'/resources/images/icons/icon_arrow_up.png');
			}
		},

		fileUploadShow: function(p){
			//첨부파일 검사
			if(this.fileAcceptCheck(p)){
				var $target 	= $("#"+this.targetObj).find("div.div-innorix-file-body");
				var object = $target.siblings('a.attachedFile')
				if(!$(object).hasClass('active')){
					$(object).addClass('active');
					$(object).siblings('.div-innorix-file-body').show();
					$(object).children('img').attr('src', g3way.sidms.common.contextPath+'/resources/images/icons/icon_arrow_up.png');
				}
			}
		},

		fileAcceptCheck : function(fileList) {
			if(fileList != null ){
				for(var idx = 0; idx < fileList.length; idx++) {
					var fileName = fileList[idx].printFileName;
					var fileId   = fileList[idx].id;
					var ext = fileName.split('.').pop().toLowerCase();
					if($.inArray(ext,  this.acceptFile.split(',')) == -1) {
						g3way.sidms.common.messageBox(null, "업로드 확장자 제한", this.acceptFile+" 확장자만 등록 가능합니다.", null, "N");
						this.control.removeFileById(fileId);
						return false;
					}
					//2. 파일명에 특수문자 체크
				    var pattern =   /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
				    if(pattern.test(fileName) ){
				        //alert("파일명에 허용된 특수문자는 '-', '_', '(', ')', '[', ']', '.' 입니다.");
				    	g3way.sidms.common.messageBox(null, "업로드 파일명 특수문제 제한", fileName+" 파일명에 특수문자를 제거해주세요.<br/>[허용 특수 문자'-', '_', '(', ')', '[', ']', '.' ]", null, "N");
				    	this.control.removeFileById(fileId);
				    	return false;
				    }
				}

			}
			return true;
		},
		beforeSend : function(dataForm, fileListName) {
			var appendFileListName = 'attachFileList';
			//첨부파일리스트 전송 이름
			if(fileListName != undefined) {
				appendFileListName = fileListName;
			}
			if(dataForm != undefined && dataForm.data != undefined ){
				if(this.fileList != undefined && this.fileList[0] instanceof Object && this.fileList[0].fileOrgnlNm != undefined) {
					setFileUploadFormData(dataForm.data, this.fileList, appendFileListName);
				}
				else {
					for(var i = 0; i < this.fileList.length; i++){
						dataForm.data.append(appendFileListName, this.fileList[i]);
					}
				}
			}
		}
};

