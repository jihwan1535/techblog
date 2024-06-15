package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.global.utility.DateFormatter;

public record PostInfoResult(
	String category,
	String topic,
	Long id,
	String title,
	String content,
	Integer viewCount,
	Integer scrapCount,
	Integer commentCount,
	Integer replyCount,
	Boolean alarm,
	String createdAt,
	Boolean modified
) {

	public static PostInfoResult of(final Post post) {
		return new PostInfoResult(
			post.getCategory().getName(),
			post.getTopic().getName(),
			post.getId(),
			post.getTile(),
			post.getContent(),
			post.getViewCount(),
			post.getScrapCount(),
			post.getCommentCount(),
			post.getReplyCount(),
			post.getAlarm(),
			DateFormatter.format(post.getCreatedAt()),
			post.getCreatedAt().isEqual(post.getUpdatedAt())
		);
	}

}
