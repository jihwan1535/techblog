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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div class="popup-container" id="popupContainer">
    <div class="popup" id="page1">
        <h3 class="text-center">회원가입</h3>
        <div class="mb-3">
            <form id="registerForm1">
                <label for="email">Email:</label><br>
                <input type="email" id="email" name="email">
                <button type="button" id="checkEmail">이메일 중복검사</button><br><br>

                <label for="password">Password:</label><br>
                <input class="p-1" type="password" id="password" name="password"><br>
                <input class="p-1" type="password" id="password_confirm"><br><br>

                <div class="container">
                    <div class="d-flex justify-content-end">
                        <button type="button" class="btn btn-secondary float-right" onclick="nextPage()">다음으로</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="popup" id="page2">
        <h3 class="text-center">회원가입</h3>
        <div class="mb-3">
            <form id="registerForm2" action="/register" method="post">
                <label for="nickname">Nickname:</label><br>
                <input type="text" id="nickname" name="nickname">
                <button type="button" id="checkNickname">닉네임 중복검사</button><br><br>

                <label for="image">Image:</label><br>
                <input type="text" id="image" name="image"><br><br>
                <label for="about_me">About Me:</label><br>
                <div class="d-flex flex-row bd-highlight mb-3">
                    <div class="bd-highlight">
                        <textarea id="about_me" name="about_me"></textarea><br><br>
                    </div>
                </div>
                <div class="container">
                    <div class="d-flex justify-content-end">
                        <button type="button" class="btn btn-secondary me-2" onclick="prevPage()">이전</button>
                        <button type="button" class="btn btn-success" onclick="submitForm()">가입 완료</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
    <script>
        function submitForm() {
            // 첫 번째 폼 데이터 수집
            var email = document.getElementById("email").value;
            var password = document.getElementById("password").value;

            // 두 번째 폼 데이터 수집
            var nickname = document.getElementById("nickname").value;
            var image = document.getElementById("image").value;
            var aboutMe = document.getElementById("about_me").value;

            // 두 번째 폼에 첫 번째 폼 데이터 추가
            document.getElementById("registerForm2").innerHTML += '<input type="hidden" name="email" value="' + email + '">';
            document.getElementById("registerForm2").innerHTML += '<input type="hidden" name="password" value="' + password + '">';

            // 두 번째 폼 제출
            document.getElementById("registerForm2").submit();
        }
    </script>
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

        function nextPage() {
            document.getElementById("page1").style.display = "none";
            document.getElementById("page2").style.display = "block";
        }

        function prevPage() {
            document.getElementById("page1").style.display = "block";
            document.getElementById("page2").style.display = "none";
        }

    </script>
</body>
</html>
