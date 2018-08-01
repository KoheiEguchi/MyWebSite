<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>商品追加</title>
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
			<p class="headermargin"><font size="7">商品追加</font>
			<c:if test="${errMsg != null}" >
	    		<div class="alert alert-danger" role="alert">
		 			${errMsg}
				</div>
			</c:if>
			<form action="AdminItemCreate" method="post" name="itemCreate">
				<p class="formTitle">商品名
				<p><textarea name="itemName" cols="100" rows="1" placeholder="Item Name"></textarea>
				<p class="formTitle">商品詳細
				<p><textarea name="itemDetail" cols="100" rows="10" placeholder="Item Detail"></textarea>
				<p class="formTitle">種類
				<p><select class="longText" name="type">
					<option value="noSelect">種類を選択してください</option>
					<option value="set">セット</option>
					<option value="sand">底砂</option>
					<option value="filter">濾過フィルター</option>
					<option value="light">照明</option>
					<option value="food">エサ</option>
					<option value="air">エアー関連</option>
				</select>
				<p class="formTitle">価格
				<p><input type="text" class="longText" name="price" placeholder="Price">
				<p class="formTitle">商品画像
				<p><input type="text" class="longText" name="fileName" placeholder="Picture Name" value="pic/">
				<p><input class="button btn-success" type="submit" value="登録">
				<p class="topmarginShort"><a href="Admin"><input class="button btn-info" type="button" value="戻る"></a>
			</form>
			<script type="text/javascript">
				document.itemCreate.itemName.focus();
			</script>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>