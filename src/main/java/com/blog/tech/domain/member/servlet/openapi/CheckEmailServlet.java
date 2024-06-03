package com.blog.tech.domain.member.servlet.openapi;

import java.io.IOException;
import java.sql.SQLException;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.response.AvailableResponseBean;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;

@WebServlet("/checkEmail")
public class CheckEmailServlet extends HttpServlet {

	private MemberController memberController;

	@Override
	public void init() throws ServletException {
		try {
			InitialContext ctx = new InitialContext();
			memberController = (MemberController) ctx.lookup("java:global/your_application_name/MemberController");
		} catch (NamingException e) {
			throw new ServletException("Failed to lookup MemberController", e);
		}
	}

	@Override
	protected void doGet(
			final HttpServletRequest req,
			final HttpServletResponse resp
	) throws ServletException, IOException {
		if (memberController == null) {
			throw new ServletException("MemberController not initialized");
		}

		final String email = req.getParameter("email");
		try {
			final AvailableResponseBean isAvail = memberController.checkEmail(email);
			resp.setContentType("text/plain");
			resp.getWriter().write(isAvail.status());
		} catch (SQLException e) {
			throw new ServletException("Failed to check email", e);
		}
	}
}

