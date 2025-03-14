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
	/* .ctn_tbl_row .ctn_tbl_th {
	    flex: 0 0 150px;
	} */
	
	/* 단말기 관리 > 상세 페이지의 경우 세로로 길기 때문에 x축만 overflow hidden 처리 */
/* 	html, body {
		overflow-x : hidden;
		overflow-y : auto;
	}
	
	.ctn_tbl_row .ctn_tbl_th,.ctn_tbl_row .ctn_tbl_td{
		min-height:unset;
		height:50px;
	}
	
	@media screen and (max-width: 1920px){
		.ctn_tbl_row .ctn_tbl_th,.ctn_tbl_row .ctn_tbl_td{
			height:40px;
		}
	} */
	
	</style>
	<script>
		var delUrl="/terminal/deleteD.ajax";
		var delbak="/terminal/list.do";
	
		$(document).ready(function(){
			//console.log("상세");
			var tagId="${data.formationNo}";
			
			$("#btnSave").on('click', function(){
				//console.log('클릭');
				/* $("#work").load('/terminal/update.do',{"formationNo":tagId}); */
				location.href='/terminal/update.do?formationNo='+tagId;
			});
			
			$("#btnCancel").on('click', function(){
				location.href='/terminal/list.do';
			});
			//$(".ctn_tbl_row .ctn_tbl_th").css("flex","0 0 150px");
			
			// 24-10-28 : 상세에서 삭제 버튼 생성
			$('#btnDelete').on('click', function(){
				var checker = confirm('정말로 삭제하시겠습니까?');
				
				if(checker) {
					var lteIp = '${data.formationNo}';
					var data = {"lteIp":lteIp};
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
							<div class="ctn_tbl_th">편성번호</div>
							<div class="ctn_tbl_td">
								${data.formationNo}
							</div>
							<div class="ctn_tbl_th">장비 Mac Address</div>
							<div class="ctn_tbl_td">
								${data.deviceMac}
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
							<div class="ctn_tbl_th">제조사</div>
							<div class="ctn_tbl_td">
								${data.carName}
							</div>
							<div class="ctn_tbl_th">담당자</div>
							<div class="ctn_tbl_td">
								${data.deviceUser}
							</div>
						</div>
						<div class="ctn_tbl_row">
                            <div class="ctn_tbl_th fm_rep">비고(기타사항)</div>
                            <div class="ctn_tbl_td">
                                <textarea id="CONTENT" name="etc" class="long-cont" style="height:400px;resize:none;width: 100%;border: 1px solid lightgray;" required></textarea>
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