//submitの二重送信防止
function doubleSubmit(){
	var submit = document.getElementById("btnSubmit");

	if(submit.disabled){
		return false;
	}else{
		submit.disabled = true;
		return true;
	}
}