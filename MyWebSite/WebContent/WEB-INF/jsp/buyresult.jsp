<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>購入完了</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
		.leftButton{margin-right:20%;}
		.rightButton{margin-left:20%;}
	</style>
</head>

<body>
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">購入完了</font>
			<p><font size="6">お買い上げありがとうございます。</font>
			<p class="topmargin"><a href="UserData?id=${user.id}"><input class="leftButton button btn-success" type="button" value="ユーザー情報確認"></a>
			<a href="Top"><input class="rightButton button btn-info" type="button" value="トップへ戻る"></a>
			<p class="topmarginShort"><font size="5">あなたへのおすすめ</font>
			<table class="table tablePic">
				<tr>
					<c:set var="cnt">
						0
					</c:set>
					<c:forEach var="item" items="${itemList}">
						<td class="table-img">
							<div class="box">
								<div class="box-img">
									<a href="ItemDetail?id=${item.id}">
										<img src="${item.fileName}" width="400px" height="200px">
									</a>
								</div>
								<div class="box-text">
									${item.itemName}
									<p><font color="red">${item.price}円</font>
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
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>