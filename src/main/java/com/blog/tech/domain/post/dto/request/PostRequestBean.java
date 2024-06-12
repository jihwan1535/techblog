package com.blog.tech.domain.post.dto.request;

public record PostRequestBean(
	Long categoryId,
	Long topicId,
	String title,
	String content
) {

	public static PostRequestBean of(
		final Long categoryId,
		final Long topicId,
		final String title,
		final String content
	) {
		return new PostRequestBean(categoryId, topicId, title, content);
	}

}
