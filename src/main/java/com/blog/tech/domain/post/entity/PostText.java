package com.blog.tech.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PostText {

	private Long id;
	private String content;

	public static PostText to(final String content) {
		return new PostText(0L, content);
	}

}
