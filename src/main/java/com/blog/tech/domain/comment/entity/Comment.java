package com.blog.tech.domain.comment.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.blog.tech.domain.comment.dto.request.CommentRequest;
import com.blog.tech.domain.comment.entity.vo.Status;
import com.blog.tech.domain.common.BaseEntity;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.post.entity.Post;

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
public class Comment extends BaseEntity {

	private Long id;
	private Long memberInfoId;
	private Long postId;
	private String content;
	private Integer reportCount;
	private Boolean alarm;
	private Status status;
	private MemberInfo member;
	private Post post;
	private List<Reply> replies;

	public static Comment to(final Long memberId, final CommentRequest bean) {
		return Comment.builder()
			.id(0L)
			.memberInfoId(memberId)
			.postId(bean.postId())
			.content(bean.content())
			.status(Status.REGISTERED)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	public static Comment from(final ResultSet rs, final int i) throws SQLException {
		return Comment.builder()
			.id(rs.getLong(i + 1))
			.memberInfoId(rs.getLong(i + 2))
			.postId(rs.getLong(i + 3))
			.content(rs.getString(i + 4))
			.reportCount(rs.getInt(i + 5))
			.alarm(rs.getBoolean(i + 6))
			.status(Status.valueOf(rs.getString(i + 7)))
			.createdAt(rs.getTimestamp(i + 8).toLocalDateTime())
			.updatedAt(rs.getTimestamp(i + 9).toLocalDateTime())
			.build();
	}

}
