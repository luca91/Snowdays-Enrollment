function eventReqValidation(){
	//variables associated with the fields on the request form   eventreqform
	var eventname = document.eventRequest.eventname.value;
	var description = document.eventRequest.description.value;
	var location = document.eventRequest.location.value;
    //var email = document.eventRequest.email.value;
    // regular e-mail expression
    var email_reg_exp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-]{2,})+\.)+([a-zA-Z0-9]{2,})+$/;
	
    //controll on EventName
    if ((eventname == "") || (eventname == "undefined")) {
    	alert("The field Event Name is required.");
    	document.eventRequest.eventname.focus();
    	return false;
    }
    
    //controll on Description
    /*
    else if ((description == "") || (description == "undefined") || (description.IndexOf("Description of the event") != (-1))) {
    	alert("The field Description is required.");
    	document.eventRequest.description.focus();
    	return false;
    }*/
    
    //controll on Location
    else if ((location == "") || (location == "undefined")) {
    	alert("The field Location is required.");
    	document.eventRequest.location.focus();
    	return false;
    }
    
    //controll on Start Date format
    else if (document.eventRequest.startdate.value.substring(2,3) != "/" ||
            document.eventRequest.startdate.value.substring(5,6) != "/" ||
            isNaN(document.eventRequest.startdate.value.substring(0,2)) ||
            isNaN(document.eventRequest.startdate.value.substring(3,5)) ||
            isNaN(document.eventRequest.startdate.value.substring(6,10))) {
              alert("Insert the date in format gg/mm/aaaa");
               document.eventRequest.startdate.value = "";
               document.eventRequest.startdate.focus();
               return false; 
    }
    else if (document.eventRequest.startdate.value.substring(0,2) > 31) {
        alert("Impossible to use value bigger than 31 for days");
        document.eventRequest.startdate.select();
        return false;
     }
     else if (document.eventRequest.startdate.value.substring(3,5) > 12) {
        alert("Impossible to use value bigger than 12 for months");
        document.eventRequest.startdate.value = "";
        document.eventRequest.startdate.focus();
        return false;
     }
     else if (document.eventRequest.startdate.value.substring(6,10) < 2013) {
        alert("Impossible to use value smaller than 2013 for the year");
        document.eventRequest.startdate.value = "";
        document.eventRequest.startdate.focus();
        return false;
     }
    //controllo su mese con 28,30,31 correttamente (no settembre con 31!)
    //controllo su BISESTILE
    //    END not before start.
    
    
    //controll on END DATE
    
    //controll on START ENROLLMENT
    //start time ?
    
    //controll on END ENROLLMENT
    //end time ?
    
    
  //send the module
     else {
        document.eventRequest.action = "";
        document.eventRequest.submit();
     }
    
}