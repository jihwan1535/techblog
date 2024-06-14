package com.blog.tech.domain.comment.controller;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.comment.dto.request.CommentRequestBean;
import com.blog.tech.domain.comment.dto.response.CommentsResponseBean;
import com.blog.tech.domain.comment.service.CommentService;

public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	public void writeCommentOnPost(final Long memberId, final CommentRequestBean request) throws SQLException {
		commentService.writeCommentOnPost(memberId, request);
	}

	public List<CommentsResponseBean> allCommentsOnPost(final Long postId) throws SQLException {
		return commentService.allCommentsAndReplies(postId);
	}
}
