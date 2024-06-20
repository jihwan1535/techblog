package com.blog.tech.domain.comment.dto.response;

import java.time.LocalDateTime;

import com.blog.tech.domain.comment.entity.Reply;
import com.blog.tech.domain.member.dto.response.MemberInfoResult;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.global.utility.DateFormatter;

public record ReplyInfoResult(
	MemberInfoResult memberInfo,
	Long replyId,
	String content,
	String createdAt,
	Boolean notModified
) {

	public static ReplyInfoResult of(final Reply reply) {
		return new ReplyInfoResult(
			fromMemberInfo(reply.getMember()),
			reply.getId(),
			reply.getContent(),
			fromDateFormat(reply.getCreatedAt()),
			reply.getCreatedAt().isEqual(reply.getUpdatedAt())
		);
	}

	private static MemberInfoResult fromMemberInfo(final MemberInfo member) {
		return MemberInfoResult.of(member);
	}

	private static String fromDateFormat(final LocalDateTime at) {
		return DateFormatter.format(at);
	}

}
