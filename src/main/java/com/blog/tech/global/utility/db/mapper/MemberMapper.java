package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.entity.vo.MemberRole;
import com.blog.tech.domain.member.entity.vo.MemberStatus;

public class MemberMapper {

	public static Member from(final ResultSet rs) throws SQLException {
		return Member.builder()
			.id(rs.getLong("id"))
			.email(rs.getString("email"))
			.password(rs.getString("password"))
			.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
			.updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
			.build();
	}

}
