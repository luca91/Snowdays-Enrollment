<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<%
int timeout = session.getMaxInactiveInterval();
response.setHeader("Refresh", timeout + "; URL = /snowdays-enrollment/login.html");
%>
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
		        <input type="hidden" name="id" value="${record.id}"/>

		        <label>Name: 
		        </label>
		        <input type="text" name="name" value="${record.name}" placeholder="Group Name" required/><br><br><br>
		        
		        <label>Referent: 
		        </label>
       	        <select name="id_group_referent" required>
       	        	<option value=""></option>
          			<c:forEach items="${listOfGroup_mng}" var="options">	               
           				<option value="${options.id }" 
           					<c:if test="${options.id == record.groupReferentID }">selected</c:if>>
           						${options.fname} ${options.lname }
           				</option>
           			</c:forEach>
           		</select> <br><br><br>           		
		        
		        <label>
		        	Saturday activity:
		        </label>
		        <select name="saturday" required>
		        	<c:forEach items="${satProgs}" var="sat">
		        		<option value="${sat}" <c:if test="${sat == record.snowvolley }">selected</c:if>>
           						${sat}
           				</option>
		        	</c:forEach>
		        </select> <br><br><br><br>
		        
		        <label>
		        	Badge type:
		        </label>
		         <select name="badge" required>
		         	<option value="PARTICIPANT">PARTICIPANT</option>
		         	<option value=""></option>
		        	<option value="STAFF">STAFF</option>
		        	<option value="PARTY/HOST">PARTY/HOST</option>
		        </select> <br><br><br><br>
		        
		        <label>
		        	Country:
		        </label>
		       <select name="country" required>
       	        	<option value=""></option>
          			<c:forEach items="${countries}" var="country">	               
           				<option value="${country}"<c:if test="${country == record.country}">selected</c:if>>
           						${country}
           				</option>
           			</c:forEach>
           		</select> <br><br><br>
		        
		        <label>
		        	Approved
		        </label>
		        <select name="approved" required>
		        <option value=""></option>
		        	<option value="NO" <c:if test="${!record.isApproved}">selected</c:if>>NO</option>
		        	<option value="YES" <c:if test="${record.isApproved}">selected</c:if>>YES</option>
		        </select> <br><br><br><br>
		        
		        <label>
		        	Blocked:
		        </label>
		        <select name="blocked" required>
		        	<option value="NO">NO</option>
		        	<option value=""></option>
		        	<option value="YES">YES</option>
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