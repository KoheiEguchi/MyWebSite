<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>購入履歴詳細</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
	</style>
</head>

<body>
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">購入履歴詳細</font>
			<table class="topmarginShort table">
				<tr>
					<td>購入日時</td>
					<td colspan="2"></td>
					<td>${buyHistory.buyDate} ${buyHistory.buyTime}</td>
				</tr>
				<c:forEach var="buyHistoryDetail" items="${buyHistoryDetailList}">
					<tr>
						<td>${buyHistoryDetail.itemName}</td>
						<td>${buyHistoryDetail.price}円</td>
						<td>${buyHistoryDetail.count}個</td>
						<td>${buyHistoryDetail.price * buyHistoryDetail.count}円</td>
					</tr>
				</c:forEach>
				<tr>
					<td>小計</td>
					<td colspan="2"></td>
					<td>${buyHistory.totalPrice - buyHistory.deliPrice}円</td>
				</tr>
				<tr>
					<td>${buyHistory.deliveryMethod}</td>
					<td colspan="2"></td>
					<td>${buyHistory.deliPrice}円</td>
				</tr>
				<tr>
					<td>合計金額</td>
					<td colspan="2"></td>
					<td><font color="red">${buyHistory.totalPrice}円</font></td>
				</tr>
			</table>
			<p><a href="UserData?id=${user.id}"><input class="topmarginShort button btn-info" type="button" value="戻る"></a>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>