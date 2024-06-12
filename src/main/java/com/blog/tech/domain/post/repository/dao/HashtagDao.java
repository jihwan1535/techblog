package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.post.entity.Hashtag;
import com.blog.tech.domain.post.repository.ifs.HashtagRepository;
import com.blog.tech.global.utility.db.mapper.MemberInfoMapper;

public class HashtagDao implements HashtagRepository {

	private final Connection conn;

	public HashtagDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Hashtag save(final Hashtag data) throws SQLException {
		final String sql = "INSERT INTO hashtag (id, tag) VALUES (?, ?)";
		final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setLong(1, data.getId());
		pstmt.setString(2, data.getName());

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
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM hashtag WHERE tag = ?");
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
}
