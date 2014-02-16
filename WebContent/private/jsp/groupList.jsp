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
<title>Bolzano Snowdays-Groups </title>
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
		<h3 class="htabs" style="margin-left: 20px">Groups</h3>
		<!-- DROPDOWN BOX SELECTION -->
		<!-- TABLE --><br><br>
		<c:if test="${status == 'blocked' && systemUser.role == 'admin'}">
			<!-- <input type="button" value="Unblock" name="blockButton" onClick="location.href='groupStatus?action=unblock'"/>  -->
			<a class="button-2" href="groupStatus?action=unblock" style="margin-left: 20px">Unblock</a>
		</c:if>
		<c:if test="${status == 'unblocked' && systemUser.role == 'admin'}">
			<!-- <input type="button" value="Block" name="blockButton" onClick="location.href='groupStatus?action=block'"/>  -->
			<a class="button-2" href="groupStatus?action=block" style="margin-left: 20px">Block</a>
		</c:if> 
		<c:if test="${systemUser.role == 'admin' }">
				<a class="button-2" href="group.jsp?action=insert">Add Group</a>
		</c:if>
		<table id="box-table-a">
		<c:if test="${fn:length(records) != 0}">
			<thead>
				<tr>			
					<th scope="col">Name</th>
					<th scope="col">Referent</th>
					<th scope="col">Max participants</th>
					<th scope="col">Actual participants</th>
					<th scope="col">Country</th>
					<th scope="col">Saturday</th>
					<th scope="col">Blocked</th>
					<th scope="col" colspan="5" style="text-align: center;">Action</th>
				</tr>
			</thead>
			</c:if>
			<tbody>
				<c:if test="${systemUser.role == 'admin'}">
					<c:forEach items="${records}" var="record">
						<tr>
							<td>${record.name}</td>
							<td>${record.groupReferentData}</td>
							<td>${record.groupMaxNumber}</td>
							<td>${record.actualParticipantNumber}
							<td>${record.country}</td>
							<td>${record.snowvolley}</td>
							<td>${record.isBlocked}</td>				
							<td><c:if
									test="${systemUser.role == 'admin' }">
									<a
										href="<c:url value='/private/group.jsp?action=edit&id=${record.id}'/>">Edit</a>
								</c:if></td>
							<td><c:if
									test="${systemUser.role == 'admin'}">
									<a
										href="<c:url value='/private/groupDelete?action=delete&id=${record.id}'/>"
										onclick="return confirmDelete();">Delete</a>
								</c:if></td>
							<td><a
								href="<c:url value='/private/participantList.html?action=listRecord&id_group=${record.id}'/>">Participants</a></td>
							<td><a href="<c:url value='/private/badge.jsp?action=listRecord&id_group=${record.id}'/>">Badges</a></td>
							<td><a href="<c:url value='/private/docsDownload?action=&id_group=${record.id}'/>">Docs</a></td>
						</tr>
					</c:forEach>			
				</c:if>
			</tbody>
		</table>
		<div class="table-buttons">
		</div>
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>