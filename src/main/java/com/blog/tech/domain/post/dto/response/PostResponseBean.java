package com.blog.tech.domain.post.dto.response;

import java.util.List;

import com.blog.tech.domain.member.dto.response.MemberInfoResultBean;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.post.entity.Hashtag;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.entity.Category;
import com.blog.tech.domain.post.entity.Topic;

public record PostResponseBean (
	MemberInfoResultBean memberInfo,
	String category,
	String topic,
	PostInfoResultBean postInfo,
	List<HashtagInfoResultBean> hashtags
) {

	public static PostResponseBean of(
		final MemberInfo member,
		final Post post,
		final Category category,
		final Topic topic,
		final List<Hashtag> hashtags
	) {
		return new PostResponseBean(
			fromMemberInfo(member),
			category.getName(),
			topic.getName(),
			fromPostInfo(post),
			fromHashtagInfo(hashtags)
		);
	}

	private static MemberInfoResultBean fromMemberInfo(final MemberInfo memberInfo) {
		return MemberInfoResultBean.of(memberInfo);
	}

	private static PostInfoResultBean fromPostInfo(final Post post) {
		return PostInfoResultBean.of(post);
	}

	private static List<HashtagInfoResultBean> fromHashtagInfo(final List<Hashtag> hashtags) {
		return hashtags.stream()
			.map(HashtagInfoResultBean::of)
			.toList();
	}

}
