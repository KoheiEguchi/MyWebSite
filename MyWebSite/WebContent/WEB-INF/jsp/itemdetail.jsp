<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>商品詳細</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
	<style type="text/css">
		body{}
		.rightText{text-align: right;}
		.inCartButton{margin-right: 10%;}
		.cancelButton{margin-left: 10%;}
		.starIcon{color: gold;}
		.hiddenButton{display: none;}
	</style>
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="hfLink();">
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">商品詳細</font>

			<jsp:include page="alert.jsp" flush="true" />

			<form action="InCart?id=${item.id}&userId=${user.id}" method="post" autocomplete="off" onsubmit="return doubleSubmit();">
				<p><font size="6"><b>${item.itemName}</b></font>
				<p class="rightText"><c:if test = "${favorite == true}">
					<input type="button" id="favoritebutton" class="hiddenButton"><i class="fas fa-star fa-2x starIcon"></i>お気に入り登録済み
				</c:if>
				<p><img src="${item.fileName}">
				<p>${item.itemDetail}
				<p><font size="5" color="red">${item.price}円</font>
				<c:if test = "${link == 'list'}">
					<p>カゴに入れる数を入力してください<input type="text" name="count" size="3" value="1" placeholder="Count">個
					<p class="topmarginShort"><input class="inCartButton button btn-success" type="submit" id="btnSubmit" value="カゴに入れる">
					<c:if test = "${favorite == false}">
						<a href="Favorite?itemId=${item.id}&userId=${user.id}&favorite=${favorite}"><input class="button btn-warning" type="button" value="お気に入り登録"></a>
					</c:if>
					<c:if test = "${favorite == true}">
						<a href="Favorite?itemId=${item.id}&userId=${user.id}&favorite=${favorite}"><input class="button btn-warning" type="button" value="お気に入り解除"></a>
					</c:if>
					<a class="cancelButton" href="Top"><input class="button btn-danger" type="button" value="買わない"></a>
				</c:if>
				<c:if test = "${link == 'cart'}">
					　×　${item.count}個　＝　<font size="5" color="red">${item.countPrice}円</font>
					<p class="topmarginShort">
					<a class="inCartButton" href="CartDelete?id=${item.id}&fromData=${fromData == true}"><input class="button btn-danger" type="button" value="カゴから出す"></a>
					<a class="cancelButton" href="InCart"><input class="button btn-success" type="button" value="戻る"></a>
				</c:if>
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>