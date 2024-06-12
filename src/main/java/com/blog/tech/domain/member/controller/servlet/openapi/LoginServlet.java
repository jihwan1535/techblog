package com.blog.tech.domain.member.controller.servlet.openapi;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.request.LoginRequestBean;
import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.member.entity.vo.MemberStatus;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

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
		final RequestDispatcher rd = req.getRequestDispatcher("/member/login.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final String email = req.getParameter("email");
		final String password = req.getParameter("password");

		// todo resp.sendRedirect 로 보완하기
		try {
			final MemberResponseBean member = memberController.login(LoginRequestBean.of(email, password));
			req.setAttribute("member", member);
			final RequestDispatcher rd = switch (member.status()) {
				case MemberStatus.REGISTERED -> setCookie(req, resp, member);
				case MemberStatus.DORMANCY -> req.getRequestDispatcher("/member/dormancyStatus.jsp");
				case MemberStatus.UNREGISTERED -> req.getRequestDispatcher("/member/unRegisteredStatus.jsp");
			};
			rd.forward(req, resp);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private RequestDispatcher setCookie(
		final HttpServletRequest req,
		final HttpServletResponse resp,
		final MemberResponseBean member
	) {
		final HttpSession session = req.getSession();
		session.setAttribute("member", member);

		final Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
		sessionCookie.setMaxAge(30 * 60);
		resp.addCookie(sessionCookie);

		return req.getRequestDispatcher("/member/loginSuccess.jsp");
	}

}
