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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .form-container-left {
            width: 20%;
            height: auto;
            padding: 20px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            background-color: #FFFFFF;
            float: left;
        }
        .form-container-center {
            width: 30%;
            height: 65%;
            padding: 20px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            margin-bottom: 20px;
            background-color: #FFFFFF;
            position: absolute;
            right: 43%;
        }
        textarea {
            width: 100%;
            height: 200%;
            padding: 20px;
            margin-bottom: 10px;
            border: 1px solid #ced4da;
            border-radius: 5px;
        }
        #imageButton{
            width: 100%;
        }
        .dropdown-menu{
            background-color: #ffffff;
            border: 1px solid black;
        }
        .container-right{
            width: 40%;
            float: right;
        }
        .btn-outline-dark{
            font-size: 12px;
            font-weight: bold;
        }
        .profileImage{
            width: 200px;
            height: 180px;
            object-fit: fill;
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
        <div class="form-container-left container-sm">
            <fieldset>
                <div style="text-align: center;" class="mb-3"><h5>Profile Image</h5></div>
                <div class="mb-3">
                    <div class="imagePreview mb-3" id="imagePreview">
                        <input type="file" id="imageUploader" style="display:none;"><br>
                        <div style="text-align: center;" class="row justify-content-center"><img class="profileImage" id="profileImage" src="<%= image %>" alt="Profile Image"></div>
                        <input type="hidden" id="image" name="image" value="<%= image %>">
                    </div>
                    <div class="dropdown mb-3 row">
                        <button class="btn btn-outline-dark dropdown-toggle container-fluid" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Edit Image </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" type="button" id="imageButton">이미지 선택</a></li>
                            <li><a class="dropdown-item" type="button" id="defaultImageButton">기본 이미지로 변경</a></li>
                        </ul>
                    </div>
                    <div class="row">
                        <input class="btn btn-outline-dark" type="submit" value="프로필 수정하기">
                    </div>
                </div>
            </fieldset>
        </div>

        <div class="form-container-center">
            <fieldset>
                <div style="text-align: center;"><h2><label id="memberNickname"><%= nickname %></label>의 프로필</h2></div>
                <div class="mb-4">
                    <div class="row">
                        <div class="col-12 mb-3"><label for="nickname">닉네임</label><br></div>
                        <div class="col-12 mb-3"><input type="text" class="form-control" id="nickname" name="nickname" value="<%= nickname %>"></div>
                        <div class="col-12 mb-3"><button type="button" id="checkNickname" class="container-fluid btn btn-outline-secondary">닉네임 중복검사</button></div>
                        <div class="col-12 mb-3"><label for="about_me">자기소개</label><br></div>
                        <div class="col-12 mb-5"><textarea class="form-control" id="about_me" name="about_me" maxlength="100"><%= aboutMe %></textarea></div>
                    </div>
                </div>
                <div class="float-end"><span id="text-limit">0</span><span>/100</span></div>
            </fieldset>
        </div>
    </form>
    <div class="container-right">
        <div class="card w-100 mb-3" style="width: 10rem;">
            <div class="card-header"><h5 class="card-title">자기소개</h5></div>
            <div class="card-body">
                <p class="card-text"><%= aboutMe %></p>
            </div>
        </div>
        <div class="card w-100 mb-3" style="width: 10rem;">
            <div class="card-header"><h5 class="card-title">작성 게시글 수</h5></div>
            <div class="card-body">
                <p class="card-text"><%= profile.postCount() %></p>
            </div>
        </div>
        <div class="card w-100 mb-3" style="width: 10rem;">
            <div class="card-header"><h5 class="card-title">작성한 댓글 수</h5></div>
            <div class="card-body">
                <p class="card-text"><%= profile.commentCount() %></p>
            </div>
        </div>
        <div class="card w-100" style="width: 10rem;">
            <div class="card-header"><h5 class="card-title">마지막 프로필 수정일</h5></div>
            <div class="card-body">
                <p class="card-text"><%= profile.updateAt() %></p>
            </div>
        </div>
    </div>
</div>
<!--

-->
<script>
    var isNicknameAvailable = true;
    var nickName = $("#memberNickname").val();
    const defaultImageUrl = 'http://localhost:8888/upload/images/profile/profile.png'
    var originalChar = $("#about_me").val().length;

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

<!--


<div class="form-container-right">
<fieldset>
<div style="text-align: center;"><h5><label class="mb-3" id="memberNickname"><%= nickname %></label>의 프로필</h5></div>
<div class="mb-3">
<div class="row mb-2">
<div class="col-6"><label for="nickname">닉네임</label><br></div>
<div class="col-6"><label for="about_me">자기소개</label><br></div>
</div>
<div class="row">
<div class="col-3"><input type="text" class="form-control" id="nickname" name="nickname" value="<%= nickname %>"></div>
<div class="col-3"><button type="button" id="checkNickname" class="btn btn-outline-secondary">닉네임 중복검사</button></div>
<div class="col-6"><textarea class="form-control" id="about_me" name="about_me"><%= aboutMe %></textarea></div>
</div>
</div>
</fieldset>
</div>
</form>
</div>

<div class="container-right">
<div class="row row-cols-md-2 mb-3">
<div class="col">
<div class="card w-100" style="width: 10rem;">
<div class="card-header"><p class="card-title">자기소개</p></div>
<div class="card-body">
<p class="card-text"><%= aboutMe %></p>
</div>
</div>
</div>
<div class="col">
<div class="card w-100" style="width: 10rem;">
<div class="card-header"><p class="card-title">작성 게시글 수</p></div>
<div class="card-body">
<p class="card-text"><%= profile.postCount() %></p>
</div>
</div>
</div>
</div>
<div class="row row-cols-md-2 mb-3">
<div class="col">
<div class="card w-100" style="width: 10rem;">
<div class="card-header"><p class="card-title">작성한 댓글 수</p></div>
<div class="card-body">
<p class="card-text"><%= profile.commentCount() %></p>
</div>
</div>
</div>
<div class="col">
<div class="card w-100" style="width: 10rem;">
<div class="card-header"><p class="card-title">마지막 프로필 수정일</p></div>
<div class="card-body">
<p class="card-text"><%= profile.updateAt() %></p>
</div>
</div>
</div>
</div>
-->