package com.blog.tech.domain.member.servlet.openapi;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final Optional<Cookie> optionalCookie = Arrays.stream(req.getCookies())
			.filter(it -> it.getName().equals("JSESSIONID"))
			.findFirst();
		final HttpSession session = req.getSession();
		if (optionalCookie.isEmpty() || Objects.isNull(session.getAttribute("member"))) {
			req.setAttribute("alert", "Invalid Access");
			final RequestDispatcher rd = req.getRequestDispatcher("/exception/alert.jsp");
			rd.include(req, resp);
		} else {
			final Cookie cookie = optionalCookie.get();
			cookie.setMaxAge(0);
			resp.addCookie(cookie);
			resp.sendRedirect("/main");
		}
	}

}
