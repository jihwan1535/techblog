package com.blog.tech.domain.post.controller;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.post.dto.response.CategoryResponse;
import com.blog.tech.domain.post.dto.response.HashtagInfoResult;
import com.blog.tech.domain.post.dto.request.PostRequest;
import com.blog.tech.domain.post.dto.response.PostResponse;
import com.blog.tech.domain.post.dto.response.AllPostResponse;
import com.blog.tech.domain.post.service.PostService;

public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	public List<AllPostResponse> getAllPostsByTopic(final Long postId, final Long topicId) throws SQLException {
		return postService.getAllPostsByTopic(postId, topicId);
	}

	public void writeOnPost(final Long memberId, final PostRequest request) throws SQLException {
		postService.writeOnPost(memberId, request);
	}

	public List<AllPostResponse> getAllPosts(final Long postId) throws SQLException {
		return postService.getAllPosts(postId);
	}

	public PostResponse getPost(final Long postId, final String ip) throws SQLException {
		return postService.getPost(postId, ip);
	}

	public List<CategoryResponse> getAllCategories() throws SQLException {
		return postService.getAllCategories();
	}

	public List<HashtagInfoResult> getRandomHashtags() throws SQLException {
		return postService.getRandomHashtags();
	}

	public List<AllPostResponse> getAllPostsByCategory(final Long postId, final Long categoryId) throws SQLException {
		return postService.getAllPostsByCategory(postId, categoryId);
	}

}
