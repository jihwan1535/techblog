<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-30
  Time: 오전 4:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>Tech Blog</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        .category, .topic {
            margin: 10px;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
        }
        .category {
            background-color: #f0f0f0;
        }
        .category.active {
            background-color: #d0d0d0;
        }
        .topic {
            background-color: #e0e0e0;
        }
    </style>
</head>
<body>
<!-- login/logout 기능에서 사용하므로 실제 main 에서는 session 을 false 시키는게 좋을듯 -->
<a href="/register">회원가입</a>
<%if (Objects.isNull(session.getAttribute("member"))) {%>
<a href="/login">로그인</a>
<%} else { %>
<a href="/api/logout">로그아웃</a>
<%}%>
<br>
<input class="nickname" type="text"/>
<input class="button" type="button" onclick="profile();" value="button"/>
<div id="categories">
</div>
<div id="topics">
</div>
<script>
    $(document).ready(function() {
        $.ajax({
            url: '/categories',
            type: 'GET',
            success: function(categories) {
                categories.forEach(function(category) {
                    var categoryElement = $('<div class="category" id="' + category.id + '">' + category.name + '</div>');
                    $('#categories').append(categoryElement);
                });
            }
        });
    });

    $(document).on('click', '.category', function() {
        $('.category').removeClass('active');
        $(this).addClass('active');

        var categoryId = $(this).attr('id');
        console.log(categoryId);
        $.ajax({
            url: '/topics',
            data: {category_id: categoryId},
            type: 'GET',
            success: function(topics) {
                $('#topics').empty();
                topics.forEach(function(topic) {
                    var topicElement = $('<div class="topic" id="' + topic.id + '">' + topic.name + '</div>');
                    $('#topics').append(topicElement);
                });
            }
        });
    });

    function profile() {
        var nickname = document.querySelector('.nickname').value;
        var url = "/profile/@" + nickname;
        window.location.href = url;
    }
</script>
</body>
</html>
