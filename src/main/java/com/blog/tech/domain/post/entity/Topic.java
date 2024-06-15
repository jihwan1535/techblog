package com.blog.tech.domain.post.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Topic {

	private Long id;
	private Long categoryId;
	private String name;

	public static Topic from(final ResultSet rs, final int i) throws SQLException {
		return Topic.builder()
			.id(rs.getLong(i + 1))
			.categoryId(rs.getLong(i + 2))
			.name(rs.getString(i + 3))
			.build();
	}

}
