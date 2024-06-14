package com.blog.tech.domain.comment.dto.request;

public record CommentRequest(
	Long postId,
	String content
) {

}
