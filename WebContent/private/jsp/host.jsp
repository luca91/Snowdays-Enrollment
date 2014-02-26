<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<%
int timeout = session.getMaxInactiveInterval();
response.setHeader("Refresh", timeout + "; URL = /snowdays-enrollment/logout.html");
%>
<html>
<head>
<title>Bolzano Snowdays-Participant Form</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/tables_style.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/style_portal.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/forms.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/datepicker.css" type="text/css" media="screen">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>
 
<script type="text/javascript" src="js/FF-cash.js"></script>  
<script type="text/javascript" src="js/user_inter_act.js"></script>
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
			<c:url value="/private/participantAdd?action=edit" /> 
		</c:set>
	<div id="stylized" class="myform">
	    <form method="POST" action="${act}" name="frmAddParticipan" enctype="multipart/form-data">
	    	<h3>Participant form - Host</h3>
	        
	        <input type="hidden" name="id_group" value="${id_group}"/> 
	        <input type="hidden" name="id" value="${id}"/> 
	        <c:if test="${image}">
	        	<h5 class="alert">Image bigger than 2MB. Choose another one.</h5>
	        </c:if><br><br><br>
	        <label>Name*</label> 
	        <input type="text" name="fname" value="${record.fname}" placeholder="Name"required/> <br><br><br>
	        <label>Surname*</label>
	        <input type="text" name="lname" value="${record.lname}" placeholder="surname" required/> <br><br><br>
	         <label>Email*</label>
	        <input type="text" name="email" value="${record.email}" required/> <br><br><br>
	        <label>Gender*</label>
	        <select name="gender" required>
	       		<c:forEach items="${genders}" var="gender">
	        		<option value="${gender}" <c:if test="${selGen == gender}">selected</c:if>>${gender}		        	
		        </c:forEach>
	        </select> <br><br><br> 
	        
	        <label>Address*</label>
	        <input name="address" type="text"  value="${record.address}" placeholder="Address" style="width:100px" required/>
	        <input name="zip" type="text"  value="${record.zip}" placeholder="ZIP" style="width:70px" required/>
	        <input name="city" type="text"  value="${record.city}" placeholder="City" style="width:150px" required/>
	        <input name="country" type="text"  value="${record.country}" placeholder="Country" style="width:150px" required/>
	        <br><br><br> 	  	            	            
	        
	       <label>Photo</label>
	        <input type="file" name="photo" <c:if test="${record.photo == null}"> required</c:if>/> <input type="text" readonly="readonly" value="${record.photo}"/> <br><br><br><br>
	        
	        <input type="button" value="Back" onClick="history.go(-1);return true;" class="input" />
	        <input type="submit" value="Submit" class="input" />
        	<!-- <c:if test="${param.id eq null }">
        		<input type="reset" value="Reset" class="input" />
        	</c:if> --><br><br><br>
	    </form>
	    </div>
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>