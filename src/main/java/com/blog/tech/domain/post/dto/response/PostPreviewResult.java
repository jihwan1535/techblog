package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.global.utility.DateFormatter;

public record PostPreviewResult(
	Long postId,
	String title,
	String content,
	Integer viewCount,
	Integer scrapCount,
	Integer commentCount,
	Integer replyCount,
	String createdAt
) {

	public static PostPreviewResult of(final Post post) {
		return new PostPreviewResult(
			post.getId(),
			post.getTile(),
			post.getContent(),
			post.getViewCount(),
			post.getScrapCount(),
			post.getCommentCount(),
			post.getReplyCount(),
			DateFormatter.format(post.getCreatedAt())
		);
	}

}
