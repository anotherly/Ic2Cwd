<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>인천2호선 혼잡도 관제시스템</title>
	<meta charset="UTF-8">
    <jsp:include page="../../cmn/top.jsp" flush="false" />
    <style>
    	body {
		  -webkit-user-select:none;
		  -moz-user-select:none;
		  -ms-user-select:none;
		  user-select:none
		}
		
		/*24-10-25 : 테이블에서 간혹 커서가 바뀌는 문제 => 테이블의 td 영역에 커서를 포인터로 맞춤(important 처리)*/
		table td{
			cursor : pointer !important;
		}
		
    </style>
<script>
		
function uploadExcel() {
	console.log("uploadExcel");
    var formData = new FormData($("#insertForm")[0]);
    $.ajax({
        url: "/afc/uploadExcel",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function(result) {
            alert("업로드 성공!");
        },
        error: function() {
            alert("업로드 실패!");
        }
    });
}
    
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
    <div id="container" class="container-wrap" style="margin-top: 0px;">
		<!-- header Start ------------------>
		<div id="header" class="header-wrap">
		</div>
		<!-- header End ------------------>
		<div id="title" class="title-wrap">
			<div class="title-inner">
				<div class="title_segments" role="tablist">
					<button id="today_menu" class="nav-link" role="tab" aria-selected="false" onclick="location.href='/setting/cwd/selectDownLog.do'">금일 데이터 목록</button>
					<button id="all_menu" class="nav-link" role="tab" aria-selected="false" onclick="location.href='/setting/cwd/selectDownLogAll.do'">전체 데이터 목록</button>
					<button id="upload_menu" class="nav-link active" role="tab" aria-selected="false" onclick="location.href='/setting/cwd/excelUpload.do'">AFC 데이터 업로드</button>
					<button id="versus_menu" class="nav-link" role="tab" aria-selected="false" onclick="location.href='/setting/cwd/versusAFCtoKPA.do'">AFC 응하중 비교</button>
				</div>
			</div>
		</div>
		<!-- contents Start ------------------>
		<div id="contents" class="contents-wrap">
			<!-- work Start -->
			<div id="work" class="work-wrap list_page">
				<div class="ctn_tbl_header">
					<div class="ttl_ctn">데이터 업로드</div>
				</div>

					<form id="insertForm" name="insertForm" method="post" enctype="multipart/form-data">
						<!-- 컨텐츠 테이블 영역 Start -->
						<div class="ctn_tbl_area">
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">파일첨부</div>
								<div class="ctn_tbl_td">
									<input type="file" name="file" class="form-control">
								</div>
								
								<div class="ctn_tbl_th fm_rep">상/하행 구분</div>
								<div class="ctn_tbl_td">
									<select id ="activeCap" name="activeCap" class ="ctn_select_box1">
										<option value="1">상행</option>
										<option value="2">하행</option>
									</select>
								</div>
							</div>
						</div>
						
						<!-- btn_box Start -->
						<div class="btn_box">
							<div class="right">
								<input type="button" class="btn btn_primary" value="업로드" onclick="uploadExcel()">
							</div>
						</div>
						<!-- btn_box End -->
					</form>

                
            </div>
			<!-- work End -->
        </div>
		<!-- contents End ------------------>
    </div>
    <!-- container End ------------------>
</body>

</html>