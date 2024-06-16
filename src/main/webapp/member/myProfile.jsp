<%@ page import="com.blog.tech.domain.member.dto.response.ProfileResponseBean" %>
<%@ page import="java.util.Objects" %>
<%@ page import="com.blog.tech.domain.member.dto.response.MemberResponseBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <title>Edit Profile</title>
    <meta name="viewport" content="width=divice-width,initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .form-container-left {
            width: 49%;
            float: left;
            padding: 20px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            background-color: #FFFFFF;
        }
        .form-container-right {
            width: 49%;
            float: right;
            padding: 20px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            margin-bottom: 20px;
            background-color: #FFFFFF;
        }
        textarea {
            width: 100%;
            padding: 20px;
            margin-bottom: 10px;
            border: 1px solid #ced4da;
            border-radius: 5px;
        }
    </style>

</head>

<body style="background-color: #F5F5F7;">
<%
    if (session.getAttribute("profile") == null) {
        response.sendRedirect("/login");
    }
%>

<jsp:include page="/css/navbar.jsp" />

<%
    final ProfileResponseBean profile = (ProfileResponseBean)request.getAttribute("profile");
    final String nickname = profile.member().nickname();
    final String image = profile.member().image();
    final String aboutMe = profile.aboutMe();
%>

<div class="container mt-5">
    <form id="profileForm" action="/api/members/profile" method="post">
        <div class="form-container-left">
            <fieldset>
                <div style="text-align: center;"><h2><label id="memberNickname"><%= nickname %></label>의 프로필</h2></div>
                <div class="mb-3">
                    <label for="nickname">닉네임</label><br>
                    <div class="input-group">
                        <input type="text" class="form-control" id="nickname" name="nickname" value="<%= nickname %>">
                        <button type="button" id="checkNickname" class="btn btn-outline-secondary">닉네임 중복검사</button>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="about_me">자기소개</label><br>
                    <textarea class="form-control" id="about_me" name="about_me"><%= aboutMe %></textarea>
                </div>
                <div style="display: flex; justify-content: center; align-items: center; width: 100%; margin-top: 20px;">
                    <div style="border: 1px solid #000; padding: 20px 140px; text-align: center; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); border-radius: 10px; background-color: #f9f9f9;">
                        <p><%= aboutMe %> - 자기소개</p>
                        <p><%= profile.postCount() %> 작성한 게시글 수</p>
                        <p><%= profile.commentCount() %> 작성한 댓글 수</p>
                        <p><%= profile.updateAt() %> 마지막 프로필 수정일</p>
                    </div>
                </div>
            </fieldset>
        </div>

        <div class="form-container-right">
            <fieldset>
                <div style="text-align: center;"><h2>프로필 사진</h2></div>
                <div class="mb-3">
                    <div class="imagePreview" id="imagePreview">
                        <input type="file" id="imageUploader" style="display:none;"><br>
                        <div style="text-align: center;"><img class="profileImage" id="profileImage" src="<%= image %>" alt="Profile Image" style="max-width: 200px;"></div>
                        <input type="hidden" id="image" name="image" value="<%= image %>">
                    </div><br>
                    <div style="text-align: center; margin-top: 10px;"><button type="button" id="imageButton">이미지 선택</button></div>
                    <div style="text-align: center; margin-top: 5px;"><button type="button" id="defaultImageButton">기본 이미지로 변경</button></div>
                </div>
            </fieldset>
        </div>
        <div style="text-align: right;">
            <input type="submit" value="프로필 수정하기" class="btn btn-primary">
        </div>
        <div style="text-align: center; margin-top: 30px;">
            <a href="/main">메인으로 돌아가기</a>
        </div>
    </form>
</div>

<script>
    var isNicknameAvailable = true;
    var nickName = $("#memberNickname").val();
    const defaultImageUrl = 'http://localhost:8888/upload/images/profile/profile.png'

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
            formData.append('file', file);

            $.ajax({
                url: '/api/uploader/images/profile',
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