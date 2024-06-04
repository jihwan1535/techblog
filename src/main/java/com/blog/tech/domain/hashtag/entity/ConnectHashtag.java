package com.blog.tech.domain.hashtag.entity;

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

}
