<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>EMS - Participant Management</title>
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
<script type="text/javascript" src="js/user_inter_act.js"></script>
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
			<c:url value="/private/participantAdd?action=edit" /> 
		</c:set>
	<div id="stylized" class="myform">
	    <form method="POST" action="${act}" name="frmAddParticipan">
	    	<h3>Participant form</h3>
			<p>Edit the data for this participant</p>
	        <input type="hidden" readonly="readonly" name="id" value="${record.id}" /> <br> 
	        <input type="hidden" readonly="readonly" name="id_group" value="${id_group}" /> <br> 
	        
	        <label>First Name:</label> 
	        <input type="text" name="fname" value="${record.fname}" /> <br><br><br>
	        <label>Last Name:</label>
	        <input type="text" name="lname" value="${record.lname}" /> <br><br><br> 
	        <label>Email:</label>
	        <input type="text" name="email" value="${record.email}" /> <br><br><br> 	            	            
	        <label>Date of birth:
	        	<span class="small">(YYYY/MM/DD)</span>
	        </label>
	        <input type="text" name="date_of_birth" value="${record.date_of_birth}" /> <br><br>
	       	<input type="hidden" name="registration_date" value="${record.registration_date}" /> <br>
	       	<label>Approved:</label>
	        <c:choose>
	        	<c:when test="${param.id == null }">
	        			<input type="hidden" name="registration_date" value="${record.approved}" /> <br>
	        	</c:when>
	        	<c:otherwise>
		           	<select name="approved">
		        		<option value="false" <c:if test="${record.approved == 'false'}">selected </c:if> >false</option>
		        	    <option value="true" <c:if test="${record.approved == 'true'}">selected</c:if> >true</option>
		        	</select> 	        	
	        	</c:otherwise>
	        </c:choose>
	       <!--  blocked :  --><input type="hidden" name="blocked"
	            value="${record.blocked}" />	            	          	                  
	        <input type="submit" value="Submit" class="input" />
	        <input type="button" value="Back" onClick="history.go(-1);return true;" class="input" />
        	<c:if test="${param.id eq null }">
        		<input type="reset" value="Reset" class="input" />
        	</c:if><br><br><br>
	    </form>
	    </div>
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>