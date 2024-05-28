<%@ page import="com.blog.tech.domain.member.dto.response.LoginResponseBean" %><%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-28
  Time: 오전 5:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    final Long memberId = (Long)session.getAttribute("member_id");
    final String nickname = (String)session.getAttribute("member_nickname");
%>
<h1><%= memberId%>번 째 회원</h1>
<p><%= nickname %>님 로그인에 성공하셨습니다.</p>
<br>
<a href="../index.jsp">메인으로 돌아가기</a>
</body>
</html>
