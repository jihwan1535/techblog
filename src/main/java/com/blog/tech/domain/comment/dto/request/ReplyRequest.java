package com.blog.tech.domain.comment.dto.request;

public record ReplyRequest(
	Long postId,
	Long commentId,
	String content
) {
}
