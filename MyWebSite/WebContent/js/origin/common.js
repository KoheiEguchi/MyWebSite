//多くのページでヘッダーとフッダーにリンクをつける
function hfLink(){
	document.getElementById("header").classList.remove("noLink");
	document.getElementById("footer").classList.remove("noLink");
}

//トップページでヘッダーにのみリンクをつける
function headerLink(){
	document.getElementById("header").classList.remove("noLink");
}

//入力中のテキストボックスの色を変える
function focusBox(control){
	control.style.backgroundColor = "#FFFFCC";
}

//入力していないテキストボックスの色を戻す
function blurBox(control){
	control.style.backgroundColor = "#FFFFFF";
}

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

//価格をカンマで区切る
function price(num){
	var newPrice = num.replace(/()(?=()+$)/g, "$1,");
	document.getElementById("oldPrice[cnt]").innerHTML = newPrice;
}

//ランキングページでの表示変更
function select(){
	for(var i = 1; i < 4; i++){
		document.getElementById("select[" + i + "]").innerHTML = "で絞り込み";
	}
}

//履歴の格納
function showHide(foldingID) {
	var g = document.getElementById(foldingID);

	if( g.style.display == "none"){
		g.style.display = "block";
		document.getElementById("hideList").innerHTML = "(クリックで格納)";
	}
	else{
		g.style.display = "none";
		document.getElementById("hideList").innerHTML = "(クリックで表示)";
	}
}

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