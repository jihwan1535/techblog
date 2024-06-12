package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.entity.Category;
import com.blog.tech.domain.post.entity.Topic;

public record PostResponseBean (
	MemberInfoResultBean memberInfo,
	String category,
	String topic,
	PostInfoResultBean postInfo
) {

	public static PostResponseBean of(
		final MemberInfo member,
		final Post post,
		final Category category,
		final Topic topic
	) {
		return new PostResponseBean(
			MemberInfoResultBean.from(member),
			category.getName(),
			topic.getName(),
			PostInfoResultBean.from(post)
		);
	}

}
