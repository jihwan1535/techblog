<%@ page import="java.util.Objects" %>
<%@ page import="com.blog.tech.domain.member.dto.response.MemberResponseBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
  <style>
    .nav-btn {
      background-color: white;
      color: black;
      border-color: gray;
    }
    .navbar {
      position: fixed;
      top: 0;
      width: 100%;
      z-index: 1000; /* 다른 요소들 위에 표시되도록 z-index 설정 */
    }
    .no-caret::after {
      display: none
    }
    body {
      padding-top: 56px; /* 네비게이션 바 높이만큼 패딩 추가 */
    }
  </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light mb-4" style="background-color: #ffffff; border-bottom: 1px solid #ced4da; padding-left: 190px">
  <a class="navbar-brand ms-3" href="/main">Tech Blog</a>
  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <form class="d-flex my-2 my-lg-0">
    <input class="form-control me-2 nickname" type="text" placeholder="Nickname"/>
    <button class="btn btn-outline-dark nav-btn rounded-pill" type="button" onclick="profile();">Search</button>
  </form>
  <div class="collapse navbar-collapse ms-auto" id="navbarSupportedContent">
    <ul class="navbar-nav ms-auto mx-3">
      <% if (Objects.isNull(session.getAttribute("member"))) { %>
      <li class="nav-item active">
        <button class="btn btn-outline-dark me-2 nav-btn rounded-pill login-btn">Login</button>
      </li>
      <li class="nav-item" style="padding-right: 190px;">
        <button class="btn btn-outline-dark nav-btn rounded-pill sign-btn">Sign-up</button>
      </li>
      <% } else { %>
      <%
        final MemberResponseBean member = (MemberResponseBean) session.getAttribute("member");
        final Long id = member.id();
        final String nickname = member.nickname();
        final String image = member.image();
      %>
      <li class="nav-item">
        <a class="btn btn-outline-dark nav-btn rounded-pill" style="margin-top: 4px; margin-right: 5px;" href="/api/posting">포스팅</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle no-caret" href="#" id="Notification" role="button" data-bs-toggle="dropdown" aria-expanded="false" style=" margin-top: 2px;">
          <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="gray" class="bi bi-bell-fill" viewBox="0 0 16 16">
            <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2m.995-14.901a1 1 0 1 0-1.99 0A5 5 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901"/>
          </svg>
        </a>
        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="Notification">
          <li><a class="dropdown-item" href="#">옵션1</a></li>
          <li><a class="dropdown-item" href="#">옵션2</a></li>
        </ul>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle no-caret" href="#" id="profileDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="padding-right: 190px">
          <img src="<%=image%>" alt="Profile Image" class="rounded-circle" style="width: 30px; height: 30px;">
        </a>
        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileDropdown" style="margin-right: 190px">
          <li><a class="dropdown-item" href="/profile/@<%=nickname%>"> 마이페이지</a></li>
          <li><a class="dropdown-item" href="/api/logout">로그아웃</a></li>
        </ul>
      </li>
      <% } %>
    </ul>
  </div>
</nav>
<div id="modalContainer"></div>
<div id="loginModalContainer"></div>
<script src="/js/RegisterModal.js"></script>
<script src="/js/LoginModal.js"></script>
</body>
</html>