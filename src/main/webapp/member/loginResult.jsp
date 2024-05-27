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
    LoginResponseBean member = (LoginResponseBean) request.getAttribute("login");
%>
<h1><%= member.id()%>번 째 회원</h1>
<p><%= member.nickname() %>님께서는 <%= member.status()%> 상태 입니다.</p>
<br>
<a href="../index.jsp">메인으로 돌아가기</a>
</body>
</html>
