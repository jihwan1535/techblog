<%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-30
  Time: 오후 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<html>
<body>
<%
    final String alertMessage = (String)request.getAttribute("alert");
%>
<script>
    var message = "<%= alertMessage %>";
    alert(message);
    window.location.href = '/main';
</script>
</body>
</html>
