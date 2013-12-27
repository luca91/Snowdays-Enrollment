<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
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
<link rel="stylesheet" href="css/forms.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/datepicker.css" type="text/css" media="screen">
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
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="js/datepickform.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/jquery.validate.js"></script>
<script type="text/javascript" src="js/jquerymyform.js"></script>
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
	<!-- TOPHEAD -->
	<c:import url="inc/tophead.jsp"/>
	<!-- CONTENT -->
	<c:set var="act">
		<c:url value="/private/eventAdd?action=listRecord" /> 
	</c:set>
	
	<div id="stylized" class="myform">
		<form method="POST" action="${act}" name="frmAddEvent" id="form" onsubmit="checkForm(this); return false;">    	    
	    	<h3>Event form</h3>
			<p>Edit the data for this event</p>
	    	<!-- HIDDEN --><!-- <label>Event ID : <input type="text" readonly="readonly" name="id"
	            value="${record.id}" /> - TO BE HIDDEN</span></label> -->
	        <input type="hidden" readonly="readonly" name="id" value="${record.id}" /> 
	        
	        <label>Manager ID : 
		        <span class="small">Choose the manager for this group</span>
		    </label>	         
	         
            <c:choose>
               	<c:when test="${record.id_manager != null  && sessionScope.systemUser.role == 'admin'}">
            		<!-- UPDATE -->
            		<select name="id_manager">
           				<c:forEach items="${listOfEvent_mng}" var="options">	               
                			<option value="${options.id }" 
                				<c:if test="${options.id == record.id_manager }">selected</c:if> 
                			>${options.id } - ${options.fname} ${options.lname }</option>
            			</c:forEach>
            		</select>
            	</c:when>
               	<c:when test="${record.id_manager != null && sessionScope.systemUser.role == 'event_mng'}">
            		<!-- UPDATE -->
            		<input type="hidden" name="id_manager" value="${record.id_manager}"/>
            	</c:when>	            	
            	<c:when test="${sessionScope.systemUser.role == 'event_mng' }">
            		<!-- INSERT --><!-- HIDDEN -->
            		<input type="hidden" name="id_manager" value="${sessionScope.systemUser.id}" readonly="readonly" />            		
            	</c:when>
            	<c:otherwise>
            		<!-- INSERT -->
               		<select name="id_manager">
           				<c:forEach items="${listOfEvent_mng}" var="record">	               
                			<option value="${record.id }">${record.id } - ${record.fname} ${record.lname }</option>
            			</c:forEach>
            		</select>
            		<!-- <input type="text" name="id_manager" value="" /> -->
            	</c:otherwise>
            </c:choose>
	        <br><br><br>     
	        
	        <label>Event Name : 
		    <span class="small">Name of the event</span>
		    </label>
		    <input type="text" name="name"	value="${record.name}" /><br><br><br> 
		    
		    <label>Description : 
		    <span class="small">Short descr. (max 255 ch)</span>
		    </label>		    
		    <input type="text" name="description" value="${record.description}" /><br><br><br>		    
		    
		    <label>Start of the event: 
		    <span class="small">Date of beginning of the event</span>
		    </label>
		    <input type="text" name="start"	value="${record.start}" placeholder="yyyy/mm/dd" id="datepicker1"/><br><br><br><br>
		    <!-- <input type="text" name="start"	value="${record.start}" placeholder="yyyy/mm/dd" id="datepicker1" /><br><br><br><br> -->
		    
		    <label>End of the event: 
		    <span class="small">Date of ending of the event</span>
		    </label>
		    <input type="text" name="end" value="${record.end}" placeholder="yyyy/mm/dd" id="datepicker2"/><br><br><br>
		    
		    <label>Enrollment start : 
		    <span class="small">Date when enrollment opens</span>
		    </label>
		    <input type="text" name="enrollment_start" value="${record.enrollment_start}" placeholder="yyyy/mm/dd" id="datepicker3"/><br><br><br>
		    
		    <label>Enrollment end : 
		    <span class="small">Deadline for enrollment</span>
		    </label>
		    <input type="text" name="enrollment_end" value="${record.enrollment_end}" placeholder="yyyy/mm/dd" id="datepicker4"/><br><br><br>
		    
	        <!-- BUTTONS -->             
	        <input type="submit" value="Submit" class="input" />
	        <input type="button" value="Back" onClick="history.go(-1);return true;" class="input" />
	        <c:if test="${param.id eq null }">
        		<input type="reset" value="Reset" class="input" id="reset" />
       		</c:if><br><br><br>
	    </form>
	</div>
	<!-- BOTTOM -->
	<c:import url="inc/bottom.jsp"/>	
</html>