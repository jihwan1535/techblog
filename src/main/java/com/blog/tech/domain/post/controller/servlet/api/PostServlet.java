package com.blog.tech.domain.post.controller.servlet.api;

import java.io.IOException;
import java.sql.SQLException;

import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.post.controller.PostController;
import com.blog.tech.domain.post.dto.request.PostRequestBean;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
		final HttpSession session = req.getSession(false);
		final MemberResponseBean member = (MemberResponseBean)session.getAttribute("member");
		final PostRequestBean request = getPostRequest(req);
		try {
			postController.writeOnPost(member.id(), request);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private PostRequestBean getPostRequest(final HttpServletRequest req) {
		final Long topicId = Long.parseLong(req.getParameter("topic_id"));
		final Long categoryId = Long.parseLong(req.getParameter("category_id"));
		final String title = req.getParameter("title");
		final String content = req.getParameter("content");
		return PostRequestBean.of(topicId, categoryId, title, content);
	}

}
