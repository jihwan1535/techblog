package com.blog.tech.domain.member.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
public class Member extends BaseEntity {

	private Long id;
	private String email;
	private String password;

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
