<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>管理者用トップページ</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
		table a{display: block; width: 100%; height: 100%;}
		table a:hover{text-decoration: none; padding-bottom: 1px; background-color: #68D0F3;}
	</style>
</head>

<body>
	<div id="adminContents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">管理者用トップページ</font>

			<jsp:include page="alert.jsp" flush="true" />

			<p class="topmarginShort"><a href="AdminOrder"><input class="button btn-info" type="button" value="未発送一覧"></a>
			<p><a href="AdminItemCreate"><input class="button btn-primary" type="button" value="商品追加"></a>
			<form action="Admin?userId=${user.id}" method="post">
				<p class="topmarginShort formTitle">名前検索
				<p><input type="text" class="longText" name="searchName" placeholder="検索語句"></input>
				<p class="formTitle">種類検索
				<p><select class="longText" name="searchType">
					<option value="all">種類指定しない</option>
					<option value="set">セット</option>
					<option value="sand">底砂</option>
					<option value="filter">濾過フィルター</option>
					<option value="light">照明</option>
					<option value="food">エサ</option>
					<option value="air">エアー関連</option>
				</select>
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
				<p><label><input type="checkbox" name="searchSold" value="true" />売り上げ順に並べ替える</label>
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
					<c:forEach var="item" items="${itemList}">
						<td class="table-img cellSize">
							<div class="box">
								<a class="noLine" href="AdminItemDetail?id=${item.id}">
									<img src="${item.fileName}" width="440px" height="220px">
									<br>
									<font color="black">
										${item.itemName}
									</font>
									<br>
									<font color="red">
										${item.price}円
									</font>
									<br>
									<font color="black">
										総売り上げ数　${item.soldNum}個
									</font>
								</a>
							</div>
						</td>
						<div style="display:none">${cnt = cnt + 1}</div>
						<c:if test="${cnt % 4 == 0}">
							<tr></tr>
						</c:if>
					</c:forEach>
				</tr>
			</table>
			<table class="table">
				<tr>
					<c:set var="cnt">
						0
					</c:set>
					<c:forEach var="searchItem" items="${searchItemList}">
						<td class="table-img cellSize">
							<div class="box">
								<a class="noLine" href="AdminItemDetail?id=${searchItem.id}">
									<img src="${searchItem.fileName}" width="440px" height="220px">
									<br>
									<font color="black">
										${searchItem.itemName}
									</font>
									<br>
									<font color="red">
										${searchItem.price}円
									</font>
									<br>
									<font color="black">
										総売り上げ数　${searchItem.soldNum}個
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
			<p class="topmarginShort"><a href="Top"><input class="button btn-info" type="button" value="戻る"></a>
			<c:if test="${searchResult == true}">
				<script  type="text/javascript">
					window.scrollTo(0,570);
				</script>
			</c:if>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>