$(document).ready(function(){
	
	$("#form").submit(function() {
		$("#form").validate({	
			
	    	/* conditions to safisfy*/
	        rules:
	        {
	            'name': required,
	            'description': required,
	            'start':
	            {
	            	required: true,
	            	date: true
	            },
	            'end':
	            {
	            	required: true,
	            	date: true
	            },
	            'enrollment_start':
	            {
	            	required: true,
	            	date: true
	            },
	            'enrollment_end':
	            {
	            	required: true,
	            	date: true
	            }
	        },
	        /* messages to display if not satisfied */
	        messages:
	        {
	            'name': {required: " The event name is required!"},
	            'description': {required: " Some description is required!"},
	            'start': {required: " Required date!", date: " A date format must be inputed"},
	            'end': {required: " Required date!", date: " A date format must be inputed"},
	            'enrollment_start': {required: " Required date!", date: " A date format must be inputed"},
	            'enrollment_end': {required: " Required date!", date: " A date format must be inputed"}
	        }	        
	    });
	});
});



/*
OLD
$(document).ready(function(){

		$('#frmAddEvent').ajaxForm( { beforeSubmit: validate } );

		$("#form").validate({			
	    	/* conditions to safisfy
	        rules:
	        {
	            name: "required",
	            description: "required",
	            start:
	            {
	            	required: true,
	            	date: true
	            },
	            end:
	            {
	            	required: true,
	            	date: true
	            },
	            enrollment_start:
	            {
	            	required: true,
	            	date: true
	            },
	            enrollment_end:
	            {
	            	required: true,
	            	date: true
	            }
	        },
	        messages to display if not satisfied 
	        message:
	        {
	            name: " The event name is required!",
	            description: " Some description is required!",
	            start: " Required date!",
	            end: " Required date!",
	            enrollment_start: " Required date!",
	            enrollment_end: " Required date!"
	        },
	        
	        beforeSubmit: function(arr, $form, options) {
	            $('#txt1').val('123456');
	            return true;
	        }
	    });
	    
});

*/