package com.blog.tech.global.config;

import java.sql.Connection;

import com.blog.tech.domain.comment.controller.CommentController;
import com.blog.tech.domain.comment.repository.dao.CommentDao;
import com.blog.tech.domain.comment.repository.dao.ReplyDao;
import com.blog.tech.domain.comment.service.CommentService;
import com.blog.tech.domain.member.controller.MemberController;
import com.blog.tech.domain.member.repository.dao.MemberDao;
import com.blog.tech.domain.member.repository.dao.MemberInfoDao;
import com.blog.tech.domain.member.service.MemberService;
import com.blog.tech.domain.post.controller.PostController;
import com.blog.tech.domain.post.repository.dao.CategoryDao;
import com.blog.tech.domain.post.repository.dao.ConnectHashtagDao;
import com.blog.tech.domain.post.repository.dao.HashtagDao;
import com.blog.tech.domain.post.repository.dao.PostDao;
import com.blog.tech.domain.post.repository.dao.TopicDao;
import com.blog.tech.domain.post.service.PostService;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ControllerLoaderListener implements ServletContextListener {

	private MemberDao member;
	private MemberInfoDao memberInfo;
	private CategoryDao category;
	private TopicDao topic;
	private PostDao post;
	private CommentDao comment;
	private ReplyDao reply;
	private HashtagDao hashtag;
	private ConnectHashtagDao connectHash;


	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final Connection conn = (Connection)sce.getServletContext().getAttribute("conn");
		setDaoConnection(conn);
		setMemberController(sce);
		setPostController(sce);
		setCommentController(sce);
	}

	private void setDaoConnection(final Connection conn) {
		this.member = new MemberDao(conn);
		this.memberInfo = new MemberInfoDao(conn);
		this.category = new CategoryDao(conn);
		this.topic = new TopicDao(conn);
		this.post = new PostDao(conn);
		this.comment = new CommentDao(conn);
		this.reply = new ReplyDao(conn);
		this.hashtag = new HashtagDao(conn);
		this.connectHash = new ConnectHashtagDao(conn);
	}

	private void setPostController(final ServletContextEvent sce) {
		final PostService postService = new PostService(post, memberInfo, category, topic, hashtag, connectHash);
		final PostController postController = new PostController(postService);
		sce.getServletContext().setAttribute("postController", postController);
	}

	private void setCommentController(final ServletContextEvent sce) {
		final CommentService commentService = new CommentService(memberInfo, post, comment, reply);
		final CommentController commentController = new CommentController(commentService);
		sce.getServletContext().setAttribute("commentController", commentController);
	}

	private void setMemberController(final ServletContextEvent sce) {
		final MemberService memberService = new MemberService(member, memberInfo);
		final MemberController memberController = new MemberController(memberService);
		sce.getServletContext().setAttribute("memberController", memberController);
	}

}
