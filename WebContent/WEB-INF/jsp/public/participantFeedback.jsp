<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Result of enrollment</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/style.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/forms_public.css" type="text/css" media="screen">
<script type="text/javascript" src="js/jquery-1.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>
<script type="text/javascript" src="js/Open_Sans_400.font.js"></script>
<script type="text/javascript" src="js/Open_Sans_Light_300.font.js"></script> 
<script type="text/javascript" src="js/Open_Sans_Semibold_600.font.js"></script>  
<script type="text/javascript" src="js/tms-0.3.js"></script>
<script type="text/javascript" src="js/tms_presets.js"></script> 
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script> 
<script type="text/javascript" src="js/FF-cash.js"></script>
<!--[if lt IE 7]>
	<div style=' clear: both; text-align:center; position: relative;'>
		<a href="http://www.microsoft.com/windows/internet-explorer/default.aspx?ocid=ie6_countdown_bannercode"><img src="http://www.theie6countdown.com/images/upgrade.jpg" border="0"  alt="" /></a>
	</div>
<![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="js/html5.js"></script>
	<link rel="stylesheet" href="css/ie.css" type="text/css" media="screen">
<![endif]-->
</head>
<body id="page1">
	<!-- header -->
	<c:import url="inc/header_forms.jsp"/>
		<!-- CONTENT -->
		<section id="content">			
			<div class="padding">
			<!-- CONFIRMATION RESULT -->
				<div id="stylized" class="myform">
				<!-- <h5>Result of enrollment:</h5><p></p> -->			
			
				<c:if test="${param.sentEmail == 'y'}">
					<h3>Your enrollment has been registered!</h3><br><p></p><br>
					<h5>An email has been sent to your address.</h5><br>
					<h5>Click <a href="<c:url value='/public/index.html'/>">here</a> to go to the home page of the Enrollment Management System.</h5><br>				
				</c:if>
			
				<c:if test="${param.sentEmail != 'y'}">
					<h5 class="alert">ATTENTION!</h5><p></p>
					<h5 class="alert2">We are sorry, but currently there are not anymore places available!</h5><br><br>
					<h5>Contact a Group Referent to get a new invitation when more places will be available.</h5><br>
					<h5>Click <a href="<c:url value='/public/index.html'/>">here</a> to go to the homepage of the Enrollment Management System.</h5><br>					
				</c:if>	
				</div>
			</div>									
		</section>
<!-- footer -->
<script type="text/javascript"> Cufon.now(); </script>
</body>
</html>