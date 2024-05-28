<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.blog.tech.domain.member.dto.response.RegisterResponseBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
</head>
<body>
<%
    RegisterResponseBean member = (RegisterResponseBean) request.getAttribute("register");
%>
    <h1><%= member.id()%>번 째 회원</h1>
    <p><%= member.nickname() %>님께서 <%= member.createdAt()%> 부로 가입하셨습니다.</p>
    <br>
    <a href="../index.jsp">메인으로 돌아가기</a>
</body>
</html>
