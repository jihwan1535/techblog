package com.blog.tech.domain.member.dto.response;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.entity.vo.MemberStatus;

public record MemberResponseBean(
		Long id,
		String nickname,
		String image,
		MemberStatus status
) {

	public static MemberResponseBean of(final MemberInfo memberInfo) {
		return new MemberResponseBean(
				memberInfo.getId(),
				memberInfo.getNickname(),
				memberInfo.getImage(),
				memberInfo.getStatus()
		);
	}

}