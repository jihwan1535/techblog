package com.blog.tech.domain.comment.dto.response;

import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.member.dto.response.MemberInfoResultBean;
import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.entity.MemberInfo;

public record CommentsResponseBean(
	MemberInfoResultBean memberInfo,
	CommentResultBean commentInfo
) {

	public static CommentsResponseBean of(final MemberInfo member, final Comment comment) {
		return new CommentsResponseBean(
			fromMemberInfo(member),
			fromCommentInfo(comment)
		);
	}

	private static MemberInfoResultBean fromMemberInfo(final MemberInfo member) {
		return MemberInfoResultBean.of(member);
	}

	private static CommentResultBean fromCommentInfo(final Comment comment) {
		return CommentResultBean.of(comment);
	}

}
