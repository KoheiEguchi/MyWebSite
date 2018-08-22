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