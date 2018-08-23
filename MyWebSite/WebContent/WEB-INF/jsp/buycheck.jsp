<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>購入確認</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
		.leftButton{margin-right:20%;}
		.rightButton{margin-left:20%;}
	</style>
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="hfLink();">
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">購入確認</font>
			<form action="BuyResult" method="post" onsubmit="return doubleSubmit();">
				<table class="table topmarginShort">
					<tr><th>配送先住所</th><td>${buy.deliAddress}</td></tr>
					<tr><th>配送方法</th><td>${buy.deliveryMethod}</td></tr>
				</table>
				<p class="topmarginShort"><font size="5">購入商品</font>
				<table class="table">
					<tr><th></th><th>商品名</th><th>単価</th><th>個数</th><th>金額
					<c:forEach var="item" items="${cart}">
						<tr><th>商品</th><td>${item.itemName}</td><td>${item.price}円</td><td>${item.count}個</td><td>${item.countPrice}円</tr>
					</c:forEach>
					<tr><th>小計</th><td colspan="3"></td><td>${buy.allPrice}円</td></tr>
					<tr><th>送料</th><td colspan="3"></td><td>${buy.deliPrice}円</td></tr>
					<tr><th>合計金額</th><td colspan="3"></td><td><font size="4" color="red"><b>${buy.totalPrice}円</b></font></td></tr>
				</table>
				<p class="topmargin"><input class="leftButton button btn-success" type="submit" id="btnSubmit" value="購入決定する">
				<a class="rightButton" href="InCart?id=${user.id}"><input class="button btn-danger" type="button" value="戻る"></a>
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>