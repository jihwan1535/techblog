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
import com.blog.tech.domain.post.entity.Topic;
import com.blog.tech.domain.post.repository.ifs.TopicRepository;

public class TopicDao implements TopicRepository {

	@Override
	public Topic save(final Topic data) throws SQLException {
		return null;
	}

	@Override
	public Optional<Topic> findById(final Long id) throws SQLException {
		final Connection connection = ConnectionManager.getConnection();
		final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM topic WHERE id = ?");
		pstmt.setLong(1, id);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final Topic topic = Topic.builder().id(rs.getLong(1)).categoryId(rs.getLong(2)).name(rs.getString(3)).build();
		rs.close();
		pstmt.close();

		return Optional.of(topic);
	}

	@Override
	public List<Topic> findAll() throws SQLException {
		final Connection connection = ConnectionManager.getConnection();
		final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM topic");
		final ResultSet rs = pstmt.executeQuery();

		final List<Topic> topics = new ArrayList<>();
		while (rs.next()) {
			topics.add(Topic.from(rs, 0));
		}

		rs.close();
		pstmt.close();
		return topics;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}

	@Override
	public List<Topic> findAllByCategoryId(final Long categoryId) throws SQLException {
		final Connection connection = ConnectionManager.getConnection();
		final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM topic WHERE category_id = ?");
		pstmt.setLong(1, categoryId);
		final ResultSet rs = pstmt.executeQuery();

		final List<Topic> topics = new ArrayList<>();
		while (rs.next()) {
			topics.add(Topic.from(rs, 0));
		}

		rs.close();
		pstmt.close();
		return topics;
	}
}
