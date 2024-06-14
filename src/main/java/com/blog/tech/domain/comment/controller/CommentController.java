package com.blog.tech.domain.comment.controller;

import java.sql.SQLException;

import com.blog.tech.domain.comment.dto.request.CommentRequestBean;
import com.blog.tech.domain.comment.service.CommentService;

public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	public void writeCommentOnPost(final Long memberId, final CommentRequestBean request) throws SQLException {
		commentService.writeCommentOnPost(memberId, request);
	}

}
