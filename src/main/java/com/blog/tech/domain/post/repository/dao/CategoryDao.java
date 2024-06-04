package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.post.entity.Category;
import com.blog.tech.domain.post.repository.ifs.CategoryRepository;

public class CategoryDao implements CategoryRepository {

	private final Connection conn;

	public CategoryDao(final Connection conn) {
		this.conn = conn;
	}

	@Override
	public Category save(final Category data) throws SQLException {
		return null;
	}

	@Override
	public Optional<Category> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<Category> findByAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}
