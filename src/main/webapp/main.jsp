<%@ page import="java.util.Objects" %>
<%@ page import="com.blog.tech.domain.member.dto.response.MemberResponseBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <title>Tech Blog</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="/css/mainLoad.css">
</head>
<body>

<jsp:include page="/css/navbar.jsp" />

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-4">
            <div id="nav-bar">
                <ul id="nav-content" class="list-group"></ul>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="d-flex justify-content-center mt-3" style="padding-top: 30px">
                <% if (Objects.isNull(session.getAttribute("member"))) { %>
                <img src="http://localhost:8888/upload/images/profile/profile.png" alt="Profile Image" class="rounded-circle" style="width: 40px; height: 40px;">
                <% } else { %>
                <%
                    final MemberResponseBean member = (MemberResponseBean) session.getAttribute("member");
                    final String image = member.image();
                %>
                <img src="<%=image%>" alt="Profile Image" class="rounded-circle" style="width: 40px; height: 40px;">
                <% } %>
                <div style="margin-left: 15px;"></div>
                <a class="btn btn-outline-dark nav-btn btn-lg w-100" style="height: 40px; font-size: 14px; line-height: 1.5; padding: 8px 12px; text-align: left; color: gray; border: 1px solid #ced4da;" href="/api/posting">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"></path>
                        <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"></path>
                    </svg>&nbsp;&nbsp;어떤 이야기를 나누고 싶나요?</a>
            </div><br>
            <hr style="background-color:#EAEAEC;">
            <div id="posts-container" class="mt-5"> </div>
        </div>
        <div class="col-lg-4">
            <div id="nav-bar-right">
                <input id="nav-toggle-right" type="checkbox"/>
                <div id="nav-header-right"><a id="nav-title-right" href="#" target="_blank">해시태그</a>
                    <label for="nav-toggle-right"><span id="nav-toggle-burger-right"></span></label>
                    <hr/>
                </div>
                <div id="nav-content-right">
                    <div id="hashtag-container" class="mt-3"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/js/mainLoad.js"></script>
<script src="/js/post.js"></script>

</body>
</html>
