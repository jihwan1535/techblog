package com.blog.tech.domain.member.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.blog.tech.domain.member.dto.request.LoginRequestBean;
import com.blog.tech.domain.member.dto.request.ProfileRequestBean;
import com.blog.tech.domain.member.dto.request.RegisterRequestBean;
import com.blog.tech.domain.member.dto.response.MemberResponseBean;
import com.blog.tech.domain.member.dto.response.ProfileResponseBean;
import com.blog.tech.domain.member.dto.response.RegisterResponseBean;
import com.blog.tech.domain.member.dto.response.AvailableResponseBean;
import com.blog.tech.domain.member.dto.response.SearchMemberResponse;
import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.entity.vo.MemberRole;
import com.blog.tech.domain.member.entity.vo.MemberStatus;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.member.repository.ifs.MemberRepository;
import com.blog.tech.global.utility.Uploader;

public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberInfoRepository memberInfoRepository;
	private final String duplication = "DUPLICATION";
	private final String available = "AVAILABLE";


	public MemberService(final MemberRepository memberRepository, final MemberInfoRepository memberInfoRepository) {
		this.memberRepository = memberRepository;
		this.memberInfoRepository = memberInfoRepository;
	}

	public RegisterResponseBean register(final RegisterRequestBean request) throws SQLException {
		memberRepository.findByEmail(request.email()).ifPresent(it -> {
			throw new RuntimeException("Duplication");
		});
		memberInfoRepository.findByNickname(request.nickname()).ifPresent(it -> {
			throw new RuntimeException("already registered nickname");
		});
		final Member memberAccount = registerAccount(request);
		final MemberInfo memberInfo = registerMember(request, memberAccount);
		return RegisterResponseBean.of(memberInfo);
	}

	private MemberInfo registerMember(final RegisterRequestBean request, final Member member) throws SQLException {
		final MemberInfo memberInfo = MemberInfo.builder()
			.id(0L)
			.memberId(member.getId())
			.nickname(request.nickname())
			.image(Uploader.DEFAULT_IMAGE)
			.aboutMe(request.aboutMe())
			.role(MemberRole.MEMBER)
			.status(MemberStatus.REGISTERED)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		return memberInfoRepository.save(memberInfo);
	}

	private Member registerAccount(final RegisterRequestBean request) throws SQLException {
		final Member member = Member.builder()
			.id(0L)
			.email(request.email())
			.password(request.password())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		return memberRepository.save(member);
	}

	public MemberResponseBean login(final LoginRequestBean request) throws SQLException {
		final Member member = memberRepository.findByEmail(request.email()).orElseThrow(() -> {
			throw new RuntimeException("Invalid email");
		});
		if (!member.getPassword().equals(request.password())) {
			throw new RuntimeException("Invalid password");
		}
		final MemberInfo memberInfo = memberInfoRepository.findByMemberId(member.getId()).orElseThrow(() -> {
			try {
				memberRepository.delete(member.getId());
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			throw new RuntimeException("invalid member");
		});
		return MemberResponseBean.of(memberInfo);
	}

	public ProfileResponseBean getProfile(final String nickname) throws SQLException {
		final MemberInfo memberInfo = memberInfoRepository.findByNickname(nickname).orElseThrow(() -> {
			throw new RuntimeException("Invalid member : " + nickname);
		});
		return ProfileResponseBean.of(memberInfo);
	}

	public ProfileResponseBean profileUpdate(final Long id, final ProfileRequestBean request) throws SQLException {
		final MemberInfo memberInfo = memberInfoRepository.findById(id).orElseThrow(() -> {
			throw new RuntimeException("Invalid member");
		});
		memberInfoRepository.findByNickname(request.nickname()).ifPresent(it -> {
			if (!it.getNickname().equals(memberInfo.getNickname())) {
				throw new RuntimeException("Duplication NickName : " + it.getNickname());
			}
		});

		memberInfo.setNickname(request.nickname());
		memberInfo.setImage(request.image());
		memberInfo.setAboutMe(request.aboutMe());

		final MemberInfo updateProfile = memberInfoRepository.save(memberInfo);
		return ProfileResponseBean.of(updateProfile);
	}

	public AvailableResponseBean isValidNickname(final String nickname) throws SQLException {
		if (memberInfoRepository.findByNickname(nickname).isPresent()) {
			return AvailableResponseBean.of(duplication);
		}
		return AvailableResponseBean.of(available);
	}

	public AvailableResponseBean isValidEmail(final String email) throws SQLException {
		if (memberRepository.findByEmail(email).isPresent()) {
			return AvailableResponseBean.of(duplication);
		}
		return AvailableResponseBean.of(available);
	}

	public List<SearchMemberResponse> searchMember(final String nickName, final Long memberId) throws SQLException {
		final String keyword = nickName + "*";
		final List<MemberInfo> memberInfos = memberInfoRepository.searchMember(keyword, memberId);

		return memberInfos.stream()
			.map(SearchMemberResponse::of)
			.toList();
	}

}
