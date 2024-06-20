package com.blog.tech.domain.member.dto.response;

import com.blog.tech.domain.member.entity.MemberInfo;

public record SearchMemberResponse(
	String nickname,
	String imageUrl,
	String aboutMe
){

	public static SearchMemberResponse of(final MemberInfo memberInfo) {
		return new SearchMemberResponse(memberInfo.getNickname(), memberInfo.getImage(), memberInfo.getAboutMe());
	}

}
