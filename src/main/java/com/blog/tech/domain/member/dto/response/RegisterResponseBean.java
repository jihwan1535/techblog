package com.blog.tech.domain.member.dto.response;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.global.utility.Utility;

public record RegisterResponseBean(
	Long id,
	String nickname,
	String createdAt
) {

	public static RegisterResponseBean of(final MemberInfo member) {
		final String format = Utility.DateTimeFormatter(member.getCreatedAt());
		return new RegisterResponseBean(member.getId(), member.getNickname(), format);
	}

}
