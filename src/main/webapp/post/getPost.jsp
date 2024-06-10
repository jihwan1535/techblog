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
                사용자 ID: <span id="user-id"></span> | 게시글 ID: <span id="post-id"></span>
                <br>
                조회수: <span id="view-count"></span> | 스크랩수: <span id="scrap-count"></span>
            </div>
            <div>
                <button id="edit-button" class="btn btn-primary btn-sm">수정하기</button>
                <button id="delete-button" class="btn btn-danger btn-sm">삭제하기</button>
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
            url: '/post',
            type: 'GET',
            data: {
                post_id: postId
            },
            success: function(response) {
                const jsonData = response;

                $('#post-title').text(jsonData.post_info.title);
                $('#post-date').text(jsonData.post_info.created_at);
                $('#view-count').text(jsonData.post_info.view_count);
                $('#scrap-count').text(jsonData.post_info.scrap_count);

                $('#author-name').text(jsonData.member_info.name);
                $('#author-image').attr('src', jsonData.member_info.image);
                $('#author-profile-link').attr('href', '/profile/@' + jsonData.member_info.name);

                $('#user-id').text(jsonData.member_info.id);
                $('#post-id').text(jsonData.post_info.id);

                var content = jsonData.post_info.content;

                const editor = new tui.Editor.factory({
                    el: document.querySelector('#post-content'),
                    initialValue: content,
                    viewer: true,
                    height: 'auto'
                });

                $('#edit-button').click(function() {
                    alert('수정하기 버튼 클릭');
                });

                $('#delete-button').click(function() {
                    alert('삭제하기 버튼 클릭');
                });
            },
            error: function(error) {
                console.error('Error fetching data', error);
            }
        });
    });
</script>
</body>
</html>
