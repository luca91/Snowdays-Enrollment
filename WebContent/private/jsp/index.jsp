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
<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/tables_style.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/style_portal.css" type="text/css" media="screen">
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
<!--[if lt IE 7]>
	<div style=' clear: both; text-align:center; position: relative;'>
		<a href="http://www.microsoft.com/windows/internet-explorer/default.aspx?ocid=ie6_countdown_bannercode"><img src="http://www.theie6countdown.com/images/upgrade.jpg" border="0"  alt="" /></a>
	</div>
<![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="/private/js/html5.js"></script>
	<link rel="stylesheet" href="/private/css/ie.css" type="text/css" media="screen">
<![endif]-->
</head>
<body id="page5">
<div class="bg">
	<div class="main">
		<!-- HEADER -->
			<c:import url="inc/tophead.jsp"/>	
			<div class=row-2>
			 <div>
			  <img src="${pageContext.request.contextPath}/private/images/mascotte1.png" class= "img-indent2" alt="" height="280" width="200">
			  <h3 class="htabs">Welcome to the internal page<br></h3>	 
			  </div>			
			<c:import url="inc/bottom.jsp"></c:import>
			
			
			</div>	
</html>