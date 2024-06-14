package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.member.entity.MemberInfo;

public record MemberInfoResultBean(
	Long id,
	String name,
	String image
) {

	public static MemberInfoResultBean of(final MemberInfo member) {
		return new MemberInfoResultBean(member.getId(), member.getNickname(), member.getImage());
	}

}
