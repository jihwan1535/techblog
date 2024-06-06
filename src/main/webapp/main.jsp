<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2024-06-03
  Time: 오후 12:30
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="css/leftsidebar.css">
    <link rel="stylesheet" href="css/rightsidebar.css">
    <link rel="stylesheet" href="css/decoration.css">
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-light mb-4" style="background-color: #686D76;">
    <a class="navbar-brand ms-3" href="main">Tech Blog</a>
    <form class="d-flex my-2 my-lg-0">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-dark" type="submit">Search</button>
    </form>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ms-auto mx-3">
            <li class="nav-item active">
                <%if (Objects.isNull(session.getAttribute("member"))) {%>
                <button class="btn btn-outline-dark me-1" onclick="location.href='member/login.jsp'">Login</button>
                <%} else {%>
                <button class="btn btn-outline-dark me-1" onclick="location.href='/api/logout'">Logout</button>
                <%}%>
            </li>
            <li class="nav-item">
                <button id="joinBtn" type="button" class="btn btn-outline-dark" data-bs-target="#signUpModal" data-bs-toggle="modal">Sign-up</button>
            </li>
        </ul>
    </div>
</nav>
<!--
<div id="nav-bar">
    <input id="nav-toggle" type="checkbox"/>
    <div id="nav-header"><a id="nav-title" href="https://codepen.io" target="_blank">C<i class="fab fa-codepen"></i>CODEPEN</a>
        <label for="nav-toggle"><span id="nav-toggle-burger"></span></label>
        <hr/>
    </div>
    <div id="nav-content">
        <div class="nav-button"><i class="fas fa-palette"></i><span>Your Work</span></div>
        <div class="nav-button"><i class="fas fa-images"></i><span>Assets</span></div>
        <div class="nav-button"><i class="fas fa-thumbtack"></i><span>Pinned Items</span></div>
        <hr/>
        <div class="nav-button"><i class="fas fa-heart"></i><span>Following</span></div>
        <div class="nav-button"><i class="fas fa-chart-line"></i><span>Trending</span></div>
        <div class="nav-button"><i class="fas fa-fire"></i><span>Challenges</span></div>
        <div class="nav-button"><i class="fas fa-magic"></i><span>Spark</span></div>
        <hr/>
        <div class="nav-button"><i class="fas fa-gem"></i><span>Codepen Pro</span></div>
        <div id="nav-content-highlight"></div>
    </div>
</div>

<div id="nav-bar-right">
    <input id="nav-toggle-right" type="checkbox"/>
    <div id="nav-header-right"><a id="nav-title-right" href="https://codepen.io" target="_blank">C<i class="fab fa-codepen"></i>DEPEN</a>
        <label for="nav-toggle-right"><span id="nav-toggle-burger-right"></span></label>
        <hr/>
    </div>
    <div id="nav-content-right">
        <div class="nav-button-right"><i class="fas fa-palette"></i><span>Your Work</span></div>
        <div class="nav-button-right"><i class="fas fa-images"></i><span>Assets</span></div>
        <div class="nav-button-right"><i class="fas fa-thumbtack"></i><span>Pinned Items</span></div>
        <hr/>
        <div class="nav-button-right"><i class="fas fa-heart"></i><span>Following</span></div>
        <div class="nav-button-right"><i class="fas fa-chart-line"></i><span>Trending</span></div>
        <div class="nav-button-right"><i class="fas fa-fire"></i><span>Challenges</span></div>
        <div class="nav-button-right"><i class="fas fa-magic"></i><span>Spark</span></div>
        <hr/>
        <div class="nav-button-right"><i class="fas fa-gem"></i><span>Codepen Pro</span></div>
        <div id="nav-content-highlight-right"></div>
    </div>
</div>
-->
<!-- 모달 -->
<div id="modalContainer"></div>

<script>
    $(document).ready(function() {
        /* 회원 가입 버튼 클릭 시 */
        $("#joinBtn").click(function() {
            $.get('./member/test_register.jsp', function(data) {
                $("#modalContainer").html(data);
                $("#signUpModal").show();
            });
        });
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>