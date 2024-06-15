<%@ page import="java.util.Objects" %>
<%@ page import="com.blog.tech.domain.member.dto.response.MemberResponseBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <title>Tech Blog</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="/css/leftsidebar.css">
    <link rel="stylesheet" href="/css/rightsidebar.css">
    <style>
        #posts-container {
            height: 500px; /* 필요한 높이로 설정 */
            overflow-y: auto;
        }
    </style>
    <style>
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
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light mb-4" style="background-color: #686D76;">
    <a class="navbar-brand ms-3" href="#">Tech Blog</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <form class="d-flex my-2 my-lg-0">
        <input class="form-control me-2 nickname" type="text" placeholder="Nickname"/>
        <button class="btn btn-light button" type="button" onclick="profile();">Search</button>
    </form>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ms-auto mx-3">
            <% if (Objects.isNull(session.getAttribute("member"))) { %>
            <li class="nav-item active">
                <a class="nav-link btn btn-outline-light me-2 login-btn" href="/login">Login</a>
            </li>
            <li class="nav-item">
                <a class="btn btn-outline-light login-btn" href="/register">Sign-up</a>
            </li>
            <% } else { %>
            <%
                final MemberResponseBean member = (MemberResponseBean) session.getAttribute("member");
                final Long id = member.id();
                final String nickname = member.nickname();
                final String image = member.image();
            %>
            <li class="nav-item">
                <a class="btn btn-light button" href="/api/posting">포스팅</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="profileDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
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
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-3">
            <div id="nav-bar">
                <input id="nav-toggle" type="checkbox"/>
                <div id="nav-header"><a id="nav-title" href="https://codepen.io" target="_blank"><i class="fab fa-codepen"></i>카테고리</a>
                    <label for="nav-toggle"><span id="nav-toggle-burger"></span></label>
                    <hr/>
                </div>
                <ul id="nav-content" class="list-group"></ul>
            </div>
        </div>
        <div class="col-lg-6 container">
            <div id="posts-container" class="mt-5"></div>
        </div>
        <div class="col-lg-3">
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
