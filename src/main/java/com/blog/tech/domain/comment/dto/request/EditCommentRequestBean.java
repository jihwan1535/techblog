package com.blog.tech.domain.comment.dto.request;

public record EditCommentRequestBean(
	Long commentId,
	String content
) {
}
