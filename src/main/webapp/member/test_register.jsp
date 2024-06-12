<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-06-04
  Time: 오후 6:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
<!-- join modal 1 -->
<div class="modal fade" id="signUpModal1" tabindex="-1" role="dialog" aria-labelledby="signUpModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="signUpModalLabel">회원가입</h1>
                <button id="closeBtn" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form class="needs-validation" novalidate id="registerForm" action="./test_register.jsp" method="post">
                    <div class="row">
                        <label for="email">EMAIL</label><br>
                    </div>
                    <div class="row">
                        <div class="col-sm-8">
                            <input class="form-control" type="email" id="email" name="email">
                        </div>
                        <div class="col-sm-4">
                            <button class="btn btn-success" type="button" id="checkEmail">이메일중복검사</button><br>
                        </div>
                    </div>
                    <div class="row">
                        <label id="invalid-feedback-email" class="form-text">이메일을 입력해주세요.</label>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <label for="password">비밀번호</label><br>
                            <input class="form-control" type="password" id="password" name="password"><br>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <label for="password_confirm">비밀번호 확인</label><br>
                            <input class="form-control" type="password" id="password_confirm">
                            <label id="invalid-feedback" class="form-text">비밀번호를 입력해주세요.</label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="nextBtn" class="btn btn-secondary float-right"
                        data-bs-target="#signUpModal2" data-bs-toggle="modal" disabled>다음으로</button>
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>
<!-- join modal2 -->
<div class="modal fade" id="signUpModal2" tabindex="-1" role="dialog" aria-labelledby="signUpModalLabel2" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="signUpModalLabel2">회원가입</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times</span>
                </button>
            </div>
            <div class="modal-body">
                <form class="needs-validation" id="registerForm2" action="./test_register.jsp" method="post">
                    <div class="mb-3 row">
                        <label for="nickname" class="form-label">Nickname</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" id="nickname" name="nickname">
                        </div>
                        <div class="col-sm-4">
                            <button class="btn btn-success" type="button" id="checkNickname">닉네임중복검사</button>
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <label class="form-label" for="about_me">About Me</label>
                        <div class="col-12">
                            <textarea class="form-control" id="about_me" name="about_me"></textarea>
                            <label class="form-text" id="guide_about_me">나자신을 소개할 강력한 문구를 한줄로 채워주세요.</label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary me-2" data-bs-target="#signUpmodal1" data-bs-toggle="modal">이전</button>
                <button id="joinBtn" type="submit" class="btn btn-primary" value="Submit">가입완료</button>
            </div>
        </div>
    </div>
</div>
<script>
    /* form1 */
    var isEmailAvailable = false;
    var isPasswordValid = false;
    var isEmailValid = false;
    var isCheckingEmail = false;
    var email;
    var password;

    const validateRegisEmail = (email) => {
        const regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return regex.test(email);
    };

    $(document).on('click', '#checkEmail', function (){
        $("#checkEmail").click(function () {
            email = $("#email").val();
            if (validateRegisEmail(email)) {
                if (!isCheckingEmail){
                    isCheckingEmail = true;
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
                            inputValidation();
                        },
                        error: function (error) {
                            console.log(error);
                        },
                        complete: function () {
                            isCheckingEmail = false;
                        }
                    });
                }
            } else {
                $("#email").css("border", "2px solid red");
                alert("올바른 이메일 주소 형식이 아닙니다.");
            }
        });
    });

    function inputValidation() {
        var emailCheck = $("#email").val();
        var passwordCheck = $("#password").val();
        var password_confirm = $("#password_confirm").val();

        if (passwordCheck.length > 0) {
            isPasswordValid = passwordValid(passwordCheck, password_confirm);
        }
        if (emailCheck.length > 0){
            isEmailValid = emailValidLabel(isEmailAvailable);
        }
        updateNextBtn();
    }

    function passwordValid(password, password_confirm) {
        if (password === password_confirm) {
            $("#password").css("border", "2px solid green");
            $("#password_confirm").css("border", "2px solid green");
            $("#invalid-feedback").text("비밀번호가 일치합니다.").css("color", "green");
            return true;
        }
        $("#password").css("border", "2px solid red");
        $("#password_confirm").css("border", "2px solid red");
        $("#invalid-feedback").text("비밀번호가 다릅니다.").css("color", "red");
        return false;
    }

    function emailValidLabel(emailVal){
        if (!emailVal) {
            $("#email").css("border", "2px solid red");
            $("#invalid-feedback-email").text("사용할 수 없는 이메일 입니다.").css("color", "red");
        } else {
            $("#email").css("border", "2px solid green");
            $("#invalid-feedback-email").text("사용가능한 이메일 입니다.").css("color", "green");
            return true;
        }
        $("#invalid-feedback-email").text("이메일을 입력해주세요.").css("color", "black");
    }

    /* 폼의 입력값 변화 이벤트 : 입력 값 변화시 매번 검증 해야 함 */
    $('#password, #password_confirm').on('input', function () {
        inputValidation();
    });

    $('#email').on('input', function (){
        inputValidation();
    });

    function updateNextBtn(){
       if (isEmailValid && isPasswordValid){
           $('#nextBtn').attr('disabled', false);
       } else{
           $('#nextBtn').attr('disabled', true);
       }
    }

    /* 모달 초기화 */
    $('#signUpModal1').on('hidden.bs.modal', function (e) {
        $(this).find('form')[0].reset()
    });

    /* 다음으로 버튼 눌렀을 시 */
    $('#nextBtn').click(function (){
        /* 비밀번호 저장 */
        password = $('#password').val();
    })
</script>

<script>
    /* form2 */
    const defaultImageUrl = "http://localhost:8888\\upload\\images\\profile.png"

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
            error: function (error){
                console.error(error);
            }
        });
    });

    $("#joinBtn").click(function (e){
        /* 데이터 수집 */
        var form1Data = $('#registerForm').serialize();
        var form2Data = $('#registerForm2').serialize();

        /* 합치기 */
        var concatData = form1Data + '&' + form2Data;

        $.ajax({
            url: '/register',
            data: concatData,
            type: 'POST',
            success: function (response){
                console.log(response);
            }
        });
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

    /*
    $('#checkEmail, #checkNickname').click(function () {
        validateForm1();
    });*/
</script>
</body>
