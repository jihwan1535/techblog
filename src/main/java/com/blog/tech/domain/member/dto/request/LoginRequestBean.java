package com.blog.tech.domain.member.dto.request;

public record LoginRequestBean(
	String email,
	String password
) {

	public static LoginRequestBean of(final String email, final String pw) {
		return new LoginRequestBean(email, pw);
	}
}
