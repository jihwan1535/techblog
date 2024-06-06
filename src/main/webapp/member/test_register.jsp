<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-06-04
  Time: 오후 6:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="/css/decoration.css">
</head>
<body>
<!-- join modal -->
<div class="modal" id="signUpModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="modalTitle1">회원가입</h1>
                <button id="closeBtn" type="button" class="btn-close" data-bs-dismiss="signUpModal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="registerForm" action="./test_register.jsp" method="post">
                    <label for="email">EMAIL</label><br>
                    <input type="email" id="email" name="email">
                    <button type="button" id="checkEmail">이메일 중복검사</button><br><br>

                    <label for="password">PASSWORD</label><br>
                    <input type="password" id="password" name="password"><br>
                    <input type="password" id="password_confirm"><br><br>
                </form>
            </div>
            <div class="modal-footer">
                <button id="nextBtn" class="btn btn-secondary float-right"
                        data-bs-target="#singUpModal2" data-bs-toggle="modal"
                        data-bs-dissmiss="modal">다음으로</button>
                <button type="button" class="btn btn-primary" data-bs-dismiss="signUpModal">닫기</button>
            </div>
        </div>
    </div>
</div>
<!-- join modal2 -->
<div class="modal" id="signUpModal2" tabindex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="modalTitle2">회원가입</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="registerForm2" action="./test_register.jsp" method="post">
                    <label for="nickname">Nickname</label><br>
                    <input type="text" id="nickname" name="nickname">
                    <button type="button" id="checkNickname">닉네임 중복검사</button><br><br>

                    <label for="imageButton">프로필 사진</label>
                    <button type="button" id="imageButton">이미지 선택</button>
                    <button type="button" id="defaultImageButton">기본 이미지로 변경</button>
                    <input type="file" id="imageUploader" style="display:none;"><br>
                    <div class="imagePreview" id="imagePreview">
                        <img class="profileImage" id="profileImage" alt="Profile Image" style="max-width: 200px;">
                        <input type="hidden" id="image" name="image">
                    </div><br>

                    <label for="about_me">About Me</label><br>
                    <div class="d-flex flex-row bd-highlight mb-3">
                        <div class="bd-highlight">
                            <textarea id="about_me" name="about_me"></textarea><br><br>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary me-2" data-bs-target="#signUpmodal" data-bs-toggle="modal">이전</button>
                <button id="joinBtn" type="submit" class="btn btn-success" value="Submit">가입완료</button>
            </div>
        </div>
    </div>
</div>
<!-- prev test -->
<!--
<div class="popup-container" id="popupContainer">
    <div class="popup" id="page1">
        <h3 class="text-center">회원가입</h3>
        <div class="mb-2">
            <form id="registerForm1">
                <label for="email">Email</label><br>
                <input type="email" id="email" name="email">
                <button type="button" id="checkEmail">이메일 중복검사</button><br><br>

                <label for="password">Password</label><br>
                <input class="p-1" type="password" id="password" name="password"><br>
                <input class="p-1" type="password" id="password_confirm"><br><br>
                <div class="container">
                    <div class="d-flex justify-content-end">
                        <button id="nextBtn" type="button" class="btn btn-secondary float-right" disabled>다음</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="popup" id="page2">
        <h3 class="text-center">회원가입</h3>
        <div class="mb-2">
            <form id="registerForm" action="/register" method="post">
                <label for="nickname">Nickname:</label><br>
                <input type="text" id="nickname" name="nickname">
                <button type="button" id="checkNickname">닉네임 중복검사</button><br><br>

                <label for="imageButton">프로필 사진:</label>
                <button type="button" id="imageButton">이미지 선택</button>
                <button type="button" id="defaultImageButton">기본 이미지로 변경</button>
                <input type="file" id="imageUploader" style="display:none;"><br>
                <div class="imagePreview" id="imagePreview">
                    <img class="profileImage" id="profileImage" alt="Profile Image" style="max-width: 200px;">
                    <input type="hidden" id="image" name="image">
                </div><br>

                <label for="about_me">About Me</label><br>
                <div class="d-flex flex-row bd-highlight mb-3">
                    <div class="bd-highlight">
                        <textarea id="about_me" name="about_me"></textarea><br><br>
                    </div>
                </div>
                <div class="container">
                    <div class="d-flex justify-content-end">
                        <button type="button" class="btn btn-secondary me-2" onclick="prevPage()">이전</button>
                        <button id="joinBtn" type="submit" class="btn btn-success" value="Submit">가입완료</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
