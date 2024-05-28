package com.blog.tech.domain.member.controller;

import java.sql.SQLException;

import com.blog.tech.domain.member.dto.request.LoginRequestBean;
import com.blog.tech.domain.member.dto.request.RegisterRequestBean;
import com.blog.tech.domain.member.dto.response.LoginResponseBean;
import com.blog.tech.domain.member.dto.response.RegisterResponseBean;
import com.blog.tech.domain.member.service.MemberService;

public class MemberController {

	private final MemberService memberService;

	public MemberController(final MemberService memberService) {
		this.memberService = memberService;
	}

	public RegisterResponseBean register(final RegisterRequestBean request) throws SQLException {
		return memberService.register(request);
		//memberService.registerMember(joinMemberBean);
	}

	public LoginResponseBean login(final LoginRequestBean request) throws SQLException {
		return memberService.login(request);
	}
}
