<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
	<title>ヘッダー</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
	</style>
</head>

<body>
	<div id="header-bk">
		<div id="header">
			<p class="userName"><a href="UserData?id=${user.id}"><font color="yellow">${user.userName}さんのユーザー情報</font></a>
			<p class="logout"><a href="Logout"><font color="yellow">ログアウト</font></a>
		</div>
	</div>
</body>

</html>