package com.blog.tech.domain.comment.dto.request;

public record DeleteReplyRequest(
	Long postId,
	Long replyId
) {
}
