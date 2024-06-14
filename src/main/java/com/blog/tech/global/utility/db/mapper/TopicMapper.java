package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.post.entity.Topic;

public class TopicMapper {

	public static Topic from(final ResultSet rs) throws SQLException {
		return Topic.builder()
			.id(rs.getLong("id"))
			.categoryId(rs.getLong("category_id"))
			.name(rs.getString("name"))
			.build();
	}

}
