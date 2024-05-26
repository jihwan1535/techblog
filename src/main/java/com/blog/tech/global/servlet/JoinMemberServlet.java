package com.blog.tech.global.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.response.RegisteredMemberBean;
import com.blog.tech.domain.member.dto.request.RegisterMemberBean;
import com.blog.tech.domain.member.repository.dao.MemberDao;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/join")
public class JoinMemberServlet extends HttpServlet {

	private MemberController memberController;

	@Override
	public void init() throws ServletException {
		final ServletContext context = this.getServletContext();
		memberController = (MemberController) context.getAttribute("memberController");
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

		resp.setContentType("text/html; charset=UTF-8");
		try {
			final RegisteredMemberBean register = memberController.register(RegisterMemberBean.of(email, password, nickname, image, aboutMe));
			req.setAttribute("register", register);
			RequestDispatcher rd = req.getRequestDispatcher(
				"/test/test.jsp"
			);
			rd.include(req, resp);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		doGet(req, resp);
	}
}
