package com.blog.tech.domain.comment.service;

import com.blog.tech.domain.comment.dto.request.*;
import com.blog.tech.domain.comment.dto.response.CommentResponse;
import com.blog.tech.domain.comment.entity.Comment;
import com.blog.tech.domain.comment.entity.Reply;
import com.blog.tech.domain.comment.entity.vo.Status;
import com.blog.tech.domain.comment.repository.factory.CommentRepositoryFactory;
import com.blog.tech.domain.comment.repository.ifs.CommentRepository;
import com.blog.tech.domain.comment.repository.ifs.ReplyRepository;
import com.blog.tech.domain.common.TransactionManager;
import com.blog.tech.domain.common.TransactionTemplate;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.repository.factory.MemberRepositoryFactory;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.repository.factory.PostRepositoryFactory;
import com.blog.tech.domain.post.repository.ifs.PostRepository;

import java.sql.SQLException;
import java.util.List;

public class CommentService {

	private final MemberInfoRepository memberInfoRepository = MemberRepositoryFactory.getMemberInfoRepository();
	private final PostRepository postRepository = PostRepositoryFactory.getPostRepository();
	private final CommentRepository commentRepository = CommentRepositoryFactory.getCommentRepository();
	private final ReplyRepository replyRepository = CommentRepositoryFactory.getReplyRepository();
	private final TransactionManager transactionManager;

	public CommentService(final TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void writeCommentOnPost(final Long memberId, final CommentRequest request) {
		new TransactionTemplate(transactionManager).execute(() -> {
			try {
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
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
		});
	}

	public List<CommentResponse> allCommentsAndReplies(final Long postId)  {
		return new TransactionTemplate(transactionManager).execute(() -> {
            final List<Comment> comments;
            try {
                comments = commentRepository.findTop10ByPostIdAndStatusDescId(postId, Status.REGISTERED);
				comments.forEach(it -> {
					try {
						final List<Reply> replies = replyRepository.findAllByCommentIdAndStatus(it.getId(), Status.REGISTERED);
						it.setReplies(replies);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				});
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

			return comments.stream()
					.map(CommentResponse::of)
					.toList();
		});
	}

	public void unRegisterComment(final Long memberId, final DeleteCommentRequest request) {
		new TransactionTemplate(transactionManager).execute(() -> {
            final Comment comment;
            try {
                comment = commentRepository.findById(request.commentId()).orElseThrow(() -> {
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
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
			return null;
		});
	}

	public void updateComment(final Long memberId, final EditCommentRequest request) {
		new TransactionTemplate(transactionManager).execute(() -> {
            try {
				final Comment comment = commentRepository.findById(request.commentId()).orElseThrow(() -> {
                    throw new IllegalArgumentException("NOT FOUND COMMENT " + request.commentId());
                });
				if (comment.getMemberInfoId() != memberId) {
					throw new IllegalArgumentException("NOT SAME USER");
				}

				comment.setContent(request.content());
				comment.updateTime();
				commentRepository.save(comment);
				return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
		});
	}

	public void writeReplyOnComment(final Long memberId, final ReplyRequest request) {
		new TransactionTemplate(transactionManager).execute(() -> {

            try {
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
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
			return null;
		});
	}

	public void updateReply(final Long memberId, final EditReplyRequest request) {
		new TransactionTemplate(transactionManager).execute(() -> {
			try {
				final Reply reply = replyRepository.findById(request.replyId()).orElseThrow(() -> {
					throw new IllegalArgumentException("NOT FOUND REPLY " + request.replyId());
				});
				if (reply.getMember().getId() != memberId) {
					throw new IllegalArgumentException("NOT SAME USER");
				}
				reply.setContent(request.content());
				reply.updateTime();
				replyRepository.save(reply);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			return null;
		});
	}

	public void unRegisterReply(final Long memberId, final DeleteReplyRequest request) {
		new TransactionTemplate(transactionManager).execute(() -> {
			final Reply reply;
			try {
				reply = replyRepository.findById(request.replyId()).orElseThrow(() -> {
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
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			return null;
		});
	}

}
