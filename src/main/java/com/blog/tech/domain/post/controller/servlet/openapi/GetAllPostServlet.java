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
		final List<AllPostResponse> posts = getPostsResponse(req);
		final String json = objectMapper.writeValueAsString(posts);

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(json);
	}

	private List<AllPostResponse> getPostsResponse(final HttpServletRequest req) {
		final Long postId = Long.parseLong(req.getParameter("post_id"));
		final Long topicId = Long.parseLong(req.getParameter("topic_id"));
		final Long categoryId = Long.parseLong(req.getParameter("category_id"));

		if (topicId > 0) {
			return postController.getAllPostsByTopic(postId, topicId);
		}

		if (categoryId > 0) {
			return postController.getAllPostsByCategory(postId, categoryId);
		}

		return postController.getAllPosts(postId);
	}

}
