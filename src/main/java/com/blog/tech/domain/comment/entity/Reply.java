package com.blog.tech.domain.comment.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.blog.tech.domain.comment.dto.request.ReplyRequest;
import com.blog.tech.domain.comment.entity.vo.Status;
import com.blog.tech.domain.common.BaseEntity;
import com.blog.tech.domain.member.entity.MemberInfo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
public class Reply extends BaseEntity {

	private Long id;
	private Long memberInfoId;
	private Long commentId;
	private String content;
	private Integer reportCount;
	private Status status;
	private MemberInfo member;
	private Comment comment;

	public static Reply to(final Long memberId, final ReplyRequest bean) {
		return Reply.builder()
			.id(0L)
			.memberInfoId(memberId)
			.commentId(bean.commentId())
			.content(bean.content())
			.status(Status.REGISTERED)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	public static Reply from(final ResultSet rs, final int i) throws SQLException {
		return Reply.builder()
			.id(rs.getLong(i + 1))
			.memberInfoId(rs.getLong(i + 2))
			.commentId(rs.getLong(i + 3))
			.content(rs.getString(i + 4))
			.reportCount(rs.getInt(i + 5))
			.status(Status.valueOf(rs.getString(i + 6)))
			.createdAt(rs.getTimestamp(i + 7).toLocalDateTime())
			.updatedAt(rs.getTimestamp(i + 8).toLocalDateTime())
			.build();
	}

}
