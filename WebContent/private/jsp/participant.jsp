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
	    	<h3>Participant form</h3> <br><br><br>
	        
	        <label>Group</label>
	        <input type="text" readonly="readonly" name="id_group" value="${id_group}"/> <br><br><br>
	        <label>Name</label> 
	        <input type="text" name="fname" value="${record.fname}" required/> <br><br><br>
	        <label>Surname</label>
	        <input type="text" name="lname" value="${record.lname}" required/> <br><br><br>
	        <label>Gender</label>
	        <select name="gender" required>
	        <option value=""/>
	        <option value="male">M</option>
	        <option value="female">F</option> 
	        </select> <br><br><br> 
	        <label>Friday activity</label>
	        <select name="friday"required>
	        	<option value=""/>
	        	<option value="skirace">Ski Race</option>
	        	<option value="snowboardrace">Snowboard Race</option>
	        	<option value="snowshoehike"> Snowshoe Hike</option>
	        	<option value="relax">Relax</option>
	        </select> <br><br><br> 	            	            
	        
	        <label>Birthday</label>
	        <input type="text" name="date_of_birth" id="datepicker" value="${record.date_of_birth}" class="hasDatepicker" required/>  <br><br><br>
	        <label>Intolerances</label>
	        <input type="text" name="intolerances" value="${record.intolerances}"/> <br><br><br>
	        <label>T-Shirt size</label>
	        <select name="tshirt"required>
	        	<option value=""></option>
	        	<option value="small">S</option>
	        	<option value="medium">M</option>
	        	<option value="large">L</option>
	        	<option value="xlarge">XL</option>
	        </select>    <br><br><br>
	        <label>Rental</label>
	        <select name="rental" required>
	        	<option value=""></option>
	        	<option value="none">none</option>
	        	<option value="skis">Only Skis</option>
	        	<option value="snowboard">Only Snowboard</option>
	        	<option value="skiandboots">Skis with boots</option>
	        	<option value="snowboardandboots">Snowboard with boots</option>
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