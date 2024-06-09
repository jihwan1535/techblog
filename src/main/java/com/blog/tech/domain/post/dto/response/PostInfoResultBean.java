package com.blog.tech.domain.post.dto.response;

import java.time.LocalDateTime;

import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.global.utility.DateFormatter;

public record PostInfoResultBean (
	Long id,
	String title,
	String content,
	Integer viewCount,
	Integer scrapCount,
	Boolean alarm,
	String createdAt,
	Boolean modified
) {

	public static PostInfoResultBean from(final Post post) {
		return new PostInfoResultBean(
			post.getId(),
			post.getTile(),
			post.getContent(),
			post.getViewCount(),
			post.getScrapCount(),
			post.getAlarm(),
			DateFormatter.format(post.getCreatedAt()),
			isModified(post.getCreatedAt(), post.getUpdatedAt())
		);
	}

	private static Boolean isModified(final LocalDateTime createTime, final LocalDateTime updateTime) {
		return createTime.isEqual(updateTime);
	}

}