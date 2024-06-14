package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.post.entity.Topic;
import com.blog.tech.domain.post.repository.ifs.TopicRepository;
import com.blog.tech.global.utility.db.mapper.TopicMapper;

public class TopicDao implements TopicRepository {

	private final Connection conn;

	public TopicDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Topic save(final Topic data) throws SQLException {
		return null;
	}

	@Override
	public Optional<Topic> findById(final Long id) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM topic WHERE id = ?");
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
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TOPIC");
		final ResultSet rs = pstmt.executeQuery();

		final List<Topic> topics = new ArrayList<>();
		while (rs.next()) {
			topics.add(TopicMapper.from(rs));
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
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TOPIC WHERE category_id = ?");
		pstmt.setLong(1, categoryId);
		final ResultSet rs = pstmt.executeQuery();

		final List<Topic> topics = new ArrayList<>();
		while (rs.next()) {
			topics.add(TopicMapper.from(rs));
		}

		rs.close();
		pstmt.close();
		return topics;
	}
}