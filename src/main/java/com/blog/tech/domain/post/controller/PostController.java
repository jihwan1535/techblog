package com.blog.tech.domain.post.controller;

import com.blog.tech.domain.post.service.PostService;

public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

}
