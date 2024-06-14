package com.blog.tech.domain.comment.entity;

import java.time.LocalDateTime;

import com.blog.tech.domain.comment.dto.request.CommentRequestBean;
import com.blog.tech.domain.comment.entity.vo.Status;
import com.blog.tech.domain.common.BaseEntity;
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

	public static Comment to(final Long memberId, final CommentRequestBean bean) {
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

}
