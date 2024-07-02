<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <link rel="stylesheet" href="http://localhost:8888/post/css/write.css?after" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        .no-click {
            pointer-events: none;
        }
        .category-select, .topic-select {
            width: 100%;
            font-size: 0.9rem; /* 카테고리 글자 크기 조정 */
        }
        #title {
            border: none;
            border-bottom: 2px solid black;
            font-size: 1.5rem;
        }
        @media (min-width: 768px) {
            #title {
                font-size: 2rem;
            }
        }
        .category-container {
            width: 50%;
        }
        .container{
            margin-top: 50px;
        }
    </style>
</head>
<body>
<jsp:include page="/navbar4.jsp"></jsp:include>

<div class="container">
    <div class="row category-container">
        <div class="col-md-6 form-group">
            <select class="form-control category-select" id="category">
                <option selected value="0">카테고리</option>
            </select>
        </div>
        <div class="col-md-6 form-group">
            <select class="form-control topic-select" id="topic">
                <option selected value="0">주제</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <input type="text" class="form-control my-lg-0" id="title" placeholder="제목을 입력하세요">
    </div>
    <div id="content" class="form-group"></div>
    <div class="editor_tag px-3"></div>
    <div class="d-flex justify-content-end px-2 mb-3">
        <button id="submitBtn" class="btn btn-outline-dark"> 업로드 </button>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>

<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
    <script>
        function getHashtags() {
            let tags = [];
            $('.editor_tag .txt_tag').each(function() {
                var tagValue = $(this).data('tag');
                tags.push(tagValue);
            });
            return tags;
        }

        $(document).ready(function() {
            const { Editor } = toastui;

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

            const editor = new Editor({
                el: document.querySelector('#content'),
                height: '500px',
                initialEditType: 'wysiwyg',
                placeholder: "내용을 입력해주세요.",
                previewStyle: 'vertical',
                hideModeSwitch: true,
                hooks: {
                    addImageBlobHook: function(blob, callback) {
                        const formData = new FormData();
                        formData.append('file', blob);
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
                },
                customHTMLRenderer: customHTMLRenderer()
            });

            $('#submitBtn').click(function() {
                const category = $('#category').val();
                if (category == 0) {
                    alert("카테고리를 선택하세요");
                    return;
                }
                const topic = $('#topic').val();

                const title = $('#title').val();
                if (title.length < 2) {
                    alert("제목은 두글자 이상으로 작성해주세요.");
                    return;
                }
                const content = editor.getMarkdown();
                if (content.length < 5) {
                    alert("내용은 다섯글자 이상으로 작성해주세요.");
                    return;
                }
                const hashtags = getHashtags();

                const postData = {
                    title: title,
                    category_id: category,
                    topic_id: topic,
                    content: content,
                    hashtags: hashtags
                };

                $.ajax({
                    url: '/api/posts/write',
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
    <script>
        let categoryMap = new Map();
        categoryMap.set(0, [{id: 0, name: "주제"}]);
        $(document).ready(function() {
            $.ajax({
                url: '/categories',
                type: 'GET',
                success: function (categories) {
                    categories.forEach(function (category) {
                        const categorySelect = $('#category');
                        const categoryId = category.id;
                        const categoryName = category.name;
                        categorySelect.append($('<option>', {value: categoryId, text: categoryName}));

                        let topics = [];
                        category.topics.forEach(function(topic) {
                            topics.push({id: topic.id, name: topic.name});
                            categoryMap.set(categoryId, topics);
                        });
                    });
                }
            });
        });

        function fetchTopics(categoryId) {
            const topicSelect = $('#topic');
            topicSelect.empty();

            let topics = categoryMap.get(categoryId);
            topics.forEach(function(topic) {
                topicSelect.append($('<option>', {value: topic.id, text: topic.name}));
            });
        }

        $('#category').change(function() {
            let selectedCategoryId = parseInt($(this).val());
            fetchTopics(selectedCategoryId);
        });
    </script>
    <script src="http://localhost:8888/post/js/hashtag.js"></script>
</div>
</body>
</html>
