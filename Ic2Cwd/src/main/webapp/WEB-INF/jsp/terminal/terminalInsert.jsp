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
 	 var carSelList=[];
		$(document).ready(function() {
			console.log("terminal insert");
			//차량유형 셀렉트박스 관련
			//carSelList =${carTypeList};
			var carSelList = [];

	        <c:forEach var="item" items="${carTypeList}">
				carSelList.push("${item}");
				itemList.push({
	                name: "${item.name}",
	                price: ${item.price}
	            });
	        </c:forEach>;
			
			var tagId=$('#carType').attr('id');
			emptyFullset(tagId,carSelList);
			
			let maxValue = Math.max($("#carType").map(function () {
		        return parseFloat($(this).val()) || 0; // 숫자로 변환, 값이 없으면 0
		    }).get());
			
			
			$("#insertForm").submit(function(){
				var insertChecker = confirm('이대로 등록하시겠습니까?');
				
				if(insertChecker) {
					//맥주소가 없을경우
					var macAdd=$("#lteRMacAdd").val();
					if(macAdd==''||typeof macAdd === "undefined"){
						$("#lteRMacAdd").val("unknown"+$("#deviceCnt").val());
					}
					$("#lteRCode").val($("#CPY_CODE").val());
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
		
		function emptyFullset(tagId,carList){
			$.each(carList, function(i, list) {
				if(list[i].carType==tagId){
					console.log(carList);
					console.log(i);
					console.log(list);
					$('#stWgt1').val(list[i].stWgt1);
					$('#stWgt2').val(list[i].stWgt1);
					$('#fcWgt1').val(list[i].stWgt1);
					$('#fcWgt2').val(list[i].stWgt1);
				}
			});
		}
		
		$('#carType').on('change',function(){
			if($('#carType').val()==maxValue){
				emptyFullset(0,carSelList);
			}else{
				emptyFullset($('#carType').val(),carSelList);
			}
		})
		
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
						<div class="ttl_ctn">차상 단말 등록</div>
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
								
									<!-- 최대값을 저장할 변수를 설정 -->
									<c:set var="maxValue" value="-999999" />
									<select id ="carType" class ="ctn_select_box1">
										<c:forEach var="carVo" items="${carTypeList}">
											<c:if test="${carVo.carType ne 0}">
												<option value="${carVo.carType}">${carVo.carName}</option>
											</c:if>
											<c:if test="${carVo.carType > maxValue}">
									            <c:set var="maxValue" value="${carVo.carType}" />
									        </c:if>
										</c:forEach>
										<option value="${maxValue+1}">신규추가</option>
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
								<button class="btn btn_primary" type="submit" role="button">등록</button>
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