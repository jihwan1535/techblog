package com.blog.tech.domain.post.dto.request;

import java.util.List;

public record PostRequest(
	Long categoryId,
	Long topicId,
	String title,
	String content,
	List<String> hashtags
) {

	public static PostRequest of(
		final Long categoryId,
		final Long topicId,
		final String title,
		final String content,
		final List<String> hashtags
	) {
		return new PostRequest(categoryId, topicId, title, content, hashtags);
	}

}
