package com.blog.tech.domain.post.service;

import java.sql.SQLException;
import java.util.Optional;

import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.member.repository.ifs.MemberRepository;
import com.blog.tech.domain.post.dto.request.PostRequestBean;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.repository.ifs.CommentRepository;
import com.blog.tech.domain.post.repository.ifs.PostRepository;
import com.blog.tech.domain.post.repository.ifs.ReplyRepository;

public class PostService {

	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;
	private final CommentRepository commentRepository;
	private final MemberInfoRepository memberRepository;

	public PostService(
		final PostRepository postRepository,
		final ReplyRepository replyRepository,
		final CommentRepository commentRepository,
		final MemberInfoRepository memberRepository
	) {
		this.postRepository = postRepository;
		this.replyRepository = replyRepository;
		this.commentRepository = commentRepository;
		this.memberRepository = memberRepository;
	}

	public void writeOnPost(final Long memberId, final PostRequestBean request) throws SQLException {
		final Post post = Post.to(memberId, request);
		postRepository.save(post);
		final MemberInfo member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("Invalid Member, " + memberId);
		});
		member.writePost();
		memberRepository.save(member);
	}

}
