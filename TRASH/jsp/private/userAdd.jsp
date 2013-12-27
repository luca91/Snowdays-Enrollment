<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<h4>New User</h4><br>
<c:if test="${systemUser.role == 'admin'}">
<!-- ADD FORM -->
	<form id="contact-form" action="" method="post" enctype="multipart/form-data">
		<fieldset>
			<label><span class="text-form">ID:(not actually needed to be displayed)</span><input name="userid" type="text" /></label>
			<label><span class="text-form">User Role:</span>
				<select id="role">
	               <option value="event_mng">Event Manager</option>
	               <option value="group_mng">Group Referent</option>
	            </select>
	        </label>
			<label><span class="text-form">First Name:</span><input name="setfname" type="text" /></label>
			<label><span class="text-form">Last Name:</span><input name="setlname" type="text" /></label>
			<label><span class="text-form">Email:</span><input name="setemail" type="text" /></label> 
			<label><span class="text-form">Password:</span><input name="setpwd" type="text" /></label>
			<label><span class="text-form">Confirm Password:</span><input name="confirmpwd" type="text" /></label> 
		</fieldset>						
	</form><br>
	<a class="button-2" onClick="">Save user</a>
	<a class="button-2" onClick="">Cancel</a>				
<script type="text/javascript"> Cufon.now(); </script>
<!-- CHECK validity of fields -->
<!-- SEND valid data to -->
</c:if>