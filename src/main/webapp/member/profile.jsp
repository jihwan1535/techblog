<%@ page import="com.blog.tech.domain.member.dto.response.ProfileResponseBean" %><%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-30
  Time: 오후 9:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    final ProfileResponseBean profile = (ProfileResponseBean)request.getAttribute("profile");
    final String nickname = profile.member().nickname();
    final String image = profile.member().image();
%>
<p><%= "@"+ nickname %>의 프로필</p>
<div class="imagePreview">
    <img class="profileImage" src="<%= image %>" alt="Profile Image" style="max-width: 200px;">
</div><br>
<p><%= profile.aboutMe() %> - 자기소개</p>
<p><%= profile.postCount() %> 작성한 게시글 수</p>
<br>
<a href="/main">메인으로 돌아가기</a>
</body>
</html>
