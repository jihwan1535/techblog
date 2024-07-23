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

	public List<AllPostResponse> getAllPostsByTopic(final Long postId, final Long topicId) {
		return postService.getAllPostsByTopic(postId, topicId);
	}

	public void writeOnPost(final Long memberId, final PostRequest request) {
		postService.writeOnPost(memberId, request);
	}

	public List<AllPostResponse> getAllPosts(final Long postId) {
		return postService.getAllPosts(postId);
	}

	public PostResponse getPost(final Long postId, final String ip) {
		return postService.getPost(postId, ip);
	}

	public List<CategoryResponse> getAllCategories() {
		return postService.getAllCategories();
	}

	public List<HashtagInfoResult> getRandomHashtags()  {
		return postService.getRandomHashtags();
	}

	public List<AllPostResponse> getAllPostsByCategory(final Long postId, final Long categoryId) {
		return postService.getAllPostsByCategory(postId, categoryId);
	}

	public List<AllPostResponse> searchPosts(final Long postId, final String keyword) {
		return postService.searchPosts(postId, keyword);
	}

}
