<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Bolzano Snowdays-Group Form</title>
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
			<c:url value="/private/groupAdd?action=recordList" /> 
		</c:set>
		
		<div id="stylized" class="myform">
		    <form method="POST" action="${act}" name="frmAddGroup" id="form">
		    	<h3>Group form</h3><br><br><br>
		        <!-- HIDDEN --><!-- <label>Group ID : <span class="small">To be hidden</span></label> -->

		        <label>Name: 
		        </label>
		        <input type="text" name="name" value="${record.name}" required/><br><br><br>
		        
		        <label>Referent: 
		        </label>
       	        <select name="id_group_referent" required>
       	        	<option value=""></option>
          			<c:forEach items="${listOfGroup_mng}" var="options">	               
           				<option value="${options.id }" 
           					<c:if test="${options.id == record.id_group_referent }">selected</c:if>>
           						${options.fname} ${options.lname }
           				</option>
           			</c:forEach>
           		</select> <br><br><br>           		
		        <label>Max participants: 
		        </label>
		        <input type="text" name="max_group_number" value="${record.max_group_number}"required/><br><br><br><br>
		        
		        <label>
		        	Snowvolley Team:
		        </label>
		        <select name="snowvolley" required>
		        	<option value=""></option>
		        	<option value="YES">YES</option>
		        	<option value="NO">NO</option>
		        </select> <br><br><br><br>
		        
		        <label>
		        	Badge type:
		        </label>
		         <select name="badge" required>
		         	<option value=""></option>
		         	<option value="PARTICIPANT">PARTICIPANT</option>
		        	<option value="STAFF">STAFF</option>
		        	<option value="PARTY/HOST">PARTY/HOST</option>
		        </select> <br><br><br><br>
		        
		        <label>
		        	Country:
		        </label>
		        <select name="country" required>
		        	<option value=""></option>
		        	<option value="Italy(IT)">Italy(IT)</option>
		        	<option value="Austria(A)">Austria(A)</option>
		        	<option value="Germany(D)">Germany(D)</option>
		        	<option value="Spain(E)">Spain(E)</option>
		        </select> <br><br><br><br>
		        
		        <label>
		        	Approved
		        </label>
		        <select name="approved" required>
		        	<option value=""></option>
		        	<option value="YES">YES</option>
		        	<option value="NO">NO</option>
		        </select> <br><br><br><br>
		        
		        <label>
		        	Blocked:
		        </label>
		        <select name="blocked" required>
		        	<option value=""></option>
		        	<option value="YES">YES</option>
		        	<option value="NO">NO</option>
		        </select> <br><br><br><br>
		        
		        
		        <!-- BUTTONS -->
		        <input type="submit" value="Submit" class="input" />
		        <input type="button" value="Back" onClick="history.go(-1);return true;" class="input" />
		        <c:if test="${param.id eq null }">
	        		<input type="reset" value="Reset" class="input" />
	       		</c:if><br><br><br>
		    </form>
	    </div>	        
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>