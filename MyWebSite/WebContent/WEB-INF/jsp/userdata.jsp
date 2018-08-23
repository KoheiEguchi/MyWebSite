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
		.linkShort{width:1%}
	</style>
	<script type="text/javascript"  src="js/origin/common.js"></script>
</head>

<body onload="hfLink();">
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">ユーザー情報</font>
			<p class="topmarginShort"><a href="InCart?fromData=${fromData == true}">
				<input class="button btn-primary" type="button" value="カゴの中を見る">
			</a>
			<p class="topmarginShort"><font size="6">${user.userName}さんの情報</font>
			<table class="table">
				<tr><th>ログインID</th><td>${user.loginId}</td></tr>
				<tr><th>住所</th><td>${user.address}</td></tr>
				<tr><th>登録日時</th><td>${user.createDate}${user.createTime}</td></tr>
				<tr><th>更新日時</th><td>${user.updateDate}${user.updateTime}</td></tr>
			</table>
			<p><a class="leftButton" href="UserUpdate?id=${user.id}"><input class="button btn-success" type="button" value="ユーザー情報更新"></a>
			<a href="Top"><input class="button btn-info" type="button" value="トップへ戻る"></a>
			<a class="rightButton" href="UserDelete?id=${user.id}"><input class="button btn-danger" type="button" value="退会"></a>
			<p><a href="#" onClick="showHide('boughtTable');return false;">
				<font size="5" color="crimson">購入履歴<span id="hideList">(クリックで表示)</span></font>
			</a></p>
			<div id="boughtTable" style="display: none">
				<table class="table">
					<tr>
						<th></th>
						<th>合計金額</th>
						<th>購入日時</th>
						<th>配送状況</th>
					</tr>
					<c:forEach var="bought" items="${boughtList}">
						<tr>
							<td><a href="History?buyId=${bought.buyId}&buyDate=${bought.buyDate}&buyTime=${bought.buyTime}&deliConfirm=${bought.deliConfirm}">
								詳しく見る
							</a></td>
							<td>${bought.totalPrice}円</td>
							<td>${bought.buyDate}${bought.buyTime}</td>
							<td>${bought.deliConfirm}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>