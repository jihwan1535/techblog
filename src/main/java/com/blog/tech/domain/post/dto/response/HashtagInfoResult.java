package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.post.entity.Hashtag;

public record HashtagInfoResult(
	Long id,
	String tag
) {

	public static HashtagInfoResult of(final Hashtag hashtag) {
		return new HashtagInfoResult(hashtag.getId(), hashtag.getTag());
	}

}
