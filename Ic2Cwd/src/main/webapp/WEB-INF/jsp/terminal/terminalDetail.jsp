<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
 	<title>인천2호선 혼잡도 관제시스템</title>
	<meta charset="UTF-8">
    <jsp:include page="../cmn/top.jsp" flush="false" />
	<style>
	
	</style>
	<script>
		var delUrl="/terminal/terminalDeleteD.ajax";
		var delbak="/terminal/terminalList.do";
	
		$(document).ready(function(){
			//console.log("상세");
			var tagId="${data.formationNo}";
			
			$("#btnSave").on('click', function(){
				//console.log('클릭');
				/* $("#work").load('/terminal/update.do',{"formationNo":tagId}); */
				location.href='/terminal/terminalUpdate.do?formationNo='+tagId;
			});
			
			$("#btnCancel").on('click', function(){
				location.href='/terminal/terminalList.do';
			});
			//$(".ctn_tbl_row .ctn_tbl_th").css("flex","0 0 150px");
			
			// 24-10-28 : 상세에서 삭제 버튼 생성
			$('#btnDelete').on('click', function(){
				var checker = confirm('정말로 삭제하시겠습니까?');
				
				if(checker) {
					var delId = '${data.formationNo}';
					var data = {"formationNo":delId};
					ajaxMethod(delUrl, data, delbak);
				} else {
					return false;
				}
			});
		});
	
	</script>
</head>
<body class="open">
	<!-- lnb Start ------------------>
	    <aside id="lnb" class="lnb">
	        <a class="lnb-control" title="메뉴 펼침/닫침"><span class="menu-toggle">메뉴 펼침/닫침</span></a>
	        <nav class="navbar navbar-expand-sm navbar-default">
	            <ul class="menu-inner"></ul>
	        </nav>
	    </aside>
    <!-- lnb End ------------------>
    
    <!-- container Start ------------------>
	<div id="container" class="container-wrap"  style="margin-top: 0px;">
		<!-- header Start ------------------>
		<div id="header" class="header-wrap">
		</div>
		<!-- header End ------------------>
		<!-- contents Start ------------------>
		<div id="contents" class="contents-wrap">
			<!-- work Start -->
			<div id="work" class="work-wrap" style="padding-top: 0px;">
				<!-- contents_box Start -->
				<div id="contents_box" class="contents_box">
					<!-- 컨텐츠 테이블 헤더 Start -->
					<div class="ctn_tbl_header">
						<div class="ttl_ctn">차상 단말 상세정보</div>
					</div>
					<!-- 컨텐츠 테이블 헤더 End -->
					<!-- 컨텐츠 테이블 영역 Start -->
					<div class="ctn_tbl_area">
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">편성번호</div>
								<div class="ctn_tbl_td">
									${data.formationNo}
								</div>
								<div class="ctn_tbl_th">담당자</div>
								<div class="ctn_tbl_td">
									${data.deviceUser}
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">장비명(사용 용도)</div>
								<div class="ctn_tbl_td">
									${data.deviceUsed}
								</div>
								<div class="ctn_tbl_th">설치 위치</div>
								<div class="ctn_tbl_td">
									${data.insLocTxt}
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">제조사</div>
								<div class="ctn_tbl_td">
									${data.carName}
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">1량 공차값</div>
								<div class="ctn_tbl_td">
									${data.stWgt1}
									
								</div>
								<div class="ctn_tbl_th fm_rep">1량 만차값</div>
								<div class="ctn_tbl_td">
									${data.fcWgt1}
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">2량 공차값</div>
								<div class="ctn_tbl_td">
									${data.stWgt2}
								</div>
								<div class="ctn_tbl_th fm_rep">2량 만차값</div>
								<div class="ctn_tbl_td">
									${data.fcWgt2}
								</div>
							</div>
							<div class="ctn_tbl_row">
	                            <div class="ctn_tbl_th">비고(기타사항)</div>
	                            <div class="ctn_tbl_td">
	                                <textarea id="CONTENT" name="etc" class="long-cont" style="height:400px;resize:none;width: 100%;border: 1px solid lightgray;" readonly>${data.etc}</textarea>
	                            </div>
	                        </div>
						</div>
						
						<!-- btn_box Start -->
						<div class="btn_box">
							<div class="right">
								<input type="button" class="btn btn_primary" id="btnSave" alt="저장" value="수정" />
								<input type='button' id='btnDelete' class='btn btn_primary' value='삭제'>
								<input type="button" class="btn" id="btnCancel" alt="취소" value="취소" />
							</div>
						</div>
						<!-- btn_box End -->
				</div>
				<!-- contents_box End -->

				<!-- footer Start ------------------>
				<!-- footer End ------------------>

			</div>
			<!-- work End -->
		</div>
		<!-- contents End ------------------>
	</div>
	<!-- container End ------------------>
</body>
</html>