package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.common.BaseDao;
import com.blog.tech.domain.post.entity.Hashtag;
import com.blog.tech.domain.post.repository.ifs.HashtagRepository;

public class HashtagDao extends BaseDao implements HashtagRepository {

	@Override
	public Hashtag save(final Hashtag data) throws SQLException {
		final String sql = "INSERT INTO hashtag (id, tag) VALUES (?, ?)";
		final PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setLong(1, data.getId());
		pstmt.setString(2, data.getTag());

		final int rows = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			data.setId(rs.getLong(1));
		}

		rs.close();
		pstmt.close();
		System.out.println("Inserted hashtag " + rows + " row(s).");

		return data;
	}

	@Override
	public Optional<Hashtag> findById(final Long id) throws SQLException {
		return Optional.empty();
	}

	@Override
	public List<Hashtag> findAll() {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}

	@Override
	public Optional<Hashtag> findByName(final String tag) throws SQLException {
		final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM hashtag WHERE tag = ?");
		pstmt.setString(1, tag);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final Hashtag hashtag = new Hashtag(rs.getLong(1), rs.getString(2));
		rs.close();
		pstmt.close();

		return Optional.of(hashtag);
	}

	@Override
	public List<Hashtag> findAllByPostId(final Long postId) throws SQLException {
		final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM hashtag h "
			+ "JOIN connect_hashtag c ON h.id = c.hashtag_id WHERE post_id = ?;");
		pstmt.setLong(1, postId);

		final ResultSet rs = pstmt.executeQuery();
		final List<Hashtag> hashtags = new ArrayList<>();

		while (rs.next()) {
			final Hashtag hashtag = new Hashtag(rs.getLong(1), rs.getString(2));
			hashtags.add(hashtag);
		}

		rs.close();
		pstmt.close();

		return hashtags;
	}

	@Override
	public List<Hashtag> findTop20DescRandom() throws SQLException {
		final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM hashtag ORDER BY RAND() LIMIT 20");

		final ResultSet rs = pstmt.executeQuery();
		final List<Hashtag> hashtags = new ArrayList<>();

		while (rs.next()) {
			final Hashtag hashtag = new Hashtag(rs.getLong(1), rs.getString(2));
			hashtags.add(hashtag);
		}

		rs.close();
		pstmt.close();

		return hashtags;
	}

}
