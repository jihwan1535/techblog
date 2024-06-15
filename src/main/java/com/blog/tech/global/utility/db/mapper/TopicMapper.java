package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.post.entity.Topic;

public class TopicMapper {

	public static Topic from(final ResultSet rs, final int i) throws SQLException {
		return Topic.builder()
			.id(rs.getLong(i + 1))
			.categoryId(rs.getLong(i + 2))
			.name(rs.getString(i + 3))
			.build();
	}

}
