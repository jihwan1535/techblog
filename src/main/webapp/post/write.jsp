<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <style>
        .no-click {
            pointer-events: none;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
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
    <button id="submitBtn" class="btn btn-primary">작성</button>

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
                placeholder: "내용을 입력해주세요.",
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
                    contentType: "application/json",
                    data: JSON.stringify(postData),
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
