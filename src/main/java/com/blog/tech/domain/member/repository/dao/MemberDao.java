package com.blog.tech.domain.member.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.repository.ifs.MemberRepository;
import com.blog.tech.global.utility.db.mapper.MemberMapper;

public class MemberDao implements MemberRepository {

	private final Connection conn;

	public MemberDao(final Connection conn) {
		this.conn = conn;
	}

	@Override
	public Member save(final Member data) throws SQLException {
		if (data.getId() > 0) {
			update(data);
			return data;
		}
		return create(data);
	}

	private void update(final Member data) throws SQLException {
		final String sql = "UPDATE member SET email = ?, password = ?, created_at = ?, updated_at = ? WHERE id = ?";
		final PreparedStatement pstmt = conn.prepareStatement(sql);
		setUpdatePstmt(pstmt, data);

		final int rows = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Updated " + rows + " row(s).");
	}

	private Member create(final Member data) throws SQLException {
		final String sql = "INSERT INTO member (id, email, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
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

	@Override
	public Optional<Member> findById(final Long id) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM member WHERE id = ?");
		pstmt.setLong(1, id);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final Member member = MemberMapper.from(rs, 0);
		rs.close();
		pstmt.close();

		return Optional.of(member);
	}

	@Override
	public List<Member> findAll() {
		/*
		* todo
		*  admin일 경우, 전체 회원 조회 기능이 필요
		*  또는 일반 회원도 전체 회원 조회가 가능하게 할 지?
		* */
		return null;
	}

	@Override
	public void delete(final Long id) throws SQLException {
		final String sql = "DELETE FROM member WHERE id = ?";
		final PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, id);

		final int rows = pstmt.executeUpdate();
		pstmt.close();
		System.out.println("Updated " + rows + " row(s).");
	}

	@Override
	public Optional<Member> findByEmail(final String email) throws SQLException {
		final PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM member WHERE email = ?");
		pstmt.setString(1, email);
		final ResultSet rs = pstmt.executeQuery();

		if (!rs.next()) {
			rs.close();
			pstmt.close();
			return Optional.empty();
		}

		final Member member = MemberMapper.from(rs, 0);
		rs.close();
		pstmt.close();

		return Optional.of(member);
	}

	private void setCreatePstmt(final PreparedStatement pstmt, final Member data) throws SQLException {
		pstmt.setLong(1, data.getId());
		pstmt.setString(2, data.getEmail());
		pstmt.setString(3, data.getPassword());
		pstmt.setTimestamp(4, Timestamp.valueOf(data.getCreatedAt()));
		pstmt.setTimestamp(5, Timestamp.valueOf(data.getUpdatedAt()));
	}

	private void setUpdatePstmt(final PreparedStatement pstmt, final Member data) throws SQLException {
		pstmt.setString(1, data.getEmail());
		pstmt.setString(2, data.getPassword());
		pstmt.setTimestamp(3, Timestamp.valueOf(data.getCreatedAt()));
		pstmt.setTimestamp(4, Timestamp.valueOf(data.getUpdatedAt()));
		pstmt.setLong(5, data.getId());
	}

}
