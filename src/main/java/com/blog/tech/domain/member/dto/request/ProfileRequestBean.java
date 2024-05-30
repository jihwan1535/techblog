package com.blog.tech.domain.member.dto.request;

public record ProfileRequestBean(
	String nickname,
	String image,
	String aboutMe
) {

	public static ProfileRequestBean of(final String nickname, final String image, final String aboutMe) {
		return new ProfileRequestBean(nickname, image, aboutMe);
	}

}
