function myComment(commentInfo, edit) {
    return `<div class="list-group-item no-border">
                                <div class="d-flex justify-content-between">
                                    <div class="d-flex align-items-center">
                                        <a href="/profile/@${commentInfo.member_info.name}">
                                            <img src="${commentInfo.member_info.image}" alt="User Image" class="rounded-circle" width="50" height="50">
                                        </a>
                                        <div class="ml-3">
                                            <h5>${commentInfo.member_info.name}</h5>
                                        </div>
                                    </div>
                                    <div class="dropdown">
                                        <a class="btn dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <i class="fas fa-ellipsis-h"></i>
                                        </a>
                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item" href="#">수정 (준비중)</a>
                                            <a class="dropdown-item" href="#">삭제 (준비중)</a>
                                            <a class="dropdown-item" href="#">알림끄기 (준비중)</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex">
                                    <div class="ml-5 mt-2">
                                        <p>${commentInfo.content}</p>
                                    </div>
                                </div>
                                <div class="d-flex justify-content-start ml-5">
                                    <small class="text-muted">${commentInfo.created_at}</small>
                                    <a href="#" class="btn btn-sm">답글 쓰기</a>
                                </div>
                                <hr>
                            </div>`;
}

function notMyComment(commentInfo, edit) {
    return `<div class="list-group-item no-border">
                                <div class="d-flex justify-content-between">
                                    <div class="d-flex align-items-center">
                                        <a href="/profile/@${commentInfo.member_info.name}">
                                            <img src="${commentInfo.member_info.image}" alt="User Image" class="rounded-circle" width="50" height="50">
                                        </a>
                                        <div class="ml-3">
                                            <h5>${commentInfo.member_info.name}</h5>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex">
                                    <div class="ml-5 mt-2">
                                        <p>${commentInfo.content}</p>
                                    </div>
                                </div>
                                <div class="d-flex justify-content-start ml-5">
                                    <small class="text-muted">${commentInfo.created_at}</small>
                                    <a href="#" class="btn btn-sm">답글</a>
                                </div>
                                <hr>
                            </div>`;
}

function myReply(reply, edit) {
    return `<div class="list-group-item no-border ml-5">
                                    <div class="d-flex justify-content-between">
                                        <div class="d-flex align-items-center">
                                            <span class="reply-marker">ㄴ</span>
                                            <a href="/profile/@${reply.member_info.name}">
                                                <img src="${reply.member_info.image}" alt="User Image" class="rounded-circle" width="40" height="40">
                                            </a>
                                            <div class="ml-3">
                                                <h6>${reply.member_info.name}${edit}</h6>
                                            </div>
                                        </div>
                                        <div class="dropdown">
                                            <a class="btn dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <i class="fas fa-ellipsis-h"></i>
                                            </a>
                                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                <a class="dropdown-item" href="#">수정 (준비중)</a>
                                                <a class="dropdown-item" href="#">삭제 (준비중)</a>
                                                <a class="dropdown-item" href="#">알림끄기 (준비중)</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="ml-5 mt-2">
                                            <p>${reply.content}</p>
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-start ml-5">
                                        <small class="text-muted">${reply.created_at}</small>
                                    </div>
                                    <hr>
                                </div>`;
}

function notMyReply(reply, edit) {
    return `<div class="list-group-item no-border ml-5">
                                    <div class="d-flex justify-content-between">
                                        <div class="d-flex align-items-center">
                                            <span class="reply-marker">ㄴ</span>
                                            <a href="/profile/@${reply.member_info.name}">
                                                <img src="${reply.member_info.image}" alt="User Image" class="rounded-circle" width="40" height="40">
                                            </a>
                                            <div class="ml-3">
                                                <h6>${reply.member_info.name}${edit}</h6>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="d-flex">
                                        <div class="ml-5 mt-2">
                                            <p>${reply.content}</p>
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-start ml-5">
                                        <small class="text-muted">${reply.created_at}</small>
                                    </div>
                                    <hr>
                                </div>`;
}

let isLoading = false;
var postId = 0;
let myMemberId = 0;

