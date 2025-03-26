<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>인천2호선 혼잡도 관제시스템</title>
<meta charset="UTF-8">
<script>

	var gauge;//게이지차트
	var bar;//바차트

	$(document).ready(function(){
		console.log("/stat/mainChart");
		var alData=ajaxMethod("/stat/mainChart.ajax",{"formationNo":$('#lteTbd').find('.selected').attr('id')});
		console.log("결과 aldata : "+alData);
		
		var xList=[]; var yList=[]; var yList2=[];
		
		if(typeof alData.xyList !== "undefined" && alData.xyList != null || alData.xyList.length !=0){
			for (var i = 0; i < alData.xyList.length; i++) {
				if(i==0){
					xList.push("시간");
					yList.push("평균 혼잡도");
					yList2.push("최대 혼잡도");
				}else{
					xList.push(alData.xyList[i].xVal);
					yList.push(alData.xyList[i].yVal);
					yList2.push(alData.xyList[i].yVal2);
				}
			}
			
			bar=barChart("bar_chart",xList,yList,yList2);
		}
		gauge=gaugeChart("gauge_chart",alData.gaugeCnt);
		
		
		
		//최초 사이즈 지정
		$("#gauge_chart").css('min-width','25vw');
		$("#gauge_chart").css('min-height','30vh');
		$("#bar_chart").css('min-width','45vw');
		$("#bar_chart").css('min-height','38vh');
		
		//반응형 화면 사이즈에 맞춰 사이즈 조정
		gauge.resize();
		bar.resize();
		
		var tbM='';
			tbM+='<tr><td>편성번호</td><td>'+alData.train.formationNo+'</td></tr>'
			/* tbM+='<tr><td>열차번호</td><td>'+alData.train.trainNo+'</td></tr>' */
			/* tbM+='<tr><td>운행방향</td><td>'+alData.train.activeCap+'</td></tr>' */
			tbM+='<tr><td>중련갯수</td><td>'+alData.train.trainAddCnt+'</td></tr>'
			tbM+='<tr><td>'
					+'<sapn style="margin-right: 10px;">제</sapn>'
					+'<sapn style="margin-right: 10px;">조</sapn>'
					+'<sapn>사</sapn>'
				+'</td><td>'+alData.train.carName+'</td></tr>'
			/* tbM+='<tr><td>최신 수신시각</td><td>'+alData.train.rcvDt+'</td></tr>' */
			$("#train_info").append(tbM);
		cssChart();
	});
	
	//접어두기
	$("#flip").on('click',function(){
		$("#lteTbd tr td").each(function(i,list){
			$(this).removeClass("selected");
		});
		chkTerId='';
		$("#all_chart").empty();
		$("#all_chart").hide();
	});
	
</script>
</head>
	<span id="flip">◀◀ 접어두기</span>
	<div id="pie_container" class="pie_container" >
		<div class="comment-div">
			<div style="display: flex; align-items: center; justify-content: space-around; width: 338px;">
				<img src="../images/ic2/train_cwd_df.png"><h1 class="chart-title" id="chart_title0">열차 정보</h1>
			</div>
			
			<table id="train_info" style="margin-left: 106px;text-align: left;"></table>
			
		</div>
		<!-- 주의 혼잡 gauge -->
		<div class="pie-contents">
			<div id="gauge_chart" class=""></div>
		</div>
	</div>
	<div>
		<div id="bar_container">
			<h1 class="chart-title"  id="chart_title2" style="padding-left: 30px;">금일 시간대별 평균 혼잡률</h1>
			<div id="bar_chart" class=""></div>
		</div>
	</div>
	
</html>