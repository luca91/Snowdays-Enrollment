<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>EMS - Users Management</title>
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
		<h3 class="htabs">Users management</h3>
		<br><br>
		<!-- <div class="table-buttons">
			<a class="button-2" href="user.jsp?action=insert" id="addevent">Add User</a>
		</div> -->
		<table id="box-table-a">
			<thead>
				<tr>			
					<th scope="col">User Id</th>
					<th scope="col">User Role</th>
					<th scope="col">First Name</th>
					<th scope="col">Last Name</th>
					<th scope="col">Email</th>
					<th scope="col" colspan=3 style="text-align: center">Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if
					test="${systemUser.role == 'admin'}">
					<c:forEach items="${users}" var="user">
						<tr>
							<td><c:out value="${user.id}"></c:out></td>
							<td><c:out value="${user.role}"></c:out></td>
							<td><c:out value="${user.fname}"></c:out></td>
							<td><c:out value="${user.lname}"></c:out></td>
							<td><c:out value="${user.email}"></c:out></td>
							<td>
								<a href="<c:url value='/private/user.jsp?action=edit&id=${user.id}'/>">Update</a>
							</td>
							<td>
	                    		<c:if test="${user.email != systemUser.email }">
									<a href="<c:url value='/private/userDelete?action=delete&id=${user.id}'/>" onclick="return confirmDelete();">Delete</a>
								</c:if>
	                    	<td>					
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<p><a class="button-2" href="user.jsp?action=insert" id="addevent">Add User</a></p>
		<hr>		
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>	
<!-- NO </body> -->
</html>