<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>EMS - Badges Management</title>
<meta charset="utf-8">
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
	<p>
		<c:choose>
			<c:when test="${systemUser.role == 'admin'}">
				<c:if test="${groups != null }">
					<select>
						<option id="option-sel-sel" selected="selected">Choose a Group:</option>
						<c:forEach items="${groups}" var="group">
							<c:url value="/private/badgeList.html?action=listRecord&id_group=${group.id}&id_event=${group.id_event}" var="url"/>
							<option value="${url}" onClick="window.location.href='${url}'">${group.name}</option>
						</c:forEach>
					</select>
					<c:if test="${id_group != 0}">
						<script>$("#option-sel-sel").text(function () {
				   			return $(this).text().replace("Choose a Group:", 'Badges for ${group_name}'); });
						</script>
					</c:if>	
				</c:if>
			</c:when>
			
			<c:when test="${systemUser.role == 'event_mng'}">
				<c:if test="${groups != null }">
					<select>
						<option id="option-sel-sel" selected="selected">Choose a Group:</option>
						<c:forEach items="${groups}" var="group">
							<c:url value="/private/badgeList.html?action=listRecord&id_group=${group.id}&id_event=${group.id_event}" var="url"/>
							<option value="${url}" onClick="window.location.href='${url}'">${group.name}</option>
						</c:forEach>
					</select>
					<c:if test="${id_group != 0}">
						<script>$("#option-sel-sel").text(function () {
				   			return $(this).text().replace("Choose a Group:", 'Badges for ${group_name}'); });
						</script>
					</c:if>	
				</c:if>
			</c:when>
			
		</c:choose>
		</p><br><br>
			
		<!-- TABLE -->
			<c:set var="act">
			<c:url value="/private/downloadBadge?action=download&id_participant=${id}" />
			</c:set>
			
			<form method="POST" action="${act}" name="downloadBadge">
				<table id="box-table-a">
					<thead>
						<tr>
							<th scope="col">Participant</th>
							<th scope="col">First Name</th>
							<th scope="col">Last Name</th>
							<th scope="col">Event</th>
							<th scope="col">Badge</th>
						</tr>
					</thead>
					
					<tbody>					
						<c:forEach items="${records}" var="record">
						<tr>
							<td>${record.id}</td>							
							<td>${record.fname}</td>
							<td>${record.lname}</td>
							<td>${id_event}</td>
							<td><a href="<c:url value='/private/downloadBadge?action=download&id=${record.id}&id_group=${id_group}&event_id=${id_event}'/>">Generate</a></td>
						</tr>
						</c:forEach>					
					</tbody>
				</table>
			</form>			
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>