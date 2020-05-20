			//파일 정보처리
			function getFileInfo(fullName, path) {
				var originalFileName; // 화면에 출력할 파일명
				var imgSrc; // 썸네일 or 파일 아이콘 이미지 파일
				var originalFileUrl; // 원본파일 요청 URL
				var uuidFileName;	// 날짜 경로를 제외한 나머지 파일명(UUID)
				var basicFileName = fullName; // 삭제시 값을 전달하기 위한
				
				//이미지 파일이면
				if(checkImageType(fullName)){
					imgSrc = path+"/upload/displayFile?fileName=" + fullName; // 썸네일 이미지 링크
					uuidFileName = fullName.substr(14); //fullName.substr(14); 14부터 끝까지 이름을 가져오세요 
					var originalImg = fullName.substr(0,12) + fullName.substr(14); // s_ 뺀 주소들을 가져옴 즉 원본이미지 경로를 알려줌
					//원본 이미지 요청링크
					originalFileUrl = path+"/upload/displayFile?fileName=" + originalImg; 
				}else { 
					imgSrc = path+"/resources/img/file-icon.png"; //파일 아이콘 이미지 링크
					uuidFileName = fullName.substr(12);
					//파일 다운로드 요청링크
					originalFileUrl = path+"/upload/displayFile?fileName=" + fullName;
				}
				originalFileName = uuidFileName.substr(uuidFileName.indexOf("_") + 1);
				//전체 파일명의 크기가  14보다 작으면 그래도 이름 출력
				// 14보다 크면 실행
				if(originalFileName.length > 14){
					//앞에서부터 11글자 자름
					var shortName = originalFileName.substr(0,10);
					//.을 기준으로 배열 생성
					var formatVal = originalFileName.split(".");
					//formatVal = originalFileName.substr(originalFileName.length-3)
					//파일명에 .이 여러개 들어가 있을 수도 있음
					// 배열 크기를 구해왕서 무조건 맨 마지막 확장자부분 출력되게 함
					var arrNum = formatVal.length -1;
					//맨처음 문자열 10글자 + ....+ 확장자
					originalFileName = shortName + "..." + formatVal[arrNum];
				}
				return {originalFileName: originalFileName, imgSrc: imgSrc, originalFileUrl: originalFileUrl, fullName: fullName, basicFileName: basicFileName};
			}
			
			
			//첨부파일 출력
			// "/2020/04/08s_asdf2_dobby.jpg", ${path}
			function printFiles(data, path) {
				//파일정보처리
				var fileInfo = getFileInfo(data, path);
				console.log(fileInfo);
				//Handlebars 파일 템플릿에 파일 정보들을 바인딩하고 HTML 생성
				var html = fileTemplate(fileInfo); // fileInfo 의 5개의 값을 가짐 fileTemplate는 register.jsp에 있다.
				html += "<input type='hidden' class='file' value='"+ fileInfo.fullName +"'>";
				//Handlebars 파일 템플릿 컴파일을 통해 생성된 HTML을 DOM에 주입
				$(".uploadedList").append(html);
				// 이미지 파일인 경우 aaaaaa파일 템플ㄹ릿에 lightbox 속성 추가
				if(fileInfo.fullName.substr(12,2) === "s_"){
					//마지막에 추가된 첨부파일 템플릿 선택자
					var that = $(".uploadedList li").last();
					//lightboax 속성 추가
					//that.find(".mailbox-attachment-name").attr("data-lightbox","uploadImages"); <-- 사용안함. 실행안되고 있는 코드
					//파일 아이콘에서 이미지 아이콘으로 변경
					that.find(".fa-paperclip").attr("class","fa fa-camera");
					
				}
									
			}
			
			
			function getOriginalName(fileName) {
				if(checkImageType(fileName)){ // 이미지 파일이면 skip
					return;
				}
				var idx= fileName.indexOf("_")+1; // uuid를 제외한 파일이름
				return fileName.substr(idx);
			}
			function getImageLink(fileName) {
				if(!checkImageType(fileName)){ // 이미지 파일이 아니면 skip
					return;			
				}
				var front=fileName.substr(0,12); // 연월일 결로
				var end=fileName.substr(14); // s_제거
				return front+end;		
			}
			function checkImageType(fileName) {
				var pattern =/jpg|gif|png|jpeg/i; // 정규표현식(대소문자 무시)
				return fileName.match(pattern); // 규칙에 맞으면 true			
			}
			
			
			//첨부파일 리스트를 출력하는 함수
			function listAttach(path, bno) {
				var listCnt = 0;
				$.ajax({
					type: "post",
					url: path+'/board/getAttach?bno='+bno,
					async: false,
					success: function (list) {
						//list: json
						//console.log(list);
						
						listCnt = list.length;
						/* console.log(list.length); */
						
						/* 
							jQuery each()는 반복문
							i와 e는 index와 element로
							json에서 {0:"apple.png"}일 때
							index는 0, element는 apple.png가 됌
						*/
						$(list).each(function(i,e) {
							console.log(list);		
							printFiles(e, path); // 첨부파일 출력 메서드 호출
						});					
					}							
				});
				return listCnt;			
			}			