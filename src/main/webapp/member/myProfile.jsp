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
        .container-sm{
            width:60%;
            background-color: #ffffff;
            margin-top: 5%;
            border-radius: 1rem;
            border: 2px solid black;
        }
        #profileForm{
            padding: 3% 3% 3%;
        }
        textarea {
            width: 100%;
            height: 30%;
            padding: 20px;
            margin-bottom: 10px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            resize: none;
        }
        #imageButton{
            width: 100%;
        }
        .dropdown-menu{
            background-color: #ffffff;
            border: 1px solid black;
            width: 50%;
        }
        #editImg, #updateImgBtn{
            font-size: 0.8rem;
            font-weight: bold;
            width: 50%;
        }
        .profileImage{
            width: 200px;
            height: 180px;
            object-fit: fill;
        }
        .profileTitle{
            text-align: center;
            font-size: 1.3rem;
        }
        .imagePreview{
            justify-content: center;
        }
        .profileImage{
            border: 1px solid black;
            border-radius: 1rem;
        }
        .btn-outline-dark{
            font-weight: bold;
            font-size: 0.9rem;
        }
        .btn-outline-secondary{
            font-weight: bold;
            border-radius: 0rem;
        }
        .post-count-label-wrap{
            display: flex;
            align-items: stretch;
            margin-bottom: 2%;
        }
        .post-count-label-wrap.btn-outline-dark{
            flex: 1;
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

<div class="container-sm">
    <form id="profileForm" action="/api/members/profile" method="post">
        <div class="row">
            <div class="col-6 mb-3 profileTitle">Profile Image</div>
            <div class="col-6 mb-3 profileTitle"><label id="memberNickname"><%= nickname %></label>의 프로필</div>
        </div>
        <div class="row">
            <div class="col">
                <div class="mb-3">
                    <div class="imagePreview mb-3" id="imagePreview">
                        <input type="file" id="imageUploader" style="display:none;"><br>
                        <div style="text-align: center;" class="justify-content-center mb-3"><img class="profileImage" id="profileImage" src="<%= image %>" alt="Profile Image"></div>
                        <input type="hidden" id="image" name="image" value="<%= image %>">
                    </div>
                    <div class="dropdown mb-3 row">
                        <button class="btn btn-outline-dark dropdown-toggle container-fluid" id="editImg" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Edit Image </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" type="button" id="imageButton">이미지 선택</a></li>
                            <li><a class="dropdown-item" type="button" id="defaultImageButton">기본 이미지로 변경</a></li>
                        </ul>
                    </div>
                    <div class="row mb-4 d-flex justify-content-center">
                        <input class="btn btn-outline-dark" id="updateImgBtn" type="submit" value="프로필 이미지 수정">
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="mb-3">
                    <label for="nickname">닉네임</label>
                    <div class="d-flex input-group mb-3">
                        <input type="text" class="form-control" maxlength="8" id="nickname" name="nickname" value="<%= nickname %>">
                        <div class="input-group-append"><button type="button" id="checkNickname" class="container-fluid btn btn-outline-secondary">닉네임 중복검사</button></div>
                    </div>
                    <div class="form-text text-start mb-2" style="font-size: 10px;">* 닉네임은 8자까지 설정할 수 있습니다.</div>
                    <div class="mb-3"><label for="about_me">자기소개</label><br></div>
                    <div class="mb-1"><textarea class="form-control" id="about_me" name="about_me" maxlength="100"><%= aboutMe %></textarea></div>
                    <div class="text-end"><span id="text-limit">0</span><span>/100</span></div>
                </div>
            </div>
        </div>
    </form>
    <div class="post-count-label-wrap d-flex justify-content-end ms-5">
        <div class="me-2"><button class="btn btn-outline-dark">작성한 게시글(<%= profile.postCount() %>)</button></div>
        <div class="me-2"><button class="btn btn-outline-dark">작성한 댓글(<%= profile.commentCount() %>)</button></div>
        <div><button class="btn btn-outline-dark float-end" id="profileUpdateBtn" type="submit" value="프로필 수정하기">프로필 수정</button></div>
    </div>
</div>

<script>
    var isNicknameAvailable = true;
    var nickName = $("#memberNickname").val();
    const defaultImageUrl = 'http://localhost:8888/upload/images/profile/profile.png'
    var originalChar = $("#about_me").val().length;
    var nicknameChar = $("#nickname").val().length;

    $("#text-limit").text(originalChar);

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

    $(document).on('keydown', '#about_me', function (){
        var limitChar = $("#about_me").val().length;
        $("#text-limit").text(limitChar);
    });
</script>
</body>
</html>