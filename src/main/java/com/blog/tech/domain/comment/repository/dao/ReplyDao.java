package com.blog.tech.domain.comment.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.comment.entity.Reply;
import com.blog.tech.domain.comment.repository.ifs.ReplyRepository;
import com.blog.tech.domain.post.entity.Category;
import com.blog.tech.domain.post.repository.ifs.CategoryRepository;

public class ReplyDao implements ReplyRepository {

	private final Connection conn;

	public ReplyDao(final Connection conn) {
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
	public List<Reply> findAll() throws SQLException {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}
