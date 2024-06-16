package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.member.dto.response.MemberInfoResult;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.global.utility.DateFormatter;

public record AllPostResponse(
	PostPreviewResult postInfo,
	MemberInfoResult memberInfo
) {

	public static AllPostResponse of(final Post post) {
		return new AllPostResponse(
			fromPostPreviewInfo(post),
			fromMemberInfo(post.getMemberInfo())
		);
	}

	private static PostPreviewResult fromPostPreviewInfo(final Post post) {
		return PostPreviewResult.of(post);
	}

	private static MemberInfoResult fromMemberInfo(final MemberInfo memberInfo) {
		return MemberInfoResult.of(memberInfo);
	}

}
