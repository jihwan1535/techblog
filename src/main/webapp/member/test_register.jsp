<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-06-04
  Time: 오후 6:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<link rel="stylesheet" href="/css/modal-form.css">
<!-- join modal 1 -->
<div class="modal fade" id="signUpModal1" tabindex="-1" role="dialog" aria-labelledby="signUpModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="signUpModalLabel">회원가입</h1>
                <button id="closeBtn" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form class="needs-validation" novalidate id="registerForm" action="./test_register.jsp" method="post">
                    <div class="row">
                        <div class="col-sm-8">
                            <input class="form-control" type="email" id="email" name="email" placeholder="이메일을 입력해주세요."/>
                            <span id="email-span"></span>
                        </div>
                        <div class="col-sm-4">
                            <button class="btn btn-success" type="button" id="checkEmail">이메일중복검사</button><br>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <input class="form-control" type="password" id="password" name="password" placeholder="비밀번호를 입력해주세요.">
                            <span id="pw-span"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <input class="form-control" type="password" id="password-confirm" placeholder="비밀번호를 한번 더 입력하세요."/>
                            <span id="pw-confirm-span"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="d" class="btn btn-secondary float-right"
                        data-bs-target="#signUpModal2" data-bs-toggle="modal">next</button>
                <button id="nextBtn" class="btn btn-secondary float-right"
                        data-bs-target="#signUpModal2" data-bs-toggle="modal" disabled>다음으로</button>
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>
<!-- join modal2 -->
<div class="modal fade" id="signUpModal2" tabindex="-1" role="dialog" aria-labelledby="signUpModalLabel2" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="signUpModalLabel2">회원가입</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times</span>
                </button>
            </div>
            <div class="modal-body">
                <form class="needs-validation" id="registerForm2" action="./test_register.jsp" method="post">
                    <div class="mb-3 row">
                        <label for="nickname" class="form-label">Nickname</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" id="nickname" name="nickname">
                        </div>
                        <div class="col-sm-4">
                            <button class="btn btn-success" type="button" id="checkNickname">닉네임중복검사</button>
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <label class="form-label" for="about_me">About Me</label>
                        <div class="col-12" id="text_area_div">
                            <textarea class="form-control" id="about_me" name="about_me" maxlength="20" resize="none"></textarea>
                            <span id="countChar">0</span><span>/20</span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary me-2" data-bs-target="#signUpmodal1" data-bs-toggle="modal">이전</button>
                <button id="registerBtn" type="submit" class="btn btn-primary" value="Submit" disabled>가입완료</button>
            </div>
        </div>
    </div>
</div>
