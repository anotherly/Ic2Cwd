<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>인천2호선 혼잡도 관제시스템</title>
<meta charset="UTF-8">
<jsp:include page="../cmn/top.jsp" flush="false" />
 	 
	<script>
	
		$(document).ready(function() {
			//console.log("고객 업데이트 화면");
			
			//최대일과 현재일이 같을 경우 발생할수 있는 문제에 대해 최대일에 +1초 
			//먼저 변수 선언
			var maxD = moment().add(1, 'seconds').format("YYYY-MM-DD");
			var defaultD  = moment().format("YYYY-MM-DD");
			$('#datetimepicker1,#datetimepicker2,#datetimepicker3,#datetimepicker4,#datetimepicker5').datetimepicker({
				 format:"YYYY-MM-DD",
				 //maxDate : maxD, 
				 defaultDate: defaultD
			 });
			
			$('.glyphicon-calendar').hover(function(){
				//console.log('달력 체크2');
			});
			
			//팀 변경시
			$('#CPY_CODE').on("change",function(){
				var tagId = $(this).val();
				var ajaxData=ajaxMethod('/terminal/routerTeamCnt.ajax',{"departCode":tagId}).data;
				$("#teamCnt").val(ajaxData[0].teamCnt);
				$("#deviceCnt").val(ajaxData[0].deviceCnt);
			});
			
			$("#btnSave").on('click',function(){
				//console.log("정보 저장");
			
				var validChk = true;
				$(".input_base_require").each(function(i,list){
					//console.log("필수값체크");
					if($(this).val()==null||$(this).val()==''){
						alert("필수 항목을 기재해 주세요");
						$(this).focus();
						validChk=false;
						return false;
					}
				});
				
				$(".input_base_number").each(function(i,list){
					//console.log("숫자체크");
					if(!(($(this).val()==null||$(this).val()=='')) &&  isNaN($(this).val())){
						alert("MSP 요금제는 숫자값만 입력 가능합니다");
						$(this).focus();
						validChk=false;
						return false;
					}
				});
				
				if(validChk){
					var updateChecker = confirm('이대로 저장하시겠습니까?');
					
					if(updateChecker) {
						$("#lteRCode").val($("#CPY_CODE").val());
						let queryString = $("#acDetailFrm").serialize();
						ajaxMethod('/terminal/update.ajax',queryString,'/terminal/list.do','저장되었습니다');
					} else {
						return false;
					}
					
				}
			}); 
			
			//y면 체크 아니면 비체크인데 비체크값을 n으로 변경
			$('input[type="checkbox"]').each(function(i,list){
				//console.log("하단체크박스 : "+i+"	/	"+$(this).attr("id"));
				if($(this).is(':checked')){
					$(this).val('Y');
				}else{
					$(this).val('N');
				}
			});
			
			//input 하위 모든 체크박스 클릭 시
			$('input[type="checkbox"]').on('click',function(){
				//console.log("하단체크박스클릭");
				if($(this).is(':checked')){
					$(this).val('Y');
				}else{
					$(this).val('N');
				}
			});
			
			$("#btnCancel").on('click',function(){
				/* location.href='/terminal/list.do'; */
				history.go(-1);
			});
			
			$("#btnReload").on('click',function(){
				var rData=ajaxMethod('/terminal/deviceReload.ajax',{"lteRIp":$("#lteRIp").val()}).rData;
				//console.log(rData);
				//$("#lteRUsed").val(rData.lteRUsed);
				//$("#insLocTxt").val(rData.insLocTxt);
				$("#lteRImei").val(rData.lteRImei);
				$("#lteRImsi").val(rData.lteRImsi);
				$("#lteRserial").val(rData.lteRserial);
				$("#lteRMacAdd").val(rData.lteRMacAdd);
				//$("#lteRIp").val(rData.lteRIp);
				//$("#conGw").val(rData.conGw);
				$("#conSystem01Ip").val(rData.conSystem01Ip);
				//$("#conSystem01").val(rData.conSystem01);
				$("#conSystem02Ip").val(rData.conSystem02Ip);
				//$("#conSystem02").val(rData.conSystem02);
				$("#lteRSimNo").val(rData.lteRSimNo);
				//$("#lteRUseRId").val(rData.lteRUseRId);
				$("#lteRDeviceType").val(rData.lteRDeviceType);
				$("#swVerCode").val(rData.swVerCode);
				$("#lterCellId").val(rData.lterCellId);
				$("#lteRBand").val(rData.lteRBand);
				$("#lteRChannel").val(rData.lteRChannel);
				$("#lteRMCC").val(rData.lteRMCC);
				$("#lteRMNC").val(rData.lteRMNC);
				$("#getDeviceInfoYdt").val(rData.getDeviceInfoYdt);
			});
			
		});
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
		<div id="container" class="container-wrap"  style="margin-top: 0px;">
			<!-- header Start ------------------>
			<div id="header" class="header-wrap">
			</div>
			<!-- header End ------------------>
			<!-- contents Start ------------------>
			<div id="contents" class="contents-wrap">
				<!-- work Start -->
				<div id="work" class="work-wrap" style="padding-top: 0px;">
					<!-- contents_box Start -->
					<div id="contents_box" class="contents_box">
						<!-- 컨텐츠 테이블 헤더 Start -->
						<div class="ctn_tbl_header">
							<div class="ttl_ctn">단말기 정보 수정</div>
							<!-- 컨텐츠 타이틀 -->
							<div class="txt_info">
								<em class="txt_info_rep">*</em> 표시는 필수 입력 항목입니다.
							</div>
							<!-- 설명글 -->
						</div>
						<!-- 컨텐츠 테이블 헤더 End -->
						<!-- 컨텐츠 테이블 영역 Start -->
						<form id="acDetailFrm" name="acDetailFrm" method="post" enctype="multipart/form-data">
							<div class="ctn_tbl_area">
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">편성번호</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="formationNo" name ="formationNo"
									maxlength="3" 
									class="form-control">
								</div>
								<div class="ctn_tbl_th">담당자</div>
								<div class="ctn_tbl_td">
									<input type="text" 
									id="deviceUser" name ="deviceUser"
									maxlength="10" 
									class="form-control">	
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">장비명(사용 용도)</div>
								<div class="ctn_tbl_td">
									<input type="text" 
									id="deviceUsed" name ="deviceUsed"
									maxlength="15" 
									class="form-control">	
								</div>
								<div class="ctn_tbl_th">설치 위치</div>
								<div class="ctn_tbl_td">
									<input type="text" 
									id="insLocTxt" name ="insLocTxt"
									maxlength="15" 
									class="form-control">	
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">제조사</div>
								<div class="ctn_tbl_td">
								
									<select id ="carType" name ="carType">
										<c:forEach var="carVo" items="${carTypeList}">
											<option value="${carVo.carType}" 
												<c:if test="${carVo.carType eq data.carType}">selected</c:if>>
												${carVo.carName}
											</option>
											<option value="-1">신규추가</option>
										</c:forEach>
									</select>
									<input type="text" 
									id="carName" name ="carName"
									maxlength="15" 
									class="form-control" readonly>
								</div>
								
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">1량 공차값</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="stWgt1" name ="stWgt1"
									maxlength="3" 
									class="form-control">
								</div>
								<div class="ctn_tbl_th fm_rep">1량 만차값</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="fcWgt1" name ="fcWgt1"
									maxlength="3" 
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">2량 공차값</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="stWgt2" name ="stWgt2"
									maxlength="3" 
									class="form-control">
								</div>
								<div class="ctn_tbl_th fm_rep">2량 만차값</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="fcWgt2" name ="fcWgt2"
									maxlength="3" 
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
	                            <div class="ctn_tbl_th fm_rep">비고(기타사항)</div>
	                            <div class="ctn_tbl_td">
	                                <textarea id="CONTENT" name="etc" class="long-cont" style="height:400px;resize:none;width: 100%;border: 1px solid lightgray;" required></textarea>
	                            </div>
	                        </div>
						</div>
							
							<!-- btn_box Start -->
							<div class="btn_box">
								<div class="right">
									<input type="button" class="btn" id="btnReload" alt="갱신" value="단말기정보 불러오기" style="background:darkgrey;color:#fff;"/>
									<input type="button" class="btn btn_primary" id="btnSave" alt="저장" value="저장" />
									<input type="button" class="btn" id="btnCancel" alt="취소" value="취소" />
								</div>
							</div>
							<!-- btn_box End -->
						</form>
					</div>
					<!-- contents_box End -->
					<!-- footer End ------------------>
				</div>
				<!-- work End -->
			</div>
		<!-- contents End ------------------>
	</div>
	<!-- container End ------------------>
</body>
</html>