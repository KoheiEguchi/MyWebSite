<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>管理者用未発送詳細</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
	</style>
	<script type="text/javascript" src="js/origin/doublesubmit.js"></script>
	<script type="text/javascript" src="js/origin/ordercheck.js"></script>
</head>

<body>
	<div id="adminContents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">未発送詳細</font>

			<jsp:include page="alert.jsp" flush="true" />

			<form name="orderForm"
				action="AdminOrderDetail?buyerId=${buyerId}&buyId=${buyId}&buyDate=${buyHistory.buyDate}&buyTime=${buyHistory.buyTime}"
				method="post" onsubmit="return doubleSubmit();">
				<table class="topmarginShort table">
					<tr>
						<th>購入者</th>
						<td colspan="3"></td>
						<td>${boughtName} 様</td>
					</tr>
					<tr>
						<th>購入日時</th>
						<td colspan="3"></td>
						<td>${buyHistory.buyDate} ${buyHistory.buyTime}</td>
					</tr>
					<c:forEach var="buyHistoryDetail" items="${buyHistoryDetailList}">
						<tr>
							<th>商品名</th>
							<td><label><input type="checkbox" name="orderItem" value="true"/>${buyHistoryDetail.itemName}</label></td>
							<td>${buyHistoryDetail.price}円</td>
							<td>${buyHistoryDetail.count}個</td>
							<td>${buyHistoryDetail.price * buyHistoryDetail.count}円</td>
						</tr>
					</c:forEach>
					<tr>
						<th>小計</th>
						<td colspan="3"></td>
						<td>${buyHistory.totalPrice - buyHistory.deliPrice}円</td>
					</tr>
					<tr>
						<th>配送方法</th>
						<td>${buyHistory.deliveryMethod}</td>
						<td colspan="2"></td>
						<td>${buyHistory.deliPrice}円</td>
					</tr>
					<tr>
						<th>合計金額</th>
						<td colspan="3"></td>
						<td><font color="red">${buyHistory.totalPrice}円</font></td>
					</tr>
				</table>
				<p><input class="topmarginShort button btn-success" type="submit" id="btnSubmit" value="発送完了" onClick="return orderCheck()">
				<p><a href="AdminOrder"><input class="topmarginShort button btn-info" type="button" value="戻る"></a>
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>