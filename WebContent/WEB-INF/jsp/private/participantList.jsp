<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>EMS - Participants Management</title>
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
		<h3 class="htabs">Participants management</h3> 
			<!-- 
			<c:choose>
				<c:when test="${id_group != 0}">
						<h5><c:if test="${id_group != 0 }">Group: ${id_group}</c:if></h5><br>
						<h3>Max #${group.max_group_number} participants</h3>	
				</c:when>
			</c:choose>
			 -->		
		<c:choose>
			<c:when test="${systemUser.role == 'admin'}">
			<c:if test="${groups != null}">			    
			    <select>
					<option id="option-sel-sel" selected="selected">Choose a Group:</option>
					<c:forEach items="${groups}" var="group">
						<c:url
							value="/private/participantList.html?action=listRecord&id_group=${group.id}"
							var="url" />
						<option value="${group.name}" onClick="window.location.href='${url}'">${group.name}</option>
					</c:forEach>
				</select>
				<c:if test="${id_group != 0}">
						<script>$("#option-sel-sel").text(function () {
				   			return $(this).text().replace("Choose a Group:", 'Participants for ${group_name}'); });
						</script>
					</c:if>	
			</c:if>
			</c:when>
			
			<c:when test="${systemUser.role == 'event_mng'}">
			<c:if test="${groups != null}">			    
			    <select>
					<option id="option-sel-sel" selected="selected">Choose a Group:</option>
					<c:forEach items="${groups}" var="group">
						<c:url
							value="/private/participantList.html?action=listRecord&id_group=${group.id}"
							var="url" />
						<option value="${group.name}" onClick="window.location.href='${url}'">${group.name}</option>
					</c:forEach>
				</select>
				<c:if test="${id_group != 0}">
						<script>$("#option-sel-sel").text(function () {
				   			return $(this).text().replace("Choose a Group:", 'Participants for ${group_name}'); });
						</script>
					</c:if>	
			</c:if>
			</c:when>
			
		</c:choose>
			
			<br></br>
			<br>
			
			<c:choose>
				<c:when test="${id_group != 0}">
						<h6>Max #${group.max_group_number} participants</h6>		
				</c:when>				
			</c:choose>
			<c:choose>				
				<c:when test="${id_group != 0 && (nrEnrolledParticipant >= group.max_group_number)}">
				<h5 class="alert">ATTENTION: Max nr. of enrolled people reached for this group!</h5>
				</c:when>				
			</c:choose>
				
		

			<!-- TABLES -->
				<table id="box-table-a">
					<thead>
					<!-- 
					<c:choose>
							<c:when test="${id_group != 0}">					
								<thead>
									<tr>
										<th scope="col" colspan=3>
										
													Max #${group.max_group_number} participants		
											
										</th>
									</tr>
							</c:when>
					</c:choose>
					 -->						
						<tr>
							<th scope="col">Participant Id</th>
							<th scope="col">Group Id</th>
							<th scope="col">First Name</th>
							<th scope="col">Last Name</th>
							<th scope="col">Email</th>
							<th scope="col">Date of birth</th>
							<th scope="col">Registration Date</th>
							<th scope="col">Approved</th>
							<!-- <th scope="col">Blocked</th> -->
							<th scope="col" colspan=3>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${records}" var="record">
							<tr>
								<td>${record.id}</td>
								<td>${record.id_group}</td>
								<td>${record.fname}</td>
								<td>${record.lname}</td>
								<td>${record.email}</td>
								<td>${record.date_of_birth}</td>
								<td>${record.registration_date}</td>
								<td>${record.approved}</td>
								<!-- <td>${record.blocked}</td> -->
								<td><a
									href="<c:url value='/private/participant.jsp?action=edit&id=${record.id}&id_group=${id_group}'/>">Update</a></td>
								<td><a
									href="<c:url value='/private/participantDelete?action=delete&id=${record.id}&id_group=${id_group}'/>"
									onclick="return confirmDelete();">Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>					
				</table>
				<!-- APPROVE ALL / DISAPPROVE ALL -->
			<div class="table-buttons">
				
				<form method="POST" action="${act}" name="frmApproveParticipan">
				<c:if test="${id_group != 0 && (nrEnrolledParticipant < group.max_group_number)}">
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
						
				</form><br><br>
				<!-- 	
				<c:if test="${id_group != 0 && (nrEnrolledParticipant < group.max_group_number)}">
					<a class="button-2" href="participant.jsp?action=insert&id_group=${id_group}">Add Participant</a>
					<br><br>
					<c:set var="act">
						<c:url
							value="/private/participantInvite?action=invite&id_group=${id_group}" />
					</c:set> -->
					<form method="POST" action="${act}" id="frmInviteParticipan"
						name="frmInviteParticipan"
						onsubmit="return confirmSend(frmInviteParticipan.elements['listTo'].value);">
						<fieldset>
							<legend>Invite participants (separate the addresses with a  " ; ")</legend>
							Email : <input type="text" name="listTo" style="width:300px;"/>
							<c:if test="${showCount == 'y' }">
						  	 		${count} email sent!
						  	 </c:if>
							<br><br>
							<input type="submit" value="Invite" class="input" />
							<input type="reset" value="Reset" class="input" />
							
						</fieldset>
					</form>
				</c:if>				
			</div>						
		<hr>
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>