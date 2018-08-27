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
		.topmarginMini{
			margin-top: 2%;
		}
	</style>

</head>

<body>
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<form action="Login" method="post" name="login" autocomplete="off">
				<p class="headermargin"><font size="7">ログイン</font></p>

				<jsp:include page="alert.jsp" flush="true" />

				<p><img src="pic/aqua1.jpg" width="700px" height="350px"></p>
				<p class="topmarginShort formTitle">ログインID</p>
				<p><input type="text" class="longText" name="loginId" placeholder="Login ID"></input></p>
				<p class="formTitle">パスワード</p>
				<p><input type="password" class="longText" name="password" placeholder="Password"></input></p>
				<p><input class="button btn-success" type="submit" value="ログイン"></p>
				<p class="topmarginMini"><a href="UserCreate"><input class="button btn-info" type="button" value="新規登録"></a></p>
				<p class="topmarginMini"><a href="PassChange"><input class="button btn-danger" type="button" value="パスワードを忘れた"></a></p>
			</form>
			<script type="text/javascript">
				document.login.loginId.focus();
			</script>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>