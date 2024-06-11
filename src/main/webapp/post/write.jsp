<%@ page import="java.util.Objects" %>
<%@ page import="com.blog.tech.domain.member.dto.response.MemberResponseBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <link rel="stylesheet" href="https://uicdn.toast.com/tui-editor/latest/tui-editor-contents.css">

    <style>
        .nav-btn {
            background-color: white !important;
            color: black !important;
        }
        .no-click {
            pointer-events: none;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light mb-4" style="background-color: #686D76;">
    <a class="navbar-brand ms-3" href="/main">Tech Blog</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <form class="d-flex my-2 my-lg-0">
        <input class="form-control me-2 nickname" type="text" placeholder="Nickname"/>
        <button class="btn btn-light button" type="button" onclick="profile();">Search</button>
    </form>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ms-auto">
            <% if (Objects.isNull(session.getAttribute("member"))) { %>
            <li class="nav-item active">
                <a class="nav-link btn btn-outline-light me-2 nav-btn" href="/login">Login</a>
            </li>
            <li class="nav-item">
                <a class="nav-link btn btn-outline-light nav-btn" href="/register">Sign-up</a>
            </li>
            <% } else { %>
            <%
                final MemberResponseBean member = (MemberResponseBean) session.getAttribute("member");
                final Long id = member.id();
                final String nickname = member.nickname();
                final String image = member.image();
            %>
            <li class="nav-item">
                <a class="nav-link btn btn-light me-3 nav-btn" href="/api/posting">포스팅</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link me-3 dropdown-toggle" href="#" id="profileDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="<%=image%>" alt="Profile Image" class="rounded-circle" style="width: 30px; height: 30px;">
                </a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileDropdown">
                    <li><a class="dropdown-item" href="/settings">설정</a></li>
                    <li><a class="dropdown-item" href="/notifications">알림</a></li>
                    <li><a class="dropdown-item" href="/api/logout">로그아웃</a></li>
                </ul>
            </li>
            <% } %>
        </ul>
    </div>
</nav>

<div class="container">
    <h2 class="text-center mt-4">Hello, TOAST UI Editor!</h2>

    <div class="form-group">
        <label for="title">제목</label>
        <input type="text" class="form-control" id="title" placeholder="제목을 입력해 주세요.">
    </div>

    <div class="form-group">
        <label for="category">카테고리</label>
        <select class="form-control" id="category"> </select>
    </div>

    <div class="form-group">
        <label for="topic">토픽</label>
        <select class="form-control" id="topic"> </select>
    </div>

    <div id="content" class="form-group"></div>
    <button id="submitBtn" class="btn btn-primary">작성</button><br><br>

    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
    <script>
        $(document).ready(function() {
            const { Editor } = toastui;

            // 커스텀 렌더러 플러그인 작성
            function customHTMLRenderer() {
                return {
                    htmlBlock: {
                        div(node) {
                            if (node.attrs && node.attrs.class === 'uploaded-file') {
                                return [
                                    { type: 'openTag', tagName: 'div', outerNewLine: true, attributes: node.attrs },
                                    { type: 'html', content: node.childrenHTML },
                                    { type: 'closeTag', tagName: 'div', outerNewLine: true }
                                ];
                            }
                            return [
                                { type: 'openTag', tagName: 'div', outerNewLine: true },
                                { type: 'html', content: node.childrenHTML },
                                { type: 'closeTag', tagName: 'div', outerNewLine: true }
                            ];
                        }
                    }
                };
            }

            // toast-ui Editor 초기화
            const editor = new Editor({
                el: document.querySelector('#content'),
                height: '500px',
                initialEditType: 'wysiwyg',
                placeholder: "",
                previewStyle: 'vertical',
                hideModeSwitch: true,
                customHTMLRenderer: customHTMLRenderer()
            });

            function fetchCategories() {
                $.get('/categories', function(data) {
                    const categorySelect = $('#category');
                    data.forEach(function(item, index) {
                        categorySelect.append($('<option>', {
                            value: item.id,
                            text: item.name
                        }));
                        if (index === 0) {
                            fetchTopics(item.id);
                        }
                    });
                });
            }

            function fetchTopics(categoryId) {
                $.get('/topics?category_id=' + categoryId, function(data) {
                    const topicSelect = $('#topic');
                    topicSelect.empty();
                    data.forEach(function(item) {
                        topicSelect.append($('<option>', {
                            value: item.id,
                            text: item.name
                        }));
                    });
                });
            }

            $('#category').change(function() {
                const categoryId = $(this).val();
                if (categoryId) {
                    fetchTopics(categoryId);
                }
            });

            $('#submitBtn').click(function() {
                const title = $('#title').val();
                const category = $('#category').val();
                const topic = $('#topic').val();
                const content = editor.getMarkdown();

                const postData = {
                    title: title,
                    category_id: category,
                    topic_id: topic,
                    content: content
                };

                $.ajax({
                    url: '/api/post/write',
                    method: 'POST',
                    data: postData,
                    success: function(data) {
                        alert('게시글이 성공적으로 작성되었습니다.');
                        window.location.href = "/main";
                    },
                    error: function(error) {
                        console.error('Error posting data:', error);
                    }
                });
            });

            fetchCategories();

            $('#content').on('dragover', function(event) {
                event.preventDefault();
                event.stopPropagation();
            });

            $('#content').on('drop', function(event) {
                event.preventDefault();
                event.stopPropagation();
                const files = event.originalEvent.dataTransfer.files;
                if (files.length > 0) {
                    const file = files[0];
                    uploadFile(file, editor);
                }
            });

            function uploadFile(file) {
                const formData = new FormData();
                formData.append('file', file);

                fetch('/api/uploader/files/posts', {
                    method: 'POST',
                    body: formData
                }).then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text();
                }).then(url => {
                    let html = '<div class="upload-file container">' +
                        '<a href="' + url + '" class="list-group-item list-group-item-action flex-column align-items-start border rounded mt-3 no-click" style="text-decoration: none; color: inherit; width: 40%">' +
                        '<div class="d-flex w-100 justify-content-start">' +
                        '<i class="fas fa-file fa-3x mr-3" style="margin-top: 13px;"></i>' +
                        '<div> <h5 class="mb-1">' + file.name + '</h5>' +
                        '<small>' + (file.size / 1024).toFixed(2) + 'KB </small> </div> </div> </a> </div>';
                    const currentHtml = editor.getHTML();
                    editor.setHTML(currentHtml + html);
                }).catch(error => {
                    console.error('파일 업로드 중 문제가 발생했습니다:', error);
                });
            }
        });
    </script>
</div>
</body>
</html>
