let selectedCategoryId = 0;
let selectedTopicId = 0;
let lastPostId = Number.MAX_SAFE_INTEGER;

function setLastPostId(id) {
    lastPostId = id;
}

$(document).ready(function() {
    function profile() {
        var nickname = document.querySelector('.nickname').value;
        var url = "/profile/@" + nickname;
        window.location.href = url;
    }

    loadPosts(lastPostId, selectedTopicId, selectedCategoryId);

    $(window).scroll(function() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 10) {
            loadPosts(lastPostId, selectedTopicId, selectedCategoryId);
        }
    });

    $.ajax({
        url: '/categories',
        type: 'GET',
        success: function (categories) {
            const navContent = $('#nav-content');
            const allPostsElement = $('<li class="list-group-item category" id="category-all" style="border: none; color: #575757; font-size: 1.2em; cursor: pointer;">' +
                '<a class="d-flex justify-content-between align-items-center" role="button" style="border: none; width: 100%;">' +
                '<span style="margin-right: auto; margin-left: 10px;">전체 게시글 보기</span>' +
                '</a> </li>');

            allPostsElement.click(function() {
                selectedCategoryId = 0;
                selectedTopicId = 0;
                $(".post").remove();
                loadPosts(Number.MAX_SAFE_INTEGER, selectedTopicId, selectedCategoryId);
            });

            navContent.append(allPostsElement);

            categories.forEach(function (category) {
                var categoryElement = $('<li class="list-group-item category" id="category-' + category.id + '" style="border: none; color: #575757;">' +
                    '<a class="d-flex justify-content-between align-items-center" role="button" style="border: none; width: 100%;">' +
                    '<span style="margin-right: auto; margin-left: 10px;">' + category.name + '</span>' +
                    '</a>' +
                    '<ul class="list-group list-group-flush" id="collapse-' + category.id + '" style="border: none;"></ul>' +
                    '</li>');

                categoryElement.click(function() {
                    selectedCategoryId = category.id;
                    selectedTopicId = 0;
                    $(".post").remove();
                    loadPosts(Number.MAX_SAFE_INTEGER, selectedTopicId, selectedCategoryId);
                });

                navContent.append(categoryElement);

                var collapseElement = $('#collapse-' + category.id);
                category.topics.forEach(function(topic) {
                    var dot = '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-dot" viewBox="0 0 16 16">' +
                        '<path d="M8 9.5a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3"/>' +
                        '</svg>';
                    var topicElement = $('<li class="list-group-item topic" id="topic-' + topic.id + '" style="border: none; color: #575757; display: flex; align-items: center;">' +
                        '<a class="d-flex justify-content-between align-items-center" role="button" style="border: none; width: 100%;">' +
                        '<span style="margin-right: auto;">' + dot + topic.name + '</span>' +
                        '</a></li>');

                    topicElement.click(function(event) {
                        event.stopPropagation();
                        selectedCategoryId = 0;
                        selectedTopicId = topic.id;
                        $(".post").remove();
                        loadPosts(Number.MAX_SAFE_INTEGER, selectedTopicId, selectedCategoryId);
                    });

                    collapseElement.append(topicElement);
                });
            });
            navContent.append($('<div id="nav-content-highlight" style="border: none;"></div>'));
        }
    });

    $.ajax({
        url: '/hashtags',
        type: 'GET',
        success: function (hashtags) {
            hashtags.forEach(function (hashtag) {
                var hashtagElement = $('<div class="hashtag" data-id="' + hashtag.id + '">' + hashtag.tag + '</div>');
                $('#hashtag-container').append(hashtagElement);
            });

            $('.hashtag').click(function() {
                var hashtagId = $(this).data('id');
                loadPostsByHashtag(hashtagId);
            });
        }
    });

    $('body').on('mouseenter', '.category a', function() {
        $(this).css('text-decoration', 'underline');
    }).on('mouseleave', '.category a', function() {
        $(this).css('text-decoration', 'none');
    });

});