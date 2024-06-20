<%@ page import="java.util.Objects" %>
<%@ page import="com.blog.tech.domain.member.dto.response.MemberResponseBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>게시글 조회</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://uicdn.toast.com/tui-editor/latest/tui-editor.css">
    <link rel="stylesheet" href="https://uicdn.toast.com/tui-editor/latest/tui-editor-contents.css">
    <style>
        /*html, body {*/
        /*    background-color: #F5F5F7; !* 배경색 설정 *!*/
        /*}*/

        .hashtag {
            display: inline-block;
            padding: .2em .6em .3em;
            font-size: 75%;
            font-weight: 700;
            line-height: 1;
            color: #fff;
            text-align: center;
            white-space: nowrap;
            vertical-align: baseline;
            border-radius: .25em;
            background-color: #6c757d;
            margin-right: .6em;
            margin-bottom: .6em;
        }
    </style>
    <style>
        .cmt_txt_cont textarea {
            width:100%;
            height:90px;
            padding:13px;
            border:1px solid #cecdce;
            background:#fff;
            font-family:-apple-system,BlinkMacSystemFont,"Apple SD Gothic Neo","Malgun Gothic","맑은 고딕",arial,굴림,Gulim,sans-serif;
            font-size:13px;
            color:#333;
            line-height:18px
        }
        .wrt_container {
            margin-left: 240px;
            margin-right: 240px;
            padding: 12px 12px 12px;
            background: #F5F5F7;
            border-top: 2px solid #2f2f2f;
            border-bottom: 2px solid #2f2f2f;
        }
        .wrt_btn {
            background-color: white;
            color: black;
            border-color: gray;
            float: right;
        }
        .cmt_cont_bottm {
            padding-top: 5px;
            padding-bottom: 10px;
        }
        .container {
            padding-right: 150px;
            padding-left: 150px
        }
        .cmt_container {
            margin-left: 240px;
            margin-right: 240px;
            background: white;
            border-top: 2px solid #2f2f2f;
        }
    </style>
</head>
<body>

<jsp:include page="/css/navbar.jsp" />

<div class="container mt-5">
    <div class="card">
        <div class="card-header">
            <div class="d-flex align-items-center">
                <a id="author-profile-link" href="#">
                    <img id="author-image" src="" alt="Author Image" class="rounded-circle" width="50" height="50">
                </a>
                <div class="ml-3">
                    <h6 id="post-category"></h6>
                    <h2 id="post-title"></h2>
                    <small class="text-muted">작성자: <span id="author-name"></span> | 작성일: <span id="post-date"></span></small>
                </div>
            </div>
        </div>
        <div class="card-body">
            <div id="post-content"></div>
        </div>
        <div class="card-footer d-flex justify-content-between">
            <div>
                사용자 ID: <span id="user-id"></span> | 게시글 ID: <span id="post-id"></span> | 알람 <span id="alarm"></span>
                <br>
                조회수: <span id="view-count"></span> | 스크랩수: <span id="scrap-count"></span> | 댓글수: <span id="comment-count"></span>
                <br>
                태그: <div id="hashtags"></div>
            </div>
        </div>
    </div>
</div>

<div class="cmt_container mt-5">
    <div id="comments"></div>
    <div id="replies"></div>
</div>

<div class="wrt_container mt-5">
    <div class="cmt_txt_cont">
        <% if (Objects.isNull(session.getAttribute("member"))) { %>
        <div class="cmt_write">
            <textarea id="no_memo" placeholder="댓글 기능은 로그인 후 이용 가능합니다." maxlength="400"></textarea>
        </div>
        <% } else { %>
        <%
            final MemberResponseBean member = (MemberResponseBean) session.getAttribute("member");
            final String nickname = member.nickname();
        %>
        <div class="cmt_write">
            <textarea id="memo" placeholder="타인의 권리를 침해하거나 명예를 훼손하는 댓글은 운영원칙 및 관련 법률에 제재를 받을 수 있습니다." maxlength="400"></textarea>
        </div>
        <div class="cmt_cont_bottm" style="position: relative">
            <h7>작성자 : <%=nickname%></h7>
            <button type="button" id="submitBtn" class="wrt_btn rounded-pill">등록</button>
        </div>
        <% } %>
    </div>
