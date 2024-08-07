package com.blog.tech.domain.post.controller.servlet.api;

import java.io.IOException;
import java.sql.SQLException;

import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.post.controller.PostController;
import com.blog.tech.domain.post.dto.request.PostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/posts/write")
public class PostServlet extends HttpServlet {

	private PostController postController;
	private ObjectMapper objectMapper;

	@Override
	public void init() throws ServletException {
		final ServletContext servletContext = this.getServletContext();
		this.postController = (PostController)servletContext.getAttribute("postController");
		this.objectMapper = (ObjectMapper)servletContext.getAttribute("objectMapper");
	}

	@Override
	protected void doPost(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final HttpSession session = req.getSession(false);
		final MemberResponseBean member = (MemberResponseBean)session.getAttribute("member");
		final PostRequest request = objectMapper.readValue(req.getInputStream(), PostRequest.class);


		postController.writeOnPost(member.id(), request);
		resp.setStatus(HttpServletResponse.SC_OK);
	}

}
