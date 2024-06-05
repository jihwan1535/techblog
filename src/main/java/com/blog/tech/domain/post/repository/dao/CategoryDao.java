package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.post.entity.Category;
import com.blog.tech.domain.post.repository.ifs.CategoryRepository;
import com.blog.tech.global.utility.db.mapper.CategoryMapper;

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
	public List<Category> findAll() throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM category");
		final ResultSet rs = pstmt.executeQuery();

		final List<Category> categories = new ArrayList<>();
		while (rs.next()) {
			categories.add(CategoryMapper.from(rs));
		}

		rs.close();
		pstmt.close();
		return categories;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}
