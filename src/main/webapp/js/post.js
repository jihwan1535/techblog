let isLoading = false;

function loadPosts(postId, topicId, categoryId) {
    console.log(postId, topicId, categoryId)
    if (isLoading) return;
    isLoading = true;

    $.ajax({
        url: '/posts',
        data: {
            post_id: postId,
            topic_id: topicId,
            category_id: categoryId
        },
        success: function (data) {
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

                if (posts.length > 0) {
                    setLastPostId(posts[posts.length - 1].post_id);
                    renderPosts(posts);
                }
            } catch (e) {
                console.error('JSON parsing error:', e);
            } finally {
                isLoading = false;
            }
        },
        error: function () {
            alert('게시글을 불러올 수 없습니다.');
            isLoading = false;
        }
    });
}

function renderPosts(posts) {
    const container = $('#posts-container');
    posts.forEach(post => {
        const postHtml = `
                    <div class="post" id="${post.post_id}">
                        <div class="profile">
                            <a id="author-profile-link" href="/profile/@${post.member_name}">
                                <img src="${post.member_image}" alt="${post.member_name}"/>
                            </a>
                            <div>
                                <div class="name">@${post.member_name}님의 포스트</div>
                                <div class="date">${post.created_at}</div>
                            </div>
                        </div>
                        <h3>${post.title}</h3>
                        <div class="content">${post.content}</div>
                        <div class="stats">
                            <div class="left">댓글: ${post.comment_count + post.reply_count}</div>
                            <div class="right">조회수: ${post.view_count} | 스크랩수: ${post.scrap_count}</div>
                        </div>
                    </div>
                `;

        container.append(postHtml);
    });

    $('.post .content a').click(function (event) {
        event.preventDefault();
    });

    $('.post').click(function () {
        const postId = $(this).attr('id');
        window.location.href = '/post/getPost.jsp?post_id=' + postId;
    });

}