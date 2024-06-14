package com.blog.tech.domain.comment.service;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.comment.dto.request.CommentRequest;
import com.blog.tech.domain.comment.dto.request.DeleteCommentRequest;
import com.blog.tech.domain.comment.dto.request.EditCommentRequest;
import com.blog.tech.domain.comment.dto.request.ReplyRequest;
import com.blog.tech.domain.comment.dto.response.CommentResponse;
import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.comment.entity.Reply;
import com.blog.tech.domain.comment.entity.vo.Status;
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

	public void writeCommentOnPost(final Long memberId, final CommentRequest request) throws SQLException {
		final Post post = postRepository.findById(request.postId()).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND POST " + request.postId());
		});
		final MemberInfo memberInfo = memberInfoRepository.findById(memberId).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND MEMBER " + memberId);
		});

		final Comment comment = Comment.to(memberId, request);
		post.commentIncreasing();
		memberInfo.commentIncreasing();

		commentRepository.save(comment);
		postRepository.save(post);
		memberInfoRepository.save(memberInfo);
	}

	public List<CommentResponse> allCommentsAndReplies(final Long postId) throws SQLException {
		final List<Comment> comments = commentRepository.findTop10ByPostIdAndStatusDescId(postId, Status.REGISTERED);
		comments.forEach(it -> {
			try {
				final List<Reply> replies = replyRepository.findAllByCommentIdAndStatus(it.getId(), Status.REGISTERED);
				it.setReplies(replies);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});

		return comments.stream()
			.map(CommentResponse::of)
			.toList();
	}

	public void unRegisterComment(final Long memberId, final DeleteCommentRequest request) throws SQLException {
		final Comment comment = commentRepository.findById(request.commentId()).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND COMMENT " + request.commentId());
		});
		if (comment.getMemberInfoId() != memberId) {
			throw new IllegalArgumentException("NOT SAME USER");
		}
		final MemberInfo memberInfo = memberInfoRepository.findById(memberId).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND MEMBER " + memberId);
		});
		final Post post = postRepository.findById(request.postId()).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND POST " + request.postId());
		});

		memberInfo.commentDecreasing();
		post.commentDecreasing();
		comment.setStatus(Status.UNREGISTERED);
		comment.updateTime();

		commentRepository.save(comment);
		memberInfoRepository.save(memberInfo);
		postRepository.save(post);
	}

	public void updateComment(final Long memberId, final EditCommentRequest request) throws SQLException {
		final Comment comment = commentRepository.findById(request.commentId()).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND COMMENT " + request.commentId());
		});
		if (comment.getMemberInfoId() != memberId) {
			throw new IllegalArgumentException("NOT SAME USER");
		}
		final MemberInfo memberInfo = memberInfoRepository.findById(memberId).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND MEMBER " + memberId);
		});

		comment.setContent(request.content());
		comment.updateTime();
		commentRepository.save(comment);
	}

	public void writeReplyOnComment(final Long memberId, final ReplyRequest request) throws SQLException {
		final Post post = postRepository.findById(request.postId()).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND POST " + request.postId());
		});
		final MemberInfo memberInfo = memberInfoRepository.findById(memberId).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND MEMBER " + memberId);
		});

		final Reply reply = Reply.to(memberId, request);
		memberInfo.commentIncreasing();
		post.replyIncreasing();

		replyRepository.save(reply);
		postRepository.save(post);
		memberInfoRepository.save(memberInfo);
	}

}