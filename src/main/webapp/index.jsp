<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<form action="join" method="post">
    <label for="email">Email:</label><br>
    <input type="email" id="email" name="email"><br>
    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password"><br>
    <label for="nickname">Nickname:</label><br>
    <input type="text" id="nickname" name="nickname"><br>
    <label for="image">Image:</label><br>
    <input type="text" id="image" name="image"><br>
    <label for="about_me">About Me:</label><br>
    <textarea id="about_me" name="about_me"></textarea><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>