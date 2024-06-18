var isSignUpModalOpen = false;
var isCheckingEmail = false;
var isEmailAvailable = false;
// password 검증
var isPasswordConfirm = false;
var isCheckingNickname = false;
var isNicknameAvailable = false;

// Email 입력값 검증
const validateRegisEmail = (email) => {
    const regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regex.test(email);
};

// password 정규식 검증
const validatePassword = (password, passwordConfirm) => {
    const regex = /^(?=.*[a-zA-Z])(?=.*[0-9]).{4,12}$/;
    if (regex.test(password)) {
        $("#password").css("border", "2px solid green");
        $("#pw-span").text("사용가능한 비밀번호입니다.").css("color", "green");
    } else {
        $("#password").css("border", "2px solid red");
        $("#pw-span").text("알파벳과 숫자를 포함한 4자리 이상 12자리 이하로 입력하세요.").css("color", "red");
    }

    if (password === passwordConfirm) {
        $("#password-confirm").css("border", "2px solid green");
        $("#pw-confirm-span").text("비밀번호가 일치합니다.").css("color", "green");
        isPasswordConfirm = true;
    } else {
        $("#password-confirm").css("border", "2px solid red");
        $("#pw-confirm-span").text("비밀번호가 일치하지 않습니다.").css("color", "red");
        isPasswordConfirm = false;
    }
};

// 닉네임 정규식 검증
const validateRegisName = (nickname) => {
    const regex = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$/;
    return regex.test(nickname);
};

function updateNextBtn() {
    console.log(isEmailAvailable + ", " + isPasswordConfirm);
    if (isEmailAvailable && isPasswordConfirm) {
        $('#nextBtn').attr('disabled', false);
    } else {
        $('#nextBtn').attr('disabled', true);
    }
}


$(".sign-btn").click(function () {
    if (isSignUpModalOpen) return;
    isSignUpModalOpen = true;

    $.ajax({
        url: "/register",
        success: function (data) {
            console.log("click");
            $("#modalContainer").html(data); // 모달 콘텐츠 삽입
            $("#signUpModal1").modal("show");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("모달 콘텐츠 로드 실패: ", textStatus, errorThrown);
        },
        complete: function () {
            isSignUpModalOpen = false; // AJAX 요청 완료 후 플래그 해제
        }
    });

    // modal이 끝났을 때, 언제일까?
    // -- 모달이 닫혔을 떄 --
    // 1. 회원가입을 완료 했을 때 - 서비스 처리가 완료된 닫기
    // 2. 모달의 닫기 버튼을 눌렀을 때 - 서비스 처리를 하지 않은 닫기 1
    // 3. 모달의 바깥쪽을 눌렀을 때 - 서비스 처리를 하지 않은 닫기 2
    // isSignUpModalOpen = false; - 위의 경우에 modal 이 닫혔다고 알림.
});

$(document).on("click", '.modal-content', function (event) {
    event.stopPropagation(); // 이벤트 버블링 방지
});

$(document).on("click", '.fade', function () {
    $("#signUpModal1").remove();
    $("#signUpModal2").remove();
    $(".modal-backdrop").remove();
});

$(document).on("click", '.btn-close', function () {
    $("#signUpModal1").remove();
    $("#signUpModal2").remove();
    $(".modal-backdrop").remove();
});


// 이메일 중복검사 검증
$(document).on('click', '#checkEmail', function () {
    if (isCheckingEmail) return;
    let email = $("#email").val();
    isCheckingEmail = true;
    if (validateRegisEmail(email)) {
        $.ajax({
            url: '/checkEmail',
            data: {email: email},
            type: 'GET',
            success: function (response) {
                if (response == 'AVAILABLE') {
                    $("#email").css("border", "2px solid green");
                    alert("사용 가능한 이메일 주소입니다.");
                    $("#email-span").text("사용 가능한 이메일 주소입니다.").css("color", "green");
                    isEmailAvailable = true;
                } else {
                    $("#email").css("border", "2px solid red");
                    alert("이미 등록된 이메일 주소입니다.");
                    $("#email-span").text("이미 등록된 이메일 주소입니다.").css("color", "red");
                    isEmailAvailable = false;
                }
            },
            error: function (error) {
                console.log(error);
            },
            complete: function () {
                updateNextBtn();
                isCheckingEmail = false;
            }
        });
    } else {
        $("#email-span").text("이메일 형식이 올바르지 않습니다.").css("color", "red");
        $("#email").css("border", "2px solid red");
        alert("올바른 이메일 주소 형식이 아닙니다.");
    }
});

