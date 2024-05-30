<%@ page import="com.blog.tech.domain.member.dto.response.ProfileResponseBean" %><%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-30
  Time: 오후 9:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    final ProfileResponseBean member = (ProfileResponseBean)request.getAttribute("profile");
%>
<p><%= member.nickname() %>의 프로필</p>
<p><%= member.image() %> - 프로필 사진</p>
<p><%= member.aboutMe() %> - 자기소개</p>
<p><%= member.postCount() %> 작성한 게시글 수</p>
<br>
<a href="/main">메인으로 돌아가기</a>
</body>
</html>
