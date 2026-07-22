<!-- 最初の画面 -->
<!-- メニュー、お問い合わせ、管理者 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOME</title>
<!-- cssファイル読み込み -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class= "mainPage">
<div class= "mainContainer">
<!-- 右側に画像を配置 -->
<div class= "rightArea">
	<img alt="メイン画像" src="${pageContext.request.contextPath}/images/main.png" class= "mainImg">
</div>

</div>
<h1 style="color:#783f04" >Honey Bloom
<img alt="アイコン" src="${pageContext.request.contextPath}/images/icon.png" class= "icon" >
</h1>
<p style="color:#783f04">はちみつ香る、しあわせなお菓子時間</p>

<<<<<<< HEAD
<form action="menu" method= "get">
<button type= "submit">商品ラインナップ</button>
</form>
<form action="contact" method= "get">
<button type= "submit">お問い合わせ</button>
</form>
<form action="menu" method= "get">
=======
<form action="${pageContext.request.contextPath}/menu"method= "get">
<button type= "submit">商品ラインナップ</button>
</form>
<form action="${pageContext.request.contextPath}/form"  method= "get">
<button type= "submit">お問い合わせ</button>
</form>
<form action=admin method= "get">
>>>>>>> branch 'main' of https://github.com/1019tbs/GroupB_TY.git
<button type= "submit">管理者画面</button>
</form>



</body>
</html>