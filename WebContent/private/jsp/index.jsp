<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<%
	System.out.println("index.html");
%>
<%
	int timeout = session.getMaxInactiveInterval();
response.setHeader("Refresh", timeout + "; URL = /snowdays-enrollment/logout.html");
%>
<html lang="en">
<head>
<title>Bolzano Snowdays - Enrollment</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="css/tables_style.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="css/style_portal.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="css/layout.css" type="text/css"
	media="screen">
<script type="text/javascript" src="private/js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="private/js/cufon-yui.js"></script>
<script type="text/javascript" src="private/js/cufon-replace.js"></script>
<!--  
<script type="text/javascript" src="private/js/Open_Sans_400.font.js"></script>
<script type="text/javascript"
	src="private/js/Open_Sans_Light_300.font.js"></script>
<script type="text/javascript"
	src="private/js/Open_Sans_Semibold_600.font.js"></script>
	-->
<script type="text/javascript" src="private/js/FF-cash.js"></script>
<script src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"
	type="text/javascript"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"
	type="text/javascript"></script>
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
			<c:import url="inc/tophead.jsp" />
			<div class="row-2">
				<div>
				<h3 class="htabs">
						<br> Welcome to the internal page
					</h3>
					<img src="${pageContext.request.contextPath}/private/images/mascotte2.png" class="img-indent-r" alt="" height="280" width="200">
				</div>
				<br><br>
				<div class="block-news">
					<h3 class="h1">
						Guidelines<br><br>
					</h3>
					<ul>
						<li><h4>IMPORTANT</h4>
							1. Photo to upload must be less than 2MB<br>
							2. Fill all the form field with "*"<br>
							3. Click on "Conclude" if you inserted everybody<br>
							4. Use ";" to separate the intolerances<br><br></li>
						<li><h4>Add a participant</h4>
 				  			1. Click on “ Add participant”<br>
						   	2. Fill the form you see (all the field with “*” near the name are required)<br>
							3. Click on “Submit” to send the data<br><br></li>
						<li><h4>Delete a participant</h4>
							1. Click on the link “Delete” on the row of the person you want to delete<br><br></li>
						<li><h4>Update person's data</h4>
							1. Click on the link “Update” on the corresponding row<br>
							2. Modify the field(s) as you need<br>
							3. Click on “Submit”<br><br></li>
						<li><h4>Update profile/ID photo</h4>
							1. Be sure that the file to upload is not larger than 2MB<br>
							2. Click on “Browse”<br>
							3. Select the file<br><br></li>
					</ul>

				</div>

				<c:import url="inc/bottom.jsp"></c:import>


			</div>
		 </div>
</html>