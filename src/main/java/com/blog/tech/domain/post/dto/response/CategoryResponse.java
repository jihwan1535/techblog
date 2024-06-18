package com.blog.tech.domain.post.dto.response;

import java.util.List;

import com.blog.tech.domain.post.entity.Category;

public record CategoryResponse(
	Long id,
	String name,
	List<TopicResponse> topics
) {

	public static CategoryResponse of(final Category category) {
		return new CategoryResponse(
			category.getId(),
			category.getName(),
			fromTopicInfo(category)
		);
	}

	private static List<TopicResponse> fromTopicInfo(final Category category) {
		return category.getTopics().stream()
			.map(TopicResponse::of)
			.toList();
	}

}
