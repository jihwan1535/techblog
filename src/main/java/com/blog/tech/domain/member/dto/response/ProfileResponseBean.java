package com.blog.tech.domain.member.dto.response;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.global.utility.DateFormatter;

public record ProfileResponseBean(
	Long id,
	String nickname,
	String image,
	String aboutMe,
	Integer postCount,
	Integer commentCount,
	String updateAt
) {

	public static ProfileResponseBean of(final MemberInfo memberInfo) {
		return new ProfileResponseBean(
			memberInfo.getId(),
			memberInfo.getNickname(),
			memberInfo.getImage(),
			memberInfo.getAboutMe(),
			memberInfo.getPostCount(),
			memberInfo.getCommentCount(),
			DateFormatter.format(memberInfo.getUpdatedAt())
		);
	}

}
