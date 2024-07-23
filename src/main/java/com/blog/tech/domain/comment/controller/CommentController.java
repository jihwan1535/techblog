package com.blog.tech.domain.comment.controller;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.comment.dto.request.CommentRequest;
import com.blog.tech.domain.comment.dto.request.DeleteCommentRequest;
import com.blog.tech.domain.comment.dto.request.DeleteReplyRequest;
import com.blog.tech.domain.comment.dto.request.EditCommentRequest;
import com.blog.tech.domain.comment.dto.request.EditReplyRequest;
import com.blog.tech.domain.comment.dto.request.ReplyRequest;
import com.blog.tech.domain.comment.dto.response.CommentResponse;
import com.blog.tech.domain.comment.service.CommentService;

public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	public void writeCommentOnPost(final Long memberId, final CommentRequest request) {
		commentService.writeCommentOnPost(memberId, request);
	}

	public List<CommentResponse> allCommentsOnPost(final Long postId) {
		return commentService.allCommentsAndReplies(postId);
	}

	public void unRegisterComment(final Long memberId, final DeleteCommentRequest request) {
		commentService.unRegisterComment(memberId, request);
	}

	public void updateComment(final Long memberId, final EditCommentRequest request) {
		commentService.updateComment(memberId, request);
	}

	public void writeReplyOnComment(final Long memberId, final ReplyRequest request) {
		commentService.writeReplyOnComment(memberId, request);
	}

	public void updateReply(final Long memberId, final EditReplyRequest request) {
		commentService.updateReply(memberId, request);
	}

	public void unRegisterReply(final Long memberId, final DeleteReplyRequest request) {
		commentService.unRegisterReply(memberId, request);
	}
}
