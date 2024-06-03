<%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-28
  Time: 오전 5:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<html>
<head>
    <title>로그인</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <link rel="stylesheet" href="/Styleseet_bootstrap/deco.css">
    <style>
        body{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .login-form{
            max-width: 420px;
            width: 100%;
            padding: 15px 15px 15px 15px;
            margin: auto;
        }
    </style>
</head>
<body>
    <form class="login-form" action="/login" method="post">
        <h1 class="text-center">TechBlog</h1>
        <br><br>
        <div class="d-flex-column bd-highlight mb-2">
            <div class="bd highlight">
                <label for="email" class="form-label">ID</label><br>
                <input class="form-control" type="email" id="email" name="email" placeholder="1234@google.com"><br>
            </div>
            <div class="bd highlight">
                <label for="password" class="form-label">Password</label><br>
                <input class="form-control" type="password" id="password" name="password">
            </div>
            <br>
            <div class="bd highlight">
                <div class="justify-content-end">
                    <button class="btn btn-lg btn-secondary btn-block w-100" type="submit" value="Submit">로그인</button>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
