<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>パスワード再設定</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
	</style>
</head>

<body>
	<div id="contents">
		<div id="header-bk">
			<div id="header" class="headerZ">
			</div>
		</div>

		<div id="body-bk">
			<p class="headermargin"><font size="7">パスワード再設定</font>

			<jsp:include page="alert.jsp" flush="true" />

			<form action="PassChange" method="post" name="usercheck">
				<c:if test="${check == null}">
					<p class="formTitle">ログインID
					<p><input type="text" class="longText" name="loginId" placeholder="Login ID">
					<p class="formTitle">ユーザー名
					<p><input type="text" class="longText" name="userName" placeholder="User Name">
					<p class="formTitle">住所
					<p><input type="text" class="addressText" name="address" placeholder="Address">
					<p><input class="button btn-success" type="submit" value="検索">
				</c:if>
			</form>
			<form action="PassChange?userId=${userId}" method="get" name="passchange">
				<c:if test="${check == true}">
					<p class="formTitle">新しいパスワードを入力してください。
					<p><input type="password" class="longText" name="password1" placeholder="New Password">
					<p class="formTitle">確認のためもう一度入力してください。
					<p><input type="password" class="longText" name="password2" placeholder="New Password">
					<p><input class="button btn-primary" type="submit" value="変更">
				</c:if>
				<p class="topmarginShort"><a href="Login"><input class="button btn-info" type="button" value="戻る"></a>
			</form>
			<script type="text/javascript">
				document.usercheck.loginId.focus();
			</script>
			<script type="text/javascript">
				document.passchange.password1.focus();
			</script>
		</div>

		<div id="footer-bk">
			<div id="footer"></div>
		</div>
	</div>
</body>
</html>