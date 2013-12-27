<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>EMS - Events Management</title>
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
		<!-- HEADER -->
						<!-- SINGLE INDIVIDUAL CONTENT -->		
						<c:import url="inc/tophead.jsp"/>				
					</div> 
					<h3 class="htabs">Events management</h3>
					<br><br>
					<!-- <div class="table-buttons">
						<a class="button-2" href="event.jsp?action=insert" id="addevent">Add Event</a>
					</div> -->									
					<table id="box-table-a">
						<thead>
							<tr>
								<th scope="col">Event Id</th>
								<th scope="col">Manager Id</th>
								<th scope="col">Event Name</th>
								<th scope="col">Description</th>
								<th scope="col">Start</th>
								<th scope="col">End</th>
								<th scope="col">Enrollment start</th>
								<th scope="col">Enrollment end</th>
								<th scope="col" colspan=4 style="text-align: center" class="limiting">Action</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${systemUser.role == 'admin' || systemUser.role == 'event_mng'}">
								<c:forEach items="${records}" var="record">
					                <tr>
					                    <td>${record.id}</td>
					                    <td>${record.id_manager}</td>
					                    <td>${record.name}</td>
					                    <td><p class="limiting">${record.description}</p></td>
					                    <td>${record.start}</td>
					                    <td>${record.end}</td>
					                    <td>${record.enrollment_start}</td>
					                    <td>${record.enrollment_end}</td>	                    	                    	                    	                    
					                    <td><a href="<c:url value='/private/event.jsp?action=edit&id=${record.id}'/>">Update</a></td>
					                    <td><a href="<c:url value='/private/eventDelete?action=delete&id=${record.id}'/>" onclick="return confirmDelete();">Delete</a></td>
					                	<td><a href="<c:url value='/private/groupList.html?action=listRecord&id_event=${record.id}'/>">Groups</a></td>
					                	<td><a href="javascript:void(window.open('<c:url value='participantsPrint.html?&id_event=${record.id}' />', 'Title', 'width=800,height=405, location=no, menubar=no, status=no,toolbar=no, scrollbars=no, resizable=no'))">Print</a>					                	
					                	</td>
					                </tr>
					            </c:forEach>
							</c:if>
						</tbody>
					</table>
					<p><div class="table-buttons">
						<a class="button-2" href="event.jsp?action=insert" id="addevent">Add Event</a>
					</div>
					</p>
					<hr>														
				</div>			
			</div>		
		</section>	
	</div>
</div>
<script type="text/javascript"> Cufon.now(); </script>
</body>
</html>