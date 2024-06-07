package com.blog.tech.domain.post.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.post.dto.request.PostRequestBean;
import com.blog.tech.domain.post.dto.response.PostsResponseBean;
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

	public List<PostsResponseBean> getAllPosts(final Long postId) throws SQLException {
		final List<Post> posts = postRepository.findTop10AllByIdDescId(postId);
		final List<MemberInfo> members = getMemberInfos(posts);
		return IntStream.range(0, posts.size())
			.mapToObj(i -> PostsResponseBean.of(posts.get(i), members.get(i)))
			.toList();
	}

	private List<MemberInfo> getMemberInfos(final List<Post> posts) {
		return posts.stream().
			map(it -> {
				try {
					return memberRepository.findById(it.getMemberInfoId()).orElseThrow(() -> {
						throw new RuntimeException("notFound Member " + it.getMemberInfoId());
					});
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}).toList();
	}
}
