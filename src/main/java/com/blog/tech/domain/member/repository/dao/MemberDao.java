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
import com.blog.tech.global.utility.DBUtility;

public class MemberDao implements MemberRepository {

	private final Connection conn;

	public MemberDao(final Connection conn) {
		this.conn = conn;
	}

	@Override
	public Member save(final Member data) throws SQLException {
		final String sql = "INSERT INTO member (id, email, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
		final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		pstmt.setLong(1, 0);
		pstmt.setString(2, data.getEmail());
		pstmt.setString(3, data.getPassword());
		pstmt.setTimestamp(4, Timestamp.valueOf(data.getCreatedAt()));
		pstmt.setTimestamp(5, Timestamp.valueOf(data.getUpdatedAt()));

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
	public Optional<Member> findById(final Long id) {
		return null;
	}

	@Override
	public List<Member> findByAll() {
		return null;
	}

	@Override
	public void delete(final Long id) {

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

		final Member member = Member.builder()
			.id(rs.getLong("id"))
			.email(rs.getString("email"))
			.password(rs.getString("password"))
			.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
			.updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
			.build();

		rs.close();
		pstmt.close();

		return Optional.of(member);
	}

}