-->
<script>
    var isEmailAvailable = false;
    var isPasswordValid = false;
    const defaultImageUrl = "http://localhost:8888\\upload\\images\\profile.png"

    const validateEmail = (email) => {
        const regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return regex.test(email);
    };

    $(document).ready(function () {
        $('#defaultImageButton').click();
        /* 범스 코드 */
        /* html 클릭 하거나 문서 닫을 때 */
        $(document).on('click', '.close', function() {
            $("#signUpModal").css('display', 'none');
        });
        /* 윈도우 창 클릭할 때 */
        $(window).click(function(event) {
            if ($(event.target).is("#myModal")) {
                $("#signUpModal").css('display', 'none');
            }
        });

        /* 닫기 버튼 클릭 시 */
        $("#closeBtn").click(function (){
            $("#signUpModal").css('display', 'none');
        });
    });

    $("#checkEmail").click(function () {
        var email = $("#email").val();
        if (validateEmail(email)) {
            $.ajax({
                url: '/checkEmail',
                data: {email: email},
                type: 'GET',
                success: function (response) {
                    if (response == 'AVAILABLE') {
                        alert("사용 가능한 이메일 주소입니다.");
                        isEmailAvailable = true;
                    } else {
                        alert("이미 등록된 이메일 주소입니다.");
                        isEmailAvailable = false;
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        } else {
            alert("올바른 이메일 주소 형식이 아닙니다.");
        }
    });

    $(document).ready(function () {
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

    $("#password_confirm").change(function () {
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
    /* 폼이 2개로 나누어져있는데 이는 어떻게 */
    $("#registerForm").submit(function (e) {
        if (!isEmailAvailable) {
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

    $('#imageButton').click(function () {
        $('#imageUploader').click();
    });

    $("#imageUploader").change(function () {
        var file = this.files[0];
        if (file) {
            var formData = new FormData();
            formData.append('image', file);

            $.ajax({
                url: '/uploader/image',
                data: formData,
                type: 'POST',
                contentType: false,
                processData: false,
                success: function (response) {
                    $("#profileImage").attr("src", response);
                    $("#image").val(response);
                },
                error: function (error) {
                    console.error('이미지 업로드 실패');
                }
            });
        }
    });

    $('#defaultImageButton').click(function () {
        $("#profileImage").attr("src", defaultImageUrl);
        $("#image").val(defaultImageUrl);
    });

/* 범스 코드 */
    /* 다음 버튼 활성화 조건 함수 */
    function validateForm1(){
        let email = $('#email').val();
        let password = $('#password').val();

        if(isEmailAvailable && email && password && isPasswordValid) {
            $('#nextBtn').prop('disabled', false);
        } else{
            $('#nextBtn').prop('disabled', true);
        }
    }

    /* 다음으로 버튼 클릭 시 */
    $(document).ready(function (){
        $('#nextBtn').click(function(){
            nextPage();
        });
    });
    /* 폼의 입력값 변화 이벤트 : 입력 값 변화시 매번 검증 해야 함 */
    $('#email, #password, #password_confirm').on('input', function () {
        validateForm1();
    });
    /* 유효성 검사 버튼 클릭 시 */
    $('#checkEmail, #checkNickname').click(function () {
        validateForm1();
    });

    function showModal(){
        $('signUpModal').css('display', 'block');
    }

</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossOrigin="anonymous"></script>
</body>
</html>
