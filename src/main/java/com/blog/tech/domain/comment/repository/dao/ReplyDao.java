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
import com.blog.tech.domain.comment.entity.Reply;
import com.blog.tech.domain.comment.entity.vo.Status;
import com.blog.tech.domain.comment.repository.ifs.ReplyRepository;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.global.utility.db.mapper.CommentMapper;
import com.blog.tech.global.utility.db.mapper.MemberInfoMapper;
import com.blog.tech.global.utility.db.mapper.ReplyMapper;

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
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reply r join member_info m "
			+ "on r.member_info_id = m.id join comment c on r.comment_id = c.id WHERE r.id = ?");
		pstmt.setLong(1, id);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final Reply reply = getReplyInfo(rs);
		rs.close();
		pstmt.close();

		return Optional.of(reply);
	}

	@Override
	public List<Reply> findAll() throws SQLException {
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {

	}

	@Override
	public List<Reply> findAllByCommentIdAndStatus(final Long commentId, final Status status) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reply r join member_info m "
			+ "on r.member_info_id = m.id join comment c on r.comment_id = c.id "
			+ "WHERE r.comment_id = ? AND r.status = ? ORDER BY r.id;");
		pstmt.setLong(1, commentId);
		pstmt.setString(2, String.valueOf(status));
		final ResultSet rs = pstmt.executeQuery();

		final List<Reply> replies = new ArrayList<>();
		while (rs.next()) {
			replies.add(getReplyInfo(rs));
		}

		rs.close();
		pstmt.close();
		return replies;
	}

	private Reply getReplyInfo(final ResultSet rs) throws SQLException {
		final Reply reply = ReplyMapper.from(rs, 0);
		final MemberInfo memberInfo = MemberInfoMapper.from(rs, 8);
		final Comment comment = CommentMapper.from(rs, 20);
		reply.setMember(memberInfo);
		reply.setComment(comment);
		return reply;
	}
}
