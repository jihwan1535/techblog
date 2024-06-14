package com.blog.tech.domain.comment.controller;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.post.dto.response.CategoryResponseBean;
import com.blog.tech.domain.post.dto.response.TopicResponseBean;
import com.blog.tech.domain.comment.service.CommentService;

public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

}
