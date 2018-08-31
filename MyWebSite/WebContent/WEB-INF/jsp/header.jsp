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
		.noLink{
			display: none;
		}
	</style>
</head>

<body>
	<div id="header-bk" class="headerZ">
		<div id="header" class="noLink">
			<p class="userName"><a href="UserData?id=${user.id}"><font color="yellow">${user.userName}様のユーザー情報</font></a></p>
			<p class="logout"><a href="Logout"><font color="yellow">ログアウト</font></a></p>
		</div>
	</div>
</body>

</html>