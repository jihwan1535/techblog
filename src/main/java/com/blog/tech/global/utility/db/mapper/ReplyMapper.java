package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.comment.entity.Reply;
import com.blog.tech.domain.comment.entity.vo.Status;

public class ReplyMapper {

	public static Reply from(final ResultSet rs, final int i) throws SQLException {
		return Reply.builder()
			.id(rs.getLong(i+1))
			.memberInfoId(rs.getLong(i+2))
			.commentId(rs.getLong(i+3))
			.content(rs.getString(i+4))
			.reportCount(rs.getInt(i+5))
			.status(Status.valueOf(rs.getString(i+6)))
			.createdAt(rs.getTimestamp(i+7).toLocalDateTime())
			.updatedAt(rs.getTimestamp(i+8).toLocalDateTime())
			.build();
	}

}
