package com.blog.tech.global.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.blog.tech.domain.comment.controller.CommentController;
import com.blog.tech.domain.comment.repository.dao.CommentDao;
import com.blog.tech.domain.comment.repository.dao.ReplyDao;
import com.blog.tech.domain.comment.service.CommentService;
import com.blog.tech.domain.common.TransactionManager;
import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.repository.dao.MemberDao;
import com.blog.tech.domain.member.repository.dao.MemberInfoDao;
import com.blog.tech.domain.member.service.MemberService;
import com.blog.tech.domain.post.controller.PostController;
import com.blog.tech.domain.post.repository.dao.CategoryDao;
import com.blog.tech.domain.post.repository.dao.ConnectHashtagDao;
import com.blog.tech.domain.post.repository.dao.HashtagDao;
import com.blog.tech.domain.post.repository.dao.PostDao;
import com.blog.tech.domain.post.repository.dao.PostViewDao;
import com.blog.tech.domain.post.repository.dao.TopicDao;
import com.blog.tech.domain.post.service.PostService;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ControllerLoaderListener implements ServletContextListener {

	private MemberDao member;
	private MemberInfoDao memberInfo;
	private CommentDao comment;
	private ReplyDao reply;
	private TransactionManager transactionManager;


	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final DataSource dataSource = (DataSource)sce.getServletContext().getAttribute("dataSource");
		this.transactionManager = new TransactionManager(dataSource);
		final Connection conn;
		try {
			conn = dataSource.getConnection();
			setDaoConnection(conn);
			setMemberController(sce);
			setPostController(sce);
			setCommentController(sce);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void setDaoConnection(final Connection conn) {
		this.comment = new CommentDao(conn);
		this.reply = new ReplyDao(conn);
	}

	private void setPostController(final ServletContextEvent sce) {
		final PostService postService = new PostService(transactionManager);
		final PostController postController = new PostController(postService);
		sce.getServletContext().setAttribute("postController", postController);
	}

	private void setCommentController(final ServletContextEvent sce) {
		final CommentService commentService = new CommentService(memberInfo, comment, reply, transactionManager);
		final CommentController commentController = new CommentController(commentService);
		sce.getServletContext().setAttribute("commentController", commentController);
	}

	private void setMemberController(final ServletContextEvent sce) {
		final MemberService memberService = new MemberService(transactionManager);
		final MemberController memberController = new MemberController(memberService);
		sce.getServletContext().setAttribute("memberController", memberController);
	}

}
