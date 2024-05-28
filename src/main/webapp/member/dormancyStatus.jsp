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
    final LoginResponseBean member = (LoginResponseBean) request.getAttribute("login");
%>
<h1>휴면 계정</h1>
<p><%= member.nickname() %>님은 <%= member.status()%> 상태 입니다.</p>
<p>휴면 해제 하시겠습니까?</p> <!-- todo member status 변경 작업 -->
<br>
<a href="../index.jsp">메인으로 돌아가기</a>
</body>
</html>
