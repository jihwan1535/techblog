package com.blog.tech.domain.hashtag.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.hashtag.entity.ConnectHashtag;
import com.blog.tech.domain.hashtag.repository.ifs.ConnectHashtagRepository;

public class ConnectHashtagDao implements ConnectHashtagRepository {

	private final Connection conn;

	public ConnectHashtagDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public ConnectHashtag save(final ConnectHashtag data) throws SQLException {
		return null;
	}

	@Override
	public Optional<ConnectHashtag> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<ConnectHashtag> findByAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}