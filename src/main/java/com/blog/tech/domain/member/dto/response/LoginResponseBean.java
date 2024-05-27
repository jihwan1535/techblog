package com.blog.tech.domain.member.dto.response;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.entity.vo.MemberStatus;

public record LoginResponseBean(
	Long id,
	String nickname,
	MemberStatus status
) {

	public static LoginResponseBean of(final MemberInfo memberInfo) {
		return new LoginResponseBean(memberInfo.getId(), memberInfo.getNickname(), memberInfo.getStatus());
	}

}
