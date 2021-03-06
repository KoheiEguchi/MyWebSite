<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
		table a:hover{text-decoration: none;}
		table td:hover{background-color: #68D0F3;}
	</style>
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="hfLink();scroll();">
	<div id="adminContents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">管理者用トップページ</font></p>

			<jsp:include page="alert.jsp" flush="true" />

			<p class="topmarginShort"><a href="AdminOrder"><input class="button btn-info" type="button" value="未発送一覧"></a></p>
			<p><a href="AdminItemCreate"><input class="button btn-primary" type="button" value="商品追加"></a></p>
			<form action="Admin?userId=${user.id}" method="post" autocomplete="off">

				<jsp:include page="search.jsp" flush="true" />

				<p><label><input type="checkbox" name="searchSold" value="true" />売り上げ順に並べ替える</label></p>
				<p><input class="button btn-success" type="submit" value="検索"></p>
			</form>
			<c:if test="${searchResult == true}">
				<div style="visibility: hidden" id="searchResult">scrollTarget</div>
				<p class="topmarginShort"><font size="5">検索結果</font>
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
										<fmt:formatNumber value="${item.price}" pattern="###,###円" />
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
										<fmt:formatNumber value="${searchItem.price}" pattern="###,###円" />
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
			<p class="topmarginShort"><a href="Top"><input class="button btn-info" type="button" value="戻る"></a></p>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>