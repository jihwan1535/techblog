package com.blog.tech.domain.member.service;

import java.sql.SQLException;
import java.time.LocalDateTime;

import com.blog.tech.domain.member.dto.request.RegisterMemberBean;
import com.blog.tech.domain.member.dto.response.RegisteredMemberBean;
import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.repository.ifs.MemberRepository;

public class MemberService {

	final MemberRepository memberRepository;

	public MemberService(final MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public void join(final RegisterMemberBean registerMemberBean) {
	}

	public RegisteredMemberBean registerAccount(final RegisterMemberBean registerMemberBean) throws SQLException {
		if (memberRepository.findByEmail(registerMemberBean.email()).isPresent()) {
			throw new RuntimeException("Duplication");
		}
		final Member member = Member.builder()
			.email(registerMemberBean.email())
			.password(registerMemberBean.password())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		return RegisteredMemberBean.of(memberRepository.save(member));
	}
}
