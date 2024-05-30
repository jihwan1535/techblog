package com.blog.tech.domain.member.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.dto.response.RegisterResponseBean;
import com.blog.tech.domain.member.dto.request.RegisterRequestBean;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

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
		final RequestDispatcher rd = req.getRequestDispatcher("/member/register.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final String email = req.getParameter("email");
		final String password = req.getParameter("password");
		final String nickname = req.getParameter("nickname");
		final String image = req.getParameter("image");
		final String aboutMe = req.getParameter("about_me");

		try {
			final RegisterResponseBean register = memberController.register(
				RegisterRequestBean.of(email, password, nickname, image, aboutMe));
			req.setAttribute("register", register);
			final RequestDispatcher rd = req.getRequestDispatcher("/member/registerResult.jsp");
			rd.forward(req, resp);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
