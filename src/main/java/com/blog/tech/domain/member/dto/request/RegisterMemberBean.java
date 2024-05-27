package com.blog.tech.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public record RegisterMemberBean(
	@NotBlank
	@Email
	String email,

	@NotBlank
	@Size(min = 8, max = 15)
	String password,

	@NotBlank
	@Size
	String nickname,

	@NotBlank
	String image,

	@NotNull
	String aboutMe
) {

	public static RegisterMemberBean of(String email, String password, String nickname, String image, String aboutMe) {
		return new RegisterMemberBean(email, password, nickname, image, aboutMe);
	}

}
