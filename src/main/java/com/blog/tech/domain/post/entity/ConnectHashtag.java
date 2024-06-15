package com.blog.tech.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class ConnectHashtag {

	private Long id;
	private Long hashtagId;
	private Long postId;
	private Hashtag hashtag;
	private Post post;

	public static ConnectHashtag to(final Long postId, final Long hashtagId) {
		return ConnectHashtag.builder()
			.id(0L)
			.hashtagId(hashtagId)
			.postId(postId)
			.build();
	}
}
