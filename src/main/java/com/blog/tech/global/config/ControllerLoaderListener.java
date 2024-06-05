package com.blog.tech.global.config;

import java.sql.Connection;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.repository.dao.MemberDao;
import com.blog.tech.domain.member.repository.dao.MemberInfoDao;
import com.blog.tech.domain.member.service.MemberService;
import com.blog.tech.domain.post.controller.PostController;
import com.blog.tech.domain.search.controller.SearchController;
import com.blog.tech.domain.search.repository.dao.CategoryDao;
import com.blog.tech.domain.post.repository.dao.CommentDao;
import com.blog.tech.domain.post.repository.dao.PostDao;
import com.blog.tech.domain.post.repository.dao.ReplyDao;
import com.blog.tech.domain.search.repository.dao.ConnectHashtagDao;
import com.blog.tech.domain.search.repository.dao.HashtagDao;
import com.blog.tech.domain.search.repository.dao.TopicDao;
import com.blog.tech.domain.post.service.PostService;
import com.blog.tech.domain.search.service.SearchService;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ControllerLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final Connection conn = (Connection)sce.getServletContext().getAttribute("conn");
		setMemberController(conn, sce);
		setPostController(conn, sce);
		setSearchController(conn, sce);
	}

	private void setPostController(final Connection conn, final ServletContextEvent sce) {
		final CommentDao commentDao = new CommentDao(conn);
		final PostDao postDao = new PostDao(conn);
		final ReplyDao replyDao = new ReplyDao(conn);
		final PostService postService = new PostService(postDao, replyDao, commentDao);
		final PostController postController = new PostController(postService);
		sce.getServletContext().setAttribute("postController", postController);
	}

	private void setSearchController(final Connection conn, final ServletContextEvent sce) {
		final CategoryDao category = new CategoryDao(conn);
		final TopicDao topic = new TopicDao(conn);
		final HashtagDao hashtag = new HashtagDao(conn);
		final ConnectHashtagDao connect = new ConnectHashtagDao(conn);
		final SearchService searchService = new SearchService(hashtag, connect, category, topic);
		final SearchController searchController = new SearchController(searchService);
		sce.getServletContext().setAttribute("searchController", searchController);
	}

	private void setMemberController(final Connection conn, final ServletContextEvent sce) {
		final MemberDao memberDao = new MemberDao(conn);
		final MemberInfoDao memberInfoDao = new MemberInfoDao(conn);
		final MemberService memberService = new MemberService(memberDao, memberInfoDao);
		final MemberController memberController = new MemberController(memberService);
		sce.getServletContext().setAttribute("memberController", memberController);
	}

}
