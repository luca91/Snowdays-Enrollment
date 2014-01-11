<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<%
int timeout = session.getMaxInactiveInterval();
response.setHeader("Refresh", timeout + "; URL = /snowdays-enrollment/login.html");
%>
<html>
<head>
<title>Bolzano Snowdays-Settings </title>
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
</head>
<body id="page5">
<div class="bg">
	<div class="main">
	<!-- TOPHEAD --><c:import url="inc/tophead.jsp"/>
	<!-- CONTENT -->
		<h3 class="htabs">Settings</h3>
		<c:set var="act">
			<c:url value="/private/settingsEdit" /> 
		</c:set>
		<div id="stylized" class="myform">
		    <form method="POST" action="${act}" name="frmSettings" id="form">
		        <!-- HIDDEN --><!-- <label>Group ID : <span class="small">To be hidden</span></label> -->

		        <label>Max Participants per group</label>
		        <input type="text" name="maxpergroup" value="${maxPerGroup}" required/><br><br><br>
		        
		        <label>Badge files</label><br><br><br>
		        <c:if test="${fn:length(countries) != 0 }">
			        <c:choose>
							<c:when test="${systemUser.role == 'admin'}">
								<c:forEach items="${badges}" var="badge">
									<h5>${badge}</h5><input name="${badge}" type="file"/><br><br><br><br>
								</c:forEach>
							</c:when>
					</c:choose>
				</c:if>
		        

		        <label>
		        	Max people per country
		        </label> <br><br><br>
		        <c:if test="${fn:length(countries) != 0}">
					<c:choose>
						<c:when test="${systemUser.role == 'admin'}">
							<c:forEach items="${countries}" var="country">
								<h5>${country.name}</h5><input name="${country.name}" value="${country.maxPeople}"/><br><br><br>
							</c:forEach>
						</c:when>
					</c:choose>
				</c:if>
		        <br><br><br>
		        
		        <label>
		        	Max UNIBZ participants
		        </label>
		        <input type="text" name="maxinternals" value="${maxInternals}"/>
		          <br><br><br><br>
		        
		        <label>
		        	Enrollment from (externals)
		        </label>
		        <input type="datetime" name="enrollmentstartext" value="${enrollmentStartExt}"/>
		        <br><br><br><br>
		       
		        
		        
		        <!-- BUTTONS -->
	       		 <input type="submit" value="Save" class="input" />
	        	<input type="button" value="Back" onClick="history.go(-1);return true;" class="input" />
		    </form>
	    </div>	        
		
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>