 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>인천2호선 혼잡도 관제시스템</title>
	<meta charset="UTF-8">
    <jsp:include page="../cmn/top.jsp" flush="false" />
<script>

	var teamCode='';
	var chkTerId='';
	var iidx='';
	var pie1;
	var pie2;
	var bar;
	
	$(document).ready(function(){
		console.log("main");
		// 24-10-10 : 페이지 옮겨도 30초 후 갱신 시 페이지 변하지 않도록 처리하기 위한 변수
		var nowPage = 0;
		var nextPage = 1;
		var firstId='';
		$('#trainTb tr td').each(function(index, value){
		    console.log("id" +  " : "  + $(this).attr('id'));
		    firstId=$(this).attr('id');
		});
		
		//우측 단말기 리스트
		var alData=ajaxMethod("/terminal/mainTerminalList.ajax");
		var Fcounter = alData.data.length;
		
		countRouter(teamCode,Fcounter); // 단말기 총 개수를 세는 함수
		trainOne(alData.data,9);
		hideTr(nowPage*6,nextPage*6);
		
		// 단말기 총 개수 함수
		function countRouter(teamCode,counter) {	
			if(counter == 0 || counter == "undefined" || counter == null || counter == '') {
				$('#countRouter').text("차량 개수 : 0개" );
			} else {
				$('#countRouter').text("차량 개수 : " + counter + " 개" );
				$('#countRouter').css('color','#fff');
			}
		}
	});	
	//동적 테이블(삭제 및 갱신)시 td 클릭 이벤트
	//차트 상세
    $(document).on('click','#trainTb td',function(){
    	var cwd = $(this).find('.img-container div').attr('id');
    	if(cwd!=0){
    		var tblist= $(".lte-table td");
        	$(tblist).each(function(i,list){
    			$(list).removeClass('selected');
    		});
        	//timerReset(firstTimer);
        	$(this).addClass("selected");
        	$("#all_chart").empty();
        	chkTerId=$(this).attr('id');
        	$("#all_chart").load("/stat/mainChart.do",{"formationNo":chkTerId});
        	$("#all_chart").show();
    	}
    });
	
	//화면 갱신
	var firstTimer = setInterval(function() {
		
		var alData=ajaxMethod("/terminal/mainTerminalList.ajax");
		var Fcounter = alData.data.length;
		trainOne(alData.data,9);
	
	}, 30*1000);
	//열차별로 상대값 달리 보여줌
	/* var firstTimer = setInterval(function() {
		
		$('#trainTb tr td').each(function(index, value){
		    var cwd = $(this).find('.img-container div').attr('id');
		    if($(this).attr('class')!='selected' && cwd!=0&& index>iidx){
		    	console.log("선택인가");
		    	$(this).trigger('click');
		    	iidx=index;
		    	return false;
		    }
		    if(index==$('#trainTb tr td').size()-1){
		    	iidx=-1;
		    }
		});
	}, 5000); */
	
</script>
</head>
<body class="open" style="background: url(../images/bg/bg0.png);background-size: cover;">
    <!-- lnb Start ------------------>
    <aside id="lnb" class="lnb">
        <a class="lnb-control" title="메뉴 펼침/닫침"><span class="menu-toggle">메뉴 펼침/닫침</span></a>
        <nav class="navbar">
			<ul class="menu-inner"></ul>
        </nav>
    </aside>
    <!-- lnb End ------------------>

    <!-- container Start ------------------>
    <div id="container" class="container-wrap" style="margin-top: 60px;background: none;" >
		<!-- header Start ------------------>
		<div id="header" class="header-wrap"></div>
		<!-- header End ------------------>

		<!-- contents Start ------------------>
		<div id="containerAll" class="containerAll">
			<!-- 내용 부분 -->
			<div id="all_chart" class="container_b" style="display:none">
				
			</div>
			<!-- 우측 단말기 테이블 전체-->
			<div id="container_b" class="container_b" style="padding: 20px;width:100%;">
			
				<!-- 팀별 선택 현황 -->
				<!-- <div class="router_top">
					<div id="teamSlt" class="tab_container"></div>
					<span id="countRouter"></span>
					<div id="paging" style="width: 150px;">
						<span id="pageStart" class="pg-btn">◀</span>
						<span id="pageEnd"  class="pg-btn">▶</span>
					</div>
				</div> -->
				
				<!-- 단말기 테이블 -->
				<div id="trainDiv">
					<div class="lte-div">
						<div id="trainNum">
							<table id="trainTb" class="lte-table">
								<tbody id="lteTbd"></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
</body>

</html>