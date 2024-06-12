package com.blog.tech.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Hashtag {

	private Long id;
	private String name;

	public static Hashtag to(final String hashtag) {
		return new Hashtag(0L, hashtag);
	}
}
