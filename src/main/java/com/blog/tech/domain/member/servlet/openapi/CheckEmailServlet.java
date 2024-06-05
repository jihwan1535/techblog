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

@WebServlet("/checkEmail")
public class CheckEmailServlet extends HttpServlet {

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
		final String email = req.getParameter("email");
		try {
			final AvailableResponseBean isAvail = memberController.checkEmail(email);
			resp.setContentType("text/plain");
			resp.getWriter().write(isAvail.status());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}