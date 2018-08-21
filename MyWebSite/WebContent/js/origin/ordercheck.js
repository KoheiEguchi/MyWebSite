//配送時の商品チェック漏れ点検
function orderCheck(){
	var f = document.orderForm.orderItem;
	var ok = 0;

	for(var i = 0; i < f.length; i++){
		if(f[i].checked){
			ok++;
		}
	}

	if(ok != f.length){
		if(confirm("チェックされていない商品がありますがよろしいですか？")){
			return true;
		}else{
			return false;
		}
	}
}