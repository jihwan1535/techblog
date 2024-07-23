package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.common.BaseDao;
import com.blog.tech.domain.common.ConnectionManager;
import com.blog.tech.domain.post.entity.Category;
import com.blog.tech.domain.post.repository.ifs.CategoryRepository;

public class CategoryDao implements CategoryRepository {

	@Override
	public Category save(final Category data) {
		return null;
	}

	// 에러 수정 템플릿
	@Override
	public Optional<Category> findById(final Long id) {
		final Connection connection = ConnectionManager.getConnection();
		try (final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM category WHERE id = ?")){
			pstmt.setLong(1, id);
			try (final ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					return Optional.empty();
				}
				final Category category = Category.builder().id(rs.getLong(1)).name(rs.getString(2)).build();
				return Optional.of(category);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Category> findAll() throws SQLException {
		final Connection connection = ConnectionManager.getConnection();
		final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM category");
		final ResultSet rs = pstmt.executeQuery();

		final List<Category> categories = new ArrayList<>();
		while (rs.next()) {
			categories.add(Category.from(rs, 0));
		}

		rs.close();
		pstmt.close();
		return categories;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}
