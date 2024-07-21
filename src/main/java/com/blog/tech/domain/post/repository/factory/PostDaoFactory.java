package com.blog.tech.domain.post.repository.factory;

import java.sql.Connection;

import com.blog.tech.domain.post.repository.dao.CategoryDao;
import com.blog.tech.domain.post.repository.dao.ConnectHashtagDao;
import com.blog.tech.domain.post.repository.dao.HashtagDao;
import com.blog.tech.domain.post.repository.dao.PostDao;
import com.blog.tech.domain.post.repository.dao.PostViewDao;
import com.blog.tech.domain.post.repository.dao.TopicDao;

public class PostDaoFactory {

	private static CategoryDao categoryDao;
	private static ConnectHashtagDao connectHashtagDao;
	private static HashtagDao hashtagDao;
	private static PostDao postDao;
	private static PostViewDao postViewDao;
	private static TopicDao topicDao;

	public static CategoryDao getCategoryDao(final Connection connection) {
		if (categoryDao == null) {
			synchronized (CategoryDao.class) {
				if (categoryDao == null) {
					categoryDao = new CategoryDao();
				}
			}
		}
		categoryDao.setConnection(connection);
		return categoryDao;
	}

	public static ConnectHashtagDao getConnectHashtagDao(final Connection connection) {
		if (connectHashtagDao == null) {
			synchronized (CategoryDao.class) {
				if (connectHashtagDao == null) {
					connectHashtagDao = new ConnectHashtagDao();
				}
			}
		}
		categoryDao.setConnection(connection);
		return connectHashtagDao;
	}

	public static HashtagDao getHashtagDao(final Connection connection) {
		if (hashtagDao == null) {
			synchronized (HashtagDao.class) {
				if (hashtagDao == null) {
					hashtagDao = new HashtagDao();
				}
			}
		}
		hashtagDao.setConnection(connection);
		return hashtagDao;
	}

	public static PostDao getPostDao(final Connection connection) {
		if (postDao == null) {
			synchronized (PostDao.class) {
				if (postDao == null) {
					postDao = new PostDao();
				}
			}
		}
		postDao.setConnection(connection);
		return postDao;
	}

	public static PostViewDao getPostViewDao(final Connection connection) {
		if (postViewDao == null) {
			synchronized (PostViewDao.class) {
				if (postViewDao == null) {
					postViewDao = new PostViewDao();
				}
			}
		}
		postViewDao.setConnection(connection);
		return postViewDao;
	}

	public static TopicDao getTopicDao(final Connection connection) {
		if (topicDao == null) {
			synchronized (TopicDao.class) {
				if (topicDao == null) {
					topicDao = new TopicDao();
				}
			}
		}
		topicDao.setConnection(connection);
		return topicDao;
	}


}
