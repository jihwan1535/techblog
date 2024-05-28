package com.blog.tech.domain.member.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.request.LoginRequestBean;
import com.blog.tech.domain.member.dto.response.LoginResponseBean;
import com.blog.tech.domain.member.dto.response.RegisterResponseBean;
import com.blog.tech.domain.member.entity.vo.MemberStatus;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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

		resp.setContentType("text/html; charset=UTF-8");
		try {
			final LoginResponseBean login = memberController.login(LoginRequestBean.of(email, password));
			req.setAttribute("login", login);
			final RequestDispatcher rd = switch (login.status()) {
				case MemberStatus.REGISTERED -> setCookie(req, resp, login);
				case MemberStatus.DORMANCY -> req.getRequestDispatcher("/member/dormancyStatus.jsp");
				case MemberStatus.UNREGISTERED -> req.getRequestDispatcher("/member/unRegisteredStatus.jsp");
			};
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

	private RequestDispatcher setCookie(
		final HttpServletRequest req,
		final HttpServletResponse resp,
		final LoginResponseBean login
	) {
		final HttpSession session = req.getSession();
		session.setAttribute("member_id", login.id());
		session.setAttribute("member_nickname", login.nickname());

		Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
		sessionCookie.setMaxAge(30 * 60);
		resp.addCookie(sessionCookie);

		return req.getRequestDispatcher("/member/loginSuccess.jsp");
	}

}
