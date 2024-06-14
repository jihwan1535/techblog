package com.blog.tech.domain.comment.dto.response;

import com.blog.tech.domain.comment.entity.Comment;

public record CommentResult(
	Long commentId,
	String content
) {

	public static CommentResult of(final Comment comment) {
		return new CommentResult(comment.getId(), comment.getContent());
	}

}
