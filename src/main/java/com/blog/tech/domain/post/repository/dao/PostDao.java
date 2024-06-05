package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.repository.ifs.PostRepository;

public class PostDao implements PostRepository {

	private final Connection conn;

	public PostDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Post save(final Post data) throws SQLException {
		return null;
	}

	@Override
	public Optional<Post> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<Post> findAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}
}
