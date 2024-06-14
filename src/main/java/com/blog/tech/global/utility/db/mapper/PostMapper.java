package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.blog.tech.domain.post.dto.request.PostRequestBean;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.entity.vo.Status;

public class PostMapper {

	public static Post from(final ResultSet rs) throws SQLException {
		return Post.builder()
			.id(rs.getLong(1))
			.memberInfoId(rs.getLong(2))
			.topicId(rs.getLong(3))
			.categoryId(rs.getLong(4))
			.tile(rs.getString(5))
			.content(rs.getString(6))
			.commentCount(rs.getInt(7))
			.replyCount(rs.getInt(8))
			.viewCount(rs.getInt(9))
			.reportCount(rs.getInt(10))
			.scrapCount(rs.getInt(11))
			.alarm(rs.getBoolean(12))
			.status(Status.valueOf(rs.getString(13)))
			.createdAt(rs.getTimestamp(14).toLocalDateTime())
			.updatedAt(rs.getTimestamp(15).toLocalDateTime())
			.build();
	}

}
