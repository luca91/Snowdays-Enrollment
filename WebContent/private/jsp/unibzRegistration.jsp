<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<%
int timeout = session.getMaxInactiveInterval();
response.setHeader("Refresh", timeout + "; URL = /snowdays-enrollment/logout.html");
%>
<html>
<head>
<title>Bolzano Snowdays - UNIBZ</title>
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
<script type="text/javascript">
	function updateEmail() {
		var name = document.getElementById("name").value;
		var surname =  document.getElementById("surname").value;
		var firstPart = name + "." + surname + "@";
		document.getElementById("email").value = firstPart;
		var option =  document.getElementById("server");
		var selectedFaculty = option.options[option.selectedIndex].value;
		var server = "";
		switch(selectedFaculty){
		case "computerscience":
			server = "stud-inf.unibz.it";
			break;
		case "design":
			server = "design-art.unibz.it";
			break;
		case "sciencetechnology":
			server = "natec.unibz.it";
			break;
		case "economics":
			server = "economics.unibz.it";
			break;
		case "education":
			server ="education.unibz.it";
			break;
		}
		firstPart = name + "." + surname + "@" + server;
		document.getElementById("email").value = firstPart;
	}
	
	function generateMessage(){
		var email = document.getElementById("email").value;
		var name = document.getElementById("name").value;
		var o =  document.getElementById("group");
		var group = o.options[o.selectedIndex].value;
		var id = "";
		switch(group){
		case "UNIBZ":
			id = document.getElementById("unibz").value;
			break;
		case "Alumni":
			id = document.getElementById("alumni").value;
			break;
		case "Host":
			id = document.getElementById("host").value;
			break;
		}
		var link = "http://scub.unibz.it:8080/snowdays-enrollment/public/participantInt.jsp?id_group=" + id + "&email=" + email+ "\n";
		document.getElementById("link").value = link;
		var message = "Dear " + name + ",\n"+ "\nplease follow the link below to complete your registration to Snowdays 2014:\n\n" + link + "\n\nThe Snowdays Team";
		document.getElementById("message").value = message;
	}
	
</script>
</head>
<body id="page5">
<div class="bg">
	<div class="main">
	<!-- TOPHEAD --><c:import url="inc/tophead.jsp"/>
	<!-- CONTENT -->
		<h3 class="htabs">Registrations UNIBZ</h3> 
			<br></br><br>	
			
			<c:url value="addLink" var="act"></c:url>
		<form method="POST" action="${act}" name="frmAddParticipan">
		 
				<input type="hidden" name="unibz" id="unibz" value="${unibz}"/>
				<input type="hidden" name="alumni" id="alumni" value="${alumni}"/>
				<input type="hidden" name="host" id="host" value="${host}"/>
				<input type="hidden" name="link" id="link" value="${link}"/><br><br><br>
				
				<input type="text" name="name" id="name" value="${record.name}" placeholder="Name" style="margin-left: 20px"/>
				<input type="text" name="surname" id="surname" value="${record.surname}" placeholder="Surname"/> <br><br>    
					    <select name="faculty" id="server" style="margin-left: 20px">
					    	<option value=""></option>
					    	<option value="computerscience">Computer Science</option>
					    	<option value="design">Design</option>
					    	<option value="sciencetechnology">Science and Technology</option>
					    	<option value="economics">Economics</option>
					    	<option value="education">Education</option>
						</select>
					 <br><br><br>
					<select name="group" id="group" style="margin-left: 20px">
					<c:forEach items="${groups}" var="g">
	        			<option value="${g}" <c:if test="${selGroup == g}">selected</c:if>>${g}		        	
		       		 </c:forEach>
					</select><br><br><br>
				<input type="text" name="email" id="email" value="${email}" placeholder="Email" style="width:350px; margin-left: 20px""/> <br><br><br>
				<input type="button" value="Generate" class="input" onClick="javascript:updateEmail();return false;" style="margin-left: 20px"/>
				<input type="button" value="Message" class="input" onClick="javascript:generateMessage();return false;"/>
				<input type="submit" value="Add" class="input" />
				<a class="button-2" href="unibzRegistrations.html">Reset</a><br><br><br>
				<textarea name="messages" id="message" style="width:700px; height:200px; margin-left: 20px" readonly></textarea>  <br><br><br>
				<!-- <input type="submit" value="Send" class="input" />  -->
					</form> 
					<!-- TABLES -->
				<table id="box-table-a">
				<c:if test="${fn:length(records) != 0}">
					<thead>
						<tr>
							<th scope="col">Position</th>
							<th scope="col">Name</th>
							<th scope="col">Surname</th>
							<th scope="col">Email</th>
							<th scope="col">Status</th>
							<th scope="col">Group</th>
							<th scope="col" colspan=3>Action</th>
						</tr>
					</thead>
				</c:if>
					<tbody>
						<c:forEach items="${records}" var="record">
							<tr>
								<td>${record.position}</td>
								<td>${record.name}</td>
								<td>${record.surname}</td>
								<td>${record.email}</td>
								<td>${record.status}</td>
								<td>${record.group}</td>
								<td>
								<a href="<c:url value='/private/show?action=show&email=${record.email}'/>">Show</a></td>
								<td><a href="<c:url value='/private/unibzRegistrations.html?action=delete&email=${record.email}'/>"onclick="return confirmDelete();">Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>					
				</table>
		 	
		
					
	<!-- BOTTOM --><c:import url="inc/bottom.jsp"/>
</html>