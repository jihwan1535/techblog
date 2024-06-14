package com.blog.tech.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ConnectHashtag {

	private Long id;
	private Long hashtagId;
	private Long postId;

	public static ConnectHashtag to(final Long postId, final Long hashtagId) {
		return new ConnectHashtag(0L, hashtagId, postId);
	}
}
