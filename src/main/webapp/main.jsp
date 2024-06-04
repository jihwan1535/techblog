<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2024-06-03
  Time: 오후 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="css/leftsidebar.css">
    <link rel="stylesheet" href="css/rightsidebar.css">

    <style>
        .login-btn {
            background-color: white !important;
            color: black !important;
        }
        .popup-container{
            display: block;
            top: 0; left : 0; width:50%; height:50%;
        }
        .popup{
            background-color: #fff;
            width: 80%;
            max-width: 500px;
            margin: 40px auto;
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

<nav class="navbar navbar-expand-lg navbar-light mb-4" style="background-color: #686D76;">
    <a class="navbar-brand ms-3" href="#">Tech Blog</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <form class="d-flex my-2 my-lg-0">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-light" type="submit">Search</button>
    </form>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ms-auto mx-3">
            <li class="nav-item active">
                <a class="nav-link btn btn-outline-light me-2 login-btn" href="/member/login.jsp">Login</a>
            </li>
            <li class="nav-item">
                <button type="button" class="btn btn-outline-light login-btn" data-target="#modal" data-toggle="#modal" onclick="openPopup()">Sign-up</button>
            </li>
        </ul>
    </div>
</nav>
<div id="nav-bar">
    <input id="nav-toggle" type="checkbox"/>
    <div id="nav-header"><a id="nav-title" href="https://codepen.io" target="_blank">C<i class="fab fa-codepen"></i>DEPEN</a>
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

<div class="popup-container" id="popupContainer"></div>

<script>
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
