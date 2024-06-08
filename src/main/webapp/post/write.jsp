<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
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
            const editor = new toastui.Editor({
                el: document.querySelector('#content'),
                height: '500px',
                initialEditType: 'wysiwyg',
                placeholder: "내용을 입력해주세요.",
                previewStyle: 'vertical',
                hideModeSwitch: true,
                hooks: {
                    addImageBlobHook: function(blob, callback) {
                        const formData = new FormData();
                        formData.append('image', blob);

                        fetch('/api/uploader/images/posts', {
                            method: 'POST',
                            body: formData
                        }).then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            return response.text();
                        }).then(url => {
                            callback(url, 'alt text');
                        }).catch(error => {
                            console.error('There has been a problem with your fetch operation:', error);
                        });
                    }
                }
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
                    topicSelect.empty(); // Clear previous topics
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
        });
    </script>
</div>
</body>
</html>
