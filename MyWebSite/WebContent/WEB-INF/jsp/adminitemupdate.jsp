<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>商品情報更新</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
	</style>
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="hfLink();">
	<div id="adminContents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">商品情報更新</font></p>

			<jsp:include page="alert.jsp" flush="true" />

			<form action="AdminItemUpdate?id=${item.id}" method="post" autocomplete="off" onSubmit="return doubleSubmit();">
				<p class="formTitle">商品名(文節ごとに<\span class="spanName"><\/span>で囲うこと)</p>
				<p><textarea name="itemName" cols="100" rows="2" placeholder="Item Name"
					onfocus="focusBox(this)" onBlur="blurBox(this)">${item.itemName}</textarea></p>
				<p class="formTitle">商品詳細(改行位置に<\br>をつけること)</p>
				<p><textarea name="itemDetail" cols="100" rows="10" placeholder="Item Detail"
					onfocus="focusBox(this)" onBlur="blurBox(this)">${item.itemDetail}</textarea></p>
				<p class="formTitle">種類</p>
				<p><select class="longText" name="type">
					<option value="${item.type}">変更しない</option>
					<option value="set">セット</option>
					<option value="sand">底砂</option>
					<option value="filter">濾過フィルター</option>
					<option value="light">照明</option>
					<option value="food">エサ</option>
					<option value="air">エアー関連</option>
				</select></p>
				<p class="formTitle">価格</p>
				<p><input type="text" class="priceText" name="price" value="${item.price}" placeholder="Price"
					onfocus="focusBox(this)" onBlur="blurBox(this)">円</p>
				<p class="formTitle">商品画像</p>
				<p><input type="text" class="longText" name="fileName" value="${item.fileName}" placeholder="Picture Name"
					onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
				<p><input class="button btn-success" type="submit" id="btnSubmit" value="更新"></p>
				<p class="topmarginShort"><a href="AdminItemDetail?id=${item.id}"><input class="button btn-info" type="button" value="戻る"></a></p>
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>