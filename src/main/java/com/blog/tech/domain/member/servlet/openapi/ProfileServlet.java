package com.blog.tech.domain.member.servlet.openapi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.response.ProfileResponseBean;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {

	private MemberController memberController;

	@Override
	public void init() throws ServletException {
		final ServletContext context = this.getServletContext();
		memberController = (MemberController)context.getAttribute("memberController");
	}

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final String requestURI = req.getRequestURI();
		final String nickname = requestURI.substring(requestURI.lastIndexOf('/') + 1);

		try {
			final ProfileResponseBean profile = memberController.profile(nickname);
			req.setAttribute("profile", profile);

			final HttpSession session = req.getSession(false);
			if (Objects.isNull(session) || Objects.isNull(session.getAttribute("member"))) {
				final RequestDispatcher rd = req.getRequestDispatcher("/member/profile.jsp");
				rd.forward(req, resp);
			}

			final RequestDispatcher rd = req.getRequestDispatcher("/member/myProfile.jsp");
			rd.forward(req, resp);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
