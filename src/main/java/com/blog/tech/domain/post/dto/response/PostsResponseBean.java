package com.blog.tech.domain.post.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.global.utility.DateFormatter;

public record PostsResponseBean(
	Long postId,
	String postTitle,
	String postCreatedAt,
	Long memberId,
	String memberName,
	String memberImage
) {

	public static PostsResponseBean of(final Post post, final MemberInfo memberInfo) {
		return new PostsResponseBean(
			post.getId(),
			post.getTile(),
			DateFormatter.format(post.getCreatedAt()),
			memberInfo.getId(),
			memberInfo.getNickname(),
			memberInfo.getImage()
		);
	}

}
