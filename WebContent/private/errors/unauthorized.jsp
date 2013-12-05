<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Unauthorized ! EMS - Enrollment Management System</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/style_portal.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>
<script type="text/javascript" src="js/Open_Sans_400.font.js"></script>
<script type="text/javascript" src="js/Open_Sans_Light_300.font.js"></script> 
<script type="text/javascript" src="js/Open_Sans_Semibold_600.font.js"></script> 
<script type="text/javascript" src="js/FF-cash.js"></script>  
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
		<header>
			<!-- TOP + LOGO -->
			<div class="row-1">
				<h1>
					<a class="logo" href="index.html">EMS</a>
					<strong class="slog">Enrollment Management System</strong>						
				</h1>
				<form id="session-id">
					Welcome - ${systemUser.email}<br>
					Role: ${systemUser.role}<br>
					Time: <jsp:useBean id="today" class="java.util.Date" scope="page" />
					<fmt:formatDate value="${today}" pattern="dd MMM yyyy - HH:mm" />
				</form>				
			</div>
			<!-- MENU -->
			<div class="row-2">
				<nav>
					<ul class="menu">										
						  <c:import url="../inc/header.jsp"/>					  
					</ul>
				</nav>
			</div>
		</header>
		<!-- CONTENT -->
		<section id="content">
			<div class="padding">				
				<div class="wrapper margin-bot">
				<!-- AJAX CONTENT -->
				<h2>Sorry - you are not authorized to do this action</h2>																							
				</div>			
			</div>		
		</section>	
	</div>
</div>
<script type="text/javascript"> Cufon.now(); </script>
</body>
</html>