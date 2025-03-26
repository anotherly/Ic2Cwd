<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html style="overflow-y:auto;">
<head>
<title>인천2호선 혼잡도 관제시스템</title>
<meta charset="UTF-8">
<jsp:include page="../cmn/top.jsp" flush="false" />
<script>
		$(document).ready(function() {
			console.log("terminal insert");
			//차량유형 셀렉트박스 관련
			//carSelList =${carTypeList};
			
			var tagId=$('#carType').val();
			emptyFullset(tagId);
			
			
			$("#btnSave").on('click',function(){
				var insertChecker = confirm('이대로 등록하시겠습니까?');
				console.log("제조사 : ");
				if(insertChecker) {
					let queryString = $("#insertForm").serialize();
					ajaxMethod('/terminal/terminalInsert.ajax',queryString,'/terminal/terminalList.do','저장되었습니다');
					
				} else {
					return false;
				}
			}); 
			
			$("#btnCancel").on('click',function(){
				location.href='/terminal/terminalList.do';
			});
			
		});
		
		function emptyFullset(tagId){
			
			 <c:forEach var="item" items="${carTypeList}">
				 if("${item.carType}"==tagId){
					 	$("#carName").val("${item.carName}");
						$('#stWgt1').val("${item.stWgt1}");
						$('#stWgt2').val("${item.stWgt2}");
						$('#fcWgt1').val("${item.fcWgt1}");
						$('#fcWgt2').val("${item.fcWgt2}");
				 }
	        </c:forEach>;
			
		}
		
		$(document).on("change", "#carType", function() {
			var maxValue = $('#maxValue').val();
			if($('#carType').val()==maxValue){
				emptyFullset(0);
				$("#carName").val("");
				$("#carName").prop("readonly", false);
			}else{
				emptyFullset($('#carType').val());
				$("#carName").prop("readonly", true);
			}
		})
		
	</script>
	

