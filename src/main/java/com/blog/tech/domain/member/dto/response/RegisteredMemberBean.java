package com.blog.tech.domain.member.dto.response;

import java.time.LocalDateTime;

import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.entity.MemberInfo;

public record RegisteredMemberBean(
	Long id,
	String nickname,
	LocalDateTime createdAt
) {

	public static RegisteredMemberBean of(final MemberInfo member) {
		return new RegisteredMemberBean(member.getId(), member.getNickname(), member.getCreatedAt());
	}

}
