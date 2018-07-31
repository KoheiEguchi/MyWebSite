<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>ログイン</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
	</style>
</head>

<body>
	<div id="contents">
		<div id="header-bk">
			<div id="header">
			</div>
		</div>

		<div id="body-bk">
			<form action="Login" method="post" name="login">
				<p class="headermargin"><font size="7">ログイン</font>

				<c:if test="${errMsg != null}" >
	    			<div class="alert alert-danger" role="alert">
		 				${errMsg}
					</div>
				</c:if>

				<p><img src="pic/aqua1.jpg" width="700px" height="350px">
				<p class="topmarginShort formTitle">ログインID
				<p><input type="text" class="longText" name="loginId" placeholder="login ID"></input>
				<p class="formTitle">パスワード
				<p><input type="password" class="longText" name="password" placeholder="password"></input>
				<p><input class="button btn-success" type="submit" value="ログイン">
				<p class="topmarginShort"><a href="UserCreate"><input class="button btn-info" type="button" value="新規登録"></a>
			</form>
			<script type="text/javascript">
				document.login.loginId.focus();
			</script>
		</div>

		<div id="footer-bk">
			<div id="footer"></div>
		</div>
	</div>
</body>

</html>