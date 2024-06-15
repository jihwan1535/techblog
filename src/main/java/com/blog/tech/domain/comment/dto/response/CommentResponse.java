package com.blog.tech.domain.comment.dto.response;

import java.util.List;

import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.comment.entity.Reply;

public record CommentResponse(
	CommentInfoResult commentInfo,
	List<ReplyInfoResult> replyInfos
) {

	public static CommentResponse of(final Comment comment) {
		return new CommentResponse(
			fromCommentInfo(comment),
			fromReplyInfo(comment.getReplies())
		);
	}

	private static CommentInfoResult fromCommentInfo(final Comment comment) {
		return CommentInfoResult.of(comment);
	}

	private static List<ReplyInfoResult> fromReplyInfo(final List<Reply> replies) {
		return replies.stream()
			.map(ReplyInfoResult::of)
			.toList();
	}
}
