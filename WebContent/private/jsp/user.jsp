<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Bolzano Snowdays-User Form</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/tables_style.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/style_portal.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/forms.css" type="text/css" media="screen">
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
	<!-- TOPHEAD --><c:import url="inc/tophead.jsp"/>
	<!-- CONTENT -->
	<c:set var="act">
			<c:url value="/private/userAdd?action=userList" /> 
	</c:set>	
	<div id="stylized" class="myform">	
	    <form method="POST" action="${act}" name="frmAddUser">
	    	<input type="hidden" name="id" value="${user.id}"/>
	    
			<h3>Users</h3> <br><br><br>
	        <label>First Name:</label>
	        <input type="text" name="fname" value="${user.fname}" required placeholder="Name"/> <br><br><br>
	         
	        <label>Last Name:</label>
	        <input type="text" name="lname" value="${user.lname}" required placeholder="Surname"/> <br><br><br> 
	         <label>Username:</label>
	        <input type="text" name="username" value="${user.username}" required/> <br><br><br> 
	        <label>Birthday:
	         <span class="small">(YYYY/MM/DD)</span>
	         </label>
	        <input type="text" name="date_of_birth" value="${user.date_of_birth}" required/> <br><br><br> 	  
	        <label>Password:</label>          
	       	<input type="password" name="password" value="${user.password}" required/> <br><br><br>
	            
	        <label>Email:</label>
	        <input type="text" name="email" value="${user.email}" required placeholder="Enter a valid email"/> <br><br><br> 
	        <label>Role:</label>
	        	<select name="role" required>
	        			<option value=""></option>
	        			<option value="admin" 
	        				<c:if test="${user.role == 'admin'}">selected</c:if>
	        				>
	        				Administrator
	        			</option>
	        			<option value="group_manager" 
	        				<c:if test="${user.role == 'group_manager'}">selected</c:if>
	        				>
	        				Group Manager
	        			</option>
	        		</select>
	        <br><br><br>   
	        <input type="submit" value="Submit" class="input"/>         
	        <input type="button" value="Back" onClick="history.go(-1);return true;" class="input" />
        	<c:if test="${param.id eq null }">
        		<input type="reset" value="Reset" class="input" /><br><br><br>
        	</c:if>
	    </form>		
	   </div>
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>