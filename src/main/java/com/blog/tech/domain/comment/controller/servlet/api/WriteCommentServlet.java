package com.blog.tech.domain.comment.controller.servlet.api;

import java.io.IOException;
import java.sql.SQLException;

import com.blog.tech.domain.comment.controller.CommentController;
import com.blog.tech.domain.comment.dto.request.CommentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/comments")
public class WriteCommentServlet extends HttpServlet {

	private CommentController commentController;
	private ObjectMapper objectMapper;

	@Override
	public void init() throws ServletException {
		final ServletContext servletContext = this.getServletContext();
		commentController = (CommentController)servletContext.getAttribute("commentController");
		objectMapper = (ObjectMapper)servletContext.getAttribute("objectMapper");
	}

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final Long memberId = Long.parseLong((String)req.getAttribute("memberID"));
		final CommentRequest request = objectMapper.readValue(req.getInputStream(), CommentRequest.class);
		try {
			commentController.writeCommentOnPost(memberId, request);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
