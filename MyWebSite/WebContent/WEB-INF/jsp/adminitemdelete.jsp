<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>商品削除</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
	<style type="text/css">
		body{}
		.inCartButton{margin-right:10%;}
		.cancelButton{margin-left:10%;}
		.warningIcon{color: yellow;}
	</style>
</head>

<body>
	<div id="adminContents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">商品削除</font>
			<form action="AdminItemDelete?id=${item.id}" method="post">
				<p><font size="5"><font color="red">商品名</font>を本当に<font color="red"><b>削除</b></font>しますか？</font>
				<p class="topmarginShort"><i class="fas fa-exclamation fa-5x warningIcon"></i>
				<p><a href="AdminItemDetail?id=${item.id}"><input class="inCartButton button btn-info" type="button" value="キャンセル"></a>
				<input class="cancelButton button btn-danger" type="submit" value="削除">
			</form>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>