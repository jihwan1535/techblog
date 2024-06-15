<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시물</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
    <style>
        body { padding-top: 50px; }
        .post { margin-bottom: 20px; padding: 20px; border: 1px solid #ddd; border-radius: 5px; cursor: pointer; transition: box-shadow 0.3s; }
        .post:hover { box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
        .post .profile { display: flex; align-items: center; margin-bottom: 10px; }
        .post .profile img { border-radius: 50%; width: 50px; height: 50px; margin-right: 10px; }
        .post .profile .name { font-weight: bold; }
        .post .profile .date { color: #777; font-size: 0.9em; margin-left: 10px; }
        .post .content { margin-top: 10px; word-wrap: break-word; overflow: hidden; max-height: 200px; position: relative; }
        .post .content:after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            height: 50px;
            background: linear-gradient(to bottom, transparent, white);
            display: block;
        }
        .post .stats { display: flex; justify-content: space-between; margin-top: 10px; font-size: 0.9em; color: #555; }
        .post .stats .left { flex: 1; text-align: left; }
        .post .stats .right { flex: 1; text-align: right; }
    </style>
    <script>
        let lastPostId = Number.MAX_SAFE_INTEGER;
        let isLoading = false;

        function loadPosts() {
            if (isLoading) return;
            isLoading = true;

            $.ajax({
                url: '/posts',
                data: { post_id: lastPostId },
                success: function(data) {
                    console.log(data);
                    try {
                        const posts = data.map(item => ({
                            post_id: item.post_info.post_id,
                            title: item.post_info.title,
                            content: item.post_info.content,
                            view_count: item.post_info.view_count,
                            scrap_count: item.post_info.scrap_count,
                            comment_count: item.post_info.comment_count,
                            reply_count: item.post_info.reply_count,
                            created_at: item.post_info.created_at,
                            member_id: item.member_info.id,
                            member_name: item.member_info.name,
                            member_image: item.member_info.image
                        }));
                        console.log(posts);

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
                const postHtml = `
                    <div class="post" id="\${post.post_id}">
                        <div class="profile">
                            <a id="author-profile-link" href="/profile/@\${post.member_name}">
                                <img src="\${post.member_image}" alt="\${post.member_name}"/>
                            </a>
                            <div>
                                <div class="name">@\${post.member_name}님의 포스트</div>
                                <div class="date">\${post.created_at}</div>
                            </div>
                        </div>
                        <h3>\${post.title}</h3>
                        <div class="content">\${post.content}</div>
                        <div class="stats">
                            <div class="left">댓글: \${post.comment_count + post.reply_count}</div>
                            <div class="right">조회수: \${post.view_count} | 스크랩수: \${post.scrap_count}</div>
                        </div>
                    </div>
                `;
                container.append(postHtml);
            });

            $('.post .content a').click(function(event) {
                event.preventDefault();
            });

            $('.post').click(function() {
                const postId = $(this).attr('id');
                window.location.href = '/view/posts?post_id=' + postId;
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
