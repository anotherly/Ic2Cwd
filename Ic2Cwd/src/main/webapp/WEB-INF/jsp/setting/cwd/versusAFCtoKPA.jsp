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
		
		#tableList thead th{
			border: 1px solid grey;
		}
		#tableList tbody td{
			border: 1px solid grey;
		}
    </style>
<script>
	
	//데이터 테이블 관련
	var iidx;//날짜컬럼 인덱스
	var selectlang;
		
	$(document).ready(function(){
		//테이블 기본설정 세팅
		dtTbSetting();

		iidx = 3;
		//console.log("사용자 목록 화면 진입");
		var colCnt=0;
		var idxTb =0;
		
		/* 25-04-02 : 현재 페이지에 맞는 버튼 토글 생성 */
		var nowUrl=location.href;
		console.log(nowUrl);
		
		var lastWord = nowUrl.split("/").pop(); //마지막 값 가져오기
		
		console.log(lastWord);
	
		//데이터테이블은 백단에서 리스트나 배열로 안보내고 vo로 보내면 못읽음 ㅡㅡ
		var tb2=$("#tableList").DataTable({
		
			"processing": true,
		    //"serverSide": true,
		    "ajax": {
		        "url": "/afc/versusList.ajax?" + $.param({ activeCap: 1 }),
		        "type": "GET"
            },  
            columns: [
                {data:"rcvDt"}, //편성
                {data:"stationName"}, //편성
                {data:"trainNo"},//상하행
                {data:"peopleCnt"},//역사명
                {data:"rate"}, //kpa1
                
                {data:"kpaRcvDt"}, //kpa2
                {data:"kpaFormationNo"}, //kpa3
                {data:"kpaStationName"}, //kpa4
                {data:"sumKpa"},
                {data:"avgRate"},
                
                {data:"timeDiffSec"},
                
            ],
            "lengthMenu": [ [5, 10, 20], [5, 10, 20] ],
            "pageLength": 10,
            pagingType : "full_numbers",
            columnDefs: [ 
            	//{ orderable: false, targets: [0] },//특정 열(인덱스번호)에 대한 정렬 비활성화
            	{className: "dt-center",targets: "_all"} 
            ],
            select: {
                style:    'multi',
                selector: 'td:first-child'
            }
           ,"order": [
    	        [0, "asc"] // 첫 번째 컬럼을 오름차순 정렬
        	] 
           ,responsive: true
           ,language : lang_kor // //or lang_eng
		});

		//데이트타임피커
		
		 // 오늘 날짜를 YYYY-MM-DD 형식으로 가져오기
	    // Moment.js를 사용하여 어제 날짜의 05:50:00과 06:10:00 설정
	    function getFormattedDateTime(hour, minute) {
        return moment().subtract(1, 'days').format('YYYY-MM-DD');
    }
    
    let defaultStart = getFormattedDateTime(7, 10);
    let defaultEnd = getFormattedDateTime(7, 20);
    
    $("#datetimepicker1").datetimepicker({
        format: 'YYYY-MM-DD',
        useCurrent: false,
        defaultDate: moment(defaultStart, 'YYYY-MM-DD')
    });
    
		//데이터테이블 기본 검색 창 안보이게
		 $("#tableList_filter").attr("hidden", "hidden");
	});

	/* 검색 */
	 function search(){
		 console.log("검색");
		 let frm = $("#searchFrm").serialize();
		 var tagUrl="/afc/versusList.ajax";
		 tbSearch("tableList",tagUrl,frm);
	 }
	/* 다운로드 */
	function versusDownload(){
		 console.log("엑셀 다운로드");
		 let frm = $("#searchFrm").serialize();
		 location.href='/afc/versusDownload.ajax?'+frm;
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
					<button id="upload_menu" class="nav-link" role="tab" aria-selected="false" onclick="location.href='/setting/cwd/excelUpload.do'">AFC 데이터 업로드</button>
					<button id="versus_menu" class="nav-link active" role="tab" aria-selected="false" onclick="location.href='/setting/cwd/versusAFCtoKPA.do'">AFC 응하중 비교</button>
				</div>
			</div>
		</div>
		<!-- contents Start ------------------>
		<div id="contents" class="contents-wrap">
			<!-- work Start -->
			<div id="work" class="work-wrap list_page">
				<div class="ctn_tbl_header">
					<div class="ttl_ctn">afc 응하중 비교</div>
				</div>
                <!-- search_box Start -->
                <div class="search_box">
                	<form id="searchFrm" name="searchFrm" class="search_form" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="sch_text_01" class="form-control-label">편성번호</label>
                            <input type="number" id="kpaFormationNo" name="kpaFormationNo" placeholder="" class="form-control">
                        </div>
                        <div class="form-group col_14">
							<label class="form-control-label"><span class="langSpan">상하행 구분</span></label>
							<select class="form-control mw_30" id="activeCap" name="activeCap">
								<option value="">전체</option>
								<option value="1" selected>상행</option>
								<option value="2">하행</option>
		                    </select>
						</div>
                        <div class="form-group col_14">
							<label class="form-control-label"><span class="langSpan">허용 시간범위(분)</span></label>
							<select class="form-control mw_30" id="timeDiffSec" name="timeDiffSec">
								<option value="2" selected>2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
		                    </select>
						</div>
						
						<div class="form-group">
                            <label for="sch_text_01" class="form-control-label">집계일자</label>
                            <div class="form_daterange" style="display: inline-flex;align-items: center;gap: 5px;" id="schDtBody">
								<div class='input-group date' id='datetimepicker1'>
									<input type='text' class="form-control dt_search" name=sDate id="sDate" required/>
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
                        </div>
						
                        
                    </form>
                    <div class="search_btn">
                        <button class="btn btn_sch btn_primary"  onclick="search()"><i class="ico_sch"></i>조회</button>
                    </div>
                </div>
                <!-- search_box End -->

                <!-- grid_box Start -->
				<div class="datatable-list-01">
					<div class="page-description">
						<div class="rows">
							<table id="tableList" class="table table-bordered" style="width: 100%;">
								<thead>
									<tr>
										<th colspan='5'>AFC</th>
										<th colspan='6'>응하중</th>
									</tr>
									<tr>
										<!-- AFC -->
										<th>시각</th>
										<th>역명</th>
										<th>호차</th>
										<th>재차인원</th>
										<th>혼잡도</th>

										<!-- KPA -->
										<th>시각</th>
										<th>편성번호</th>
										<th>역명</th>
										<th>무게총합</th>
										<th>혼잡도</th>
										
										<th>시간차(초)</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					
					<div id ="btnDiv" class="btn_box" style="display: flex;flex-direction: row-reverse;float:right;justify-content: space-around;width: 250px;">
						<button class="btn btn_primary" id="btnDownload" onclick="versusDownload()">
							<span class="langSpan">다운로드</span>
						</button>
					</div>
				</div>
                <!-- grid_box End -->
            </div>
			<!-- work End -->
        </div>
		<!-- contents End ------------------>
    </div>
    <!-- container End ------------------>
</body>

</html>