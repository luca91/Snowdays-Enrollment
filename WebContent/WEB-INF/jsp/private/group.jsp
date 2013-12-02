<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>EMS - Group Management</title>
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
		    	<h3>Groups form</h3>
				<p>Edit the data for this group</p>	        
		        <!-- HIDDEN --><!-- <label>Group ID : <span class="small">To be hidden</span></label> -->
		        <input type="hidden" readonly="readonly" name="id" value="${record.id}" />
		        <!-- HIDDEN --><!-- <label>Event ID : <span class="small">To be hidden</span></label> -->		        
		        <input type="hidden" name="id_event" readonly="readonly" value="${id_event}" /> 

		        <label>Group Name: 
		        <span class="small">Input the name for the group</span>
		        </label>
		        <input type="text" name="name" value="${record.name}" /><br><br><br>
		        
		        <label>Group referent : 
		        <span class="small">Contact person for the group</span>
		        </label>
       	        <select name="id_group_referent">
          			<c:forEach items="${listOfGroup_mng}" var="options">	               
           				<option value="${options.id }" 
           					<c:if test="${options.id == record.id_group_referent }">selected</c:if>
           						>${options.id } - ${options.fname} ${options.lname }
           				</option>
           			</c:forEach>
           		</select><br><br><br>           		
		        <label>Max # participants : 
		        <span class="small">Nr. of people allowed for the group</span>
		        </label>
		        <input type="text" name="max_group_number" value="${record.max_group_number}" /><br><br><br><br>
		        
		        <!-- BUTTONS -->
		        <input type="hidden" name="blocked" value="${record.blocked}" />
		        <input type="submit" value="Submit" class="input" />
		        <input type="button" value="Back" onClick="history.go(-1);return true;" class="input" />
		        <c:if test="${param.id eq null }">
	        		<input type="reset" value="Reset" class="input" />
	       		</c:if><br><br><br>
		    </form>
	    </div>	        
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>