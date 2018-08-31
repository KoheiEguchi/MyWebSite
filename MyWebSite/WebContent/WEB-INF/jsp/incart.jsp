<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>カゴ内一覧</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/origin/common.css">
	<style type="text/css">
		body{}
		table a{display: block; width: 100%; height: 100%;}
		table a:hover{text-decoration: none;}
		table td:hover{background-color: #68D0F3;}
		.leftButton{margin-right:20%;}
		.rightButton{margin-left:20%;}
	</style>
	<script type="text/javascript" src="js/origin/common.js"></script>
</head>

<body onload="hfLink();">
	<div id="contents">
		<jsp:include page="header.jsp" flush="true" />

		<div id="body-bk">
			<p class="headermargin"><font size="7">カゴ内一覧</font></p>

			<jsp:include page="alert.jsp" flush="true" />

			<p class="topmarginShort"><font size="5">${cartActionMessage}</font></p>
			<form action="BuyCheck?id=${user.id}" method="post" autocomplete="off">
				<c:if test="${noCart != null}">
					<c:if test="${fromData == true}">
						<p class="topmarginShort">
							<a href="UserData?id=${user.id}"><input class="button btn-info" type="button" value="ユーザー情報に戻る"></a>
						</p>
					</c:if>
					<c:if test="${fromData != true}">
						<p class="topmarginShort">
							<a href="Top"><input class="button btn-info" type="button" value="買い物を続ける"></a>
						</p>
					</c:if>
				</c:if>
				<c:if test="${noCart == null}">
					<c:if test="${fromData == true}">
						<p class="topmarginShort">
						<a class="leftButton" href="UserData?id=${user.id}"><input class="button btn-info" type="button" value="ユーザー情報に戻る"></a>
					</c:if>
					<c:if test="${fromData != true}">
						<p class="topmarginShort">
						<a class="leftButton" href="Top"><input class="button btn-info" type="button" value="買い物を続ける"></a>
					</c:if>
					<input class="rightButton button btn-success" type="submit" value="レジへ進む">
					<p>
						配送方法を選んでください<br>
						<label><input type="radio" name="deliveryMethod" value="normal" checked="checked"><font size="4">通常配送(送料無料)</font></label>
						<br>
						<label><input type="radio" name="deliveryMethod" value="quick"><font size="4">お急ぎ配送(送料500円)</font></label>
						<br>
						<label><input type="radio" name="deliveryMethod" value="select"><font size="4">日時指定配送(送料200円)</font></label>
					</p>
					<p class="topmarginShort">配送先の住所を入力してください</p>
					<p><input type="text" class="addressText" name="deliAddress" value="${user.address}" placeholder="Address"
						onfocus="focusBox(this)" onBlur="blurBox(this)"></p>
				</c:if>
				<p class="topmarginShort"><font size="5">カゴ内の商品</font></p>
				<c:if test="${noCart != null}">
					<p class="topmarginShort"><font size="6">カゴは空です</font></p>
				</c:if>
				<table class="table">
					<tr>
						<c:set var="cnt">
							0
						</c:set>
						<c:forEach var="item" items="${cart}">
							<td class="table-img cellSize">
								<div class="box">
									<a class="noLine" href="CartItemDetail?id=${item.id}&count=${item.count}&fromData=${fromData == true}">
										<img src="${item.fileName}" width="440px" height="220px">
										<br>
										<font color="black">${item.itemName}</font>
										<br>
										<font color="red"><fmt:formatNumber value="${item.price}" pattern="###,###円" /></font>
										<font color="black">　×　${item.count}個　＝　</font>
										<font size="5" color="red"><fmt:formatNumber value="${item.countPrice}" pattern="###,###円" /></font>
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
			</form>
			<c:if test="${noCart == null}">
				<p><a href="CartAllDelete?fromData=${fromData == true}">
					<input class="button btn-danger"type="button" value="カゴを空にする" onClick="return deleteCheck()">
				</a></p>
			</c:if>
		</div>

		<jsp:include page="footer.jsp" flush="true" />
	</div>
</body>

</html>