<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>ユーザー情報更新</title>
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
			<p class="headermargin"><font size="7">ユーザー情報更新</font>

			<jsp:include page="alert.jsp" flush="true" />

			<form action="UserUpdate?id=${user.id}" method="post">
				<p class="formTitle">ログインID
				<p><input type="text" class="longText" name="loginId" value="${user.loginId}">
				<p class="formTitle">ユーザー名
				<p><input type="text" class="longText" name="userName" value="${user.userName}">
				<p class="formTitle">パスワード
				<p><input type="password" class="longText" name="password1" placeholder="Password">
				<p class="formTitle">パスワード(確認)
				<p><input type="password" class="longText" name="password2" placeholder="Password">
				<p class="formTitle">住所
				<p><input type="text" class="addressText" name="address" value="${user.address}">
				<p><input class="button btn-success" type="submit" value="更新">
				<p class="topmarginShort"><a href="UserData?id=${user.id}"><input class="button btn-info" type="button" value="戻る"></a>
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>