package com.blog.tech.domain.comment.controller.servlet.openapi;

import java.io.IOException;
import java.sql.SQLException;

import com.blog.tech.domain.comment.controller.CommentController;
import com.blog.tech.domain.comment.dto.request.CommentRequestBean;
import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.post.dto.request.PostRequestBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/comment")
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
		final CommentRequestBean request = objectMapper.readValue(req.getInputStream(), CommentRequestBean.class);
		try {
			commentController.writeCommentOnPost(memberId, request);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