</div><br><br>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://uicdn.toast.com/tui-editor/latest/tui-editor-Editor-full.js"></script>
<script>
    let isLoading = false;
    let postId;

    function loadAll(postId) {
        if (isLoading) return;
        isLoading = true;

        $.ajax({
            url: '/comments',
            data: {post_id: postId},
            success: function (data) {
                try {
                    const comments = data.map(item => ({
                        comment_id: item.comment_info.comment_id,
                        content: item.comment_info.content,
                        alarm: item.comment_info.alarm,
                        created_at: item.comment_info.created_at,
                        not_modified: item.comment_info.not_modified,
                        member_id: item.comment_info.member_info.id,
                        member_name: item.comment_info.member_info.name,
                        member_image: item.comment_info.member_info.image,
                        // reply_id: item.reply_infos.reply_id,
                        // reply_content: item.reply_infos.content,
                        // reply_created_at: item.reply_infos.created_at,
                        // reply_not_modified: item.reply_infos.not_modified,
                        // reply_member_id: item.reply_infos.member_info.id,
                        // reply_member_name: item.reply_infos.member_info.name,
                        // reply_member_image: item.reply_infos.member_info.image
                    }));
                    renderComments(comments);
                } catch (e) {
                    console.error('JSON parsing error:', e);
                } finally {
                    isLoading = false;
                }
            },
            error: function () {
                alert('Failed to load comments');
                isLoading = false;
            }
        });
    }

    function renderComments(comments) {
        const container = $('#comments');
        comments.forEach(comment => {
            const commentHtml = `
            <div class="comment d-flex" style="padding: 10px; border-bottom: 1px solid #ccc;" data-comment-id="\${comment.comment_id}">
                <div class="profile-photo mr-3" onClick="location.href ='/profile/@\{comment.member_name}'" style="cursor:pointer;">
                    <img src="\${comment.member_image}" alt="\${comment.member_name}" class="rounded-circle" width="25" height="25"/>
                </div>
                <div class="comment-details d-flex flex-column flex-grow-1">
                    <div class="d-flex justify-content-between align-items-center">
                        <div class="name" style="font-weight: bold; margin-left: 10px;">\${comment.member_name}</div>
                        <div class="content" style="margin-right: 500px">\${comment.content}</div>
                        <div class="d-flex align-items-center">
                            <button class="notificationButton" style="background: none; border: none; margin-bottom: 2px" data-comment-id="\${comment.comment_id}">
                                <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="gray" class="bi bi-bell-fill active-icon" viewBox="0 0 16 16">
                                    <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2m.995-14.901a1 1 0 1 0-1.99 0A5 5 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901"/>
                                </svg>
                                <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="gray" class="bi bi-bell-slash-fill inactive-icon" viewBox="0 0 16 16" style="display: none;">
                                    <path d="M5.164 14H15c-1.5-1-2-5.902-2-7q0-.396-.06-.776zm6.288-10.617A5 5 0 0 0 8.995 2.1a1 1 0 1 0-1.99 0A5 5 0 0 0 3 7c0 .898-.335 4.342-1.278 6.113zM10 15a2 2 0 1 1-4 0zm-9.375.625a.53.53 0 0 0 .75.75l14.75-14.75a.53.53 0 0 0-.75-.75z"/>
                                </svg>
                            </button>
                            <div class="date text-muted" style="font-size: 0.8em; margin-right: 10px">\${comment.created_at}</div>
                            <button type="button" class="update_btn btn btn-sm ml-2" data-comment-id="\${comment.comment_id}" style="background-color: white; color: gray; border-color: gray; margin-right: 5px">수정</button>
                            <button type="button" class="delete_btn btn btn-sm ml-2" data-comment-id="\${comment.comment_id}" style="background-color: white; color: gray; border-color: gray;">삭제</button>
                        </div>
                    </div>
                </div>
            </div>`;

            container.append(commentHtml);
        });

        $(document).on('click', '.notificationButton', function() {
            const commentId = $(this).data('comment-id');

            $(this).find('.active-icon, .inactive-icon').toggle();

        });

        $('.update_btn').click(function() {
            alert('개발중인 기능입니다.');
        });

        $(document).on('click', '.delete_btn', function() {
            const commentId = $(this).data('comment-id');
            const urlParams = new URLSearchParams(window.location.search);
            const postId = urlParams.get('post_id');

            const postData = {
                post_id: postId,
                comment_id: commentId
            };

            $.ajax({
                url: '/api/comments/unregister',
                method: 'POST',
                contentType: "application/json",
                data: JSON.stringify(postData),
                success: function (data) {
                    alert('댓글이 삭제되었습니다.');
                    window.location.href = "/post/getPost.jsp?post_id=" + postId;
                },
                error: function (error) {
                    console.error('Error commenting data:', error);
                }
            });
        });
    }

    $(document).ready(function() {
        const urlParams = new URLSearchParams(window.location.search);
        postId = urlParams.get('post_id');

        loadAll(postId)

        $.ajax({
            url: '/view/posts',
            type: 'GET',
            data: {
                post_id: postId
            },
            success: function(response) {
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

                $('#user-id').text(memberInfo.id);
                $('#post-id').text(memberInfo.id);
                if (memberInfo.alarm) {
                    alarm = "끄기";
                }
                $('#alarm').text(alarm);

                $('#view-count').text(postInfo.view_count);
                $('#scrap-count').text(postInfo.scrap_count);
                $('#comment-count').text(postInfo.comment_count + postInfo.reply_count);


                hashtags.forEach(function(tag) {
                    $('#hashtags').append('<span class="hashtag ' + tag.id + '">' + tag.tag + '</span> ');
                });
            },
            error: function(error) {
                console.error('Error fetching data', error);
            }
        });


        $('#submitBtn').click(function() {
            const content = $('#memo').val();

            const postData = {
                post_id: postId,
                content: content
            };

            $.ajax({
                url: '/api/comments',
                method: 'POST',
                contentType: "application/json",
                data: JSON.stringify(postData),
                success: function (data) {
                    alert('댓글이 등록되었습니다.');
                    window.location.href = "/post/getPost.jsp?post_id=" + postId;
                },
                error: function (error) {
                    console.error('Error commenting data:', error);
                }
            });
        });
    });

    $('#edit-button').click(function() {
        alert('수정하기 버튼 클릭');
    });

    $('#delete-button').click(function() {
        alert('삭제하기 버튼 클릭');
    });

    $(document).on('click', '.content', function() {
        const clickedComment = $(this).closest('.comment');
        const replyContainer = clickedComment.find('.reply_container');

        // Check if the reply form is already open in the clicked comment
        if (replyContainer.length === 0) {
            // Close any other open forms
            $('.reply_container').remove();

            // Add the reply form to the clicked comment
            addCommentForm(clickedComment);
        } else {
            // If the form is already open in the clicked comment, close it
            replyContainer.remove();
        }
    });

    function addCommentForm(commentElement) {
        const commentId = commentElement.data('comment-id');
        const replyContainerHtml = `
    <style>
        .cmt_txt_cont textarea {
            width: 100%;
            height: 90px;
            padding: 13px;
            border: 1px solid #cecdce;
            background: #fff;
            font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", "Malgun Gothic", "맑은 고딕", arial, 굴림, Gulim, sans-serif;
            font-size: 13px;
            color: #333;
            line-height: 18px;
        }
        .reply_container {
            margin: 10px 0;
            padding: 12px;
            background: #FFFFFF;
            border-bottom: 2px solid #2f2f2f;
        }
        .wrt_btn {
            margin-right: 5px;
            background-color: white;
            color: black;
            border-color: gray;
            float: right;
        }
        .close_btn {
            background-color: white;
            color: black;
            border-color: gray;
            float: right;
        }
        .cmt_cont_bottm {
            padding-top: 5px;
            padding-bottom: 10px;
        }
        .container {
            padding-right: 150px;
            padding-left: 150px;
        }
        .cmt_container {
            margin-left: 240px;
            margin-right: 240px;
            background: white;
            border-top: 2px solid #2f2f2f;
        }
    </style>
    <div class="reply_container">
        <div class="cmt_txt_cont">
            <% if (Objects.isNull(session.getAttribute("member"))) { %>
            <div class="cmt_write">
                <textarea id="no_reply" placeholder="대댓글 기능은 로그인 후 이용 가능합니다." maxlength="400"></textarea>
            </div>
            <div class="cmt_cont_bottm" style="position: relative">
                <button type="button" class="close_btn rounded-pill">닫기</button>
            </div>
            <% } else { %>
            <%
                final MemberResponseBean member = (MemberResponseBean) session.getAttribute("member");
                final String nickname = member.nickname();
            %>
            <div class="cmt_write">
                <textarea id="reply" placeholder="타인의 권리를 침해하거나 명예를 훼손하는 댓글은 운영원칙 및 관련 법률에 제재를 받을 수 있습니다." maxlength="400"></textarea>
            </div>
            <div class="cmt_cont_bottm" style="position: relative">
                <h7>작성자 : <%=nickname%></h7>
                <button type="button" class="close_btn rounded-pill">닫기</button>
                <button type="button" class="wrt_btn rounded-pill" data-comment-id="\${commentId}">등록</button>
            </div>
            <% } %>
        </div>
    </div>`;
        $(replyContainerHtml).insertAfter(commentElement);
    }

    $(document).on('click', '.wrt_btn', function() {
        const replyContainer = $(this).closest('.reply_container');
        const commentId = $(this).data('comment-id');
        const reply_content = $('#reply').val();

        const postData = {
            post_id: postId,
            comment_id: commentId,
            content: reply_content
        };

        $.ajax({
            url: '/api/replies',
            method: 'POST',
            contentType: "application/json",
            data: JSON.stringify(postData),
            success: function (data) {
                alert('대댓글이 등록되었습니다.');
                window.location.href = "/post/getPost.jsp?post_id=" + postId;
            },
            error: function (error) {
                console.error('Error commenting data:', error);
            }
        });

        // AJAX request to submit the comment reply can be added here

        // Close the reply form
        replyContainer.remove();
    });

    $(document).on('click', '.close_btn', function() {
        $(this).closest('.reply_container').remove();
    });
</script>
</body>
</html>
