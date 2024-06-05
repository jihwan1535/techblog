package com.blog.tech.domain.member.servlet.api;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/logout")
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(
			final HttpServletRequest req,
			final HttpServletResponse resp
	) throws ServletException, IOException {
		final HttpSession session = req.getSession(false);
		if (Objects.nonNull(session)) {
			session.invalidate();
		}
		resp.sendRedirect("/main");
	}

}