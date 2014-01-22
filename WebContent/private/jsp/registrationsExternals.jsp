<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<%
int timeout = session.getMaxInactiveInterval();
response.setHeader("Refresh", timeout + "; URL = http://scub.unibz.it:8080/snowdays-enrollment/");
%>
<html>
<head>
<title>Bolzano Snowdays-Externals</title>
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
		<h3 class="htabs">Registrations Externals</h3> 
			<br></br><br>		
			
			<h6 style="font-family:Titillium">Total ${actualParticipants}/${totalParticipants}</h6>
			<h6 style="font-family:Titillium">Total registered ${totalRegistered}</h6>
			

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
								<td>${record.position}</td>
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