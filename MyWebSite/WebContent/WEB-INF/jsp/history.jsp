<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>購入履歴詳細</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
		table td{text-align: right;}
		.itemName{text-align: left;}
	</style>
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="hfLink();">
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">購入履歴詳細</font></p>
			<table class="topmarginShort table">
				<tr>
					<th>購入日時</th>
					<td colspan="3"></td>
					<td>${buyHistory.buyDate} ${buyHistory.buyTime}</td>
				</tr>
				<c:forEach var="buyHistoryDetail" items="${buyHistoryDetailList}">
					<tr>
						<th>商品</th>
						<td class="itemName">${buyHistoryDetail.itemName}</td>
						<td><fmt:formatNumber value="${buyHistoryDetail.price}" pattern="###,###円" /></td>
						<td>${buyHistoryDetail.count}個</td>
						<td><fmt:formatNumber value="${buyHistoryDetail.price * buyHistoryDetail.count}" pattern="###,###円" /></td>
					</tr>
				</c:forEach>
				<tr>
					<th>小計</th>
					<td colspan="3"></td>
					<td><fmt:formatNumber value="${buyHistory.totalPrice - buyHistory.deliPrice}" pattern="###,###円" /></td>
				</tr>
				<tr>
					<th>配送方法</th>
					<td class="itemName">${buyHistory.deliveryMethod}</td>
					<td colspan="2"></td>
					<td><fmt:formatNumber value="${buyHistory.deliPrice}" pattern="###,###円" /></td>
				</tr>
				<tr>
					<th>合計金額</th>
					<td colspan="3"></td>
					<td><font color="red"><fmt:formatNumber value="${buyHistory.totalPrice}" pattern="###,###円" /></font></td>
				</tr>
				<tr>
					<th>配送状況</th>
					<td colspan="3"></td>
					<td>${deliConfirm}</td>
			</table>
			<p><a href="UserData?id=${user.id}"><input class="topmarginShort button btn-info" type="button" value="戻る"></a></p>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>