<%@ page import="com.blog.tech.domain.member.dto.response.ProfileResponseBean" %><%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-30
  Time: 오후 9:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        form {
            max-width: 500px;
            margin: 0 auto;
        }

        label {
            display: block;
            margin-bottom: 8px;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="file"] {
            display: none;
        }

        button {
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }

        input[type="submit"] {
            padding: 10px 15px;
            background-color: #008CBA;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #007bb5;
        }

        .imagePreview img {
            max-width: 100%;
            height: auto;
            display: block;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<%
    final ProfileResponseBean member = (ProfileResponseBean)request.getAttribute("profile");
%>
<p><label id = "memberNickname"><%= member.nickname() %></label>의 프로필</p>
<div class="imagePreview">
    <img class="profileImage" src="<%= member.image() %>" alt="Profile Image" style="max-width: 200px;">
</div><br>
<p><%= member.aboutMe() %> - 자기소개</p>
<p><%= member.postCount() %> 작성한 게시글 수</p>
<p><%= member.commentCount() %> 작성한 댓글 수</p>
<p><%= member.updateAt() %> 마지막 프로필 수정일</p>
<br>
<a href="/main">메인으로 돌아가기</a>
<hr>
<form id="profileForm" action="/api/members/profile" method="post">
    <label for="nickname">닉네임:</label>
    <input type="text" id="nickname" name="nickname" value="<%= member.nickname() %>">
    <button type="button" id="checkNickname">닉네임 중복검사</button><br>
    <label for="imageButton">프로필 사진:</label>
    <button type="button" id="imageButton">이미지 선택</button>
    <button type="button" id="defaultImageButton">기본 이미지로 변경</button>
    <input type="file" id="imageUploader" style="display:none;"><br>
    <div class="imagePreview" id="imagePreview">
        <img class="profileImage" id="profileImage" src="<%= member.image() %>" alt="Profile Image" style="max-width: 200px;">
        <input type="hidden" id="image" name="image" value="<%= member.image() %>">
    </div><br>
    <label for="about_me">자기소개</label><br>
    <textarea id="about_me" name="about_me"><%= member.aboutMe() %></textarea><br>
    <input type="submit" value="프로필 수정하기">
</form>
<script>
    var isNicknameAvailable = true;
    var nickName = $("#memberNickname").val();

    const defaultImageUrl = 'http://localhost:8888\\upload\\images\\profile.png'

    $("#checkNickname").click(function(){
        var changeNickname = $("#nickname").val();
        $.ajax({
            url: '/checkNickname',
            data: {nickname: changeNickname},
            type: 'GET',
            success: function(response){
                if(response == 'AVAILABLE'){
                    alert("사용 가능한 닉네임입니다.");
                    isNicknameAvailable = true;
                } else {
                    alert("이미 사용중인 닉네임입니다.");
                    isNicknameAvailable = false;
                }
            },
            error: function(error){
                console.log(error);
            }
        });
    });

    $("#checkNickname").change(function(){
        var changeNickname = $("#nickname").val();
        if (nickName != changeNickname) {
            isNicknameAvailable = false;
        } else {
            isNicknameAvailable = true;
        }
    });

    $("#profileForm").submit(function(e){
        if(!isNicknameAvailable){
            e.preventDefault();
            alert("닉네임 중복 검사를 통과해야 합니다.");
        }
    });

    $('#imageButton').click(function() {
        $('#imageUploader').click();
    });

    $("#imageUploader").change(function(){
        var file = this.files[0];
        if (file) {
            var formData = new FormData();
            formData.append('image', file);

            $.ajax({
                url: '/uploader/image',
                data: formData,
                type: 'POST',
                contentType: false,
                processData: false,
                success: function(response){
                    $("#profileImage").attr("src", response);
                    $("#image").val(response);
                },
                error: function(error){
                    console.error('이미지 업로드 실패');
                }
            });
        }
    });

    $('#defaultImageButton').click(function() {
        $("#profileImage").attr("src", defaultImageUrl);
        $("#image").val(defaultImageUrl);
    });
</script>
</body>
</html>
