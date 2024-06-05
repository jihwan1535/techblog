package com.blog.tech.domain.search.service;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.search.entity.Category;
import com.blog.tech.domain.search.repository.ifs.CategoryRepository;
import com.blog.tech.domain.search.repository.ifs.ConnectHashtagRepository;
import com.blog.tech.domain.search.repository.ifs.HashtagRepository;
import com.blog.tech.domain.search.repository.ifs.TopicRepository;
import com.blog.tech.domain.search.response.CategoryResponseBean;

public class SearchService {

	private final HashtagRepository hashtagRepository;
	private final ConnectHashtagRepository connectHashtagRepository;
	private final CategoryRepository categoryRepository;
	private final TopicRepository topicRepository;

	public SearchService(
		final HashtagRepository hashtagRepository,
		final ConnectHashtagRepository connectHashtagRepository, CategoryRepository categoryRepository,
		TopicRepository topicRepository
	) {
		this.hashtagRepository = hashtagRepository;
		this.connectHashtagRepository = connectHashtagRepository;
		this.categoryRepository = categoryRepository;
		this.topicRepository = topicRepository;
	}

	public List<CategoryResponseBean> getAllCategories() throws SQLException {
		final List<Category> categories = categoryRepository.findAll();
		return categories.stream()
			.map(CategoryResponseBean::of)
			.toList();
	}

}
