package com.blog.tech.global.exception;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/exception")
public class ExceptionServlet extends HttpServlet {

	@Override
	protected void doGet(
			final HttpServletRequest req,
			final HttpServletResponse resp
	) throws ServletException, IOException {
		final RequestDispatcher rd = req.getRequestDispatcher("/exception/alert.jsp");
		rd.forward(req, resp);
	}

}