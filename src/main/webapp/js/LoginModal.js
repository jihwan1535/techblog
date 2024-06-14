var isLoginModalOpen = false;

$(".login-btn").click(function () {
    console.log(isLoginModalOpen)
    if (isLoginModalOpen) return;
    isLoginModalOpen = true;

    $.ajax({
        url: "/member/login.jsp",
        success: function (data) {
            console.log("click");
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

$(document).on("click", '.modal-content', function (event) {
    event.stopPropagation(); // 이벤트 버블링 방지
});

