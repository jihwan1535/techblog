package com.blog.tech.domain.member.dto.response;

import com.blog.tech.domain.member.entity.MemberInfo;

public record MemberInfoResult(
	Long id,
	String name,
	String image
) {

	public static MemberInfoResult of(final MemberInfo member) {
		return new MemberInfoResult(member.getId(), member.getNickname(), member.getImage());
	}

}
