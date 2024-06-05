package com.blog.tech.domain.search.controller;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.search.dto.response.CategoryResponseBean;
import com.blog.tech.domain.search.dto.response.TopicResponseBean;
import com.blog.tech.domain.search.service.SearchService;

public class SearchController {

	private final SearchService searchService;

	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	public List<CategoryResponseBean> getAllCategories() throws SQLException {
		return searchService.getAllCategories();
	}

	public List<TopicResponseBean> getAllTopicsByCategory(final Long categoryId) throws SQLException {
		return searchService.getAllTopicsByCategory(categoryId);
	}

}