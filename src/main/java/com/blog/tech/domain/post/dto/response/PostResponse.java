package com.blog.tech.domain.post.dto.response;

import java.util.List;

import com.blog.tech.domain.member.dto.response.MemberInfoResult;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.post.entity.Hashtag;
import com.blog.tech.domain.post.entity.Post;

public record PostResponse(
	MemberInfoResult memberInfo,
	PostInfoResult postInfo,
	List<HashtagInfoResult> hashtags
) {

	public static PostResponse of(
		final Post post,
		final List<Hashtag> hashtags
	) {
		return new PostResponse(
			fromMemberInfo(post.getMemberInfo()),
			fromPostInfo(post),
			fromHashtagInfo(hashtags)
		);
	}

	private static MemberInfoResult fromMemberInfo(final MemberInfo memberInfo) {
		return MemberInfoResult.of(memberInfo);
	}

	private static PostInfoResult fromPostInfo(final Post post) {
		return PostInfoResult.of(post);
	}

	private static List<HashtagInfoResult> fromHashtagInfo(final List<Hashtag> hashtags) {
		return hashtags.stream()
			.map(HashtagInfoResult::of)
			.toList();
	}

}
