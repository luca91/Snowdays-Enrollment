<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:choose>
	<c:when test="${systemUser.role == 'admin' }">
		<% System.out.println("admin"); %>
		<li><a href='<c:url value="/private/index.html"/>'>Home</a></li>
		<li><a href='userList.html' id="listusers">Users</a></li>
		<li><a href='groupList.html' id="listgroups">Groups</a></li>
		<li><a href='participantList.html' id="listparticip">Participants</a></li>
		<li><a href='badgeList.html' id="listbadges">Badges</a></li>
		<li><a href='externalsRegistrations.html' id="extregistrations">Registration Externals</a></li>
		<li><a href='unibzRegistration.html' id="extregistration">Registration UniBz</a></li>
	</c:when>
	<c:when test="${systemUser.role == 'group_mng' }">
		<% System.out.println("group_mng"); %>
		<li><a href='<c:url value="/private/index.html"/>'>Home</a></li>
		<li><a href='participantList.html' id="listparticip">Participants Management</a></li>
	</c:when>
</c:choose>