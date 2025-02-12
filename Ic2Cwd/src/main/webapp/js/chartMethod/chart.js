//갱신함수의 관련 타이머 셋팅
var mainChartTimer=null;
var subChartTimer=null;
var tableTimer=null;

function chartTimerReset(){
	clearInterval(mainChartTimer);
	mainChartTimer=null;
	clearInterval(subChartTimer);
	subChartTimer=null;
}

function allTimerReset(){
	clearInterval(mainChartTimer);
	mainChartTimer=null;
	clearInterval(subChartTimer);
	subChartTimer=null;
	clearInterval(tableTimer);
	tableTimer=null;
}

//타이머 리셋 함수
function timerReset(timer){
	clearInterval(timer);
	timer=null;
}
/************************************************************************
함수명 : exportChartToPng
설 명 : 차트 svg를 이미지로 변환
인 자 : chartid
사용법 : 
작성일 : 2020-12-21
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2020.12.21   정다빈       최초작성
************************************************************************/

function exportChartToPng(chartID){
	//fix weird back fill
	//console.log("exportChartToPng 차트아이디: "+chartID);
	d3.select('#'+chartID).selectAll("path").attr("fill", "none");
	//fix no axes
	d3.select('#'+chartID).selectAll("path.domain").attr("stroke", "black");
	//fix no tick
	d3.select('#'+chartID).selectAll(".tick line").attr("stroke", "black");
	fcnt = $('#'+chartID).find('svg').length;
	var svgElement = $('#'+chartID).find('svg')[downCnt];
	saveSvgAsPng(svgElement, chartID+'.png');
}

function cssChart(){
//	console.log("화면에 맞게 차트요소 색상변경");
	$("g text").css("fill","#fff");
	
	$(".c3-axis c3-axis-x text").css("fill","#fff");
	$("tspan").css("fill","#fff");
	
	$(".c3-axis-x path").css("stroke","#fff");
	$(".c3-axis-x line").css("stroke","#fff");
	
	$(".c3-axis-y path").css("stroke","#fff");
	$(".c3-axis-y line").css("stroke","#fff");
	$(".c3-axis-y2 path").css("stroke","#fff");
	$(".c3-axis-y2 line").css("stroke","#fff");
	
//	$(".c3-target-여유").children().css("fill","black");
//	$(".c3-target-보통").children().css("fill","black");
//	$(".c3-target-주의").children().css("fill","black");
//	$(".c3-target-혼잡").children().css("fill","black");
	
	$(".c3-chart-arc").css("font-weight","bold");
}

function cssNonChart(){
//	console.log("화면에 맞게 차트요소 색상변경");
	$("text").css("fill","");
	
	$(".c3-axis c3-axis-x text").css("fill","");
	$(".c3-axis c3-axis-y text").css("fill","");
	$("tspan").css("fill","");
	
	$(".c3-axis-x path").css("stroke","");
	$(".c3-axis-x line").css("stroke","");
	
	$(".c3-axis-y path").css("stroke","");
	$(".c3-axis-y line").css("stroke","");
	
	//$(".c3-chart-arc").css("font-weight","bold");
}


/************************************************************************
함수명 : barChart
설 명 : 스택 막대 그래프 생성
인 자 : divId(그려지는 div의 id값),xVal(가로축값),yVal(세로축값),w(가로길이),h(세로길이)
사용법 : 
작성일 : 2021-05-10
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.10   정다빈       최초작성
 ************************************************************************/

function barChart(divId,alData,legnd,w,h){
	console.log("막대 차트 생성함수 진입");
	var legend='';
	if(typeof legnd !=="undefined"){
		legend=legnd
	}
	//db에서 조회한 데이터를 상황에 맞게 배열에 삽입
	var xList=[];
	var yList=[];
	
	if(typeof alData !== "undefined" && alData != null || alData.length !=0){
	
		for (var i = 0; i < alData.length; i++) {
			if(i==0){
				xList.push("시간");
				yList.push("혼잡도");
			}else{
				xList.push(alData[i].xVal);
				yList.push(alData[i].yVal);
			}
		}
		//xList=['a','b','c','d','e'];
		console.log(xList);
		var rstC =  c3.generate({
			bindto: '#'+divId,
			size: {
		        height: h,
		        width: w,
		    },
		    data: {
		    	x:'시간',
		    	columns: [
		    		xList,yList
		        ],
		        type: 'bar',

	        	colors: {
	        		혼잡도: function(d) {
	        	    	//console.log("d"+d.value);
		    			var color=""
	        	    	if(d.value==1){
	        	    		color='#50E94F';
	        	    	}else if(d.value==2){
	        	    		color='#F5E001';
	        	    	}else if(d.value==3){
	        	    		color='#F68F00';
	        	    	}else{
	        	    		color='#FE0000';
	        	    	}
	        	        return color;
	        	      }
	        	},
		    },

		    /*colors: {
		  	      value: function(d) {
		  	    	console.log("d"+d);
		  	        return '#'+(0xff0000+(d.value-25)*256*3).toString(16);
		  	      }
		  	},*/
		    
		    axis: {
			    x: {
			    	type: 'category',
			        categories: xList,
			        label: {
	                    text: '시간',
	                    /*position: 'bottom',*/
	                }
			    },
	            y: {
                    max: 4,
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
		        // or
		        //width: 100 // this makes bar width 100px
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
		return rstC;
	}else{
		return 0;
	}
	
}

/************************************************************************
함수명 : gaugeChart
설 명 : 스택 막대 그래프 생성
인 자 : divId(그려지는 div의 id값),per(표출되는 수치 값),w(가로길이),h(세로길이)
사용법 : 
작성일 : 2021-05-10
작성자 : 솔루션사업팀 정다빈
수정일        수정자       수정내용
----------- ------ -------------------
2021.05.10   정다빈       최초작성
 ************************************************************************/

function gaugeChart(divId,per,w,h){
	console.log("스택 막대 차트 생성함수 진입");
	//var jsData = sData.data;
	var rstC =  c3.generate({
			bindto: '#'+divId,
			size: {
		        height: h,
		        width: w,
		    },
		    data: {
		        columns: [
		            ['주의/혼잡 비율', per]
		        ],
		        type: 'gauge',
//		        onclick: function (d, i) { console.log("onclick", d, i); },
//		        onmouseover: function (d, i) { console.log("onmouseover", d, i); },
//		        onmouseout: function (d, i) { console.log("onmouseout", d, i); }
		    },
		    gauge: {
		    },
		    color: {
		        pattern: ['#50E94F', '#F5E001', '#F68F00', '#FE0000'], // the three color levels for the percentage values.
		        threshold: {
		            values: [25, 50, 75, 100]
		        }
		    },
		});
	return rstC;
}

function barLoad(chart){
	chart.load({
        columns: [
            ['data3', 130, -150, 200, 300, -200, 100]
        ]
    });
}
