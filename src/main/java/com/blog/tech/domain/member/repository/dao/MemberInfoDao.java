package com.blog.tech.domain.member.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.global.utility.db.mapper.MemberInfoMapper;

public class MemberInfoDao implements MemberInfoRepository {

	private final Connection conn;

	public MemberInfoDao(final Connection conn) {
		this.conn = conn;
	}

	@Override
	public MemberInfo save(final MemberInfo data) throws SQLException {
		if (findById(data.getId()).isPresent()) {
			update(data);
			return data;
		}
		return create(data);
	}

	private MemberInfo create(final MemberInfo data) throws SQLException {
		final String sql = "INSERT INTO member_info (id, member_id, nickname, image, about_me, role, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

	private void update(final MemberInfo data) throws SQLException {
		final String sql = "UPDATE member_info SET member_id = ?, nickname = ?, image = ?, about_me = ?, role = ?, post_count = ?, comment_count = ?, alarm_count = ?, status = ?, created_at = ?, updated_at = ? WHERE id = ?";
		final PreparedStatement pstmt = conn.prepareStatement(sql);
		setUpdatePstmt(pstmt, data);

		final int rows = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Updated " + rows + " row(s).");
	}

	@Override
	public Optional<MemberInfo> findById(final Long id) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM member_info WHERE id = ?");
		pstmt.setLong(1, id);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final MemberInfo memberInfo = MemberInfoMapper.from(rs);
		rs.close();
		pstmt.close();

		return Optional.of(memberInfo);
	}

	@Override
	public List<MemberInfo> findAll() {
		return null;
	}

	@Override
	public void delete(final Long id) {

	}

	@Override
	public Optional<MemberInfo> findByMemberId(final Long memberId) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM member_info WHERE member_id = ?");
		pstmt.setLong(1, memberId);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final MemberInfo memberInfo = MemberInfoMapper.from(rs);

		rs.close();
		pstmt.close();

		return Optional.of(memberInfo);
	}

	@Override
	public Optional<MemberInfo> findByNickname(final String nickname) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM member_info WHERE nickname = ?");
		pstmt.setString(1, nickname);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final MemberInfo memberInfo = MemberInfoMapper.from(rs);

		rs.close();
		pstmt.close();

		return Optional.of(memberInfo);
	}

	private void setCreatePstmt(final PreparedStatement pstmt, final MemberInfo data) throws SQLException {
		pstmt.setLong(1, data.getId());
		pstmt.setLong(2, data.getMemberId());
		pstmt.setString(3, data.getNickname());
		pstmt.setString(4, data.getImage());
		pstmt.setString(5, data.getAboutMe());
		pstmt.setString(6, data.getRole().toString());
		pstmt.setString(7, data.getStatus().toString());
		pstmt.setTimestamp(8, Timestamp.valueOf(data.getCreatedAt()));
		pstmt.setTimestamp(9, Timestamp.valueOf(data.getUpdatedAt()));
	}

	private void setUpdatePstmt(final PreparedStatement pstmt, final MemberInfo data) throws SQLException {
		pstmt.setLong(1, data.getMemberId());
		pstmt.setString(2, data.getNickname());
		pstmt.setString(3, data.getImage());
		pstmt.setString(4, data.getAboutMe());
		pstmt.setString(5, data.getRole().toString());
		pstmt.setInt(6, data.getPostCount());
		pstmt.setInt(7, data.getCommentCount());
		pstmt.setInt(8, data.getAlarmCount());
		pstmt.setString(9, data.getStatus().toString());
		pstmt.setTimestamp(10, Timestamp.valueOf(data.getCreatedAt()));
		pstmt.setTimestamp(11, Timestamp.valueOf(data.getUpdatedAt()));
		pstmt.setLong(12, data.getId());
	}

}
