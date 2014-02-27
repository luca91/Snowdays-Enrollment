<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<%
int timeout = session.getMaxInactiveInterval();
response.setHeader("Refresh", timeout + "; URL = /snowdays-enrollment/logout.html");
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
		<c:if test="${groupName != null}"><h3 class="htabs" style="margin-left: 20px">Participants - ${groupName}</h3></c:if>
		<c:if test="${groupName == null}"><h3 class="htabs" style="margin-left: 20px">All Participants</h3>
		<div style="margin-left: 350px">
		<c:forEach items="${pages}" var="page" >
			<a href='participantList.html?page=${page}'>${page}</a>
		</c:forEach>
		</div></c:if>
		<c:if test="${fn:length(groups) != 0}">
			<c:choose>
			<c:when test="${systemUser.role == 'admin'}">
			<c:if test="${groups != null}">			    
			    <select style="margin-left: 20px">
			    	<c:url value="/private/participantList.html?page=1" var="url_gen"></c:url>
					<option value="empty" onClick="window.location.href='${url_gen}'"></option>
						<c:if test="${id_group == null}">selected</c:if>
					
					<c:forEach items="${groups}" var="group">
						<c:url
							value="/private/participantList.html?action=listRecord&id_group=${group.id}"
							var="url" />
						<option value="${group.name}" onClick="location='${url}'" 
						<c:if test="${group.id == id_group}">selected</c:if>>${group.name}</option>
					</c:forEach>
				</select>
			</c:if>
		
			
			<br></br>
			<br>
			
			<c:choose>
				<c:when test="${id_group > 0}">
						<h6 style="margin-left: 20px">Max ${groupMaxNumber} participants - Actual participants: ${group.actualParticipantNumber}</h6>		
				</c:when>				
			</c:choose>
			<c:choose>				
				<c:when test="${id_group != null && (nrEnrolledParticipant >= groupMaxNumber)}">
				<h5 class="alert" style="margin-left: 20px">ATTENTION: Max nr. of enrolled people reached for this group!</h5>
				</c:when>				
			</c:choose>
				
			<c:if test="${((fn:length(groups) != 0) && (id_group != null))}">
					<a class="button-2" href="participant.jsp?action=insert&id_group=${id_group}" style="margin-left: 20px">Add Participant</a>
				</c:if>
				<c:if test="${((fn:length(groups) != 0) && (id_group != null))}">
					<a class="button-2" href="downloadAllBadges style="margin-left: 20px">Badges</a>
				</c:if>

			<!-- TABLES -->
				<table id="box-table-a">
				<c:if test="${fn:length(records) != 0}">
					<thead>
						<tr>
							<th scope="col">Last Name</th>
							<th scope="col">First Name</th>
							<th scope="col">Group</th>
							<th scope="col">Photo</th>
							<th scope="col">ID Photo</th>
							<th scope="col" colspan=3>Action</th>
						</tr>
					</thead>
				</c:if>
					<tbody>
						<c:forEach items="${records}" var="record">
							<tr>
								<td>${record.lname}</td>
								<td>${record.fname}</td>
								<td>${record.groupName}</td>
								<td>${record.photo}</td>
								<td>${record.document}</td>
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
				
		</c:when>
		<c:otherwise>
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
				
		<c:if test="${!blocked}">	
			<!-- 	<c:if test="${((nrEnrolledParticipant < groupMaxNumber) && (id_group > 0))}">
					<a class="button-2" href="participant.jsp?action=insert&id_group=${id_group}">Add Participant</a>
				</c:if> 
				 <c:if test="${systemUser.role == 'group_manager' && !blocked}">
					<a class="button-2" href="participant.jsp?action=conclude&id_group=${id_group}">Conclude</a>
				</c:if> --> 
				<a class="button-2" href="downloadDocs?id_group=${id_group}">Download all docs</a>
		</c:if>

			<!-- TABLES -->
				<table id="box-table-a">
				<c:if test="${fn:length(records) != 0}">
					<thead>
						<tr>
							<th scope="col">Last Name</th>
							<th scope="col">First Name</th>
							<th scope="col">Group</th>
							<th scope="col">Photo</th>
							<th scope="col">ID Photo</th>
							<th scope="col" colspan=3>Action</th>
						</tr>
					</thead>
				</c:if>
					<tbody>
						<c:forEach items="${records}" var="record">
							<tr>
								<td>${record.lname}</td>
								<td>${record.fname}</td>
								<td>${record.groupName}</td>
								<td>${record.photo}</td>
								<td>${record.document}</td>
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
		</c:otherwise>
		</c:choose>
		</c:if>
			</div>						
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>