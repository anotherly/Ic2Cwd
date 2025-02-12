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
		
		gauge=gaugeChart("gauge_chart",alData.gaugeCnt);
		bar=barChart("bar_chart",alData.xyList);
		
		//최초 사이즈 지정
		$("#gauge_chart").css('min-width','25vw');
		$("#gauge_chart").css('min-height','30vh');
		$("#bar_chart").css('min-width','45vw');
		$("#bar_chart").css('min-height','38vh');
		
		//반응형 화면 사이즈에 맞춰 사이즈 조정
		gauge.resize();
		bar.resize();
		
		var tbM='';
			tbM+='<tr><td>편성번호</td><td>'+alData.train.trainNo+'</td></tr>'
			tbM+='<tr><td>열차번호</td><td>'+alData.train.formationNo+'</td></tr>'
			tbM+='<tr><td>운행방향</td><td>'+alData.train.activeCap+'</td></tr>'
			tbM+='<tr><td>중련갯수</td><td>'+alData.train.trainAddCnt+'</td></tr>'
			tbM+='<tr><td>정보수신</td><td>'+alData.train.rcvDt+'</td></tr>'
			$("#train_info").append(tbM);
		cssChart();
	});
</script>
</head>
	<div id="pie_container" class="pie_container" >
		<div class="comment-div">
			<div style="display: flex; align-items: center; justify-content: space-around; width: 338px;">
				<img src="../images/ic2/train_cwd_df.png"><h1 class="chart-title" id="chart_title0">열차 정보</h1>
			</div>
			<!-- <div id="train_info">
				<span>편성번호 ${alData.train.formationNo}</span>	<br>			
				<span>열차번호 ${alData.train.trainNo}</span>	<br>			
				<span>상행 / 0중련 </span> <br>
				<span>192.168.2.151</span>
			</div> -->
			
			<table id="train_info"></table>
			
		</div>
		<!-- 주의 혼잡 gauge -->
		<div class="pie-contents">
			<div id="gauge_chart" class=""></div>
		</div>
	</div>
	<div>
		<div id="bar_container">
			<h1 class="chart-title"  id="chart_title2" style="padding-left: 30px;">금일 운행 기록</h1>
			<div id="bar_chart" class=""></div>
		</div>
	</div>
	
</html>