// 이메일 검사 후 변경되었을 시,
$(document).on('input', '#email', function () {
    isEmailAvailable = false;

    if(validateRegisEmail($("#email").val())){
        $("#email").css("border", "2px solid green")
        $("#email-span").text("올바른 이메일 형식입니다.").css("color", "green");
    }else{
        $("#email").css("border", "2px solid red");
        $("#email-span").text("이메일 형식이 올바르지 않습니다.").css("color", "red");
    }

    updateNextBtn();
});

// password 검증
$(document).on('input', '#password', function () {
    let password = $("#password").val();
    let passwordConfirm = $("#password-confirm").val();

    validatePassword(password, passwordConfirm);
    updateNextBtn();
});

// password 확인 검증
$(document).on('input', '#password-confirm', function () {
    let password = $("#password").val();
    let passwordConfirm = $("#password-confirm").val();

    validatePassword(password, passwordConfirm);
    updateNextBtn();
});

$(document).on('click', '#checkNickname', function () {
    if (isCheckingNickname) return;
    let nickname = $("#nickname").val();
    isCheckingNickname = true;

    if (validateRegisName(nickname)) {
        $.ajax({
            url: '/checkNickname',
            data: {nickname: nickname},
            type: 'GET',
            success: function (response) {
                if (response == 'AVAILABLE') {
                    $("#nickname").css("border", "2px solid green");
                    alert("사용 가능한 닉네임입니다.");
                    isNicknameAvailable = true;
                    $('#registerBtn').attr('disabled', false);
                } else {
                    $("#nickname").css("border", "2px solid red");
                    alert("이미 사용중인 닉네임입니다.");
                    isNicknameAvailable = false;
                }
            },
            error: function (error) {
                console.log(error);
            },
            complete: function () {
                isCheckingNickname = false;
            }
        });
    } else {
        $("#nickname").css("border", "2px solid red");
        isCheckingNickname = false;
    }
});

// 닉네임이 입력될 때마다 유효성 검사 초기화
$(document).on('input', '#nickname', function () {
    $("#nickname").css("border", "2px solid red");
    isCheckingNickname = false;
    $('#registerBtn').attr('disabled', true);
});

/* 글자 수 실시간 카운팅 및 초과 입력 방지 */
$(document).on('keydown', '#about_me', function (e){
    // 백스페이스, 삭제, 화살표 키는 허용
    if (e.which === 8 || e.which === 46 || (e.which >= 37 && e.which <= 40)) {
        return;
    }
    let textLen = $("#about_me").val().length;
    if (textLen >= 20) {
        alert("최대 20자까지 입력가능합니다.");
        // 경고창이 띄워지면 20자로 변경됨
        $("#about_me").val($("#about_me").val().substring(0, 20));
    } else {
        $("#countChar").text(textLen + 1); // 키 입력을 고려하여 +1
    }
});

$(document).on("click", "#registerBtn", function () {
    let email = $("#email").val();
    let password = $("#password").val();
    let nickname = $("#nickname").val();
    let aboutMe = $("#about_me").val();

    $.ajax({
        url: '/register',
        data: {
            email : email,
            password : password,
            nickname : nickname,
            aboutMe : aboutMe
        },
        type: 'POST',
        success: function (response){
            alert("회원가입이 완료되었습니다.")
            window.location.href = "/main";
        }
    });
});