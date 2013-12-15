<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- HEADER -->
<header>
	<!-- TOP + LOGO -->
	<div class="row-1">
		<h1>
			<a class="logo" href="index.html">Bolzano Snowdays - Enrollment</a>
			<strong class="slog">Enrollment Management System</strong>						
		</h1>
		<form id="session-id">
			Welcome - ${systemUser.email} <a href="<c:url value='/logout.html'/>">Logout</a> <br>
			Role:
			<c:choose>  
				<c:when test="${systemUser.role == 'admin'}">
					Administrator
				</c:when>
				<c:when test="${systemUser.role == 'group_mng'}">
					Group Manager
				</c:when>
			</c:choose>	
			${systemUser.id } <br/>
			Time: <jsp:useBean id="today" class="java.util.Date" scope="page" />
			<fmt:formatDate value="${today}" pattern="dd MMM yyyy - HH:mm" />
		</form>				
	</div>
	<!-- MENU -->
	<div class="row-2">
		<nav>
			<ul class="menu">										
				  <c:import url="inc/header.jsp"/>					  
			</ul>
		</nav>
	</div>
</header>
<!-- CONTENT -->
<section id="content">
	<div class="padding">				
		<div class="wrapper margin-bot">
		<!-- AJAX CONTENT -->				
			<div class="col-3" id="ajax-parse">											
				<div class="indent">							
																								
				</div>
				<!-- SINGLE INDIVIDUAL CONTENT -->