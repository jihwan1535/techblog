package com.blog.tech.domain.comment.dto.request;

public record CommentRequestBean(
	Long postId,
	String content
) {

}
