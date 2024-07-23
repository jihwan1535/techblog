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

	public static CategoryDao getCategoryDao() {
		if (categoryDao == null) {
			synchronized (CategoryDao.class) {
				if (categoryDao == null) {
					categoryDao = new CategoryDao();
				}
			}
		}
		return categoryDao;
	}

	public static ConnectHashtagDao getConnectHashtagDao() {
		if (connectHashtagDao == null) {
			synchronized (CategoryDao.class) {
				if (connectHashtagDao == null) {
					connectHashtagDao = new ConnectHashtagDao();
				}
			}
		}
		return connectHashtagDao;
	}

	public static HashtagDao getHashtagDao() {
		if (hashtagDao == null) {
			synchronized (HashtagDao.class) {
				if (hashtagDao == null) {
					hashtagDao = new HashtagDao();
				}
			}
		}
		return hashtagDao;
	}

	public static PostDao getPostDao() {
		if (postDao == null) {
			synchronized (PostDao.class) {
				if (postDao == null) {
					postDao = new PostDao();
				}
			}
		}
		return postDao;
	}

	public static PostViewDao getPostViewDao() {
		if (postViewDao == null) {
			synchronized (PostViewDao.class) {
				if (postViewDao == null) {
					postViewDao = new PostViewDao();
				}
			}
		}
		return postViewDao;
	}

	public static TopicDao getTopicDao() {
		if (topicDao == null) {
			synchronized (TopicDao.class) {
				if (topicDao == null) {
					topicDao = new TopicDao();
				}
			}
		}
		return topicDao;
	}


}
