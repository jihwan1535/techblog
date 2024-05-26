package com.blog.tech.global.servlet;

import java.io.IOException;
import java.sql.Connection;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.request.JoinMemberBean;
import com.blog.tech.domain.member.service.MemberService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/join")
public class JoinMemberServlet extends HttpServlet {

	private final MemberController memberController;

	public JoinMemberServlet(final MemberController memberController) {
		this.memberController = memberController;
	}

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final String email = req.getParameter("email");
		final String password = req.getParameter("password");
		final String nickname = req.getParameter("nickname");
		final String image = req.getParameter("image");
		final String aboutMe = req.getParameter("about_me");

		memberController.join(JoinMemberBean.of(email, password, nickname, image, aboutMe));

	}

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		doGet(req, resp);
	}
}
