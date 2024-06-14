package com.blog.tech.domain.post.dto.request;

import java.util.List;

public record PostRequestBean(
	Long categoryId,
	Long topicId,
	String title,
	String content,
	List<String> hashtags
) {

	public static PostRequestBean of(
		final Long categoryId,
		final Long topicId,
		final String title,
		final String content,
		final List<String> hashtags
	) {
		return new PostRequestBean(categoryId, topicId, title, content, hashtags);
	}

}
