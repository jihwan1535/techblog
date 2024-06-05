package com.blog.tech.domain.post.servlet.api;

import java.io.IOException;
import java.sql.SQLException;

import com.blog.tech.domain.post.controller.PostController;
import com.blog.tech.domain.post.dto.request.PostRequestBean;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/post/write")
public class PostServlet extends HttpServlet {

	private PostController postController;

	@Override
	public void init() throws ServletException {
		final ServletContext servletContext = this.getServletContext();
		this.postController = (PostController)servletContext.getAttribute("postController");
	}

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final Long memberId = (Long)req.getAttribute("memberId");
		final Long topicId = (Long)req.getAttribute("topicId");
		final Long categoryId = (Long)req.getAttribute("categoryId");
		final String title = (String)req.getAttribute("title");
		final String content = (String)req.getAttribute("content");
		final PostRequestBean request = PostRequestBean.of(memberId, topicId, categoryId, title, content);
		try {
			postController.writeOnPost(request);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
