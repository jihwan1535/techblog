package com.blog.tech.domain.member.dto.response;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.global.utility.Utility;

public record RegisterResponseBean(
	Long id,
	String nickname,
	String createdAt
) {

	public static RegisterResponseBean of(final MemberInfo member) {
		return new RegisterResponseBean(
			member.getId(),
			member.getNickname(),
			Utility.DateTimeFormatter(member.getCreatedAt())
		);
	}

}
