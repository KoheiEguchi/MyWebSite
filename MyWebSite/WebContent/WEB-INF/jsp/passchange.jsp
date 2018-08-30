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
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="firstText();">
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">パスワード再設定</font></p>

			<jsp:include page="alert.jsp" flush="true" />

			<form action="PassChange" method="post" name="usercheck" autocomplete="off">
				<c:if test="${check == null}">
					<p class="formTitle">ログインID</p>
					<p><input type="text" class="longText" id="firstText" name="loginId" placeholder="Login ID"
						onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
					<p class="formTitle">ユーザー名</p>
					<p><input type="text" class="longText" name="userName" placeholder="User Name"
						onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
					<p class="formTitle">住所</p>
					<p><input type="text" class="addressText" name="address" placeholder="Address"
						onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
					<p><input class="button btn-success" type="submit" value="検索"></p>
				</c:if>
			</form>
			<form action="PassChange?userId=${userId}" method="get" name="passchange">
				<c:if test="${check == true}">
					<p class="formTitle">新しいパスワードを入力してください。</p>
					<p><input type="password" class="longText" id="firstText" name="password1" placeholder="New Password"
						onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
					<p class="formTitle">確認のためもう一度入力してください。</p>
					<p><input type="password" class="longText" name="password2" placeholder="New Password"
						onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
					<p><input class="button btn-primary" type="submit" value="変更"></p>
				</c:if>
				<p class="topmarginShort"><a href="Login"><input class="button btn-info" type="button" value="戻る"></a></p>
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>
</html>