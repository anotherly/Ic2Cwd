<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>인천2호선 혼잡도 관제시스템</title>
<meta charset="UTF-8">
<jsp:include page="../../cmn/top.jsp" flush="false" />
	<script>
		$(document).ready(function() {
			console.log("혼잡도 기준값 변경페이지");
			$("#btnSave").on('click',function(){
				var validChk = true;
				
				$(".input_base_require").each(function(i,list){
					if($(this).val()==null||$(this).val()==''){
						alert("필수 항목을 기재해 주세요");
						$(this).focus();
						validChk=false;
						return false;
					}
				});
				
				if(validChk){
					var dept = $("#cpyCode option:selected").val()+"-"+$("#hqCode option:selected").val()+"-"+$("#teamCode option:selected").val();
					var updateChecker = confirm('이대로 저장하시겠습니까?');
					if(updateChecker) {
						//혼잡도변수 계산 
						let calcNum = ( ( 1000 * $("#maxPsCnt").val() ) / ( $("#boradStCnt").val() * $("#standardKg").val() ) ).toFixed(2);
						$("#calcNum").val(calcNum);
						let queryString = $("#updateForm").serialize();
						ajaxMethod('/setting/cwd/cwdUpdate.ajax',queryString,'','저장되었습니다');
						 var synData = 
						{
						   "req_type" : "1",
						   "calc_num" : $("#calcNum").val(),
						   "multip_num" : $("#multipNum").val(),
						   "calc_location" : $("#calcLocation").val(),
						};
						 
						 $.ajax({
						   type : 'POST',		// 요청 메서드
						   url : 'http://grkeeper.kr:15000/ic2api',	// 요청 URI	
						   header : {"content-type" : "application/json"},	// header - 요청 헤더(JSON형식으로 보냄)
						   data : JSON.stringify(synData),	// data - 서버로 전송할 데이터. stringify()로 직렬화 필요.
						   // success, error -> 콜백함수
						   success : function(result){ 
							 var ackParse = JSON.parse(result);	// result는 서버가 전송한 데이터
						   },
						   error : function(){
							   
							}
						 });	
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
			<div id="work" class="work-wrap">
				<!-- contents_box Start -->
				<div id="contents_box" class="contents_box">
					<!-- 컨텐츠 테이블 헤더 Start -->
					<div class="ctn_tbl_header">
						<div class="ttl_ctn">혼잡도 계산 정보</div>
						<!-- 설명글 -->
					</div>
					<!-- 컨텐츠 테이블 헤더 End -->
					<!-- 컨텐츠 테이블 영역 Start -->
					<form id="updateForm" name="updateForm" method="post" enctype="multipart/form-data">
					
					<input type="hidden" 
									id ="calcNum" 
									name ="calcNum" 
									maxlength="6" 
									value="${data.calcNum}"
									class="form-control">
					
						<!-- 컨텐츠 테이블 영역 Start -->
						<div class="ctn_tbl_area">
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">탑승기준 인원수(A)<br>(단위:명)</div>
								<div class="ctn_tbl_td">
								<input type="number" 
									id ="boradStCnt" 
									name ="boradStCnt" 
									maxlength="6" 
									value="${data.boradStCnt}"
									class="form-control input_base_require">
								</div>
								<div class="ctn_tbl_th fm_rep">최대 승객 하중(B)<br>(단위 : 톤)</div>
								<div class="ctn_tbl_td">
								<input type="number" 
									id ="maxPsCnt" 
									name ="maxPsCnt" 
									maxlength="2" 
									value="${data.maxPsCnt}"
									class="form-control input_base_require">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">기준 몸무게(C)<br>(단위 : kg/인)</div>
								<div class="ctn_tbl_td">
									<input type="number" 
										id ="standardKg" 
										name ="standardKg" 
										maxlength="3" 
										value="${data.standardKg}"
										class="form-control input_base_require">
								</div>
								<div class="ctn_tbl_th fm_rep">KPA 가중치 값(D)</div>
								<div class="ctn_tbl_td">
									<input type="number" 
										id ="multipNum" 
										name ="multipNum" 
										maxlength="2" 
										value="${data.multipNum}"
										class="form-control input_base_require">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">혼잡도 계산 위치</div>
								<div class="ctn_tbl_td">
									<select id ="calcLocation" name ="calcLocation">
										<option value='1' <c:if test="${data.calcLocation eq 1}">selected</c:if> >차상</option>
										<option value='2' <c:if test="${data.calcLocation eq 2}">selected</c:if> >서버</option>
									</select>
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">산출식</div>
								<div class="ctn_tbl_td">
									<div class='cwd_cal1'>
										<div class='cwd_cal2'>
											<span>1000 X B</span>
											<span>─────────</span>
											<span>A X C</span>
										</div>
										<div class='cwd_cal2'>
											<span></span>
											<span>X</span>
											<span></span>
										</div>
										<div class='cwd_cal2'>
											<span>(D X 측정 kpa – 공차 kpa)</span>
											<span>──────────────────</span>
											<span>(만차 kpa – 공차 kpa)</span>
										</div>
										<div class='cwd_cal2'>
											<span></span>
											<span>X 100 %</span>
											<span></span>
										</div>
										<div class='cwd_cal2'>
											<span></span>
											<span>=> 결과 혼잡도 %</span>
											<span></span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- btn_box Start -->
						<div class="btn_box">
							<div class="right">
								<input type="button" class="btn btn_primary" id="btnSave" alt="저장" value="저장"/>
							</div>
						</div>
						<!-- btn_box End -->
					</form>
				</div>
				<!-- contents_box End -->
			</div>
			<!-- work End -->
		</div>
		<!-- contents End ------------------>
	</div>
	<!-- container End ------------------>
</body>

</html>