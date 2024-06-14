package com.blog.tech.domain.comment.dto.request;

public record EditCommentRequest(
	Long commentId,
	String content
) {
}
