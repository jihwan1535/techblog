package com.blog.tech.domain.member.dto.response;

import java.time.LocalDateTime;

import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.global.utility.Utility;

public record RegisteredMemberBean(
	Long id,
	String nickname,
	String createdAt
) {

	public static RegisteredMemberBean of(final MemberInfo member) {
		final String format = Utility.DateTimeFormatter(member.getCreatedAt());
		return new RegisteredMemberBean(member.getId(), member.getNickname(), format);
	}

}
