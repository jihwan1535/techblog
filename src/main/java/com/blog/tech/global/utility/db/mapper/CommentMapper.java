package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.comment.entity.vo.Status;

public class CommentMapper {

	public static Comment from(final ResultSet rs) throws SQLException {
		return Comment.builder()
			.id(rs.getLong(1))
			.memberInfoId(rs.getLong(2))
			.postId(rs.getLong(3))
			.content(rs.getString(4))
			.reportCount(rs.getInt(5))
			.alarm(rs.getBoolean(6))
			.status(Status.valueOf(rs.getString(7)))
			.createdAt(rs.getTimestamp(8).toLocalDateTime())
			.updatedAt(rs.getTimestamp(9).toLocalDateTime())
			.build();
	}

}
