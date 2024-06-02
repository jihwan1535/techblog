<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-30
  Time: 오전 4:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        .popup-container{
            display: block;
            top: 0; left : 0; width:50%; height:50%;
        }
        .popup{
            background-color: #fff;
            width: 80%;
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            border-radius: 10px;
            border: 3px solid black;
            align-content: center;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }
    </style>
</head>
<body>
<!-- nav_bar -->
<nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapse" data-toggle="collapse"
                        data-target="#bs-example-navbar-callapse-1" aria-expanded="false">
                    <span class="sr-only"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <a class="navbar-brand" href="main">TechBlog</a>
            <!-- login/logout 기능에서 사용하므로 실제 main 에서는 session 을 false 시키는게 좋을듯 -->
            <div class="ml-auto d-flex">
                <button type="button" class="btn btn-outline-dark me-2"
                        data-target="#modal" data-toggle="#modal" onclick="openPopup()">회원가입</button>
                <%if (Objects.isNull(session.getAttribute("member"))) {%>
                <button type="button" class="btn btn-outline-dark" onclick="location.href='/login'">로그인</button>
                <%} else { %>
                <button type="button" class="btn btn-outline-dark" onclick="location.href='/api/logout'">로그아웃</button>
                <%}%>
            </div>
        </div>
</nav>
<!-- 블로거 찾기 -->
<div class="mt-3 container">
    <div class="row justify-content-end">
        <div class="col-md-6">
            <div class="text-end">
            <p class="fs-5 fw-bold"> 블로거 찾기 </p>
            <input class="nickname" id="nickname" type="text" placeholder="닉네임을 입력해주세요."/>
            <input class="button" type="button" onclick="profile();" value="button"/>
            </div>
        </div>
    </div>
</div>

<div class="popup-container" id="popupContainer"></div>

<script>
    function profile() {
        var nickname = document.querySelector('.nickname').value;
        var url = "/profile/@" + nickname;
        window.location.href = url;
    }
    function openPopup() {
        $.ajax({
            url: "/member/register.jsp",
            method: "GET",
            success: function(data) {
                $("#popupContainer").html(data);
                document.getElementById("popupContainer").style.display = "block";
                document.getElementById("page1").style.display = "block";
                document.getElementById("page2").style.display = "none"
            }
        });
    }

    function nextPage() {
        document.getElementById("page1").style.display = "none";
        document.getElementById("page2").style.display = "block";
    }

    function prevPage() {
        document.getElementById("page1").style.display = "block";
        document.getElementById("page2").style.display = "none";
    }
</script>
</body>
</html>