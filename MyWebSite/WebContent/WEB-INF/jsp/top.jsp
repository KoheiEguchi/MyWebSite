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
		table a{display: block; width: 100%; height: 100%;}
		table a:hover{text-decoration: none; padding-bottom: 1px; background-color: #68D0F3;}
		.starIcon{color: gold;}
	</style>
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="headerLink()">
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">トップページ</font>

			<jsp:include page="alert.jsp" flush="true" />

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
						<td class="table-img cellSize">
							<div class="box">
								<a class="noLine" href="ItemDetail?id=${item.id}&userId=${user.id}">
									<img src="${item.fileName}" width="440px" height="220px">
									<br>
									<font color="black">
										${item.itemName}
									</font>
									<br>
									<font color="red">
										${item.price}円
									</font>
								</a>
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
			<form action="Top?userId=${user.id}" method="post" autocomplete="off">

				<jsp:include page="search.jsp" flush="true" />

				<p><label>
					<i class="fas fa-star starIcon"></i><input type="checkbox" name="searchFavorite" value="true">お気に入りで絞り込む
				</label>
				<p><input class="button btn-success" type="submit" value="検索">
			</form>
			<c:if test="${searchResult == true}">
				<p><font size="5">検索結果</font>
			</c:if>
			<table class="table">
				<tr>
					<c:set var="cnt">
						0
					</c:set>
					<c:forEach var="searchItem" items="${searchItemList}">
						<td class="table-img cellSize">
							<div class="box">
								<a class="tableLink noLine" href="ItemDetail?id=${searchItem.id}&userId=${user.id}">
									<img src="${searchItem.fileName}" width="440px" height="220px">
									<br>
									<font color="black">
										${searchItem.itemName}
									</font>
									<br>
									<font color="red">
										${searchItem.price}円
									</font>
								</a>
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
				<script type="text/javascript">
					window.scrollTo(0,950);
				</script>
			</c:if>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>