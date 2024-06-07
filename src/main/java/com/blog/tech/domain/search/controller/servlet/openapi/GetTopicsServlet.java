package com.blog.tech.domain.search.controller.servlet.openapi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.search.controller.SearchController;
import com.blog.tech.domain.search.dto.response.CategoryResponseBean;
import com.blog.tech.domain.search.dto.response.TopicResponseBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/topics")
public class GetTopicsServlet extends HttpServlet {

	private SearchController searchController;
	private ObjectMapper objectMapper;

	@Override
	public void init() throws ServletException {
		final ServletContext context = this.getServletContext();
		searchController = (SearchController)context.getAttribute("searchController");
		objectMapper = (ObjectMapper)context.getAttribute("objectMapper");
	}

	@Override
	protected void doGet(
		final HttpServletRequest req,
		final HttpServletResponse resp
	) throws ServletException, IOException {
		final Long categoryId = Long.parseLong(req.getParameter("category_id"));
		try {
			final List<TopicResponseBean> topics = searchController.getAllTopicsByCategory(categoryId);
			final String json = objectMapper.writeValueAsString(topics);

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
