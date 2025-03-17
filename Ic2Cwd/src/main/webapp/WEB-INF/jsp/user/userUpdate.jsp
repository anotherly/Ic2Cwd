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
					
					if(validChk){
						
						var dept = $("#cpyCode option:selected").val()+"-"+$("#hqCode option:selected").val()+"-"+$("#teamCode option:selected").val();
						//240112 수정 - 메인 관리자 권한일땐 공란으로
						/* if($("select[name='userAuth']").val()==0){
							$("#departCode").val("");
						}else{
							//console.log('else 실행');
							$("#departCode").val(dept);
						} */
						
						var updateChecker = confirm('이대로 저장하시겠습니까?');
						if(updateChecker) {
							let queryString = $("#updateForm").serialize();
							ajaxMethod('/user/userUpdate.ajax',queryString,'/user/userList.do','저장되었습니다');
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
				location.href='/user/userList.do';
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
						<div class="ttl_ctn">사용자 수정</div>
						<!-- 설명글 -->
					</div>
					<!-- 컨텐츠 테이블 헤더 End -->
					<!-- 컨텐츠 테이블 영역 Start -->
					<form id="updateForm" name="updateForm" method="post" enctype="multipart/form-data">
						<!-- 컨텐츠 테이블 영역 Start -->
						<div class="ctn_tbl_area">
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th fm_rep">사용자 ID</div>
								<div class="ctn_tbl_td">
								<input type="hidden" id="userId" name="userId" value="${data.userId}"/> 
									${data.userId}
								</div>
								<div class="ctn_tbl_th fm_rep">사용자 이름</div>
								<div class="ctn_tbl_td">
								<input type="text" 
									name ="userName" 
									maxlength="10" 
									value="${data.userName}"
									class="form-control input_base_require">
								</div>
							</div>
							
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th">비밀번호<br>(변경시에만 입력)</div>
								<div class="ctn_tbl_td">
								<input type="text" 
									id="userPw" name ="userPw" 
									maxlength="15" 
									class="form-control">
								</div>
								<div class="ctn_tbl_th">비밀번호 확인</div>
								<div class="ctn_tbl_td">
									<input type="text" 
									id="userPw1" name ="userPw1" 
									maxlength="15" 
									class="form-control">
								</div>
							</div>
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th ">직급</div>
								<div class="ctn_tbl_td">
									<input type="text" 
									name ="userRank"
									value="${data.userRank}" 
									maxlength="5" 
									class="form-control">
								</div>
								<div class="ctn_tbl_th ">부서</div>
								<div class="ctn_tbl_td">
									<input type="text" 
									name ="userDept"
									value="${data.userDept}"  
									maxlength="10" 
									class="form-control">
								</div>
							</div>
							
							<div class="ctn_tbl_row">
								<div class="ctn_tbl_th ">휴대 전화번호 ( - 생략)</div>
								<div class="ctn_tbl_td">
									<input type="text" 
									id="userPhone" name ="userPhone"
									value="${data.userPhone}" 
									maxlength="13" 
									onkeydown='onlyNumber(event)' onkeyup='onlyNumber(event)' 
									class="form-control">
								</div>
								<div class="ctn_tbl_th ">이메일</div>
								<div class="ctn_tbl_td">
									<input type="text" 
									id="userEmail" name ="userEmail"
									value="${data.userEmail}" 
									maxlength="20" 
									class="form-control">
								</div>
							</div>
						</div>
						<!-- btn_box Start -->
						<div class="btn_box">
							<div class="right">
								<input type="button" class="btn btn_primary" id="btnSave" alt="수정" value="수정" />
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