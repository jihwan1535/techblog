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

</head>
<body>
<%
    final ProfileResponseBean member = (ProfileResponseBean)request.getAttribute("profile");
%>
<p><%= member.nickname() %>의 프로필</p>
<p><%= member.image() %> - 프로필 사진</p>
<p><%= member.aboutMe() %> - 자기소개</p>
<p><%= member.postCount() %> 작성한 게시글 수</p>
<p><%= member.commentCount() %> 작성한 댓글 수</p>
<p><%= member.updateAt() %> 마지막 프로필 수정일</p>
<br>
<a href="/main">메인으로 돌아가기</a>
<hr>
<form action="/api/members/profile" method="post">
    <label for="nickname">닉네임:</label><br>
    <input type="text" id="nickname" name="nickname" value="<%= member.nickname() %>">
    <button type="button" id="checkNickname">닉네임 중복검사</button><br>
    <label for="image">프로필 사진:</label><br>
    <input type="file" id="image" name="image"><br>
    <label for="about_me">자기소개:</label><br>
    <textarea id="about_me" name="about_me"><%= member.aboutMe() %></textarea><br>
    <input type="submit" value="프로필 수정하기" onclick="">
</form>
<script>
    $(document).ready(function(){
        $("#checkNickname").click(function(){
            var nickname = $("#nickname").val();
            $.ajax({
                url: '/checkNickname',
                data: {nickname: nickname},
                type: 'GET',
                success: function(response){
                    if(response == 'AVAILABLE'){
                        alert("사용 가능한 닉네임입니다.");
                    } else {
                        alert("이미 사용중인 닉네임입니다.");
                    }
                },
                error: function(error){
                    console.log(error);
                }
            });
        });
    });
</script>
</body>
</html>
