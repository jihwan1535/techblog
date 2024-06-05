package com.blog.tech.domain.post.controller;

import java.sql.SQLException;

import com.blog.tech.domain.post.dto.request.PostRequestBean;
import com.blog.tech.domain.post.service.PostService;

public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	public void writeOnPost(final PostRequestBean request) throws SQLException, SQLException {
		postService.writeOnPost(request);
	}

}
