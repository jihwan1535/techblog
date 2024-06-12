package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.post.entity.Topic;

public record TopicResponseBean (
	Long id,
	String name
) {

	public static TopicResponseBean of(final Topic topic) {
		return new TopicResponseBean(topic.getId(), topic.getName());
	}

}
