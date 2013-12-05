function checkDate(field) {
	var allowBlank = true; 
	var minYear = 1902; 
	var maxYear = (new Date()).getFullYear(); 
	var errorMsg = ""; 
	// regular expression to match required date format EU format yyyy/mm/dd
	re = /^(\d{4})\/(\d{1,2})\/(\d{1,2})$/;
	
    /* EU */
	if(field.value != '') {
	      if(regs = field.value.match(re)) {
	        if(regs[1] < minYear || regs[1] > maxYear) {
	        	errorMsg = "Invalid value for year: " + regs[1] + " - must be between " + minYear + " and " + maxYear;
	        } else if(regs[2] < 1 || regs[2] > 12) {
	          errorMsg = "Invalid value for month: " + regs[2];
	        } else if(regs[3] < 1 || regs[3] > 31) {
	        		errorMsg = "Invalid value for day: " + regs[3];
	        }
	      } else {
	        errorMsg = "Invalid date format: " + field.value;
	      }
	    } else if(!allowBlank) {
	      errorMsg = "Empty date not allowed!";
	    }

	    if(errorMsg != "") {
	      alert(errorMsg);
	      field.focus();
	      return false;
	    }
	return true; 
}

function checkForm(form) {
	
	return checkDate(form.start) && checkDate(form.end) && checkDate(form.enrollment_start) && checkDate(form.enrollment_end);
}

/*<form method="POST" action="${act}" name="frmAddEvent" id="form" onsubmit="checkForm(this); return false;">*/