<%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-28
  Time: 오전 5:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <h1>회원가입 page</h1>
    <form id="registerForm" action="/register" method="post">
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email">
        <button type="button" id="checkEmail">이메일 중복검사</button><br>
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password"><br>
        <input type="password" id="password_confirm"><br>
        <label for="nickname">Nickname:</label><br>
        <input type="text" id="nickname" name="nickname">
        <button type="button" id="checkNickname">닉네임 중복검사</button><br>
        <label for="image">Image:</label><br>
        <input type="text" id="image" name="image"><br>
        <label for="about_me">About Me:</label><br>
        <textarea id="about_me" name="about_me"></textarea><br>
        <input type="submit" value="Submit">
    </form>
    <script>
        var isEmailAvailable = false;
        var isPasswordValid = false;

        const validateEmail = (email) => {
            const regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return regex.test(email);
        };

        $(document).ready(function(){
            $("#checkEmail").click(function(){
                var email = $("#email").val();
                if(validateEmail(email)) {
                    $.ajax({
                        url: '/checkEmail',
                        data: {email: email},
                        type: 'GET',
                        success: function(response){
                            if(response == 'AVAILABLE'){
                                alert("사용 가능한 이메일 주소입니다.");
                                isEmailAvailable = true;
                            } else {
                                alert("이미 등록된 이메일 주소입니다.");
                                isEmailAvailable = false;
                            }
                        },
                        error: function(error){
                            console.log(error);
                        }
                    });
                } else {
                    alert("올바른 이메일 주소 형식이 아닙니다.");
                }
            });

            $(document).ready(function() {
                $("#checkNickname").click(function () {
                    var nickname = $("#nickname").val();
                    $.ajax({
                        url: '/checkNickname',
                        data: {nickname: nickname},
                        type: 'GET',
                        success: function (response) {
                            if (response == 'AVAILABLE') {
                                alert("사용 가능한 닉네임입니다.");
                                isNicknameAvailable = true;
                            } else {
                                alert("이미 사용중인 닉네임입니다.");
                                isNicknameAvailable = false;
                            }
                        },
                        error: function (error) {
                            console.log(error);
                        }
                    });
                });
            });

            $("#password_confirm").change(function(){
                var password = $("#password").val();
                var password_confirm = $("#password_confirm").val();
                if (password.length > 0) {
                    if (password === password_confirm) {
                        $("#password_confirm").css("border", "1px solid green");
                        isPasswordValid = true;
                    } else {
                        $("#password_confirm").css("border", "1px solid red");
                        isPasswordValid = false;
                    }
                }
            });

            $("#registerForm").submit(function(e){
                if(!isEmailAvailable){
                    e.preventDefault();
                    alert("이메일 중복 검사를 통과해야 합니다.");
                } else if (!isPasswordValid) {
                    e.preventDefault();
                    alert("비밀번호가 다릅니다.");
                } else if (!isNicknameAvailable) {
                    e.preventDefault();
                    alert("닉네임 중복 검사를 통과해야 합니다.");
                }
            });

        });
    </script>
</body>
</html>
