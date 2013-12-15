<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<%System.out.println("Login"); %>
<html lang="en">
<head>
<title>Bolzano Snowdays - Enrollment Login</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/style.css" type="text/css" media="screen">
</head>
<body id="page5">
<div class="bg">
	<div class="main">
		<!-- HEADER -->
			<c:import url="inc/tophead.jsp"/>
				<div id="stylized" class="myform">
				
				<!--<c:set var="act">
					<c:url value="/private/index.html" /> 
				</c:set> -->
		<form method="POST" action="j_security_check" name="loginForm" id="form">
			<h3>Login</h3><br><br><br>
			<label>Username</label>
			<input type="text" name="j_username"/><br><br>
			<label>Password</label>
			<input type="password" name="j_password"/><br><br>
			
			<!-- BUTTONS -->			
		    <input type="submit" value="Login" class="input" />
		    <input type="button" value="Cancel" onClick="window.location='../public/index.html'" class="input" /><br><br><br>
	
		</form>
	</div>						
			<c:import url="inc/bottom.jsp"></c:import>
</html>