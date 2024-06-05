package com.blog.tech.domain.member.dto.response;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.global.utility.DateFormatter;

public record ProfileResponseBean(
		MemberResponseBean member,
		String aboutMe,
		Integer postCount,
		Integer commentCount,
		String updateAt
) {

	public static ProfileResponseBean of(final MemberInfo memberInfo) {
		return new ProfileResponseBean(
				from(memberInfo),
				memberInfo.getAboutMe(),
				memberInfo.getPostCount(),
				memberInfo.getCommentCount(),
				DateFormatter.format(memberInfo.getUpdatedAt())
		);
	}

	private static MemberResponseBean from(final MemberInfo memberInfo) {
		return MemberResponseBean.of(memberInfo);
	}

}