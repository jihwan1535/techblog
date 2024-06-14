package com.blog.tech.domain.comment.dto.response;

import com.blog.tech.domain.comment.entity.Comment;

public record CommentResultBean(
	Long commentId,
	String content
) {

	public static CommentResultBean of(final Comment comment) {
		return new CommentResultBean(comment.getId(), comment.getContent());
	}

}
