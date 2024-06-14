package com.blog.tech.domain.comment.dto.response;

import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.member.dto.response.MemberInfoResultBean;
import com.blog.tech.domain.member.entity.MemberInfo;

public record CommentsResponse(
	MemberInfoResultBean memberInfo,
	CommentResult commentInfo
) {

	public static CommentsResponse of(final MemberInfo member, final Comment comment) {
		return new CommentsResponse(
			fromMemberInfo(member),
			fromCommentInfo(comment)
		);
	}

	private static MemberInfoResultBean fromMemberInfo(final MemberInfo member) {
		return MemberInfoResultBean.of(member);
	}

	private static CommentResult fromCommentInfo(final Comment comment) {
		return CommentResult.of(comment);
	}

}
