package com.blog.tech.domain.post.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.blog.tech.domain.post.dto.request.PostRequestBean;
import com.blog.tech.domain.post.dto.response.CategoryResponseBean;
import com.blog.tech.domain.post.entity.Category;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.repository.ifs.CategoryRepository;
import com.blog.tech.domain.post.repository.ifs.CommentRepository;
import com.blog.tech.domain.post.repository.ifs.PostAttachRepository;
import com.blog.tech.domain.post.repository.ifs.PostRepository;
import com.blog.tech.domain.post.repository.ifs.ReplyRepository;
import com.blog.tech.domain.post.repository.ifs.TopicRepository;

public class PostService {

	private final CategoryRepository categoryRepository;
	private final PostRepository postRepository;
	private final TopicRepository topicRepository;
	private final ReplyRepository replyRepository;
	private final CommentRepository commentRepository;
	private final PostAttachRepository postAttachRepository;

	public PostService(
		final CategoryRepository categoryRepository,
		final PostRepository postRepository,
		final TopicRepository topicRepository,
		final ReplyRepository replyRepository,
		final CommentRepository commentRepository,
		final PostAttachRepository postAttachRepository
	) {
		this.categoryRepository = categoryRepository;
		this.postRepository = postRepository;
		this.topicRepository = topicRepository;
		this.replyRepository = replyRepository;
		this.commentRepository = commentRepository;
		this.postAttachRepository = postAttachRepository;
	}

	public void writeOnPost(final PostRequestBean request) throws SQLException {
		final Post post = Post.to(request);
		postRepository.save(post);
	}

	public List<CategoryResponseBean> getAllCategories() throws SQLException {
		final List<Category> categories = categoryRepository.findAll();
		return categories.stream()
			.map(CategoryResponseBean::of)
			.toList();
	}

}
