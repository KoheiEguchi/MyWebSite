<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>管理用商品詳細</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
		.rightText{text-align:right;}
		.inCartButton{margin-right:10%;}
		.cancelButton{margin-left:10%;}
	</style>
	<script type="text/javascript"  src="js/origin/showhide.js"></script>
</head>

<body>
	<div id="adminContents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">商品詳細</font>
			<p><font size="6"><b>${item.itemName}</b></font>
			<p><img src="${item.fileName}">
			<p>${item.itemDetail}
			<p><font size="5" color="red">${item.price}円</font>
			<div id="hideList">
				<p><a href="#" onClick="showHide('soldTable');return false;"><font size="5" color="yellow">売り上げ履歴(クリックで表示)</font></a>
			</div>
			<div id="soldTable" style="display: none">
				<table class="table">
					<tr><th>売り上げ日時</th><th>売り上げ個数</th>
					<c:forEach var="soldHistory" items="${soldHistoryList}">
						<tr><td>${soldHistory.buyDate} ${soldHistory.buyTime}</td><td>${soldHistory.count}個</td></tr>
					</c:forEach>
					<tr><th>合計</th><th><b>${item.soldNum}個</b></th></tr>
				</table>
			</div>
			<p class="topmarginShort"><a class="inCartButton" href="AdminItemUpdate?id=${item.id}"><input class="button btn-info" type="button" value="商品情報更新"></a>
			<a href="Admin"><input class="button btn-primary" type="button" value="戻る"></a>
			<a class="cancelButton" href="AdminItemDelete?id=${item.id}"><input class="button btn-danger" type="button" value="商品削除"></a>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>