package com.blog.tech.domain.comment.service;

import java.sql.SQLException;

import com.blog.tech.domain.comment.dto.request.CommentRequestBean;
import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.comment.repository.ifs.CommentRepository;
import com.blog.tech.domain.comment.repository.ifs.ReplyRepository;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.repository.ifs.PostRepository;

public class CommentService {

	private final MemberInfoRepository memberInfoRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final ReplyRepository replyRepository;

	public CommentService(
		final MemberInfoRepository memberInfoRepository,
		final PostRepository postRepository,
		final CommentRepository commentRepository,
		final ReplyRepository replyRepository
	) {
		this.memberInfoRepository = memberInfoRepository;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.replyRepository = replyRepository;
	}

	public void writeCommentOnPost(final Long memberId, final CommentRequestBean request) throws SQLException {
		final Post post = postRepository.findById(request.postId()).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND POST " + request.postId());
		});
		final MemberInfo memberInfo = memberInfoRepository.findByMemberId(memberId).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND MEMBER " + memberId);
		});

		final Comment comment = Comment.to(memberId, request);
		post.commentIncreasing();
		memberInfo.commentIncreasing();

		commentRepository.save(comment);
		postRepository.save(post);
		memberInfoRepository.save(memberInfo);
	}

}
