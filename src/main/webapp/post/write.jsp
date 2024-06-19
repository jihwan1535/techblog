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
    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style>
        .nav-btn {
            background-color: white;
            color: black;
            border-color: gray;
        }
        .no-click {
            pointer-events: none;
        }

        .form-group.img{
            resize: both;
            overflow: auto;
        }

        .resize-able {
            display: inline-block;
            position: relative;
        }

        .resize-handle {
            width: 10px;
            height: 10px;
            background-color: gray;
            position: absolute;
            bottom: 0;
            right: 0;
            cursor: se-resize;
            z-index: 10; /* 핸들이 이미지 위에 위치하도록 합니다 */
        }

    </style>
</head>
<body>

<jsp:include page="../global/navbar.jsp"/>
<div class="container mt-3"></div>

<div class="container">
    <h2 class="text-start mt-4 p-3">Write Your Post</h2>
    <div class="container">
        <div class="row">
            <div class="col form-group">
                <label for="category">카테고리</label>
                <select class="form-control" id="category"> </select>
            </div>

            <div class="col form-group">
                <label for="topic">토픽</label>
                <select class="form-control" id="topic"> </select>
            </div>
        </div>
    </div>

    <div class="col form-group">
        <input type="text" class="form-control my-lg-0" id="title" placeholder="제목을 입력하세요">
    </div>
    <div id="content" class="col form-group"></div>
    <div class="editor_tag px-3"></div>
    <div class="d-flex justify-content-end px-2 mb-3">
        <button id="submitBtn" class="btn btn-outline-dark"> 작성 </button>
    </div>
</div>
</body>
</html>
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script>
    function addResizeHandle(img) {
        if (img.parentElement.classList.contains('resize-able')) return;

        /* 1. resize 할 수 있는 div 태그를 생성 */
        const wrapper = document.createElement('div');
        wrapper.classList.add('resize-able');
        wrapper.style.display = 'inline-block';
        wrapper.style.position = 'relative';

        /* 2. img 부모 요소에 wrapper 삽입하여 img wrapper 안에 추가 */
        img.parentNode.insertBefore(wrapper, img);
        wrapper.appendChild(img);

        /* 3. resize 핸들 생성 후 추가 */
        const handle = document.createElement('div');
        handle.classList.add('resize-handle');
        wrapper.appendChild(handle);

        /* 4. 리사이즈 핸들에 이벤트 리스너 추가 */
        handle.addEventListener('mousedown', startResize);


        function startResize(event) {
            event.preventDefault();
            window.addEventListener('mousemove', resizeImage);
            window.addEventListener('mouseup', stopResize);

            function resizeImage(e) {
                const rect = wrapper.getBoundingClientRect();
                img.style.width = (e.clientX - rect.left) + 'px';
                img.style.height = (e.clientY - rect.top) + 'px';
            }
        }
    }

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
                            // 삽입 후 적용
                            const imgElements = document.querySelectorAll(url);
                            if (imgElements.length > 0){
                                addResizeHandle(imgElements[0]);
                            }
                        }).catch(error => {
                            console.error('There has been a problem with your fetch operation:', error);
                        });
                }
            },
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
            const hashtags = getHashtags();
            console.log(hashtags);

            const postData = {
                title: title,
                category_id: category,
                topic_id: topic,
                content: content,
                hashtags: hashtags
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
                let html = '<div class="upload-file container-fluid">' +
                    '<a href="' + url + '" class="list-group-item list-group-item-action flex-column align-items-start border rounded mt-3 no-click" style="text-decoration: none; color: inherit; width: 40%">' +
                    '<div class="d-flex justify-content-start">' +
                    '<i class="fas fa-file fa-3x mr-3" style="margin-top: 0.5rem;"></i>' +
                    '<div> <a class="mb-1">' + file.name + '</a>' +
                    '<small>' + (file.size / 1024).toFixed(2) + 'KB </small> </div> </div> </a> </div>';
                const currentHtml = editor.getHTML();
                editor.setHTML(currentHtml + html);
            }).catch(error => {
                console.error('파일 업로드 중 문제가 발생했습니다:', error);
            });
        }
    });
</script>
<script src="http://localhost:8888/post/js/hashtag.js"></script>