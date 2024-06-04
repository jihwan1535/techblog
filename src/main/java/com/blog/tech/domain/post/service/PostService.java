package com.blog.tech.domain.post.service;

import com.blog.tech.domain.post.repository.ifs.CategoryRepository;
import com.blog.tech.domain.post.repository.ifs.CommentRepository;
import com.blog.tech.domain.post.repository.ifs.PostRepository;
import com.blog.tech.domain.post.repository.ifs.ReplyRepository;
import com.blog.tech.domain.post.repository.ifs.TopicRepository;

public class PostService {

	private final CategoryRepository categoryRepository;
	private final PostRepository postRepository;
	private final TopicRepository topicRepository;
	private final ReplyRepository replyRepository;
	private final CommentRepository commentRepository;

	public PostService(
		final CategoryRepository categoryRepository,
		final PostRepository postRepository,
		final TopicRepository topicRepository,
		final ReplyRepository replyRepository,
		final CommentRepository commentRepository
	) {
		this.categoryRepository = categoryRepository;
		this.postRepository = postRepository;
		this.topicRepository = topicRepository;
		this.replyRepository = replyRepository;
		this.commentRepository = commentRepository;
	}

}
