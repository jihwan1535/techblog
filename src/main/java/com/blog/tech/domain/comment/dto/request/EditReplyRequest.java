package com.blog.tech.domain.comment.dto.request;

public record EditReplyRequest(
	Long replyId,
	String content
) {
}
