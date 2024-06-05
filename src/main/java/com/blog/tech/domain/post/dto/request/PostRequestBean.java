package com.blog.tech.domain.post.dto.request;

public record PostRequestBean(
	Long memberId,
	Long categoryId,
	Long topicId,
	String title,
	String content
) {

	public static PostRequestBean of(
		final Long memberId,
		final Long categoryId,
		final Long topicId,
		final String title,
		final String content
	) {
		return new PostRequestBean(memberId, categoryId, topicId, title, content);
	}

}
