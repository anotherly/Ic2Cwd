<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>인천2호선 혼잡도 관제시스템</title>
	<meta charset="UTF-8">
    <jsp:include page="../../cmn/top.jsp" flush="false" />
    
    <script>
    var delUrl="/setting/asp/aspDeleteD.ajax";
	var delbak="/setting/asp/aspList.do";
	
	$(document).ready(function(){
		//console.log("상세");
		var tagId='${data.userId}';
		$("#btnSave").on('click', function(){
			/* $("#contents").load('/user/update.do',{"userId":tagId}); */
			
			//24-10-17 : 뒤로가기 문제 해결
			location.href='/setting/asp/aspUpdate.do?userId=' + tagId;
		});
		$("#btnCancel").on('click', function(){
			location.href='/setting/asp/aspList.do';
		});
		
		$('#btnDelete').on('click', function() {
			var checker = confirm('정말로 삭제하시겠습니까?');
			
			if(checker) {
				var userId = '${data.userId}';
				var data = {"userId":userId};
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
			<div id="work" class="work-wrap">
				<!-- contents_box Start -->
				<div id="contents_box" class="contents_box">
					<!-- 컨텐츠 테이블 헤더 Start -->
					<div class="ctn_tbl_header">
						<div class="ttl_ctn">사용자 상세정보</div>
					</div>
					<!-- 컨텐츠 테이블 헤더 End -->
					<!-- 컨텐츠 테이블 영역 Start -->
					<div class="ctn_tbl_area">
						<div class="ctn_tbl_row">
							<div class="ctn_tbl_th fm_rep">사용자 ID</div>
							<div class="ctn_tbl_td">
								${data.userId}								
							</div>
							<div class="ctn_tbl_th fm_rep">사용자 이름</div>
							<div class="ctn_tbl_td">
								${data.userName}
							</div>
						</div>
						<div class="ctn_tbl_row">
							<div class="ctn_tbl_th ">직급</div>
							<div class="ctn_tbl_td">
								${data.userRank}
							</div>
							<div class="ctn_tbl_th ">부서</div>
							<div class="ctn_tbl_td">
								${data.userDept}
							</div>
						</div>
						
						<div class="ctn_tbl_row">
							<div class="ctn_tbl_th ">휴대 전화번호 ( - 생략)</div>
							<div class="ctn_tbl_td">
								${data.userPhone}
							</div>
							<div class="ctn_tbl_th ">이메일</div>
							<div class="ctn_tbl_td">
								${data.userEmail}
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
			</div>
			<!-- work End -->
		</div>
		<!-- contents End ------------------>
	</div>
	<!-- container End ------------------>
</body>
</html>