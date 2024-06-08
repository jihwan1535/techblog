package com.blog.tech.domain.search.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.search.entity.Category;
import com.blog.tech.domain.search.repository.ifs.CategoryRepository;
import com.blog.tech.global.utility.db.mapper.CategoryMapper;
import com.blog.tech.global.utility.db.mapper.PostMapper;

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
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM category WHERE id = ?");
		pstmt.setLong(1, id);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final Category category = Category.builder().id(rs.getLong(1)).name(rs.getString(2)).build();
		rs.close();
		pstmt.close();

		return Optional.of(category);
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
