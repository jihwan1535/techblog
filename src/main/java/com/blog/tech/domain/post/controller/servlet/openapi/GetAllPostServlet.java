package com.blog.tech.domain.post.controller.servlet.openapi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import com.blog.tech.domain.post.controller.PostController;
import com.blog.tech.domain.post.dto.response.AllPostResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/posts")
public class GetAllPostServlet extends HttpServlet {

	private PostController postController;
	private ObjectMapper objectMapper;

	@Override
	public void init() throws ServletException {
		final ServletContext servletContext = this.getServletContext();
		this.postController = (PostController)servletContext.getAttribute("postController");
		this.objectMapper = (ObjectMapper)servletContext.getAttribute("objectMapper");
	}

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		try {
			final List<AllPostResponse> posts = getPostsResponse(req);
			final String json = objectMapper.writeValueAsString(posts);

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private List<AllPostResponse> getPostsResponse(final HttpServletRequest req) throws SQLException {
		final Long postId = Long.parseLong(req.getParameter("post_id"));
		final String topicId = req.getParameter("topic_id");
		if (Objects.isNull(topicId)) {
			return postController.getAllPosts(postId);
		}

		final Long parseTopicId = Long.parseLong(req.getParameter("topic_id"));
		return postController.getAllPostsByTopic(postId, parseTopicId);
	}

}
