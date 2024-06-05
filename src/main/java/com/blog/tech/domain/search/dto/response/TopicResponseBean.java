package com.blog.tech.domain.search.dto.response;

import com.blog.tech.domain.search.entity.Category;
import com.blog.tech.domain.search.entity.Topic;

public record TopicResponseBean (
	Long id,
	String name
) {

	public static TopicResponseBean of(final Topic topic) {
		return new TopicResponseBean(topic.getId(), topic.getName());
	}

}
