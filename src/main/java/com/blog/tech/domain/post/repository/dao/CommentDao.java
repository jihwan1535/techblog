package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.post.entity.Comment;
import com.blog.tech.domain.post.repository.ifs.CommentRepository;

public class CommentDao implements CommentRepository {

	private final Connection conn;

	public CommentDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Comment save(final Comment data) throws SQLException {
		return null;
	}

	@Override
	public Optional<Comment> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<Comment> findByAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}
