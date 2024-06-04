package com.blog.tech.domain.hashtag.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.hashtag.entity.Hashtag;
import com.blog.tech.domain.hashtag.repository.ifs.HashtagRepository;

public class HashtagDao implements HashtagRepository {

	private final Connection conn;

	public HashtagDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Hashtag save(final Hashtag data) throws SQLException {
		return null;
	}

	@Override
	public Optional<Hashtag> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<Hashtag> findByAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}
