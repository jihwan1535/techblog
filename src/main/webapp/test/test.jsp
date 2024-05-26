<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.blog.tech.domain.member.dto.response.RegisteredMemberBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Result</title>
</head>
<body>
<h1>Registration Result</h1>
<%
    RegisteredMemberBean member = (RegisteredMemberBean) request.getAttribute("register");
    if (member != null) {
%>
<p>Id: <%= member.id() %></p>
<p>Email: <%= member.email() %></p>
<p>Password: <%= member.password() %></p>
<%
} else {
%>
<p>No member registered.</p>
<%
    }
%>
</body>
</html>
