//多くのページでヘッダーとフッダーにリンクをつける
function hfLink(){
	document.getElementById("header").classList.remove("noLink");
	document.getElementById("footer").classList.remove("noLink");
}

//トップページでヘッダーにのみリンクをつける
function headerLink(){
	document.getElementById("header").classList.remove("noLink");
}

//入力必須な最初のテキストボックスにフォーカスをあてる
function firstText(){
	document.getElementById("firstText").focus();
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

//検索、絞り込み後画面を結果へスクロールする
function scroll(){
	document.getElementById("searchResult").scrollIntoView(true);
}

//ランキングページでの表示変更
function select(){
	for(var i = 1; i < 4; i++){
		document.getElementById("select[" + i + "]").innerHTML = "で絞り込み";
	}
}

//カゴ内を空にするかの確認
function deleteCheck(){
	if(window.confirm('カゴの中を空にしてよろしいですか？')){
		return true;
	}else{
		return false;
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

//購入履歴閲覧後リストを開いたままにする
function listOpen(foldingID){
	var g = document.getElementById(foldingID);
	var r = document.referrer;
	var h = "MyWebSite/History?buyId=";

	if(r.indexOf(h) > -1){
		g.style.display = "block";
		document.getElementById("hideList").innerHTML = "(クリックで格納)";
	}
}

//配送時の商品チェック漏れ点検
function orderCheck(){
	var f = document.orderForm.orderItem;
	var ok = 0;

	//商品が複数種類ある場合
	if(f.length != undefined){
		for(var i = 0; i < f.length; i++){
			if(f[i].checked){
				ok++;
			}
		}

	//商品が一種類だけの場合
	}else{
		if(f.checked){
			return true;
		}
	}

	//チェックされていない商品がある場合
	if(ok != f.length){
		if(confirm("チェックされていない商品がありますがよろしいですか？")){
			return true;
		}else{
			return false;
		}
	}
}

//アカウントや商品削除時の確認
function deleteCheck(){
	if(window.confirm('本当に削除してよろしいですか？')){
		return true;
	}else{
		return false;
	}
}