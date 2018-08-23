<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>退会</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
	<style type="text/css">
		body{}
		.inCartButton{margin-right:10%;}
		.cancelButton{margin-left:10%;}
		.warningIcon{color: orange;}
	</style>
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="hfLink();">
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<form action="UserDelete?id=${user.id}" method="post">
				<p class="headermargin"><font size="7">退会</font>
				<p><font size="5"> <font color="red">${user.userName}</font>さんのアカウントを<font color="red"><b>削除</b></font>しますがよろしいですか？</font>
				<p class="topmarginShort"><i class="fas fa-exclamation fa-5x warningIcon"></i>
				<p><a class="inCartButton" href="UserData?id=${user.id}"><input class="button btn-info" type="button" value="キャンセル"></a>
				<input class="cancelButton button btn-danger" type="submit" value="退会">
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>