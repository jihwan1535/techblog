package com.blog.tech.domain.comment.service;

import java.sql.SQLException;
import java.util.List;

import com.blog.tech.domain.comment.dto.request.CommentRequest;
import com.blog.tech.domain.comment.dto.request.DeleteCommentRequest;
import com.blog.tech.domain.comment.dto.request.DeleteReplyRequest;
import com.blog.tech.domain.comment.dto.request.EditCommentRequest;
import com.blog.tech.domain.comment.dto.request.EditReplyRequest;
import com.blog.tech.domain.comment.dto.request.ReplyRequest;
import com.blog.tech.domain.comment.dto.response.CommentResponse;
import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.comment.entity.Reply;
import com.blog.tech.domain.comment.entity.vo.Status;
import com.blog.tech.domain.comment.repository.ifs.CommentRepository;
import com.blog.tech.domain.comment.repository.ifs.ReplyRepository;
import com.blog.tech.domain.common.TransactionManager;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.repository.factory.PostRepositoryFactory;
import com.blog.tech.domain.post.repository.ifs.PostRepository;

public class CommentService {

	private final MemberInfoRepository memberInfoRepository;
	private final PostRepository postRepository = PostRepositoryFactory.getPostDao();
	private final CommentRepository commentRepository;
	private final ReplyRepository replyRepository;
	private final TransactionManager transactionManager;

	public CommentService(
		final MemberInfoRepository memberInfoRepository,
		final CommentRepository commentRepository,
		final ReplyRepository replyRepository,
		final TransactionManager transactionManager
	) {
		this.transactionManager = transactionManager;
		this.memberInfoRepository = memberInfoRepository;
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

		comment.getMember().commentDecreasing();
		comment.getPost().commentDecreasing();
		comment.setStatus(Status.UNREGISTERED);
		comment.updateTime();

		commentRepository.save(comment);
		memberInfoRepository.save(comment.getMember());
		postRepository.save(comment.getPost());
	}

	public void updateComment(final Long memberId, final EditCommentRequest request) throws SQLException {
		final Comment comment = commentRepository.findById(request.commentId()).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND COMMENT " + request.commentId());
		});
		if (comment.getMemberInfoId() != memberId) {
			throw new IllegalArgumentException("NOT SAME USER");
		}

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

	public void updateReply(final Long memberId, final EditReplyRequest request) throws SQLException {
		final Reply reply = replyRepository.findById(request.replyId()).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND REPLY " + request.replyId());
		});
		if (reply.getMember().getId() != memberId) {
			throw new IllegalArgumentException("NOT SAME USER");
		}
		reply.setContent(request.content());
		reply.updateTime();
		replyRepository.save(reply);
	}

	public void unRegisterReply(final Long memberId, final DeleteReplyRequest request) throws SQLException {
		final Reply reply = replyRepository.findById(request.replyId()).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND COMMENT " + request.replyId());
		});
		if (reply.getMember().getId() != memberId) {
			throw new IllegalArgumentException("NOT SAME USER");
		}
		final Post post = postRepository.findById(request.postId()).orElseThrow(() -> {
			throw new IllegalArgumentException("NOT FOUND POST " + request.postId());
		});

		reply.getMember().commentDecreasing();
		post.replyDecreasing();
		reply.setStatus(Status.UNREGISTERED);
		reply.updateTime();

		replyRepository.save(reply);
		memberInfoRepository.save(reply.getMember());
		postRepository.save(post);
	}

}
