<%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-28
  Time: 오전 5:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>회원가입 page</h1>
    <form action="/register" method="post">
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email"><br>
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password"><br>
        <label for="nickname">Nickname:</label><br>
        <input type="text" id="nickname" name="nickname"><br>
        <label for="image">Image:</label><br>
        <input type="text" id="image" name="image"><br>
        <label for="about_me">About Me:</label><br>
        <textarea id="about_me" name="about_me"></textarea><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
