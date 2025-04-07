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
		console.log("첫번째 값:" + alData.first.yVal2);
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
		//gauge=gaugeChart("gauge_chart",alData.gaugeCnt);
		
		
		
		//최초 사이즈 지정
		$("#gauge_chart").css('display','flex');
		$("#gauge_chart").css('flex-direction','column');
		$("#bar_chart").css('min-width','45vw');
		$("#bar_chart").css('min-height','38vh');
		
		//반응형 화면 사이즈에 맞춰 사이즈 조정
		//gauge.resize();
		bar.resize();
		
		var trainAddcnt = 0;
		
		if(alData.train.trainAddCnt == 0 || alData.train.trainAddCnt == null) {
			trainAddcnt = '해당없음';
		}
		
		
		var tbM='';
			tbM+='<tr><td>편성번호</td><td>'+alData.train.formationNo+'</td></tr>'
			/* tbM+='<tr><td>열차번호</td><td>'+alData.train.trainNo+'</td></tr>' */
			/* tbM+='<tr><td>운행방향</td><td>'+alData.train.activeCap+'</td></tr>' */
			tbM+='<tr><td>중련갯수</td><td>'+trainAddcnt+'</td></tr>'
			tbM+='<tr><td>'
					+'<sapn style="margin-right: 10px;">제</sapn>'
					+'<sapn style="margin-right: 10px;">조</sapn>'
					+'<sapn>사</sapn>'
				+'</td><td>'+alData.train.carName+'</td></tr>'
			/* tbM+='<tr><td>최신 수신시각</td><td>'+alData.train.rcvDt+'</td></tr>' */
			$("#train_info").append(tbM);
		cssChart();
		
		/* 시간 형식으로 포맷하기 */
		var firstVal = (alData.first.xVal < 10) ? '0' + alData.first.xVal + ':00' : alData.first.xVal + ':00';
		var secondVal = (alData.second.xVal < 10) ? '0' + alData.second.xVal + ':00' : alData.second.xVal + ':00';
		var thirdVal = (alData.third.xVal < 10) ? '0' + alData.third.xVal + ':00' : alData.third.xVal + ':00';
		
		var max='';
		max += '<p style="font-size:24px; margin:0; color: white;">1. 시간( ' + firstVal + ' ) 혼잡률 ( ' + alData.first.yVal2 +' % )</p>';
		max += '<p style="font-size:24px; margin:0; color: white;">2. 시간( ' + secondVal + ' ) 혼잡률 ( ' + alData.second.yVal2 +' % )</p>';
		max += '<p style="font-size:24px; margin:0; color: white;">3. 시간( ' + thirdVal + ' ) 혼잡률 ( ' + alData.third.yVal2 +' % )</p>'
		$("#gauge_chart").append(max);
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
		<!-- class="pie_container" -->
		<div style="width: 100%;display: flex;flex-direction: row;align-items: center;">
			<div id="gauge_chart" class="pie_container">
				<!-- 25-04-01 : 금일 최대 혼잡률(%) -->
				<h1 id="todayMax" class="chart-title">금일 최대 혼잡률(%)</h1>
			</div>
		</div>
	</div>
	<div>
		<div id="bar_container">
			<h1 class="chart-title"  id="chart_title2" style="padding-left: 30px;">금일 시간대별 평균/최대 혼잡률</h1>
			<div id="bar_chart" class=""></div>
		</div>
	</div>
	
</html>