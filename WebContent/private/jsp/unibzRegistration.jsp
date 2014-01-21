<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<%
int timeout = session.getMaxInactiveInterval();
response.setHeader("Refresh", timeout + "; URL = /snowdays-enrollment/");
%>
<html>
<head>
<title>Bolzano Snowdays-UNIBZ</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/tables_style.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/style_portal.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>

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
		<h3 class="htabs">Registrations UNIBZ</h3> 
			<br></br><br>	
			
			<c:url value="private/unibzRegistrations.html?action=check" var="act"></c:url>
			<form method="POST" action="${act}" name="frmAddParticipan">
				<input type="text" name="unibzpartname" placeholder="Name"/>
				<input type="text" name="unibzparsurname" placeholder="Surname"/>
				<input type="text" name="email" value="${email}" placeholder="Email"/> <br><br><br>
				<c:choose>
					<c:when test="${systemUser.role == 'admin'}">			    
					    <select name="faculty">
					    	<option value="computerscience">Computer Science</option>
					    	<option value="design">Design</option>
					    	<option value="sciencetechnology">Science and Technology</option>
					    	<option value="economics">Economics</option>
					    	<option value="education">Education</option>
						</select>
					</c:when>
				</c:choose>
			</form>

			<!-- TABLES -->
				<table id="box-table-a">
				<c:if test="${fn:length(records) != 0}">
					<thead>
						<tr>
							<th scope="col">Position</th>
							<th scope="col">Name</th>
							<th scope="col">Participants #</th>
							<th scope="col">Registration time</th>
						</tr>
					</thead>
				</c:if>
					<tbody>
						<c:forEach items="${records}" var="record">
							<tr>
								<td></td>
								<td>${record.name}</td>
								<td>${record.actualParticipantNumber}</td>
								<td>${record.timeFirstRegistration}</td>
							</tr>
						</c:forEach>
					</tbody>					
				</table>			
		<hr>
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>