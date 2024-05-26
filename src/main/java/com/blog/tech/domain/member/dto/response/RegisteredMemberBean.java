package com.blog.tech.domain.member.dto.response;

import com.blog.tech.domain.member.entity.Member;

public record RegisteredMemberBean(
	Long id,
	String email,
	String password
) {

	public static RegisteredMemberBean of(final Member member) {
		return new RegisteredMemberBean(member.getId(), member.getEmail(), member.getPassword());
	}

}
