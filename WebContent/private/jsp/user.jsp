<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<%
int timeout = session.getMaxInactiveInterval();
response.setHeader("Refresh", timeout + "; URL = /snowdays-enrollment/");
%>
<html>
<head>
<title>Bolzano Snowdays-User Form</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/tables_style.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/style_portal.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/forms.css" type="text/css" media="screen">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<link rel="stylesheet" href="css/datepicker.css" type="text/css" media="screen">
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>
<script type="text/javascript" src="js/Open_Sans_400.font.js"></script>
<script type="text/javascript" src="js/Open_Sans_Light_300.font.js"></script> 
<script type="text/javascript" src="js/Open_Sans_Semibold_600.font.js"></script> 
<script type="text/javascript" src="js/FF-cash.js"></script>  
<script src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" > 
	$(function(){
		$("#datepicker").datepicker({ dateFormat: 'yy-mm-dd', maxDate: new Date(1995,01,01)});
	})
</script>
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
	        <label>Name</label>
	        <input type="text" name="fname" value="${user.fname}" required placeholder="Name"/> <br><br><br>
	         
	        <label>Surame</label>
	        <input type="text" name="lname" value="${user.lname}" required placeholder="Surname"/> <br><br><br> 
	         <label>Username</label>
	        <input type="text" name="username" value="${user.username}" placeholder="Username" required/> <br><br><br> 
	        <label>Birthday
	        </label>
	        <input type="text" name="date_of_birth" id="datepicker" value="${user.date_of_birth}" placeholder="Birthday"/> <br><br><br> 	  
	        <label>Password</label>          
	       	<input type="password" name="password" value="${user.password}" placeholder="Password" required/> <br><br><br>
	            
	        <label>Email</label>
	        <input type="text" name="email" value="${user.email}" required placeholder="Enter a valid email"/> <br><br><br> 
	        <label>Role</label>
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