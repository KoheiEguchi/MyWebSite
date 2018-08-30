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
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="firstText();">
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<form action="UserCreate" method="post" name="userCreate" autocomplete="off" onsubmit="return doubleSubmit();">
				<p class="headermargin"><font size="7">新規登録</font></p>

				<jsp:include page="alert.jsp" flush="true" />

				<p class="formTitle">ログインID</p>
				<p><input type="text" class="longText" id="firstText" name="loginId" placeholder="Login ID"
					onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
				<p class="formTitle">ユーザー名</p>
				<p><input type="text" class="longText" name="userName" placeholder="User Name"
					onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
				<p class="formTitle">パスワード</p>
				<p><input type="password" class="longText" name="password1" placeholder="Password"
					onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
				<p class="formTitle">パスワード(確認)</p>
				<p><input type="password" class="longText" name="password2" placeholder="Password"
					onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
				<p class="formTitle">住所</p>
				<p><input type="text" class="addressText" name="address" placeholder="Address"
					onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
				<p><input class="button btn-success" type="submit" id="btnSubmit" value="登録"></p>
				<p class="topmarginShort"><a href="Login"><input class="button btn-info" type="button" value="戻る"></a></p>
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>