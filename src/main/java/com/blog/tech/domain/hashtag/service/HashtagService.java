package com.blog.tech.domain.hashtag.service;

import com.blog.tech.domain.hashtag.repository.ifs.ConnectHashtagRepository;
import com.blog.tech.domain.hashtag.repository.ifs.HashtagRepository;

public class HashtagService {

	private final HashtagRepository hashtagRepository;
	private final ConnectHashtagRepository connectHashtagRepository;

	public HashtagService(
		final HashtagRepository hashtagRepository,
		final ConnectHashtagRepository connectHashtagRepository
	) {
		this.hashtagRepository = hashtagRepository;
		this.connectHashtagRepository = connectHashtagRepository;
	}

}
