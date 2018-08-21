<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>新規登録</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
	</style>
	<script type="text/javascript" src="js/origin/doublesubmit.js"></script>
</head>

<body>
	<div id="contents">
		<div id="header-bk" class="headerZ">
			<div id="header">
			</div>
		</div>

		<div id="body-bk">
			<form action="UserCreate" method="post" name="userCreate" onsubmit="return doubleSubmit();">
				<p class="headermargin"><font size="7">新規登録</font>

				<jsp:include page="alert.jsp" flush="true" />

				<p class="formTitle">ログインID
				<p><input type="text" class="longText" name="loginId" placeholder="Login ID">
				<p class="formTitle">ユーザー名
				<p><input type="text" class="longText" name="userName" placeholder="User Name">
				<p class="formTitle">パスワード
				<p><input type="password" class="longText" name="password1" placeholder="Password">
				<p class="formTitle">パスワード(確認)
				<p><input type="password" class="longText" name="password2" placeholder="Password">
				<p class="formTitle">住所
				<p><input type="text" class="addressText" name="address" placeholder="Address">
				<p><input class="button btn-success" type="submit" id="btnSubmit" value="登録">
				<p class="topmarginShort"><a href="Login"><input class="button btn-info" type="button" value="戻る"></a>
			</form>
			<script type="text/javascript">
				document.userCreate.loginId.focus();
			</script>
		</div>

		<div id="footer-bk">
			<div id="footer"></div>
		</div>
	</div>
</body>

</html>