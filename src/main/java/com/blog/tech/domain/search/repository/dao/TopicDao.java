package com.blog.tech.domain.search.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.search.entity.Topic;
import com.blog.tech.domain.search.repository.ifs.TopicRepository;

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
		return Optional.empty();
	}

	@Override
	public List<Topic> findAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}
