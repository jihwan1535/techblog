package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.comment.entity.vo.Status;

public class CommentMapper {

	public static Comment from(final ResultSet rs, final int i) throws SQLException {
		return Comment.builder()
			.id(rs.getLong(i+1))
			.memberInfoId(rs.getLong(i+2))
			.postId(rs.getLong(i+3))
			.content(rs.getString(i+4))
			.reportCount(rs.getInt(i+5))
			.alarm(rs.getBoolean(i+6))
			.status(Status.valueOf(rs.getString(i+7)))
			.createdAt(rs.getTimestamp(i+8).toLocalDateTime())
			.updatedAt(rs.getTimestamp(i+9).toLocalDateTime())
			.build();
	}

}
