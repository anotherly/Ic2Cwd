var startNum=0;
var endNum=1;
var tbM;//tr 내부에 들어가는 td들
var tbCnt=0;//전체 들어가는 데이터 수 (42개 이하인데 변경예정)
var trCnt=1;//행 카운트
var chkTerId;//최초 선택여부
/////////////////////////////

var mok=0;//전체 데이터를 td 수로(colCnt) 나눴을때 몫 (이건 최대 페이지 개수를 구할때 쓰임)
var namage=0;//나머지(마지막 행에서 빈칸 채울때 씀)
var maxPage=0;// 최대페이지 수 : 몫/6(한화면 행수)
var plusPage=0;// 몫/6의 나머지 -> 남은 행수를 계산해서 채울때 씀
var opVal1="";//자식창으로 보낼 값
var opVal2="";//자식창으로 보낼 값
/////////////////////////////

//alData : 데이터
//colCnt : 열 수
//rowCnt : 행 수

//차트화면 우측 단말기테이블
function trainOne(alData,colCnt,rowCnt){
	//console.log("차트화면 우측 단말기테이블");
	$("#lteTbd").empty();
	//전역변수 초기화
	tbM="";
	trCnt=1;
	tbCnt=0;
	
	startNum=0;
	endNum=1;
	
	mok=Math.floor(alData.length/colCnt,0);
	namage=alData.length%colCnt;
	maxPage=Math.floor(mok/6,0);
	
	var oldName="";//팀이 변경되기 전 이전 팀
	var enterLine=1;//현재 td number
	var blankCnt=0;//페이지 안깨지기 위한 공백 카운트
	//var backName="";//여유보통주의혼잡상태
	
	for (var i = 0; i < alData.length; i++) {
		if(i==0){
			//oldName=alData[i].teamName;
			chkTerId=alData[i].formationNo;
		}else{
			chkTerId=0;
		}
		//td 내부 만듦
		tbM+= tdCreate(alData[i]); 
		tbCnt++;
		//colCnt열 초과시 줄바꿈
		if(parseInt((enterLine)%colCnt)==0){
			var tbCont = "<tr id='tr"+trCnt+"'>"+tbM+"</tr>";
			$("#lteTbd").append(tbCont);	
			tbM="";
			trCnt++;
		}else{
			//데이터의 마지막
			if(i==alData.length-1){
				//행을 생성
				trCreate();
			}
		}	
		enterLine++;
		
	}
	//테이블 생성 완료후 해야할 것들
	plusPage=6-($("#lteTbd tr").length%6);
	
	
}

//tr을 생성하고 행번호를 생성
function trCreate(){
	var trValue = "<tr id='tr"+trCnt+"'>"+tbM+"</tr>";
	$("#lteTbd").append(trValue);	
	tbM="";
	trCnt++;
}

//중간에 들어가는 td값을 채움
//tdid,tdCont,spanVal
function tdCreate(data){
	console.log("tdCreate");
	var tdid= data.formationNo;
	var updown = "";
	if(data.activeCap!=null && typeof data.activeCap !=="undefined"){
		updown=data.activeCap
	}
	var funcTbCont="";
	
	var vRate1=data.rate1;
	var vRate2=data.rate2;
	
	if(data.stationName=="현재 미운행"){
		vRate1=0;
		vRate2=0;
	}
	
		if(data.trainAddCnt==1){//중련됬을 경우
			funcTbCont=
			"<td id='"+tdid+"' class='added' style='border-right: none;border-left: none;'>"
				+"<div class='td-div'>"
					+"<div class='td-top' style='height: 53%;'>"
						+"<span>"+data.formationNo+"</span>"
						+"<span style='font-size: calc(1vw - 8px);'>"+data.stationName+" "+updown+"</span>"
					+"</div>"
					+"<div class='td-cont'>"
						+"<div class='img-container'>"				
							+"<div id='1ryang' style=" 
								+"'background:url(../images/train/cong_"+data.cwd1+"_1.png) no-repeat;" 
								+"background-size: contain;'>"
							+"</div>"
							+"<span>"+vRate1+"%</span>"
						+"</div>"
						+"<div class='img-container'>"
							+"<div id='2ryang' style=" 
								+"'background:url(../images/train/cong_"+data.cwd2+"_2.png) no-repeat;" 
								+"background-size: contain;'>" 
							+"</div>"
							+"<span>"+vRate2+"%</span>"
						+"</div>"
					+"</div>"
				+"</div>"
			+"</td>";
		}else{
			funcTbCont=
			"<td id='"+tdid+"'>"
				+"<div class='td-div'>"
					+"<div class='td-top' style='height: 53%;'>"
						+"<span>"+data.formationNo+"</span>"
						+"<span style='font-size: calc(1vw - 8px);'>"+data.stationName+" "+updown+"</span>"
					+"</div>"
					+"<div class='td-cont'>"
						+"<div class='img-container'>"				
							+"<div id='1ryang' style=" 
								+"'background:url(../images/train/cong_"+data.cwd1+"_1.png) no-repeat;" 
								+"background-size: contain;'>"
							+"</div>"
							+"<span>"+vRate1+"%</span>"
						+"</div>"
						+"<div class='img-container'>"
							+"<div id='2ryang' style=" 
								+"'background:url(../images/train/cong_"+data.cwd2+"_2.png) no-repeat;" 
								+"background-size: contain;'>" 
							+"</div>"
							+"<span>"+vRate2+"%</span>"
						+"</div>"
					+"</div>"
				+"</div>"
			+"</td>";
		}
		//혼잡 심각의 경우
		
		if(
			data.rate1>=190 || data.rate2>190	
		){
			newSoundAlert(data.formationNo,"심각");
		}
	return funcTbCont;
}

//배경 선택
/*function selectBackgra(avgCwd){
	var backgra='';
	
	//상태표기
	if(avgCwd=='1'){
		backgra=backgra+"1";
	}else if(avgCwd=='2'){
		backgra=backgra+"_2";
	}else if (avgCwd=='3') {
		backgra=backgra+"_3";
	}else if (avgCwd=='4') {
		backgra=backgra+"_4";
	}else{
		backgra=backgra+"_0";
	}
	return backgra;
}*/

//페이징 처리를 위한 행 감춤
function hideTr(startPage,endPage){
	$("#lteTbd tr").each(function(i,list){
		
		if(i >= startPage && i < endPage){
			$(list).show();
		}else{
			$(list).hide();
		}
	});
}

//혼잡,심각의 경우 경고사운드창 띄움
function newSoundAlert(forNum,status){
	opVal1=forNum;
	opVal2=status;
	console.log("경고창 팝업");
    var url = "/chart/cwd.do";
    var windowName = "경고 창";
    
    var popupW = 800;  // 팝업 넓이
    var popupH = 400;  // 팝업 높이
    var left = Math.ceil((window.screen.width - popupW)/2);
    var top = Math.ceil((window.screen.height - popupH)/2);
    window.open(url, windowName, ',width=' + popupW +  ', height=' + popupH +  ', left=' + left +  ', top=' + top + ', toolbar=no, location=no, status=no, menubar=no, scrollbars=no, resizable=no');
    
}

