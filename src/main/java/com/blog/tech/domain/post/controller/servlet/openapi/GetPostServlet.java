package com.blog.tech.domain.post.controller.servlet.openapi;

import java.io.IOException;
import java.sql.SQLException;

import com.blog.tech.domain.post.controller.PostController;
import com.blog.tech.domain.post.dto.response.PostResponse;
import com.blog.tech.global.utility.HashEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/view/posts")
public class GetPostServlet extends HttpServlet {

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
		final Long postId = Long.parseLong(req.getParameter("post_id"));
		final String ip = getIpAddress(req);
		final String encodingIp = HashEncoder.generateHash(ip);
		final PostResponse post = postController.getPost(postId, encodingIp);
		final String json = objectMapper.writeValueAsString(post);

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(json);
	}

	private String getIpAddress(final HttpServletRequest req) {
		final String ipAddress = req.getHeader("X-FORWARDED-FOR");

		if (ipAddress != null) {
			return ipAddress.split(",")[0];
		}

		return req.getRemoteAddr();
	}

}
