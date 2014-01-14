<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:choose>
	<c:when test="${systemUser.role == 'admin' }">
		<li><a href='<c:url value="/private/index.html"/>'>Home</a></li>
		<li><a href='userList.html' id="listusers">Users</a></li>
		<li><a href='groupList.html' id="listgroups">Groups</a></li>
		<li><a href='participantList.html' id="listparticip">Participants</a></li>
		<li><a href='badgeList.html' id="listbadges">Badges</a></li>
		<li><a href='registrations.html' id="registrations">Registrations</a></li>
		<li><a href='settings.html' id="settings">Settings</a></li>
	</c:when>
	<c:when test="${systemUser.role == 'group_manager' }">
		<li><a href='<c:url value="/private/index.html"/>'>Home</a></li>
		<li class="class-item"><a href='participantList.html' id="listparticip">Participants</a></li>
	</c:when>
</c:choose>