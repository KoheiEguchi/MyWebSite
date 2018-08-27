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
		.detailWrapper{max-width: 60%; margin: 0 auto; text-align: center;}
		.detail{display: inline-block; text-align: left;}
	</style>
	<script type="text/javascript"  src="js/origin/common.js"></script>
</head>

<body onload="hfLink();">
	<div id="adminContents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">商品詳細</font></p>
			<p><font size="6"><b>${item.itemName}</b></font></p>
			<p><img src="${item.fileName}"></p>
			<div class="detailWrapper"><p class="detail">${item.itemDetail}</p></div>
			<p><font size="5" color="red">${item.price}円</font></p>
			<p><a href="#" onClick="showHide('soldTable');return false;">
				<font size="5" color="yellow">売り上げ履歴<span id="hideList">(クリックで表示)</span></font></a></p>
			<div id="soldTable" style="display: none">
				<table class="table">
					<tr><th>売り上げ日時</th><th>売り上げ個数</th>
					<c:forEach var="soldHistory" items="${soldHistoryList}">
						<tr><td>${soldHistory.buyDate} ${soldHistory.buyTime}</td><td>${soldHistory.count}個</td></tr>
					</c:forEach>
					<tr><th>合計</th><th><b>${item.soldNum}個</b></th></tr>
				</table>
			</div>
			<p class="topmarginShort">
				<a class="inCartButton" href="AdminItemUpdate?id=${item.id}"><input class="button btn-info" type="button" value="商品情報更新"></a>
				<a href="Admin"><input class="button btn-primary" type="button" value="戻る"></a>
				<a class="cancelButton" href="AdminItemDelete?id=${item.id}"><input class="button btn-danger" type="button" value="商品削除"></a>
			</p>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>