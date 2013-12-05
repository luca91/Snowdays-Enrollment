$(document).ready(function(){
	  $("#btn1").click(function(){
	    $("#ajax-parse").append("<b>Appended text</b>.");
	  });
	  /*
	  $(".p2").click(function(){
	  	$("#ajax-parse").load("eventList.html");
	  });
	  
	  $(".p3").click(function(){
	  	$("#ajax-parse").empty();
	  });*/	  
	  
	  $("#listusers").click(function(){
	  	$("#ajax-parse").load("userList.html");
	  });
	  
	  $("#listevents").click(function(){
	  	$("#ajax-parse").load("eventList.html");
	  });
	  
	  $("#listgroups").click(function(){
	  	$("#ajax-parse").load("groupList.html");
	  });
	  
	  $("#listparticip").click(function(){
	  	$("#ajax-parse").load("participantList.html");
	  });
	  
	  
	  $("#listbadges").click(function(){
	  	$("#ajax-parse").load("badgeList.html");
	  });
	  
	  $("#addusers").click(function(){
		  	$("#ajax-parse").load("event.jsp?action=insert");
	  });
});