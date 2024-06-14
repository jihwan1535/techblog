package com.blog.tech.domain.comment.dto.request;

public record DeleteCommentRequest(
	Long postId,
	Long commentId
) {
}
