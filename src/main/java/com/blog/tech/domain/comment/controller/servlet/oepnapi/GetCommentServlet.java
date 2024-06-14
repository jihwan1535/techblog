package com.blog.tech.domain.comment.controller.servlet.oepnapi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.comment.controller.CommentController;
import com.blog.tech.domain.comment.dto.response.CommentsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/comments")
public class GetCommentServlet extends HttpServlet {

	private CommentController commentController;
	private ObjectMapper objectMapper;

	@Override
	public void init() throws ServletException {
		final ServletContext servletContext = this.getServletContext();
		commentController = (CommentController)servletContext.getAttribute("commentController");
		objectMapper = (ObjectMapper)servletContext.getAttribute("objectMapper");
	}

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final Long postId = Long.valueOf(req.getParameter("post_id"));
		try {
			final List<CommentsResponse> comments = commentController.allCommentsOnPost(postId);
			final String json = objectMapper.writeValueAsString(comments);

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
