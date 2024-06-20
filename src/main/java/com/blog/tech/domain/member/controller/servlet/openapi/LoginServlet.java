package com.blog.tech.domain.member.controller.servlet.openapi;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.request.LoginRequestBean;
import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.member.entity.vo.MemberStatus;
import com.blog.tech.global.utility.HashEncoder;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/openapi/login")
public class LoginServlet extends HttpServlet {

	private MemberController memberController;

	@Override
	public void init() throws ServletException {
		final ServletContext context = this.getServletContext();
		memberController = (MemberController) context.getAttribute("memberController");
	}

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final String email = req.getParameter("email");
		final String password = req.getParameter("password");
		final String encodedPassword = HashEncoder.generateHash(password);

		try {
			final MemberResponseBean member = memberController.login(LoginRequestBean.of(email, encodedPassword));
			req.setAttribute("member", member);
			switch (member.status()) {
				case MemberStatus.REGISTERED -> {
					final HttpSession session = req.getSession();
					session.setAttribute("member", member);
				}
				case MemberStatus.DORMANCY -> resp.sendRedirect("/member/dormancyStatus.jsp");
				case MemberStatus.UNREGISTERED -> resp.sendRedirect("/member/unRegisteredStatus.jsp");
			};
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
