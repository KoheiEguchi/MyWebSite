//ランキングページでの表示変更
function select(){
	for(var i = 1; i < 4; i++){
		document.getElementById("select[" + i + "]").innerHTML = "で絞り込み";
	}
}