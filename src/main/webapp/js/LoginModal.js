var isLoginModalOpen = false;

$(".login-btn").click(function () {
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
});