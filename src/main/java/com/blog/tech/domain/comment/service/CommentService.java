package com.blog.tech.domain.comment.service;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.comment.repository.ifs.CommentRepository;
import com.blog.tech.domain.comment.repository.ifs.ReplyRepository;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.post.dto.response.CategoryResponseBean;
import com.blog.tech.domain.post.dto.response.TopicResponseBean;
import com.blog.tech.domain.post.entity.Category;
import com.blog.tech.domain.post.entity.Topic;

public class CommentService {

	private final MemberInfoRepository memberInfoRepository;
	private final CommentRepository commentRepository;
	private final ReplyRepository replyRepository;

	public CommentService(
		final MemberInfoRepository memberInfoRepository,
		final CommentRepository commentRepository,
		final ReplyRepository replyRepository
	) {
		this.memberInfoRepository = memberInfoRepository;
		this.commentRepository = commentRepository;
		this.replyRepository = replyRepository;
	}

}
