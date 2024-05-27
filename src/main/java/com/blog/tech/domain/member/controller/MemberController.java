package com.blog.tech.domain.member.controller;

import java.sql.SQLException;

import com.blog.tech.domain.member.dto.request.RegisterMemberBean;
import com.blog.tech.domain.member.dto.response.RegisteredMemberBean;
import com.blog.tech.domain.member.service.MemberService;

import jakarta.validation.Valid;

public class MemberController {

	private final MemberService memberService;

	public MemberController(final MemberService memberService) {
		this.memberService = memberService;
	}

	public RegisteredMemberBean register(final RegisterMemberBean registerMemberBean) throws SQLException {
		return memberService.register(registerMemberBean);
		//memberService.registerMember(joinMemberBean);
	}

}
