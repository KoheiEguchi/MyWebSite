<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
	<title>検索</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
	</style>
</head>

<body>
	<p class="topmarginShort formTitle">名前<span id="select[1]">検索</span></p>
	<p><input type="text" class="longText" name="searchName" placeholder="Word"
		onfocus="focusBox(this)" onBlur="blurBox(this)"></input></p>
	<p class="formTitle">種類<span id="select[2]">検索</span></p>
	<p><select class="longText" name="searchType">
		<option value="all">種類指定しない</option>
		<option value="set">セット</option>
		<option value="sand">底砂</option>
		<option value="filter">濾過フィルター</option>
		<option value="light">照明</option>
		<option value="food">エサ</option>
		<option value="air">エアー関連</option>
	</select></p>
	<p class="formTitle">価格<span id="select[3]">検索</span></p>
	<p><select class="longText" name="searchPrice">
		<option value="0">価格指定しない</option>
		<option value="1000">～1000円</option>
		<option value="3000">1001～3000円</option>
		<option value="5000">3001～5000円</option>
		<option value="10000">5001～10000円</option>
		<option value="20000">10001～20000円</option>
		<option value="20001">20001円～</option>
	</select></p>
</body>

</html>