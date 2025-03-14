<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>단말장치(LTE-R) 관리 WEB 시스템</title>
<meta charset="UTF-8">
<style>
		@media(max-width : 1910px) {
			
			body, html {
				width : 1920px;
				height : 1080px;
				overflow : auto;
			}
			
			aside.lnb {
				height : 1080px;
				position : absolute;
			}
		
			.menu-inner {
				height: 264px;
			}
			
			.menu-inner > .menu-item  {
				width : 60px;
			}
			
			.lnb .lnb-control {
				width : 1920px;
			}
			
			.open .container-wrap {
				width : 1860px;
			}
			
			.container-wrap .header-wrap {
				position : absolute;
				width : 1920px;
				z-index:999;
			}
			
			.containerAll {
				width: 1820px;
    			height: 960px;
    			min-width : 0px;
    			min-height : 0px;
			}
			
		}
	</style>
<script>

	var bar1;
	var bar2;
	var bar3;

	$(document).ready(function(){
		console.log("통계 차트 생성 부분 진입");
		
		//db에서 조회한 데이터를 상황에 맞게 배열에 삽입
		var xList=[];
		var yList=[];
		var yList2=[];
		
		var x2List=["상하행","상행","하행"]; // 비교 2,3 공통 x
		var y2List=[];//비교 2 y1
		var y3List=[];//비교 3 y1
		
		var alData=ajaxMethod("/stat/statChart.ajax",{"keyDate":$("#sDate").val(),"keyType":typeId});
		//데이터 집계부분
		var barData1 = alData.barData1;
		var barData2 = alData.barData2;
		
		
		
		for (var i = 0; i < barData1.length; i++) {
			if(i==0){
				xList.push("역명");
				yList.push("상선");
				yList2.push("하선");
			}else{
				xList.push(barData1[i].xVal);
				yList.push(barData1[i].yVal);
				yList2.push(barData1[i].yVal2);
			}
		}
		
		if(typeId=="monStat"){
			y2List.push("전월");
			y3List.push("전전월");
		}else if(typeId=="yearStat"){
			y2List.push("전년");
			y3List.push("전전년");
		}else{
			y2List.push("전일");
			y3List.push("전전일");
		}
		
		for (var i = 0; i < barData2.length; i++) {
			y2List.push(barData2[i].yVal2);
			y3List.push(barData2[i].yVal3);
		}
		
		//bar1= barChart('bar_chart1',xList,yList,yList2);
			
		bar1=c3.generate({
			bindto: '#bar_chart1',
		    data: {
		    	x:'역명',
		    	columns: [
		    		xList,yList,yList2
		        ],
		        type: 'bar',
	        	
        	 labels: {
	               format:{
	            	   "상선":function (v, id, i, j) {
	            		   if(v!=null && v>=10){
		   						var format= d3.format(',');
		   		                return format(v);
		   					}else{
		   						return "";
		   					}
		            	   return d3.format(v); 
		               },  
			        	"하선":function (v, id, i, j) {
			        		if(v!=null && v>=10){
		   						var format= d3.format(',');
		   		                return format(v);
		   					}else{
		   						return "";
		   					} 
			        	}  
	               } 
	           }
	        	
		    },
		    axis: {
			    x: {
			    	type: 'category',
			        categories: xList,
			        label: {
	                    text: '역명',
	                    position: 'bottom',
	                },
				    tick: {
		                rotate: 75,
		                multiline: false
		            },
		            height: 130
			    },
	            y: {
                    max: 220,
                    min:0,
                    padding: {top: 20, bottom: 0},
                    tick: {
                        // format: d3.format('d')
                        format: function(d) {
                          if (Math.floor(d) != d){
                            return;
                          }
                          return d;
                        }
                    }
	            }
		   },
		    bar: {
		        width: {//막대 두께
		            ratio: 0.5 // this makes bar width 50% of length between ticks
		        }
		    }
		    ,legend: {
		        padding: 15,
		        item: {
		          tile: {
		            width: 20,
		            height: 20
		          }
		        }
		    	,show: false
		      }
		}); 
		
		bar2= barChart('bar_chart2',x2List,y2List);
		bar3= barChart('bar_chart3',x2List,y3List);
		//최초 사이즈 지정
 		$("#bar_chart1").css('min-width','40vw');
		$("#bar_chart1").css('min-height','35vh');
		
		$("#bar_chart2").css('min-width','25vw');
		$("#bar_chart2").css('min-height','35vh');
		
		$("#bar_chart3").css('min-width','25vw');
		$("#bar_chart3").css('min-height','35vh'); 
		
		//거지같은 범례 내리기 트랜스레이트
		/* $("g.c3-legend-item.c3-legend-item-UP").css("transform","translateY(10px)");
		$("g.c3-legend-item.c3-legend-item-DOWN").css("transform","translateY(10px)");
		 */
		//반응형 화면 사이즈에 맞춰 사이즈 조정
		bar1.resize();
		bar2.resize();
		bar3.resize(); 

		//화면 테마에 맞춰 그래프 선 및 폰트 색상 변경
		cssChart();
	});
</script>
</head>
 <div class="stat_chart_container"  style="width: 100%;display: flex;flex-direction: row; align-items: center;justify-content: space-around;">
 	<div id="pie_container" class="pie_container" style="width: 100%;display: flex;flex-direction: row;align-items: center;justify-content: space-around;">
		<div  class="pie-contents">
			<h2 class="chart-title" id="chart_title0">구간 별 최대 혼잡도</h2>
			<div id="bar_chart1" class=""></div>
		</div>
	</div>
	<div class="pie-contents">
		<h2 class="chart-title" id="chart_title1">전일 대비</h2>
		<div id="bar_chart2" class=""></div>
	</div>
	<div id="bar_container">
		<h2 class="chart-title"  id="chart_title2" style="text-align: center;">전월 대비</h2>
		<div id="bar_chart3" class=""></div>
	</div>
 </div>

<!-- 	<div style="width: 100%;display: flex;flex-direction: row;align-items: center;justify-content: space-around;">
	</div>
 -->	
</html>