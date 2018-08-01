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
</head>

<body>
	<div id="adminContents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">商品情報更新</font>
			<c:if test="${errMsg != null}" >
	    		<div class="alert alert-danger" role="alert">
		 			${errMsg}
				</div>
			</c:if>
			<form action="AdminItemUpdate?id=${item.id}" method="post">
				<p class="formTitle">商品名
				<p><textarea name="itemName" cols="100" rows="1">${item.itemName}</textarea>
				<p class="formTitle">商品詳細
				<p><textarea name="itemDetail" cols="100" rows="10">${item.itemDetail}</textarea>
				<p class="formTitle">種類
				<p><select class="longText" name="type">
					<option value="${item.type}">変更しない</option>
					<option value="set">セット</option>
					<option value="sand">底砂</option>
					<option value="filter">濾過フィルター</option>
					<option value="light">照明</option>
					<option value="food">エサ</option>
					<option value="air">エアー関連</option>
				</select>
				<p class="formTitle">価格
				<p><input type="text" class="longText" name="price" value="${item.price}">
				<p class="formTitle">商品画像
				<p><input type="text" class="longText" name="fileName" value="${item.fileName}">
				<p><input class="button btn-success" type="submit" value="更新">
				<p class="topmarginShort"><a href="AdminItemDetail?id=${item.id}"><input class="button btn-info" type="button" value="戻る"></a>
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>