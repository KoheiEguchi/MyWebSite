<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>トップページ</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
	<style type="text/css">
		body{}
		.starIcon{color: gold;}
	</style>
</head>

<body>
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">トップページ</font>
			<p><img src="pic/aqua2.jpg" width="800px" height="400px">
			<c:if test="${searchResult != true}">
				<p><font size="6">ようこそ</font>
				<p class=""><font size="5">おすすめ商品</font>
			</c:if>
			<table class="table tablePic">
				<tr>
					<c:set var="cnt">
						0
					</c:set>
					<c:forEach var="item" items="${itemList}">
						<td class="table-img">
							<div class="box">
								<div class="box-img">
									<a href="ItemDetail?id=${item.id}&userId=${user.id}">
										<img src="${item.fileName}" width="400px" height="200px">
									</a>
								</div>
								<div class="box-text">
									${item.itemName}
									<br>
									<font color="red">
										${item.price}円
									</font>
								</div>
							</div>
						</td>
						<div style="display:none">
							${cnt = cnt + 1}
						</div>
						<c:if test="${cnt % 4 == 0}">
							<tr></tr>
						</c:if>
					</c:forEach>
				</tr>
			</table>
			<p class="topmarginShort"><a href="Ranking"><input class="button btn-info" type="button" value="人気ランキング"></a>
			<form action="Top?userId=${user.id}" method="post">
				<p class="topmarginShort formTitle">名前検索
				<p><input type="text" class="longText" name="searchName" placeholder="検索語句"></input>
				<p class="formTitle">価格検索
				<p><select class="longText" name="searchPrice">
					<option value="0">価格指定しない</option>
					<option value="1000">～1000円</option>
					<option value="3000">1001～3000円</option>
					<option value="5000">3001～5000円</option>
					<option value="10000">5001～10000円</option>
					<option value="20000">10001～20000円</option>
					<option value="20001">20001円～</option>
				</select>
				<p><label for="searchfavorite">
					<i class="fas fa-star starIcon"></i><input type="checkbox" name="searchFavorite" value="true" />お気に入りで絞り込む
				</label>
				<p><input class="button btn-success" type="submit" value="検索">
			</form>
			<p><font size="5">検索結果</font>
			<table class="table">
				<tr>
					<c:set var="cnt">
						0
					</c:set>
					<c:forEach var="searchItem" items="${searchItemList}">
						<td class="table-img">
							<div class="box">
								<div class="box-img">
									<a href="ItemDetail?id=${searchItem.id}&userId=${user.id}">
										<img src="${searchItem.fileName}" width="400px" height="200px">
									</a>
								</div>
								<div class="box-text">
									${searchItem.itemName}
									<br>
									<font color="red">
										${searchItem.price}円
									</font>
								</div>
							</div>
						</td>
						<div style="display:none">
							${cnt = cnt + 1}
						</div>
						<c:if test="${cnt % 4 == 0}">
							<tr></tr>
						</c:if>
					</c:forEach>
				</tr>
			</table>
			<c:if test = "${user.id == '1'}">
				<p class="topmarginShort"><a href="Admin"><input class="button btn-primary" type="button" value="管理者用ページ"></a>
			</c:if>
			<c:if test="${searchResult == true}">
				<script  type="text/javascript">
					window.scrollTo(0,850);
				</script>
			</c:if>
		</div>

		<div id="footer-bk">
			<div id="footer"></div>
		</div>
	</div>
</body>

</html>