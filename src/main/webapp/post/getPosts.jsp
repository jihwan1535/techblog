<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Posts</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            padding-top: 50px;
        }
        .post {
            margin-bottom: 20px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            cursor: pointer; /* 포스트 클릭 가능하게 변경 */
        }
        .post:hover {
            background-color: #f0f0f0; /* 포스트 hover 효과 추가 */
        }
    </style>
    <script>
        let lastPostId = Number.MAX_SAFE_INTEGER;
        let isLoading = false;

        function loadPosts() {
            if (isLoading) return;
            isLoading = true;

            $.ajax({
                url: '/posts',
                data: {
                    post_id: lastPostId
                },
                success: function(data) {
                    try {
                        let cleanedData = JSON.stringify(data);
                        const posts = JSON.parse(cleanedData);

                        if (posts.length > 0) {
                            lastPostId = posts[posts.length - 1].post_id;
                            renderPosts(posts);
                        }
                    } catch (e) {
                        console.error('JSON parsing error:', e);
                    } finally {
                        isLoading = false;
                    }
                },
                error: function() {
                    alert('Failed to load posts');
                    isLoading = false;
                }
            });
        }

        function renderPosts(posts) {
            const container = $('#posts-container');
            posts.forEach(post => {
                const postHtml = `<div class="post" id=` + post.post_id + `>
                    <h3>` + post.post_title + `</h3>
                <p>작성일:` + post.post_created_at + `</p>
                <p>작성자:` + post.member_name + `</p>
                <img src=` + post.member_image + ` id=` + post.member_id + ` alt=`+post.member_name+`
                            class="img-thumbnail" style="width: 50px; height: 50px;">
                    </div>
                `;
                container.append(postHtml);
            });

            $('.post').click(function() {
                const postId = $(this).attr('id');
                window.location.href = 'getPost.jsp?post_id=' + postId;
            });
        }

        $(document).ready(function() {
            loadPosts();

            $(window).scroll(function() {
                if ($(window).scrollTop() + $(window).height() >= $(document).height() - 10) {
                    loadPosts();
                }
            });
        });
    </script>
</head>
<body>
<div class="container">
    <div id="posts-container" class="mt-5"></div>
</div>
</body>
</html>
