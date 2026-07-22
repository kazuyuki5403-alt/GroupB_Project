<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/form/submit"
      method="post">

    <!-- お問い合わせを表す隠し項目 -->
    <input type="hidden"
           name="genre"
           value="contact">

    <label>お名前</label>
    <input type="text"
           name="customerName"
           value="${customerName}">

    <label>メールアドレス</label>
    <input type="email"
           name="email"
           value="${email}">

    <label>電話番号</label>
    <input type="text"
           name="phone"
           value="${phone}">

    <label>件名</label>
    <input type="text"
           name="subject"
           value="${subject}">

    <label>お問い合わせ内容</label>
    <textarea name="message">${message}</textarea>

    <p style="color:red;">
        ${errorMsg}
    </p>

    <button type="submit">
        お問い合わせを送信
    </button>

</form>
</body>
</html>