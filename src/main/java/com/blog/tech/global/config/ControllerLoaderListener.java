package com.blog.tech.global.config;

import java.sql.Connection;

import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.repository.dao.MemberDao;
import com.blog.tech.domain.member.repository.dao.MemberInfoDao;
import com.blog.tech.domain.member.service.MemberService;
import com.blog.tech.domain.post.controller.PostController;
import com.blog.tech.domain.post.repository.dao.CategoryDao;
import com.blog.tech.domain.post.repository.dao.CommentDao;
import com.blog.tech.domain.post.repository.dao.PostAttachDao;
import com.blog.tech.domain.post.repository.dao.PostDao;
import com.blog.tech.domain.post.repository.dao.ReplyDao;
import com.blog.tech.domain.post.repository.dao.TopicDao;
import com.blog.tech.domain.post.service.PostService;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ControllerLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final Connection conn = (Connection)sce.getServletContext().getAttribute("conn");
		setMemberController(conn, sce);
		setPostController(conn, sce);
	}

	private void setPostController(final Connection conn, final ServletContextEvent sce) {
		final CategoryDao category = new CategoryDao(conn);
		final CommentDao comment = new CommentDao(conn);
		final PostDao post = new PostDao(conn);
		final ReplyDao reply = new ReplyDao(conn);
		final TopicDao topic = new TopicDao(conn);
		final PostAttachDao postAttach = new PostAttachDao(conn);
		final PostService postService = new PostService(category, post, topic, reply, comment, postAttach);
		final PostController postController = new PostController(postService);
		sce.getServletContext().setAttribute("postController", postController);
	}

	private void setMemberController(final Connection conn, final ServletContextEvent sce) {
		final MemberDao memberDao = new MemberDao(conn);
		final MemberInfoDao memberInfoDao = new MemberInfoDao(conn);
		final MemberService memberService = new MemberService(memberDao, memberInfoDao);
		final MemberController memberController = new MemberController(memberService);
		sce.getServletContext().setAttribute("memberController", memberController);
	}

}
