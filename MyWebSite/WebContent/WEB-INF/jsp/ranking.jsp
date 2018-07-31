<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>人気ランキング</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
	<style type="text/css">
		body{}
		.centerText{text-align: center;}
		.starIcon{color: gold;}
	</style>
</head>

<body>
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">人気ランキング</font>
			<form action="Ranking?userId=${user.id}" method="post">
				<p class="topmarginShort formTitle">名前で絞り込む
				<p><input type="text" class="longText" name="searchName" placeholder="絞り込み語句"></input>
				<p class="formTitle">価格で絞り込む
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
				<p><input class="button btn-success" type="submit" value="絞り込み">
			</form>
			<table class="table">
				<tr>
					<c:set var="cnt">
						0
					</c:set>
					<c:forEach var="item" items="${itemList}">
						<td class="centerText">
							<font size="5">
								<c:choose>
									<c:when test="${cnt == 0}">
										<font size="6" color="orange">
											1位
										</font>
									</c:when>
									<c:when test="${cnt == 1}">
										<font size="6" color="gray">
											2位
										</font>
									</c:when>
									<c:when test="${cnt == 2}">
										<font size="6" color="bronze">
											3位
										</font>
									</c:when>
									<c:otherwise>
										<font size="5">
											${cnt+1}位
										</font>
									</c:otherwise>
								</c:choose>
								<br>
								<a href="ItemDetail?id=${item.id}&userId=${user.id}">
									<img src="${item.fileName}" width="500px" height="250px">
								</a>
								<br>
								${item.itemName}
								<br>
								<font color="red">
									${item.price}円
								</font>
							</font>
						<div style="display:none">
							${cnt = cnt + 1}
						</div>
						</td>
						<c:if test="${cnt % 3 == 0}">
							<tr></tr>
						</c:if>
					</c:forEach>
				</tr>
			</table>
			<p class="topmarginShort"><a href="Top"><input class="button btn-info" type="button" value="戻る"></a>
			<c:if test="${searchResult == true}">
				<script type="text/javascript">
					window.scrollTo(0,330);
				</script>
			</c:if>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>