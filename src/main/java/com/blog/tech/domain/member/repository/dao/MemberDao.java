package com.blog.tech.domain.member.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.repository.ifs.MemberRepository;
import com.blog.tech.global.utility.DBUtility;

public class MemberDao implements MemberRepository {

	private final Connection conn = DBUtility.CONNECTION;

	@Override
	public Member save(final Member data) throws SQLException {
		final String sql = "INSERT INTO member (id, email, password, created_at, updated_at) VALUES (?, ?, ? ?, ?)";
		final PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		pstmt.setLong(1, 0);
		pstmt.setString(2, data.getEmail());
		pstmt.setString(3, data.getPassword());
		pstmt.setTimestamp(4, Timestamp.valueOf(data.getCreatedAt()));
		pstmt.setTimestamp(5, Timestamp.valueOf(data.getCreatedAt()));

		final int rows = pstmt.executeUpdate();
		pstmt.close();

		System.out.println("Inserted " + rows + " row(s).");

		return data;
	}

	@Override
	public Member findById(final Long id) {
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
	public Member findByEmail(final String email) {
		return null;
	}

}
