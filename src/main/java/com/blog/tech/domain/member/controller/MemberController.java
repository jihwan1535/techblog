package com.blog.tech.domain.member.controller;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.member.dto.request.LoginRequestBean;
import com.blog.tech.domain.member.dto.request.ProfileRequestBean;
import com.blog.tech.domain.member.dto.request.RegisterRequestBean;
import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.member.dto.response.ProfileResponseBean;
import com.blog.tech.domain.member.dto.response.RegisterResponseBean;
import com.blog.tech.domain.member.dto.response.AvailableResponseBean;
import com.blog.tech.domain.member.dto.response.SearchMemberResponse;
import com.blog.tech.domain.member.service.MemberService;

public class MemberController {

	private final MemberService memberService;

	public MemberController(final MemberService memberService) {
		this.memberService = memberService;
	}

	public RegisterResponseBean register(final RegisterRequestBean request) {
		return memberService.register(request);
	}

	public MemberResponseBean login(final LoginRequestBean request) {
		return memberService.login(request);
	}

	public ProfileResponseBean profile(final String nickname) {
		return memberService.getProfile(nickname);
	}

	public ProfileResponseBean profileUpdate(final Long id, final ProfileRequestBean request) {
		return memberService.profileUpdate(id, request);
	}

	public AvailableResponseBean checkNickname(final String nickname) {
		return memberService.isValidNickname(nickname);
	}

	public AvailableResponseBean checkEmail(final String email) {
		return memberService.isValidEmail(email);
	}

	public List<SearchMemberResponse> searchMember(final String nickname, final Long memberId) {
		return memberService.searchMember(nickname, memberId);
	}

}
