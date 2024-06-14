package com.blog.tech.domain.comment.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
		if (data.getId() > 0) {
			update(data);
			return data;
		}
		return create(data);
	}

	private Reply create(final Reply data) throws SQLException {
		final String sql = "INSERT INTO reply (id, member_info_id, comment_id, content, status, created_at, updated_at) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		pstmt.setLong(1, data.getId());
		pstmt.setLong(2, data.getMemberInfoId());
		pstmt.setLong(3, data.getCommentId());
		pstmt.setString(4, data.getContent());
		pstmt.setString(5, String.valueOf(data.getStatus()));
		pstmt.setTimestamp(6, Timestamp.valueOf(data.getCreatedAt()));
		pstmt.setTimestamp(7, Timestamp.valueOf(data.getUpdatedAt()));

		final int rows = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			data.setId(rs.getLong(1));
		}
		rs.close();
		pstmt.close();
		System.out.println("Inserted reply " + rows + " row(s).");

		return data;
	}

	private void update(final Reply data) throws SQLException {
		final String sql = "UPDATE reply SET content = ?, report_count = ?, status = ?, updated_at = ? WHERE id = ?";
		final PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, data.getContent());
		pstmt.setLong(2, data.getReportCount());
		pstmt.setString(3, data.getStatus().toString());
		pstmt.setTimestamp(4, Timestamp.valueOf(data.getUpdatedAt()));
		pstmt.setLong(5, data.getId());

		final int rows = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Updated comment " + rows + " row(s).");
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
