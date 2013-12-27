<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>EMS - Groups Management</title>
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
		<h3 class="htabs">Groups management</h3>
		<!-- DROPDOWN BOX SELECTION -->
		<p>
		<c:choose>
			<c:when test="${systemUser.role == 'admin'}">
				<c:if test="${events != null}">
					    <select>
						<option id="option-sel-sel" selected="selected">Choose an Event:</option>
						<c:forEach items="${events}" var="event">
							<c:url value="/private/groupList.html?action=listRecord&id_event=${event.id}" var="url" />
							<option value="${event.id}"	onClick="window.location.href='${url}'">${event.name}</option>
							<!-- SCRIPT HERE to work correctly? -->							
						</c:forEach>						
					</select>
					<c:if test="${param.id_event != 0}"><script>$("#option-sel-sel").text(function () {
				   			 return $(this).text().replace("Choose an Event:", 'Groups of ${event_name}'); 
								});
						</script>
					</c:if>				
				</c:if>
			</c:when>
			
			<c:when test="${systemUser.role == 'event_mng'}">
				<c:if test="${events != null}">
						<c:if test="${param.id_event != 0}">Groups for  ${name_event}<br/></c:if>
				</c:if>
			</c:when>
			<c:when test="${systemUser.role == 'group_mng' }">
				<h3>Yours Groups</h3><!-- ??? -->
			</c:when>
		</c:choose>
		</p>
		<!-- TABLE --><br><br>
		<!-- <br><br>		
		<div class="table-buttons">
		<c:if test="${(param.id_event != 0) &&(systemUser.role != 'group_mng'  || systemUser.role != 'admin') }">
				<a class="button-2" href="group.jsp?action=insert&id_event=${param.id_event}">Add Group</a>
		</c:if>
		</div>	 -->	
		<table id="box-table-a">
			<thead>
				<tr>			
					<th scope="col">Group Id</th>
					<th scope="col">Event Id</th>
					<th scope="col">Group Name</th>
					<th scope="col">Group referent Id</th>
					<th scope="col">Max Group Number</th>
					<!-- <th scope="col">Blocked</th> -->
					<th scope="col" colspan="4" style="text-align: center;">Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${systemUser.role == 'admin' || systemUser.role == 'event_mng'}">
					<c:forEach items="${records}" var="record">
						<tr>
							<td>${record.id}</td>
							<td>${record.id_event}</td>
							<td>${record.name}</td>
							<td>${record.id_group_referent}</td>
							<td>${record.max_group_number}</td>
							<!-- <td>${record.blocked}</td> -->					
							<td><c:if
									test="${(systemUser.role == 'admin') || (systemUser.role == 'event_mng') }">
									<a
										href="<c:url value='/private/group.jsp?action=edit&id=${record.id}&id_event=${record.id_event}'/>">Update</a>
								</c:if></td>
							<td><c:if
									test="${(systemUser.role == 'admin') || (systemUser.role == 'event_mng') }">
									<a
										href="<c:url value='/private/groupDelete?action=delete&id=${record.id}&id_event=${record.id_event}'/>"
										onclick="return confirmDelete();">Delete</a>
								</c:if></td>
							<td><a
								href="<c:url value='/private/participantList.html?action=listRecord&id_group=${record.id}'/>">Participants</a></td>
							<td><a href="<c:url value='/private/badge.jsp?action=listRecord&id_group=${record.id}&id_event=${record.id_event}'/>">Badges</a></td>
						</tr>
					</c:forEach>			
				</c:if>
			</tbody>
		</table>
		<hr>
		<div class="table-buttons">
		<c:if test="${(param.id_event != null) && (systemUser.role == 'event_mng'  || systemUser.role == 'admin') }">
				<a class="button-2" href="group.jsp?action=insert&id_event=${param.id_event}">Add Group</a>
		</c:if>
		</div>
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>