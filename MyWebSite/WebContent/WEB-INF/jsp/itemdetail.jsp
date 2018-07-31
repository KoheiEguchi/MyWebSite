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
		.rightText{text-align:right;}
		.inCartButton{margin-right:10%;}
		.cancelButton{margin-left:10%;}
		.starIcon{color: gold;}
		.hiddenButton{display: none;}
	</style>
</head>

<body>
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">商品詳細</font>
			<form action="InCart?id=${item.id}&userId=${user.id}" method="post">
				<p><font size="6"><b>${item.itemName}</b></font>
				<p class="rightText">
					<c:if test = "${favorite == true}">
						<label for="favorite">
							<input type="button" id="favoritebutton" class="hiddenButton">
								<i class="fas fa-star fa-2x starIcon"></i>
							お気に入り登録済み
						</label>
					</c:if>
				<p><img src="${item.fileName}">
				<p>${item.itemDetail}
				<p><font size="5" color="red">${item.price}円</font>
				<c:if test = "${link == 'list'}">
					<p>カゴに入れる数を入力してください<input type="text" name="count" size="3" value="1">個
					<p class="topmarginShort"><input class="inCartButton button btn-success" type="submit" value="カゴに入れる">
					<c:if test = "${favorite == false}">
						<a href="Favorite?itemId=${item.id}&userId=${user.id}&favorite=${favorite}"><input class="button btn-warning" type="button" value="お気に入り登録"></a>
					</c:if>
					<c:if test = "${favorite == true}">
						<a href="Favorite?itemId=${item.id}&userId=${user.id}&favorite=${favorite}"><input class="button btn-warning" type="button" value="お気に入り解除"></a>
					</c:if>
					<a href="Top"><input class="cancelButton button btn-danger" type="button" value="買わない"></a>
				</c:if>
				<c:if test = "${link == 'cart'}">
					　×　${item.count}個　＝　<font size="5" color="red">${item.countPrice}円</font>
					<p class="topmarginShort"><a href="CartDelete?id=${item.id}"><input class="inCartButton button btn-danger" type="button" value="カゴから出す"></a>
					<a href="InCart"><input class="cancelButton button btn-success" type="button" value="戻る"></a>
				</c:if>
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>