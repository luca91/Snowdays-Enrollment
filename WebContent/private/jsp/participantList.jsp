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
<title>Bolzano Snowdays-Participants</title>
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
		<c:if test="${groupName != null}"><h3 class="htabs">Participants - ${groupName}</c:if></h3>
		<c:if test="${groupName == null}"><h3 class="htabs">All Participants</h3></c:if>
		<c:if test="${fn:length(groups) != 0}">
		<c:choose>
			<c:when test="${systemUser.role == 'admin'}">
			<c:if test="${groups != null}">			    
			    <select>
			    	<c:url value="/private/participantList.html" var="url_gen"></c:url>
					<option value="empty" onClick="window.location.href='${url_gen}'"></option>
						<c:if test="${id_group == null}">selected</c:if>
					
					<c:forEach items="${groups}" var="group">
						<c:url
							value="/private/participantList.html?action=listRecord&id_group=${group.id}"
							var="url" />
						<option value="${group.name}" onClick="window.location.href='${url}'" 
						<c:if test="${group.id == id_group}">selected</c:if>>${group.name}</option>
					</c:forEach>
				</select>
			</c:if>
		
			
			<br></br>
			<br>
			
			<c:choose>
				<c:when test="${id_group > 0}">
						<h6>Max ${groupMaxNumber} participants - Actual participants: ${group.actualParticipantNumber}</h6>		
				</c:when>				
			</c:choose>
			<c:choose>				
				<c:when test="${id_group != null && (nrEnrolledParticipant >= groupMaxNumber)}">
				<h5 class="alert">ATTENTION: Max nr. of enrolled people reached for this group!</h5>
				</c:when>				
			</c:choose>
				
		

			<!-- TABLES -->
				<table id="box-table-a">
				<c:if test="${fn:length(records) != 0}">
					<thead>
						<tr>
							<th scope="col">First Name</th>
							<th scope="col">Last Name</th>
							<th scope="col">Group</th>
							<th scope="col">Date of birth</th>
							<th scope="col" colspan=3>Action</th>
						</tr>
					</thead>
				</c:if>
					<tbody>
						<c:forEach items="${records}" var="record">
							<tr>
								<td>${record.fname}</td>
								<td>${record.lname}</td>
								<td>${record.groupName}</td>
								<td>${record.date_of_birth}</td>
								<td><a
									href="<c:url value='/private/participant.jsp?action=edit&id=${record.id}&id_group=${record.id_group}'/>">Update</a></td>
								<td><a
									href="<c:url value='/private/participantDelete?action=delete&id=${record.id}&id_group=${record.id_group}'/>"
									onclick="return confirmDelete();">Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>					
				</table>
				<!-- APPROVE ALL / DISAPPROVE ALL -->
			<div class="table-buttons">
				
				<c:if test="${((fn:length(groups) != 0) && (id_group != null))}">
					<a class="button-2" href="participant.jsp?action=insert&id_group=${id_group}">Add Participant</a>
				</c:if>
				
		</c:when>
		<c:otherwise>
		<c:if test="${!blocked}">
			<c:choose>
				<c:when test="${id_group > 0}">
						<h6>Max ${groupMaxNumber} participants - Actual participants: ${nrEnrolledParticipant}</h6>		
				</c:when>				
			</c:choose>
			<c:choose>				
				<c:when test="${id_group != null && (nrEnrolledParticipant >= groupMaxNumber)}">
				<h5 class="alert">ATTENTION: Max nr. of enrolled people reached for this group!</h5>
				</c:when>				
			</c:choose>
				
		

			<!-- TABLES -->
				<table id="box-table-a">
				<c:if test="${fn:length(records) != 0}">
					<thead>
						<tr>
							<th scope="col">First Name</th>
							<th scope="col">Last Name</th>
							<th scope="col">Group</th>
							<th scope="col">Date of birth</th>
							<th scope="col" colspan=3>Action</th>
						</tr>
					</thead>
				</c:if>
					<tbody>
						<c:forEach items="${records}" var="record">
							<tr>
								<td>${record.fname}</td>
								<td>${record.lname}</td>
								<td>${record.groupName}</td>
								<td>${record.date_of_birth}</td>
								<td><a
									href="<c:url value='/private/participant.jsp?action=edit&id=${record.id}&id_group=${record.id_group}'/>">Update</a></td>
								<td><a
									href="<c:url value='/private/participantDelete?action=delete&id=${record.id}&id_group=${record.id_group}'/>"
									onclick="return confirmDelete();">Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>					
				</table>
				<!-- APPROVE ALL / DISAPPROVE ALL -->
			<div class="table-buttons">
				
				<c:if test="${((nrEnrolledParticipant < groupMaxNumber) && (id_group > 0))}">
					<a class="button-2" href="participant.jsp?action=insert&id_group=${id_group}">Add Participant</a>
				</c:if>
				<c:if test="${systemUser.role == 'group_manager' && !blocked}">
					<a class="button-2" href="participant.jsp?action=conclude&id_group=${id_group}">Conclude</a>
				</c:if>
		</c:if>
		</c:otherwise>
			
		</c:choose>
		</c:if>
				
				<!--  <form method="POST" action="${act}" name="frmApproveParticipan">
				<c:if test="${id_group != 0 && (nrEnrolledParticipant < group.groupMaxNumber)}">
					<a class="button-2" href="participant.jsp?action=insert&id_group=${id_group}">Add Participant</a>
					<c:set var="act">
						<c:url value="/private/participantInvite?action=invite&id_group=${id_group}" />
					</c:set>					
				</c:if>
				<c:set var="act">
					<c:url value="/private/participantApprove?action=approve&id_group=${id_group}" />
				</c:set>				
					<c:if test="${not empty records}">
						<input type="submit" value="Approve All" class="input" />											
					</c:if>				
				<c:set var="act"><c:url value="/private/participantApprove?action=disapprove&id_group=${id_group}" /></c:set>				
								
					<c:if test="${not empty records}">
						<input type="submit" value="Disapprove All" class="input" />
					</c:if>
					</form> --> 
			</div>						
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>