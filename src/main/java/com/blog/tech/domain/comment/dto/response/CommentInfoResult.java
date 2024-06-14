package com.blog.tech.domain.comment.dto.response;

import java.time.LocalDateTime;

import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.member.dto.response.MemberInfoResultBean;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.global.utility.DateFormatter;

public record CommentInfoResult(
	MemberInfoResultBean memberInfo,
	Long commentId,
	String content,
	Boolean alarm,
	String createAt,
	Boolean modified
) {

	public static CommentInfoResult of(final Comment comment) {
		return new CommentInfoResult(
			fromMemberInfo(comment.getMember()),
			comment.getId(),
			comment.getContent(),
			comment.getAlarm(),
			fromDateFormat(comment.getCreatedAt()),
			comment.getCreatedAt().isEqual(comment.getUpdatedAt())
		);
	}

	private static MemberInfoResultBean fromMemberInfo(final MemberInfo member) {
		return MemberInfoResultBean.of(member);
	}

	private static String fromDateFormat(final LocalDateTime at) {
		return DateFormatter.format(at);
	}

}
