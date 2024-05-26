package com.blog.tech.domain.member.controller;

import com.blog.tech.domain.member.dto.request.JoinMemberBean;
import com.blog.tech.domain.member.service.MemberService;

import jakarta.validation.Valid;

public class MemberController {

	private final MemberService memberService;

	public MemberController(final MemberService memberService) {
		this.memberService = memberService;
	}

	public void join(@Valid final JoinMemberBean joinMemberBean) {
		memberService.registerAccount(joinMemberBean);
		//memberService.registerMember(joinMemberBean);
	}

}
