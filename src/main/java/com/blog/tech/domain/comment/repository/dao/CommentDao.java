package com.blog.tech.domain.comment.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.comment.entity.vo.Status;
import com.blog.tech.domain.comment.repository.ifs.CommentRepository;
import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.global.utility.db.mapper.CommentMapper;
import com.blog.tech.global.utility.db.mapper.MemberInfoMapper;
import com.blog.tech.global.utility.db.mapper.MemberMapper;
import com.blog.tech.global.utility.db.mapper.PostMapper;

public class CommentDao implements CommentRepository {
	private final Connection conn;

	public CommentDao(final Connection conn) {
		this.conn = conn;
	}

	@Override
	public Comment save(final Comment data) throws SQLException {
		if (data.getId() > 0) {
			update(data);
			return data;
		}
		return create(data);
	}

	private Comment create(final Comment data) throws SQLException {
		final String sql = "INSERT INTO comment (id, member_info_id, post_id, content, status, created_at, updated_at) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		pstmt.setLong(1, data.getId());
		pstmt.setLong(2, data.getMemberInfoId());
		pstmt.setLong(3, data.getPostId());
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
		System.out.println("Inserted comment " + rows + " row(s).");

		return data;
	}

	private void update(final Comment data) throws SQLException {
		final String sql = "UPDATE comment SET content = ?, report_count = ?, alarm = ?, status = ?, updated_at = ? WHERE id = ?";
		final PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, data.getContent());
		pstmt.setLong(2, data.getReportCount());
		pstmt.setBoolean(3, data.getAlarm());
		pstmt.setString(4, data.getStatus().toString());
		pstmt.setTimestamp(5, Timestamp.valueOf(data.getUpdatedAt()));
		pstmt.setLong(6, data.getId());

		final int rows = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Updated comment " + rows + " row(s).");
	}

	@Override
	public Optional<Comment> findById(final Long id) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM comment WHERE id = ?");
		pstmt.setLong(1, id);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final Comment comment = CommentMapper.from(rs, 0);
		rs.close();
		pstmt.close();

		return Optional.of(comment);
	}

	@Override
	public List<Comment> findAll() throws SQLException {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}

	@Override
	public List<Comment> findTop10ByPostIdAndStatusDescId(final Long postId, final Status status) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM comment c join member_info m "
			+ "on c.member_info_id = m.id join post p on c.post_id = p.id "
			+ "WHERE c.post_id = ? AND c.status = ? "
			+ "ORDER BY c.id DESC LIMIT 10;");
		pstmt.setLong(1, postId);
		pstmt.setString(2, String.valueOf(status));
		final ResultSet rs = pstmt.executeQuery();

		final List<Comment> comments = new ArrayList<>();
		while (rs.next()) {
			final Comment comment = CommentMapper.from(rs, 0);
			final MemberInfo memberInfo = MemberInfoMapper.from(rs, 9);
			final Post post = PostMapper.from(rs, 21);
			comment.setMember(memberInfo);
			comment.setPost(post);
			comments.add(comment);
		}

		rs.close();
		pstmt.close();
		return comments;
	}
}
