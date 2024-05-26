package com.blog.tech.domain.member.service;

import com.blog.tech.domain.member.dto.request.JoinMemberBean;
import com.blog.tech.domain.member.repository.ifs.MemberRepository;

public class MemberService {

	final MemberRepository memberRepository;

	public MemberService(final MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public void join(final JoinMemberBean joinMemberBean) {
	}

	public void registerAccount(final JoinMemberBean joinMemberBean) {
		//memberRepository.save()
	}
}
