package com.blog.tech.domain.comment.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

import com.blog.tech.domain.comment.dto.request.CommentRequest;
import com.blog.tech.domain.comment.dto.request.DeleteCommentRequest;
import com.blog.tech.domain.comment.dto.request.EditCommentRequest;
import com.blog.tech.domain.comment.dto.request.ReplyRequest;
import com.blog.tech.domain.comment.dto.response.CommentsResponse;
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

	public List<CommentsResponse> allCommentsAndReplies(final Long postId) throws SQLException {
		final List<Comment> comments = commentRepository.findTop10ByPostIdAndStatusDescId(postId);
		final List<MemberInfo> memberInfos = getMemberInfos(comments);

		return IntStream.range(0, comments.size())
			.mapToObj(i -> CommentsResponse.of(memberInfos.get(i), comments.get(i)))
			.toList();
	}

	private List<MemberInfo> getMemberInfos(final List<Comment> comments) {
		return comments.stream().
			map(it -> {
				try {
					return memberInfoRepository.findById(it.getMemberInfoId()).orElseThrow(() -> {
						throw new RuntimeException("notFound Member " + it.getMemberInfoId());
					});
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}).toList();
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
