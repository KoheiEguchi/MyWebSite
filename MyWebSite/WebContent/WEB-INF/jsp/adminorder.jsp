<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>管理者用未発送一覧</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
		.buyerButton{padding-right: 30%;}
		.priceButton{padding-right: 35%;}
		.dateButton{padding-right: 10%;}
	</style>
</head>

<body>
	<div id="adminContents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">未発送一覧</font>
			<form action="AdminOrder" method="post">
				<c:if test="${noOrder == null}">
					<table class="table">
						<tr>
							<th>クリックで並び替え(${orderSelect}で並べ替え中)</th>
							<th><input class="buyerButton button btn-primary" type="submit" name="order" value="購入者"></th>
							<th><input class="priceButton button btn-warning" type="submit" name="order" value="合計金額"></th>
							<th><input class="dateButton button btn-success" type="submit" name="order" value="購入日時"></th>
						</tr>
						<c:forEach var="order" items="${orderList}">
							<tr>
								<td><a href="AdminOrderDetail?buyerId=${order.buyerId}&buyId=${order.buyId}&buyDate=${order.buyDate}&buyTime=${order.buyTime}">
									詳しく見る
								</a></td>
								<td>${order.name} 様</td>
								<td>${order.totalPrice}円</td>
								<td>${order.buyDate}${order.buyTime}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${noOrder != null}">
					<p class="topmarginShort"><font size="6">未発送の商品はありません</font>
				</c:if>
			</form>
			<p class="topmarginShort"><a href="Admin"><input class="button btn-info" type="button" value="戻る"></a>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>