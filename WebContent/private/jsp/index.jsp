<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<%
System.out.println("index.html");
%>
<%
int timeout = session.getMaxInactiveInterval();
response.setHeader("Refresh", timeout + "; URL = /snowdays-enrollment/login.html");
%>
<html lang="en">
<head>
<title>Bolzano Snowdays - Enrollment</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="css/tables_style.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="css/style_portal.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
<script type="text/javascript" src="private/js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="private/js/cufon-yui.js"></script>
<script type="text/javascript" src="private/js/cufon-replace.js"></script>
<script type="text/javascript" src="private/js/Open_Sans_400.font.js"></script>
<script type="text/javascript" src="private/js/Open_Sans_Light_300.font.js"></script> 
<script type="text/javascript" src="private/js/Open_Sans_Semibold_600.font.js"></script> 
<script type="text/javascript" src="private/js/FF-cash.js"></script>  
<script src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body id="page5">
<div class="bg">
	<div class="main">
		<!-- HEADER -->
			<c:import url="inc/tophead.jsp"/>
			<c:if test="${systemUser.role == 'admin' }">
				<a href='settings.html'><h3 class="htabs">Settings</h3></a><br><hr><br>
			</c:if>						
			<c:import url="inc/bottom.jsp"></c:import>
</html>