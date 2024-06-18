package com.blog.tech.domain.post.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.post.dto.response.TopicResponse;

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
	private List<Topic> topics;

	public static Category from(final ResultSet rs, final int i) throws SQLException {
		return Category.builder()
			.id(rs.getLong(i + 1))
			.name(rs.getString(i + 2))
			.build();
	}

}
