var isLoginModalOpen = false;

$(".login-btn").click(function (event) {
    if (isLoginModalOpen) return;
    isLoginModalOpen = true;

    $.ajax({
        url: "/login",
        type: 'GET',
        success: function (data) {
            $("#loginModalContainer").html(data); // 모달 콘텐츠 삽입
            $("#loginModal").modal("show");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("모달 콘텐츠 로드 실패: ", textStatus, errorThrown);
        },
        complete: function () {
            isLoginModalOpen = false; // AJAX 요청 완료 후 플래그 해제
        }
    });

    $(document).on("click", "#loginBtn", function (event) {
        event.preventDefault();
        let email = $("#login_email").val();
        let password = $("#login_password").val();

        $.ajax({
            url: '/openapi/login',
            data: {
                email : email,
                password : password,
            },
            type: 'POST',
            success: function (){
                alert("로그인이 완료되었습니다.");
                window.location.href = "/main";
            }, error: function () {
                $("#login_email").css("border", "2px solid red");
                $("#login_password").css("border", "2px solid red");
                alert("아이디 또는 비밀번호를 확인하세요");
            }
        });
    });

});