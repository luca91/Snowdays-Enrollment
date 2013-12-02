$(document).ready(function(){ 
	  $("#addevent").click(function(){
		  	$("#ajax-parse").load("event.jsp?action=insert");
	  });
	  
	  $("#updateevent").click(function(){
		  	$("#ajax-parse").load("event.jsp?action=edit&id=${record.id}");
	  });
	  
});
