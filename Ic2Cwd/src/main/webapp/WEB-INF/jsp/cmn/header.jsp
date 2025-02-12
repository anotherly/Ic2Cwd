<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<div class="header-div">

	<h1 class='logo'>
		<a style="width:200px;" href="/chart/main.do"></a>
	</h1>
	
	<div class="sys-name">
		<span style="font-size: 24px;">혼잡도 관리 시스템</span>
		<span style="font-size: 10px;">Crowdedness Management System</span>
	</div>
	
</div>
<div class="header_util">
	<div class="username">
		<c:choose>
			<c:when test="${login.userName != null}">
				<ul class="nav-icons" style="margin-top: 0px;">
					<li style="display: flex;">
						<i class="ion-person"></i>
						<div style="margin-left: 10px;">${login.userName}님 환영합니다.</div>
						<a href="/login/logout.do" style="margin-left: 10px;">logout</a>
					</li>
				</ul>
				
			</c:when>
			<c:otherwise>
				<ul class="nav-icons" style="margin-top: 0px;">
					<li style="display: flex;">
						<a href="/login/login.do" style="margin-left: 10px;">login</a>
					</li>
				</ul>
			</c:otherwise>
		</c:choose>
	</div>
</div>