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
public class Category {

	private Long id;
	private String name;

	public static Category from(final ResultSet rs, final int i) throws SQLException {
		return Category.builder()
			.id(rs.getLong("id"))
			.name(rs.getString("name"))
			.build();
	}

}
