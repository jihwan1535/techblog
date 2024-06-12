package com.blog.tech.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestBean(
	@NotBlank
	@Email
	String email,

	@NotBlank
	@Size(min = 8, max = 15)
	String password,

	@NotBlank
	@Size
	String nickname,

	@NotNull
	String aboutMe
) {

	public static RegisterRequestBean of(
		final String email,
		final String password,
		final String nickname,
		final String aboutMe
	) {
		return new RegisterRequestBean(email, password, nickname, aboutMe);
	}

}
