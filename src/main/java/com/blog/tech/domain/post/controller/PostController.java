package com.blog.tech.domain.post.controller;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.post.dto.response.CategoryResponseBean;
import com.blog.tech.domain.post.dto.response.TopicResponseBean;
import com.blog.tech.domain.post.dto.request.PostRequestBean;
import com.blog.tech.domain.post.dto.response.PostResponseBean;
import com.blog.tech.domain.post.dto.response.PostsResponseBean;
import com.blog.tech.domain.post.service.PostService;

public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	public void writeOnPost(final Long memberId, final PostRequestBean request) throws SQLException {
		postService.writeOnPost(memberId, request);
	}

	public List<PostsResponseBean> getAllPosts(final Long postId) throws SQLException {
		return postService.getAllPosts(postId);
	}

	public PostResponseBean getPost(final Long postId) throws SQLException {
		return postService.getPost(postId);
	}

	public List<CategoryResponseBean> getAllCategories() throws SQLException {
		return postService.getAllCategories();
	}

	public List<TopicResponseBean> getAllTopicsByCategory(final Long categoryId) throws SQLException {
		return postService.getAllTopicsByCategory(categoryId);
	}
}
