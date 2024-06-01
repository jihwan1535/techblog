package com.blog.tech.domain.member.controller;

import java.sql.SQLException;

import com.blog.tech.domain.member.dto.request.LoginRequestBean;
import com.blog.tech.domain.member.dto.request.ProfileRequestBean;
import com.blog.tech.domain.member.dto.request.RegisterRequestBean;
import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.member.dto.response.ProfileResponseBean;
import com.blog.tech.domain.member.dto.response.RegisterResponseBean;
import com.blog.tech.domain.member.dto.response.AvailableResponseBean;
import com.blog.tech.domain.member.service.MemberService;

public class MemberController {

	private final MemberService memberService;

	public MemberController(final MemberService memberService) {
		this.memberService = memberService;
	}

	public RegisterResponseBean register(final RegisterRequestBean request) throws SQLException {
		return memberService.register(request);
	}

	public MemberResponseBean login(final LoginRequestBean request) throws SQLException {
		return memberService.login(request);
	}

	public ProfileResponseBean profile(final String nickname) throws SQLException {
		return memberService.profile(nickname);
	}

	public ProfileResponseBean profileUpdate(final Long id, final ProfileRequestBean request) throws SQLException {
		return memberService.profileUpdate(id, request);
	}

	public AvailableResponseBean checkNickname(final String nickname) throws SQLException {
		return memberService.isValidNickname(nickname);
	}

	public AvailableResponseBean checkEmail(final String email) throws SQLException {
		return memberService.isValidEmail(email);
	}
}
