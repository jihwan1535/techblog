package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.post.entity.Topic;

public record TopicResponse(
	Long id,
	String name
) {

	public static TopicResponse of(final Topic topic) {
		return new TopicResponse(topic.getId(), topic.getName());
	}

}
