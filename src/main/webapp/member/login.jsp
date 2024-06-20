<%--
  Created by IntelliJ IDEA.
  User: jihwa
  Date: 2024-05-28
  Time: 오전 5:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<link rel="stylesheet" href="/css/mainLoad.css">
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="signUpModalLabel2" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="loginModalTitle">로그인</h1>
                <button type="button" class="btn-close btn-close-black" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- form -->
                <form class="needs-validation" id="loginForm" action="/login" method="post">
                    <div class="row mb-3">
                        <div class="col-sm-12">
                            <input class="form-control" name = "email" type="email" id="login_email" placeholder="1234@naver.com">
                        </div>
                    </div>
                    <div class="row mb-4">
                        <div class="col-12">
                            <input class="form-control" name = "password" type="password" id="login_password" placeholder="비밀번호">
                        </div>
                    </div>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <button class="mb-2 btn btn-lg rounded-3 btn-outline-success" type="submit" id="loginBtn">Login</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>