<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>カゴ内一覧</title>
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
			<p class="headermargin"><font size="7">カゴ内一覧</font>
			<p class="topmarginShort"><font size="5">${cartActionMessage}</font>
			<form action="BuyCheck?id=${user.id}&count=${item.count}" method="post">
				<c:if test = "${noCart != null}">
					<p class="topmarginShort"><a href="Top"><input class="button btn-info" type="button" value="買い物を続ける"></a>
				</c:if>
				<c:if test = "${noCart == null}">
					<p class="topmarginShort"><a href="Top"><input class="leftButton button btn-info" type="button" value="買い物を続ける"></a>
					<input class="rightButton button btn-success" type="submit" value="レジへ進む">
					<p>配送方法を選んでください<select name="deliveryMethod">
						<option value="normal">通常配送(送料無料)</option>
						<option value="quick">お急ぎ配送(送料500円)</option>
						<option value="select">日時指定配送(送料200円)</option>
					</select>
					<p>配送先の住所を入力してください<textarea class="topmarginShort" name="deliAddress" cols="38" rows="2">${user.address}</textarea>
				</c:if>
				<p class="topmarginShort"><font size="5">カゴ内の商品</font>
				<c:if test="${noCart != null}">
					<p class="topmarginShort"><font size="6">カゴは空です</font>
				</c:if>
				<table class="table">
					<tr>
						<c:set var="cnt">
							0
						</c:set>
						<c:forEach var="item" items="${cart}">
							<td class="table-img">
								<div class="box">
									<div class="box-img">
										<a href="CartItemDetail?id=${item.id}&count=${item.count}">
											<img src="${item.fileName}" width="400px" height="200px">
										</a>
									</div>
									<div class="box-text">
										${item.itemName}
										<p><font color="red">${item.price}円</font>
										　×　${item.count}個
										　＝　<font size="5" color="red">${item.countPrice}円</font>
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
			</form>
			<c:if test = "${noCart == null}">
				<p><a href="CartAllDelete?count=${buy.count}"><input class="button btn-danger" type="button" value="カゴを空にする"></a>
			</c:if>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>