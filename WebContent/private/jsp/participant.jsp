<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Bolzano Snowdays-Participant Form</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/tables_style.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/style_portal.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/forms.css" type="text/css" media="screen">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>
<script type="text/javascript" src="js/Open_Sans_400.font.js"></script>
<script type="text/javascript" src="js/Open_Sans_Light_300.font.js"></script> 
<script type="text/javascript" src="js/Open_Sans_Semibold_600.font.js"></script> 
<script type="text/javascript" src="js/FF-cash.js"></script>  
<script type="text/javascript" src="js/user_inter_act.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" > 
	$(function(){
		("#datepicker").datepicker();	
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
	    <form method="POST" action="${act}" name="frmAddParticipan">
	    	<h3>Participant form</h3>
	        
	        <input type="hidden" name="id_group" value="${id_group}"/> 
	        <input type="hidden" name="id" value="${id}"/> <br><br><br>
	        <label>Name</label> 
	        <input type="text" name="fname" value="${record.fname}" required/> <br><br><br>
	        <label>Surname</label>
	        <input type="text" name="lname" value="${record.lname}" required/> <br><br><br>
	        <label>Gender</label>
	        <select name="gender" required>
	       		<c:forEach items="${genders}" var="gender">
	        		<option value="${gender}" <c:if test="${selGen == gender }">selected</c:if>>${gender}		        	
		        </c:forEach>
	        </select> <br><br><br> 
	        <label>Birthday</label>
	        <input type="text" name="date_of_birth" id="datepicker" value="${record.date_of_birth}" class="hasDatepicker" required/>  <br><br><br>
	        <c:if test="${group == UNIBZ}">
	        	<label>Adresse</label>
	        	<input name="adresse" placeholder="Adresse" type="text"/> <br><br><br>
	        </c:if>
	        <label>Friday activity</label>
	        <select name="friday"required>
	        	<c:forEach items="${programs}" var="program">
	        		<option value="${program}" <c:if test="${selPro == program }">selected</c:if>>${program}		        	
		        </c:forEach>
	        </select> <br><br><br> 	            	            
	        
	        <label>Intolerances</label>
	        <input type="text" name="intolerances" value="${record.intolerances}"/> <br><br><br>
	        <label>T-Shirt size</label>
	        <select name="tshirt"required>
	        	<c:forEach items="${tshirts}" var="tshirt">
	        		<option value="${tshirt}" <c:if test="${selTS == tshirt }">selected</c:if>>${tshirt}		        	
		        </c:forEach>
	        </select>    <br><br><br>
	        <label>Rental</label>
	        <select name="rental" required>
	        	<c:forEach items="${rentals}" var="rental">
	        		<option value="${rental}" <c:if test="${selRen == rental}">selected</c:if>>${rental}		        	
		        </c:forEach>
	        </select>       <br><br><br> 
	        <label>Photo</label>
	        <input type="file"/> <br><br><br>
	        
	             	                  
	        <input type="submit" value="Submit" class="input" />
	        <input type="button" value="Back" onClick="history.go(-1);return true;" class="input" />
        	<c:if test="${param.id eq null }">
        		<input type="reset" value="Reset" class="input" />
        	</c:if><br><br><br>
	    </form>
	    </div>
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>