function confirmDelete(){
	var msg ="Are you sure to delete the record?";
	if(confirm(msg)){
		return true;
	}
	else {
		return false;
	}
}

function confirmSend(list){
	var a = new Array();
	a = list.split(";");
	
	var msg ="You are sending invitation via mail to the following addresses: \n\n";
	
	for(var i=0;i<a.length;i++){
		
		msg += "- " + a[i] + " \n";
		//alert(a[i]);
    }
	
	if(confirm(msg)){
		return true;
	}
	else {
		document.getElementById("frmInviteParticipan").reset();
		return false;
	}
}

function confirmDelete(){
	var msg ="Are you sure to delete the record?";
	if(confirm(msg)){
		return true;
	}
	else {
		return false;
	}
}