<%@ page import="java.util.Objects" %>
<%@ page import="com.blog.tech.domain.member.dto.response.MemberResponseBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <title>Tech Blog</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="/css/leftsidebar.css">
    <link rel="stylesheet" href="/css/rightsidebar.css">
        <style>
            #posts-container {
                height: 500px; /* 필요한 높이로 설정 */
            }
        </style>
    <style>
        .post { margin-bottom: 20px; padding: 20px; border: 1px solid #ddd; border-radius: 5px; cursor: pointer; transition: box-shadow 0.3s; }
        .post:hover { box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
        .post .profile { display: flex; align-items: center; margin-bottom: 10px; }
        .post .profile img { border-radius: 50%; width: 50px; height: 50px; margin-right: 10px; }
        .post .profile .name { font-weight: bold; }
        .post .profile .date { color: #777; font-size: 0.9em; margin-left: 10px; }
        .post .content { margin-top: 10px; word-wrap: break-word; overflow: hidden; max-height: 300px; position: relative; }
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
</head>
<body>

<jsp:include page="/css/navbar.jsp" />

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-4">
            <div id="nav-bar">
                <input id="nav-toggle" type="checkbox"/>
                <div id="nav-header"><a id="nav-title" href="https://codepen.io" target="_blank"><i class="fab fa-codepen"></i>카테고리</a>
                    <label for="nav-toggle"><span id="nav-toggle-burger"></span></label>
                    <hr/>
                </div>
                <ul id="nav-content" class="list-group"></ul>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="d-flex justify-content-center mt-3">
                <% if (Objects.isNull(session.getAttribute("member"))) { %>
                <img src="http://localhost:8888/upload/images/profile/profile.png" alt="Profile Image" class="rounded-circle" style="width: 40px; height: 40px;">
                <% } else { %>
                <%
                    final MemberResponseBean member = (MemberResponseBean) session.getAttribute("member");
                    final String image = member.image();
                %>
                <img src="<%=image%>" alt="Profile Image" class="rounded-circle" style="width: 40px; height: 40px;">
                <% } %>
                <div style="margin-left: 15px;"></div>
                <a class="btn btn-outline-dark nav-btn btn-lg w-100" style="height: 40px; font-size: 14px; line-height: 1.5; padding: 8px 12px; text-align: left; color: gray; border: 1px solid #ced4da;" href="/api/posting">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"></path>
                        <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"></path>
                    </svg>&nbsp;&nbsp;어떤 이야기를 나누고 싶나요?</a>
            </div><br>
            <hr style="background-color:#EAEAEC;">
            <div id="posts-container" class="mt-5"> </div>
        </div>
        <div class="col-lg-4">
            <div id="nav-bar-right">
                <input id="nav-toggle-right" type="checkbox"/>
                <div id="nav-header-right"><a id="nav-title-right" href="https://codepen.io" target="_blank">C<i class="fab fa-codepen"></i>DEPEN</a>
                    <label for="nav-toggle-right"><span id="nav-toggle-burger-right"></span></label>
                    <hr/>
                </div>
                <div id="nav-content-right">
                    <div class="nav-button-right"><i class="fas fa-palette"></i><span>Your Work</span></div>
                    <div class="nav-button-right"><i class="fas fa-images"></i><span>Assets</span></div>
                    <div class="nav-button-right"><i class="fas fa-thumbtack"></i><span>Pinned Items</span></div>
                    <hr/>
                    <div class="nav-button-right"><i class="fas fa-heart"></i><span>Following</span></div>
                    <div class="nav-button-right"><i class="fas fa-chart-line"></i><span>Trending</span></div>
                    <div class="nav-button-right"><i class="fas fa-fire"></i><span>Challenges</span></div>
                    <div class="nav-button-right"><i class="fas fa-magic"></i><span>Spark</span></div>
                    <hr/>
                    <div class="nav-button-right"><i class="fas fa-gem"></i><span>Codepen Pro</span></div>
                    <div id="nav-content-highlight-right"></div>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="topics"></div>

<script>
    function profile() {
        var nickname = document.querySelector('.nickname').value;
        var url = "/profile/@" + nickname;
        window.location.href = url;
    }
    $(document).ready(function() {
        $.ajax({
            url: '/categories',
            type: 'GET',
            success: function (categories) {
                categories.forEach(function (category) {
                    var categoryElement = $('<li class="list-group-item category" id="category-' + category.id + '">' +
                        '<a class="d-flex justify-content-between align-items-center" data-bs-toggle="collapse" href="#collapse-' + category.id + '" role="button" aria-expanded="false" aria-controls="collapse-' + category.id + '">' +
                        '<span>' + category.name + '</span>' +
                        '<i class="fas fa-chevron-down"></i></a>' +
                        '<ul class="collapse list-group list-group-flush" id="collapse-' + category.id + '"></ul>' +
                        '</li>');
                    $('#nav-content').append(categoryElement);
                });
                $('#nav-content').append($('<div id="nav-content-highlight"></div>'));
            }
        });
    });
    $(document).on('click', '.category a', function() {
        var categoryId = $(this).closest('.category').attr('id').split('-')[1];
        var collapseElement = $('#collapse-' + categoryId);
        var categoryElement = $(this).closest('.category');

        if (collapseElement.hasClass('show')) {
            collapseElement.collapse('hide');
        } else {
            $('.collapse').collapse('hide');
            collapseElement.collapse('show');

            $.ajax({
                url: '/topics',
                data: {category_id: categoryId},
                type: 'GET',
                success: function(topics) {
                    collapseElement.empty();
                    topics.forEach(function(topic) {
                        var topicElement = $('<li class="list-group-item topic" id="topic-' + topic.id + '">' +
                            topic.name + '</li>');
                        collapseElement.append(topicElement);
                    });
                }
            });
        }
    });
</script>
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
            window.location.href = '/post/getPost.jsp?post_id=' + postId;
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
</body>
</html>