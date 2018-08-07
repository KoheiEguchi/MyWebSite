<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>ユーザー情報</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
		.leftButton{margin-right:20%;}
		.rightButton{margin-left:20%;}
	</style>
</head>

<body>
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">ユーザー情報</font>
			<p class="topmarginShort"><a href="InCart?fromData"><input class="button btn-primary" type="button" value="カゴの中を見る"></a>
			<p class="topmarginShort"><font size="6">${user.userName}さんの情報</font>
			<table class="table">
				<tr><td>ログインID</td><td>${user.loginId}</td></tr>
				<tr><td>住所</td><td>${user.address}</td></tr>
				<tr><td>登録日時</td><td>${user.createDate}${user.createTime}</td></tr>
				<tr><td>更新日時</td><td>${user.updateDate}${user.updateTime}</td></tr>
			</table>
			<p><a href="UserUpdate?id=${user.id}"><input class="leftButton button btn-success" type="button" value="ユーザー情報更新"></a>
			<a href="Top"><input class="button btn-info" type="button" value="トップへ戻る"></a>
			<a href="UserDelete?id=${user.id}"><input class="rightButton button btn-danger" type="button" value="退会"></a>
			<p><font size="5">購入履歴</font>
			<table class="table">
				<tr>
					<th></th>
					<th>合計金額</th>
					<th>購入日時</th>
				</tr>
				<c:forEach var="bought" items="${boughtList}">
					<tr>
						<td><a href="History?buyId=${bought.buyId}&buyDate=${bought.buyDate}&buyTime=${bought.buyTime}">詳しく見る</a></td>
						<td>${bought.totalPrice}円</td>
						<td>${bought.buyDate}${bought.buyTime}</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>