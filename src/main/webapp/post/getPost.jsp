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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://uicdn.toast.com/tui-editor/latest/tui-editor-Editor-full.js"></script>
    <link rel="stylesheet" href="http://localhost:8888/post/css/view.css">
</head>
<body>

<jsp:include page="/navbar4.jsp" />

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
                조회수: <span id="view-count"></span> | 스크랩수: <span id="scrap-count"></span> | <a>알림 <span id="alarm"></span></a>
                <br>
                <div id="hashtags"></div>
            </div>
        </div>
    </div>

    <div class="mt-5">
        <h5>댓글 (<span id="comment-count"></span>)</h5>
        <div id="comments-section" class="list-group">
        </div>
    </div>

    <div class="mt-5 write-container">
    </div>
</div>
<script src="/post/js/hashtag.js"></script>
<script src="/post/js/postLoad.js"></script>
</body>
</html>
