package com.blog.tech.domain.post.service;

import java.sql.SQLException;

import com.blog.tech.domain.post.dto.request.PostRequestBean;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.repository.ifs.CommentRepository;
import com.blog.tech.domain.post.repository.ifs.PostRepository;
import com.blog.tech.domain.post.repository.ifs.ReplyRepository;

public class PostService {

	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	private final CommentRepository commentRepository;

	public PostService(
		final PostRepository postRepository,
		final ReplyRepository replyRepository,
		final CommentRepository commentRepository
	) {
		this.postRepository = postRepository;
		this.replyRepository = replyRepository;
		this.commentRepository = commentRepository;
	}

	public void writeOnPost(final PostRequestBean request) throws SQLException {
		final Post post = Post.to(request);
		postRepository.save(post);
	}

}
