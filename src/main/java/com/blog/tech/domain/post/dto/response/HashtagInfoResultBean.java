package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.post.entity.Hashtag;

public record HashtagInfoResultBean(
	Long id,
	String tag
) {

	public static HashtagInfoResultBean of(final Hashtag hashtag) {
		return new HashtagInfoResultBean(hashtag.getId(), hashtag.getTag());
	}

}
