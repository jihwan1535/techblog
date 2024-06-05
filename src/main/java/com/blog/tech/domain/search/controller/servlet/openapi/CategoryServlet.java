package com.blog.tech.domain.search.controller.servlet.openapi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.search.controller.SearchController;
import com.blog.tech.domain.search.response.CategoryResponseBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {

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
		try {
			final List<CategoryResponseBean> categories = searchController.getAllCategories();
			final String json = objectMapper.writeValueAsString(categories);

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(json);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