</head>
<body class="open" style="overflow-y:auto;">
    <!-- lnb Start ------------------>
    <aside id="lnb" class="lnb">
        <a class="lnb-control" title="메뉴 펼침/닫침"><span class="menu-toggle">메뉴 펼침/닫침</span></a>
        <nav class="navbar navbar-expand-sm navbar-default">
            <ul class="menu-inner"></ul>
        </nav>
    </aside>
    <!-- lnb End ------------------>

	<!-- container Start ------------------>
	<div id="container" class="container-wrap" style="margin-top: calc(5vh - 10px);">
		<!-- header Start ------------------>
		<div id="header" class="header-wrap">
		</div>
		<!-- header End ------------------>
		<!-- contents Start ------------------>
		<div id="contents" class="contents-wrap">
			<!-- work Start -->
			<div id="work" class="work-wrap" style="padding-top: 0px;">
				<!-- contents_box Start -->
				<div id="contents_box" class="contents_box" style="margin-top: 0px;padding-top: 5px;">
					<!-- 컨텐츠 테이블 헤더 Start -->
					<div class="ctn_tbl_header">
						<div class="ttl_ctn">차량정보 등록</div>
						<!-- 컨텐츠 타이틀 -->
						<div class="txt_info">
							<em class="txt_info_rep">*</em> 표시는 필수 입력 항목입니다.
						</div>
						<!-- 설명글 -->
					</div>
					<!-- 컨텐츠 테이블 헤더 End -->
					<!-- 컨텐츠 테이블 영역 Start -->
					<form id="insertForm" name="insertForm" method="post" enctype="multipart/form-data">
						<div class="ctn_tbl_area">
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">편성번호</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="formationNo" name ="formationNo"
									maxlength="3" 
									class="form-control">
								</div>
								
								<div class="ctn_tbl_th fm_rep">제조사</div>
								<div class="ctn_tbl_td">
									<!-- 최대값을 저장할 변수를 설정 -->
									<c:set var="maxValue" value="-999999" />
									<select id ="carType" name="carType" class ="ctn_select_box1">
										<c:forEach var="carVo" items="${carTypeList}">
											<c:if test="${carVo.carType ne 0}">
												<option value="${carVo.carType}">${carVo.carName}</option>
											</c:if>
											<c:if test="${carVo.carType > maxValue}">
									            <c:set var="maxValue" value="${carVo.carType}" />
									        </c:if>
										</c:forEach>
										<option id='maxValue' value="${maxValue+1}">신규추가</option>
									</select>
									<input type="text" 
									id="carName" name ="carName"
									maxlength="15" 
									class="form-control" 
									value=""
									readonly>
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
								<div class="ctn_tbl_th fm_rep">최대승객하중(단위 :톤)</div>
								<div class="ctn_tbl_td">
								
								<input type="hidden" id="boardStCnt" name ="boardStCnt"  value="103" class="form-control">
									<input type="number" 
									id="maxPsCnt" name ="maxPsCnt"
									maxlength="3"
									value="13"  
									class="form-control">
								</div>
								<div class="ctn_tbl_th fm_rep">기준몸무게</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="standardKg" name ="standardKg"
									maxlength="3"
									value="66"  
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">보정율(~70)Kpa</div>
								<div class="ctn_tbl_td" style="flex: 0.4;">
									<input type="number"
									id="kpa_00_70" name ="kpa_00_70"
									maxlength="3"
									value="1.0"
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">보정율(71~75)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_71_75" name ="kpa_71_75"
									maxlength="3"
									value="1.0" 
									class="form-control">
								</div>
								<div class="ctn_tbl_th">보정율(76~80)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_76_80" name ="kpa_76_80"
									maxlength="3"
									value="1.0"
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">보정율(81~85)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_81_85" name ="kpa_81_85"
									maxlength="3" 
									value="0.7"
									class="form-control">
								</div>
								<div class="ctn_tbl_th">보정율(86~90)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_86_90" name ="kpa_86_90"
									maxlength="3"
									value="0.7"
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">보정율(91~95)Kpa</div>
									<div class="ctn_tbl_td">
										<input type="number" 
										id="kpa_91_95" name ="kpa_91_95"
										maxlength="3"
										value="0.6" 
										class="form-control">
									</div>
								<div class="ctn_tbl_th">보정율(96~100)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_96_100" name ="kpa_96_100"
									maxlength="3"
									value="0.6"
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">보정율(101~105)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_101_105" name ="kpa_101_105"
									maxlength="3" 
									value="0.5"
									class="form-control">
								</div>
								<div class="ctn_tbl_th">보정율(106~110)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_106_110" name ="kpa_106_110"
									maxlength="3"
									value="0.5"
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">보정율(111~115)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_111_115" name ="kpa_111_115"
									maxlength="3"
									value="1.0" 
									class="form-control">
								</div>
								<div class="ctn_tbl_th">보정율(116~120)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_116_120" name ="kpa_116_120"
									maxlength="3"
									value="1.0"
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">보정율(121~125)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_121_125" name ="kpa_121_125"
									maxlength="3"
									value="1.0" 
									class="form-control">
								</div>
								<div class="ctn_tbl_th">보정율(126~130)Kpa</div>
								<div class="ctn_tbl_td">
									<input type="number" 
									id="kpa_126_130" name ="kpa_126_130"
									maxlength="3"
									value="1.0"
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">보정율(131~)Kpa</div>
								<div class="ctn_tbl_td" style="flex: 0.4;">
									<input type="number"
									id="kpa_131_if" name ="kpa_131_if"
									maxlength="3"
									value="1.0" 
									class="form-control">
								</div>
							</div>
						</div>
						
						<!-- btn_box Start -->
						<div class="btn_box">
							<div class="right">
								<input type="button" class="btn btn_primary" id="btnSave" alt="저장" value="저장" />
								<input type="button" class="btn" id="btnCancel" alt="취소" value="취소" />
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