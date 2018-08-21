//履歴の格納
function showHide(foldingID) {
	if( document.getElementById(foldingID)){
		if( document.getElementById(foldingID).style.display == "none"){
			document.getElementById(foldingID).style.display = "block";
			document.getElementById("hideList").innerHTML = "売り上げ履歴(クリックで格納)";//表示させるとcssが反応しなくなりクリックもできなくなる
		}
		else{
			document.getElementById(foldingID).style.display = "none";
			document.getElementById("hideList").innerHTML = "売り上げ履歴(クリックで表示)";
		}
	}
}