<%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-06-08
  Time: 오후 4:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>게시글 조회</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://uicdn.toast.com/tui-editor/latest/tui-editor.css">
    <link rel="stylesheet" href="https://uicdn.toast.com/tui-editor/latest/tui-editor-contents.css">
    <style>
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
</head>
<body>
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

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://uicdn.toast.com/tui-editor/latest/tui-editor-Editor-full.js"></script>
<script>
    $(document).ready(function() {
        const urlParams = new URLSearchParams(window.location.search);
        const postId = urlParams.get('post_id');

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
    });

    $('#edit-button').click(function() {
        alert('수정하기 버튼 클릭');
    });

    $('#delete-button').click(function() {
        alert('삭제하기 버튼 클릭');
    });
</script>
</body>
</html>
