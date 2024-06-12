package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.post.entity.PostAttach;
import com.blog.tech.domain.post.repository.ifs.PostAttachRepository;

public class PostAttachDao implements PostAttachRepository {

	private final Connection conn;

	public PostAttachDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public PostAttach save(final PostAttach data) throws SQLException {
		return null;
	}

	@Override
	public Optional<PostAttach> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<PostAttach> findAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}
