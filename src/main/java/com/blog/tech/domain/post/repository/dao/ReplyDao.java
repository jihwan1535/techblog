package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.post.entity.Reply;
import com.blog.tech.domain.post.repository.ifs.ReplyRepository;

public class ReplyDao implements ReplyRepository {

	private final Connection conn;

	public ReplyDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Reply save(final Reply data) throws SQLException {
		return null;
	}

	@Override
	public Optional<Reply> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<Reply> findAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}
