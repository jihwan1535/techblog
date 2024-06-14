package com.blog.tech.domain.comment.dto.response;

import java.time.LocalDateTime;

import com.blog.tech.domain.comment.entity.Reply;
import com.blog.tech.domain.member.dto.response.MemberInfoResultBean;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.global.utility.DateFormatter;

public record ReplyInfoResult(
	MemberInfoResultBean memberInfo,
	Long replyId,
	String content,
	String createAt,
	Boolean modified
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

	private static MemberInfoResultBean fromMemberInfo(final MemberInfo member) {
		return MemberInfoResultBean.of(member);
	}

	private static String fromDateFormat(final LocalDateTime at) {
		return DateFormatter.format(at);
	}

}
