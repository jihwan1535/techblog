package com.blog.tech.global.config;

import javax.sql.DataSource;

import com.blog.tech.domain.comment.controller.CommentController;
import com.blog.tech.domain.comment.service.CommentService;
import com.blog.tech.domain.common.TransactionManager;
import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.service.MemberService;
import com.blog.tech.domain.post.controller.PostController;
import com.blog.tech.domain.post.service.PostService;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ControllerLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final DataSource dataSource = (DataSource)sce.getServletContext().getAttribute("dataSource");
		final TransactionManager transactionManager = new TransactionManager(dataSource);
		setMemberController(sce, transactionManager);
		setPostController(sce, transactionManager);
		setCommentController(sce, transactionManager);
	}

	private void setPostController(final ServletContextEvent sce, final TransactionManager transactionManager) {
		final PostService postService = new PostService(transactionManager);
		final PostController postController = new PostController(postService);
		sce.getServletContext().setAttribute("postController", postController);
	}

	private void setCommentController(final ServletContextEvent sce, final TransactionManager transactionManager) {
		final CommentService commentService = new CommentService(transactionManager);
		final CommentController commentController = new CommentController(commentService);
		sce.getServletContext().setAttribute("commentController", commentController);
	}

	private void setMemberController(final ServletContextEvent sce, final TransactionManager transactionManager) {
		final MemberService memberService = new MemberService(transactionManager);
		final MemberController memberController = new MemberController(memberService);
		sce.getServletContext().setAttribute("memberController", memberController);
	}

}
