package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.post.entity.ConnectHashtag;
import com.blog.tech.domain.post.entity.Hashtag;
import com.blog.tech.domain.post.repository.ifs.ConnectHashtagRepository;
import com.blog.tech.global.utility.db.mapper.ConnectHashTagMapper;

public class ConnectHashtagDao implements ConnectHashtagRepository {

	private final Connection conn;

	public ConnectHashtagDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public ConnectHashtag save(final ConnectHashtag data) throws SQLException {
		if (data.getId() > 0) {
			update(data);
			return data;
		}
		return create(data);
	}

	private ConnectHashtag create(final ConnectHashtag data) throws SQLException {
		final String sql = "INSERT INTO connect_hashtag (id, hashtag_id, post_id) VALUES (?, ?, ?)";
		final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setLong(1, data.getId());
		pstmt.setLong(2, data.getHashtagId());
		pstmt.setLong(3, data.getPostId());

		final int rows = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			data.setId(rs.getLong(1));
		}
		rs.close();
		pstmt.close();
		System.out.println("Inserted connect_hashtag " + rows + " row(s).");

		return data;
	}

	private void update(final ConnectHashtag data) throws SQLException {
		final String sql = "UPDATE connect_hashtag SET hashtag_id, post_id = ? WHERE id = ?";
		final PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, data.getHashtagId());
		pstmt.setLong(2, data.getPostId());
		pstmt.setLong(3, data.getId());

		final int rows = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Updated connect_hashtag " + rows + " row(s).");
	}

	@Override
	public Optional<ConnectHashtag> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<ConnectHashtag> findAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}

	@Override
	public Optional<ConnectHashtag> findByHashtagIdAndPostId(
		final Long hashtagId,
		final Long postId
	) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM connect_hashtag WHERE hashtag_id = ? AND post_id = ?");
		pstmt.setLong(1, hashtagId);
		pstmt.setLong(2, postId);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final ConnectHashtag connectHashtag = ConnectHashTagMapper.from(rs, 0);
		rs.close();
		pstmt.close();

		return Optional.of(connectHashtag);
	}
}
