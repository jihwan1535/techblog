package com.blog.tech.domain.post.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
		if (findById(data.getId()).isPresent()) {
			update(data);
			return data;
		}
		return create(data);
	}
	private void update(final Post data) throws SQLException {
		final String sql = "UPDATE post SET topic_id = ?, category_id = ?, title = ?, content = ?, comment_count = ?, "
			+ "view_count = ?, report_count = ?, scrap_count = ?, alarm = ?, status = ?, updated_at = ? WHERE id = ?";
		final PreparedStatement pstmt = conn.prepareStatement(sql);
		setUpdatePstmt(pstmt, data);

		final int rows = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Updated " + rows + " row(s).");
	}

	private void setUpdatePstmt(final PreparedStatement pstmt, final Post data) throws SQLException {
		pstmt.setLong(1, data.getTopicId());
		pstmt.setLong(2, data.getCategoryId());
		pstmt.setString(3, data.getTile());
		pstmt.setString(4, data.getContent());
		pstmt.setInt(5, data.getCommentCount());
		pstmt.setInt(6, data.getViewCount());
		pstmt.setInt(7, data.getReportCount());
		pstmt.setInt(8, data.getScrapCount());
		pstmt.setBoolean(9, data.getAlarm());
		pstmt.setString(10, data.getStatus().toString());
		pstmt.setTimestamp(11, Timestamp.valueOf(data.getUpdatedAt()));
		pstmt.setLong(12, data.getId());
	}

	private Post create(final Post data) throws SQLException {
		final String sql = "INSERT INTO post (id, member_info_id, topic_id, category_id, title, content, "
			+ "status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		setCreatePstmt(pstmt, data);

		final int rows = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			data.setId(rs.getLong(1));
		}
		rs.close();
		pstmt.close();
		System.out.println("Inserted " + rows + " row(s).");

		return data;
	}

	private void setCreatePstmt(final PreparedStatement pstmt, final Post data) throws SQLException {
		pstmt.setLong(1, data.getId());
		pstmt.setLong(2, data.getMemberInfoId());
		pstmt.setLong(3, data.getTopicId());
		pstmt.setLong(4, data.getCategoryId());
		pstmt.setString(5, data.getTile());
		pstmt.setString(6, data.getContent());
		pstmt.setString(7, data.getStatus().toString());
		pstmt.setTimestamp(8, Timestamp.valueOf(data.getUpdatedAt()));
		pstmt.setTimestamp(9, Timestamp.valueOf(data.getUpdatedAt()));
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
