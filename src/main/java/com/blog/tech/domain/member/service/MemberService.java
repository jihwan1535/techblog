package com.blog.tech.domain.member.service;

import java.sql.SQLException;
import java.time.LocalDateTime;

import com.blog.tech.domain.member.dto.request.RegisterMemberBean;
import com.blog.tech.domain.member.dto.response.RegisteredMemberBean;
import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.entity.vo.MemberRole;
import com.blog.tech.domain.member.entity.vo.MemberStatus;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.member.repository.ifs.MemberRepository;

public class MemberService {

	final MemberRepository memberRepository;
	final MemberInfoRepository memberInfoRepository;

	public MemberService(final MemberRepository memberRepository, final MemberInfoRepository memberInfoRepository) {
		this.memberRepository = memberRepository;
		this.memberInfoRepository = memberInfoRepository;
	}

	public void join(final RegisterMemberBean registerMemberBean) {
	}

	public RegisteredMemberBean register(final RegisterMemberBean registerMemberBean) throws SQLException {
		final Member memberAccount = registerAccount(registerMemberBean);
		final MemberInfo memberInfo = registerMember(registerMemberBean, memberAccount);
		return RegisteredMemberBean.of(memberInfo);
	}

	private MemberInfo registerMember(
		final RegisterMemberBean registerMemberBean,
		final Member member
	) throws SQLException {
		final MemberInfo memberInfo = MemberInfo.builder()
			.id(0L)
			.memberId(member.getId())
			.nickname(registerMemberBean.nickname())
			.image(registerMemberBean.image())
			.role(MemberRole.MEMBER)
			.status(MemberStatus.REGISTERED)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		memberInfoRepository.findByMemberId(member.getId()).ifPresent(it -> {
			throw new RuntimeException("already registered");
		});
		return memberInfoRepository.save(memberInfo);
	}

	private Member registerAccount(final RegisterMemberBean registerMemberBean) throws SQLException {
		if (memberRepository.findByEmail(registerMemberBean.email()).isPresent()) {
			throw new RuntimeException("Duplication");
		}
		final Member member = Member.builder()
			.id(0L)
			.email(registerMemberBean.email())
			.password(registerMemberBean.password())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		return memberRepository.save(member);
	}

}
