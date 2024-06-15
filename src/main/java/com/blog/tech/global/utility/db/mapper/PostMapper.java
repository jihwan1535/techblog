package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.entity.vo.Status;

public class PostMapper {

	public static Post from(final ResultSet rs, final int i) throws SQLException {
		return Post.builder()
			.id(rs.getLong(i + 1))
			.memberInfoId(rs.getLong(i + 2))
			.topicId(rs.getLong(i + 3))
			.categoryId(rs.getLong(i + 4))
			.tile(rs.getString(i + 5))
			.content(rs.getString(i + 6))
			.commentCount(rs.getInt(i + 7))
			.replyCount(rs.getInt(i + 8))
			.viewCount(rs.getInt(i + 9))
			.reportCount(rs.getInt(i + 10))
			.scrapCount(rs.getInt(i + 11))
			.alarm(rs.getBoolean(i + 12))
			.status(Status.valueOf(rs.getString(i + 13)))
			.createdAt(rs.getTimestamp(i + 14).toLocalDateTime())
			.updatedAt(rs.getTimestamp(i + 15).toLocalDateTime())
			.build();
	}

}
