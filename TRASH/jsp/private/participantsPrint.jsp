<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Print list of Participant for Event</title>
		<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/tables_style.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/style_portal.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="screen">
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
	<body>
		<h3>List of Participants for ${eventName }</h3>
		<c:if test="${empty listOfParticipant}">
			<p>The list of participants is empty</p>
		</c:if>
		<c:if test="${not empty listOfParticipant}">
			<table id="box-table-a">
				<thead>			
					<tr>
						<th scope="col">Participant Id</th>
						<th scope="col">Group Id</th>
						<th scope="col">First Name</th>
						<th scope="col">Last Name</th>
						<th scope="col">Email</th>
						<th scope="col">Date of birth</th>
						<th scope="col">Registration Date</th>
					</tr>
				</thead>
	
				<tbody>
					<c:forEach items="${listOfParticipant}" var="record">
						<tr>
							<td>${record.id}</td>
							<td>${record.id_group}</td>
							<td>${record.fname}</td>
							<td>${record.lname}</td>
							<td>${record.email}</td>
							<td>${record.date_of_birth}</td>
							<td>${record.registration_date}</td>
						</tr>
					</c:forEach>
	
				</tbody>
			</table>
		</c:if>
	</body>
</html>
