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
		
		/* $(".title_segments button").each(function(i,list){
			console.log("진입");
			if(lastWord == 'selectDownLog.do') { // 금일이라면
				console.log("금일 진입");
				$("#today_menu").addClass("active");
			} else {
				console.log("전체 진입");
				$("#all_menu").addClass("active");
			}
		}); */
	
		//데이터테이블은 백단에서 리스트나 배열로 안보내고 vo로 보내면 못읽음 ㅡㅡ
		var tb2=$("#tableList").DataTable({
		
			"processing": true,
		    //"serverSide": true,
		    "ajax": {
		        "url": "/afc/selectDownLogListToday.ajax?" + $.param({ activeCap: 1 }),
		        "type": "GET"
            },  
            columns: [
                {data:"rcvDt"}, //편성
                {data:"formationNo"}, //편성
                {data:"activeCap"},//상하행
                {data:"stationName"},//역사명
                
                {data:"kpa1"}, //kpa1
                {data:"kpa2"}, //kpa2
                {data:"kpa3"}, //kpa3
                {data:"kpa4"}, //kpa4
                
                {data:"avg1"},
                {data:"avg2"},
                
                {data:"cal1"},
                {data:"cal2"},
                {data:"range1"},
                {data:"range2"},
                
                {data:"rate1"},
                {data:"rate2"},

                
            ],
            "lengthMenu": [ [5, 10, 20], [5, 10, 20] ],
            "pageLength": 10,
            pagingType : "full_numbers",
            columnDefs: [ 
            	{ orderable: false, targets: [0] }//특정 열(인덱스번호)에 대한 정렬 비활성화
            	,{className: "dt-center",targets: "_all"} 
            ],
            select: {
                style:    'multi',
                selector: 'td:first-child'
            }
           ,"order": [
	            [2, "asc"],  
    	        [1, "asc"],  
    	        [0, "asc"] // 첫 번째 컬럼을 오름차순 정렬
        	] 
           ,responsive: true
           ,language : lang_kor // //or lang_eng
		});

		//데이트타임피커
		
		 // 오늘 날짜를 YYYY-MM-DD 형식으로 가져오기
	    // Moment.js를 사용하여 어제 날짜의 05:50:00과 06:10:00 설정
	    function getFormattedDateTime(hour, minute) {
		    return moment().set({ hour: hour, minute: minute, second: 0 }).format('YYYY-MM-DD HH:mm:ss');
		}

    
    let defaultStart = getFormattedDateTime(7, 10);
    let defaultEnd = getFormattedDateTime(7, 20);
    
    $("#datetimepicker1").datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss',
        useCurrent: false,
        defaultDate: moment(defaultStart, 'YYYY-MM-DD HH:mm:ss'),
        minDate: moment(defaultStart, 'YYYY-MM-DD HH:mm:ss').startOf('day'),  
        maxDate: moment(defaultStart, 'YYYY-MM-DD HH:mm:ss').endOf('day')
    });
    
    $("#datetimepicker2").datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss',
        useCurrent: false,
        defaultDate: moment(defaultEnd, 'YYYY-MM-DD HH:mm:ss'),
        minDate: moment(defaultEnd, 'YYYY-MM-DD HH:mm:ss').startOf('day'),  
        maxDate: moment(defaultEnd, 'YYYY-MM-DD HH:mm:ss').endOf('day')
    });
		
		//데이터테이블 기본 검색 창 안보이게
		 $("#tableList_filter").attr("hidden", "hidden");
	});

	/* 검색 */
	 function search(){
		 console.log("검색");
		 let frm = $("#searchFrm").serialize();
		 var tagUrl="/afc/selectDownLogListToday.ajax";
		 tbSearch("tableList",tagUrl,frm);
	 }
	/* 다운로드 */
	 function excelDownload(){
		 console.log("엑셀 다운로드");
		 let frm = $("#searchFrm").serialize();
		 location.href='/afc/excelDownToday.ajax?'+frm;
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
					<button id="today_menu" class="nav-link active" role="tab" aria-selected="false" onclick="location.href='/setting/cwd/selectDownLog.do'">금일 목록</button>
					<button id="all_menu" class="nav-link" role="tab" aria-selected="false" onclick="location.href='/setting/cwd/selectDownLogAll.do'">전체 목록</button>
					<button id="upload_menu" class="nav-link" role="tab" aria-selected="false" onclick="location.href='/setting/cwd/excelUpload.do'">데이터 업로드</button>
				</div>
			</div>
		</div>
		<!-- contents Start ------------------>
		<div id="contents" class="contents-wrap">
			<!-- work Start -->
			<div id="work" class="work-wrap list_page">
				<div class="ctn_tbl_header">
					<div class="ttl_ctn">데이터 목록</div>
				</div>
                <!-- search_box Start -->
                <div class="search_box">
                	<form id="searchFrm" name="searchFrm" class="search_form" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="sch_text_01" class="form-control-label">편성번호</label>
                            <input type="number" id="formationNo" name="formationNo" placeholder="" class="form-control">
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
							<label class="form-control-label"><span class="langSpan">중복 행 제거수</span></label>
							<select class="form-control mw_30" id="dufCnt" name="dufCnt">
								<option value="2" selected>2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
		                    </select>
						</div>
                        <div class="form_daterange" style="display: inline-flex;align-items: center;gap: 5px;" id="schDtBody">
							<!-- 기간 -->
							<div class='input-group date' id='datetimepicker1'>
								<input type='text' class="form-control dt_search" name=sDate id="sDate" required/>
								<span class="input-group-addon">
									<span class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
							 ~ 
							<div class='input-group date' id='datetimepicker2'>
								<input type="text" class="form-control dt_search" id="eDate" name="eDate" required/>
								<span class="input-group-addon">
									<span class="glyphicon glyphicon-calendar"></span>
								</span>
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
										<th>시각</th>
										<th>편성번호</th>
										<th>상/하행</th>
										<th>역사명</th>
										
										<th>kpa1</th>
										<th>kpa2</th>
										<th>kpa3</th>
										<th>kpa4</th>

										<th>평균1</th>
										<th>평균2</th>
										
										<th>계산1</th>
										<th>계산2</th>
										<th>보정률1</th>
										<th>보정률2</th>
										
										<th>1량 혼잡률</th>
										<th>2량 혼잡률</th>
										
									</tr>
								</thead>
							</table>
						</div>
					</div>
					
					<div id ="btnDiv" class="btn_box" style="display: flex;flex-direction: row-reverse;float:right;">
						<button class="btn btn_primary" id="btnDownload" onclick="excelDownload()">
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