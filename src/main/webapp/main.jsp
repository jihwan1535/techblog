<%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-30
  Time: 오전 4:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>Tech Blog</title>
</head>
<body>
    <a href="/register">회원가입</a>
    <a href="/login">로그인</a>
    <br>
    <input class="nickname" type="text"/>
    <input class="button" type="button" onclick="profile();" value="button"/>
    <script>
        function profile() {
            var nickname = document.querySelector('.nickname').value;
            var url = "/profile/@" + nickname;
            window.location.href = url;
        }
    </script>
</body>
</html>
