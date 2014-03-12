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
<title>Bolzano Snowdays-Badges</title>
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
	<h3 class="htabs">Badges</h3>
	<a class="button-2" href='downloadAllBadges?type=participant'>Participant</a>
	<a class="button-2" href='downloadAllBadges?type=staff'>Staff</a>
	<a class="button-2" href='downloadAllBadges?type=host'>Host</a>
	<a class="button-2" href='downloadAllBadges?type=sponsor'>Sponsor</a>
	<!-- <c:if test="${fn:length(groups) != 0}">
		<c:choose>
			<c:when test="${systemUser.role == 'admin'}">
				<c:if test="${groups != null }">
					<select>
						<c:url value="/private/badgeList.html" var="url_gen"></c:url>
						<option value="empty" onClick="window.location.href='${url_gen}'"></option>
							<c:if test="${id_group == null}">selected</c:if>
						<c:forEach items="${groups}" var="group">
							<c:url value="/private/badgeList.html?action=listRecord&id_group=${group.id}" var="url"/>
							<option value="${group.name}" onClick="window.location.href='${url}'"
							<c:if test="${group.id == id_group}">selected</c:if>>${group.name}</option>
						</c:forEach>
					</select>
				</c:if>
			</c:when>
		</c:choose>
	</c:if> -->
		<br><br>
			
		<!-- TABLE -->
			<c:set var="act">
			<c:url value="/private/downloadBadge?action=download&id_participant=${id}" />
			</c:set>
			
			<form method="POST" action="${act}" name="downloadBadge">
				<table id="box-table-a">
				<c:if test="${fn:length(records) != 0}">
					<thead>
						<tr>
							<th scope="col">First Name</th>
							<th scope="col">Last Name</th>
							<th scope="col">Badge</th>
						</tr>
					</thead>
					</c:if>
					<tbody>					
						<c:forEach items="${records}" var="record">
						<tr>						
							<td>${record.fname}</td>
							<td>${record.lname}</td>
							<td><a href="<c:url value='/private/downloadBadge?action=download&id=${record.id}&id_group=${record.id_group}'/>">Generate</a></td>
						</tr>
						</c:forEach>					
					</tbody>
				</table>
			</form>			
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>