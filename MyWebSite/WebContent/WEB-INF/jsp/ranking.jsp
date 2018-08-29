<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
		table a{display: block; width: 100%; height: 100%;}
		table a:hover{text-decoration: none;}
		table td:hover{background-color: #68D0F3;}
		.centerText{text-align: center;}
		.starIcon{color: gold;}
	</style>
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="hfLink(); select();">
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">人気ランキング</font></p>

			<jsp:include page="alert.jsp" flush="true" />

			<form action="Ranking?userId=${user.id}" method="post" autocomplete="off">

				<jsp:include page="search.jsp" flush="true" />

				<p><label>
					<i class="fas fa-star starIcon"></i><input type="checkbox" name="searchFavorite" value="true">お気に入りで絞り込む
				</label></p>
				<p class="formTitle">表示順位を変える</p>
				<p><input type="text" name="rankNum" size="1" value="${rankNum}" placeholder="Rank"
					onfocus="focusBox(this)" onBlur="blurBox(this)">位まで表示</p>
				<p><input class="button btn-success" type="submit" value="絞り込み"></p>
			</form>
			<c:if test="${searchResult == true}">
				<p><font size="5">絞り込み結果</font></p>
			</c:if>
			<table class="table">
				<tr>
					<c:set var="cnt">
						0
					</c:set>
					<c:forEach var="item" items="${itemList}">
						<td class="centerText cellSize">
							<font size="5">
								<c:choose>
									<c:when test="${cnt == 0}">
										<font size="6" color="orange">1位</font>
									</c:when>
									<c:when test="${cnt == 1}">
										<font size="6" color="gray">2位</font>
									</c:when>
									<c:when test="${cnt == 2}">
										<font size="6" color="bronze">3位</font>
									</c:when>
									<c:otherwise>
										<font size="5">${cnt+1}位</font>
									</c:otherwise>
								</c:choose>
								<br>
								<a class="noLine" href="ItemDetail?id=${item.id}&userId=${user.id}">
									<img src="${item.fileName}" width="600px" height="300px">
									<br>
									<font color="black">
										${item.itemName}
									</font>
									<br>
									<font color="red">
										<fmt:formatNumber value="${item.price}" pattern="###,###円" />
									</font>
								</a>
							</font>
						<div style="display:none">${cnt = cnt + 1}</div>
						</td>
						<c:if test="${cnt % 3 == 0}">
							<tr></tr>
						</c:if>
					</c:forEach>
				</tr>
			</table>
			<p class="topmarginShort"><a href="Top"><input class="button btn-info" type="button" value="戻る"></a></p>
			<c:if test="${searchResult == true}">
				<script type="text/javascript">
					window.scrollTo(0,470);
				</script>
			</c:if>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>