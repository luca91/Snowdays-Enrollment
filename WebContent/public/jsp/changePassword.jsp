<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>EMS - Enrollment Management System - Change Password Page</title>
<meta charset="utf-8">
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
<!-- HEADER -->
<c:import url="inc/header_forms.jsp"/>
<!-- CONTENT -->
<section id="content">			
	<div class="padding">
	<div id="stylized" class="myform">
	<!-- <p>Enrollment Management System</p><br> -->
	<h3>Reset Password</h3><br>
	<c:choose>
		<c:when test="${param.email == null }">

			<c:set var="act">
				<c:url value="/public/changePasswordSend" />
			</c:set>
			<form action="${act}" method="post" name="frmChangePasswordSend" id="form">
				<label>Email: 
				<span class="small">Type your e-mail</span>
				</label>
				<input type="text" name="email" /><br><br><br> <input
					type="submit" value="Submit" class="input" /><br><br><br>
			</form>
		</c:when>
		<c:when test="${message != 2 }">
			<c:set var="act">
				<c:url value="/public/changePasswordDo" />
			</c:set>
			<form action="${act}" method="post" name="frmChangePasswordDo" id="form">
				<label>Email: 
				<span class="small">Type your e-mail</span>
				</label>
				<input type="text" name="email" readonly
					value="${param.email}" /><br><br><br>
				<c:if test="${message == 6 }"> Email not recognized</c:if>
				<label>Old Password: 
				</label><input type="password" name="oldPassword"
					value="${oldPassword }" /><br><br><br>
				<c:if test="${message == 5 }"> Incorrect password</c:if>
				<label>New Password: 
				</label><input type="password" name="newPassword"
					value="${newPassword }"><br><br><br>
				<c:if test="${message == 4 }"> Empty password</c:if>
				<label>Confirm New Password: 
				</label><input type="password"
					name="confirmedPassword" value="${confirmedPassword }"><br><br><br>
				<c:if test="${message == 3 }"> Confirmed password wrong</c:if>
				<input type="hidden" name="action" value="change" readonly>
				<input type="submit" value="Submit" class="input" /><br><br><br>
			</form>
		</c:when>
		<c:otherwise>
			<c:if test="${message == 2 }">
				<br> Password changed
		        	<c:url value="/private/index.html" var="url" />
				<br>
				<br>
				<a href="${url }">Link</a> to enter private site
		        </c:if>
		</c:otherwise>
	</c:choose>
	</div>
</div>
</section>
</body>
</html>