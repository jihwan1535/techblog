package com.blog.tech.domain.post.repository.factory;

import com.blog.tech.domain.post.repository.dao.CategoryDao;
import com.blog.tech.domain.post.repository.dao.ConnectHashtagDao;
import com.blog.tech.domain.post.repository.dao.HashtagDao;
import com.blog.tech.domain.post.repository.dao.PostDao;
import com.blog.tech.domain.post.repository.dao.PostViewDao;
import com.blog.tech.domain.post.repository.dao.TopicDao;
import com.blog.tech.domain.post.repository.ifs.*;

public class PostRepositoryFactory {

	private static CategoryRepository categoryRepository;
	private static ConnectHashtagRepository connectHashtagRepository;
	private static HashtagRepository hashtagRepository;
	private static PostRepository postRepository;
	private static PostViewRepository postViewRepository;
	private static TopicRepository topicRepository;

	public static CategoryRepository getCategoryRepository() {
		if (categoryRepository == null) {
			synchronized (CategoryRepository.class) {
				if (categoryRepository == null) {
					categoryRepository = new CategoryDao();
				}
			}
		}
		return categoryRepository;
	}

	public static ConnectHashtagRepository getConnectHashtagRepository() {
		if (connectHashtagRepository == null) {
			synchronized (ConnectHashtagRepository.class) {
				if (connectHashtagRepository == null) {
					connectHashtagRepository = new ConnectHashtagDao();
				}
			}
		}
		return connectHashtagRepository;
	}

	public static HashtagRepository getHashtagRepository() {
		if (hashtagRepository == null) {
			synchronized (HashtagRepository.class) {
				if (hashtagRepository == null) {
					hashtagRepository = new HashtagDao();
				}
			}
		}
		return hashtagRepository;
	}

	public static PostRepository getPostRepository() {
		if (postRepository == null) {
			synchronized (PostRepository.class) {
				if (postRepository == null) {
					postRepository = new PostDao();
				}
			}
		}
		return postRepository;
	}

	public static PostViewRepository getPostViewRepository() {
		if (postViewRepository == null) {
			synchronized (PostViewRepository.class) {
				if (postViewRepository == null) {
					postViewRepository = new PostViewDao();
				}
			}
		}
		return postViewRepository;
	}

	public static TopicRepository getTopicRepository() {
		if (topicRepository == null) {
			synchronized (TopicRepository.class) {
				if (topicRepository == null) {
					topicRepository = new TopicDao();
				}
			}
		}
		return topicRepository;
	}


}
