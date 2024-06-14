package com.blog.tech.domain.comment.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.comment.repository.ifs.CommentRepository;
import com.blog.tech.domain.post.entity.Category;

public class CommentDao implements CommentRepository {
	@Override
	public Comment save(final Comment data) throws SQLException {
		return null;
	}

	@Override
	public Optional<Comment> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<Comment> findAll() throws SQLException {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}

	private final Connection conn;

	public CommentDao(final Connection conn) {
		this.conn = conn;
	}

}
