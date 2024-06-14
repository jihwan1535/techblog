package com.blog.tech.domain.comment.entity;

import java.time.LocalDateTime;

import com.blog.tech.domain.comment.dto.request.ReplyRequest;
import com.blog.tech.domain.common.BaseEntity;
import com.blog.tech.domain.post.entity.vo.Status;

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

}
