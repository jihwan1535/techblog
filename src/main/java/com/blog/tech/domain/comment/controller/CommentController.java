package com.blog.tech.domain.comment.controller;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.comment.dto.request.CommentRequest;
import com.blog.tech.domain.comment.dto.request.DeleteCommentRequest;
import com.blog.tech.domain.comment.dto.request.EditCommentRequest;
import com.blog.tech.domain.comment.dto.request.ReplyRequest;
import com.blog.tech.domain.comment.dto.response.CommentResponse;
import com.blog.tech.domain.comment.service.CommentService;

public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	public void writeCommentOnPost(final Long memberId, final CommentRequest request) throws SQLException {
		commentService.writeCommentOnPost(memberId, request);
	}

	public List<CommentResponse> allCommentsOnPost(final Long postId) throws SQLException {
		return commentService.allCommentsAndReplies(postId);
	}

	public void unRegisterComment(final Long memberId, final DeleteCommentRequest request) throws SQLException {
		commentService.unRegisterComment(memberId, request);
	}

	public void updateComment(final Long memberId, final EditCommentRequest request) throws SQLException {
		commentService.updateComment(memberId, request);
	}

	public void writeReplyOnComment(final Long memberId, final ReplyRequest request) throws SQLException {
		commentService.writeReplyOnComment(memberId, request);
	}

}
