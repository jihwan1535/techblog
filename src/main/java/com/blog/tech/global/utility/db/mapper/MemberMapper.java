package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.member.entity.Member;

public class MemberMapper {

	public static Member from(final ResultSet rs, final int i) throws SQLException {
		return Member.builder()
			.id(rs.getLong(i+1))
			.email(rs.getString(i+2))
			.password(rs.getString(i+3))
			.createdAt(rs.getTimestamp(i+4).toLocalDateTime())
			.updatedAt(rs.getTimestamp(i+5).toLocalDateTime())
			.build();
	}

}