$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    postId = urlParams.get('post_id');
    console.log(postId);
    $.ajax({
        url: '/auth',
        success: function (data) {
            myMemberId = parseInt(data.member_id);
            console.log(myMemberId);
        }
    })

    $.ajax({
        url: '/view/posts',
        type: 'GET',
        data: {
            post_id: postId
        },
        success: function (response) {
            const memberInfo = response.member_info;
            const postInfo = response.post_info;
            const hashtags = response.hashtags;
            let content = postInfo.content;
            let alarm = "설정";

            $('#post-category').text(postInfo.category + "/" + postInfo.topic);
            $('#post-title').text(postInfo.title);
            $('#author-image').attr('src', memberInfo.image);
            $('#author-name').text(memberInfo.name);
            $('#post-date').text(postInfo.created_at);
            $('#author-profile-link').attr('href', '/profile/@' + memberInfo.name);

            const editor = new tui.Editor.factory({
                el: document.querySelector('#post-content'),
                initialValue: content,
                viewer: true,
                height: 'auto'
            });

            if (myMemberId > 0) {
                $('.write-container').append(`
                <form >
                <div class="form-group d-flex">
                    <a href="#" class="mr-3">
                        <img src="${memberInfo.image}" alt="프로필 사진" class="rounded-circle" style="width: 50px; height: 50px;">
                    </a>
                    <textarea class="form-control flex-grow-1" id="comment-content" rows="3" placeholder="댓글을 입력하세요..." style="resize: none;"></textarea>
                </div>
                <div class="text-right">
                    <button type="submit" class="btn btn-secondary" id="comment-form">댓글 작성</button>
                </div></form>`);
            }

            if (memberInfo.alarm) {
                alarm = "끄기";
            }
            $('#alarm').text(alarm);

            $('#view-count').text(postInfo.view_count);
            $('#scrap-count').text(postInfo.scrap_count);
            $('#comment-count').text(postInfo.comment_count + postInfo.reply_count);
            commentCount = postInfo.comment_count + postInfo.reply_count;

            hashtags.forEach(function (tag) {
                $('#hashtags').append('<span class="hashtag ' + tag.id + '">' + tag.tag + '</span> ');
            });
        },
        error: function (error) {
            console.error('Error fetching data', error);
        }
    });

    $('#edit-button').click(function () {
        alert('수정하기 버튼 클릭');
    });

    $('#delete-button').click(function () {
        alert('삭제하기 버튼 클릭');
    });

    function loadComments(postId) {
        console.log(postId);
        $.ajax({
            url: `/comments?post_id=` + postId,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                var commentsSection = $('#comments-section');
                commentsSection.empty();

                data.forEach(function (comments) {
                    const commentInfo = comments.comment_info;
                    let edit = "";
                    if (!commentInfo.not_modified) {
                        edit = '<span class="is-edit"> (수정됨)</span>';
                    }

                    if (parseInt(commentInfo.member_info.id) == myMemberId) {
                        commentsSection.append(myComment(commentInfo, edit));
                    } else {
                        commentsSection.append(notMyComment(commentInfo, edit));
                    }

                    comments.reply_infos.forEach(function (reply) {
                        let edit = "";
                        if (!reply.not_modified) {
                            edit = '<span class="is-edit"> (수정됨)</span>';
                        }

                        if (parseInt(commentInfo.member_info.id) == myMemberId) {
                            commentsSection.append(myReply(commentInfo, edit));
                        } else {
                            commentsSection.append(notMyReply(commentInfo, edit));
                        }
                    });
                });
            },
            error: function (err) {
                console.error("댓글 로드 실패:", err);
            }
        });
    }

    $(document).on("click", "#comment-form", function (event) {
        event.preventDefault();
        const content = $('#comment-content').val();

        $.ajax({
            url: '/api/comments',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                post_id: postId,
                content: content
            }),
            success: function () {
                $('#comment-content').val('');
                loadComments(postId);
                let commentCount = parseInt($('#comment-count').text());
                $('#comment-count').text(commentCount + 1);
            },
            error: function (err) {
                console.error("댓글 작성 실패:", err);
            }
        });
    });

    loadComments(postId);

});