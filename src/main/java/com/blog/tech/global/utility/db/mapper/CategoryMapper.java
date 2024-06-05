package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.post.entity.Category;

public class CategoryMapper {

	public static Category from(final ResultSet rs) throws SQLException {
		return Category.builder()
			.id(rs.getLong("id"))
			.name(rs.getString("name"))
			.build();
	}

}
