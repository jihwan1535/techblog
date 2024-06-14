package com.blog.tech.domain.post.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.blog.tech.domain.post.dto.response.CategoryResponseBean;
import com.blog.tech.domain.post.dto.response.TopicResponseBean;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.post.dto.request.PostRequestBean;
import com.blog.tech.domain.post.dto.response.PostResponseBean;
import com.blog.tech.domain.post.dto.response.PostsResponseBean;
import com.blog.tech.domain.post.entity.ConnectHashtag;
import com.blog.tech.domain.post.entity.Hashtag;
import com.blog.tech.domain.post.entity.Post;
import com.blog.tech.domain.post.repository.ifs.ConnectHashtagRepository;
import com.blog.tech.domain.post.repository.ifs.HashtagRepository;
import com.blog.tech.domain.post.repository.ifs.PostRepository;
import com.blog.tech.domain.post.entity.Category;
import com.blog.tech.domain.post.entity.Topic;
import com.blog.tech.domain.post.repository.ifs.CategoryRepository;
import com.blog.tech.domain.post.repository.ifs.TopicRepository;

public class PostService {

	private final PostRepository postRepository;
	private final MemberInfoRepository memberRepository;
	private final CategoryRepository categoryRepository;
	private final TopicRepository topicRepository;
	private final HashtagRepository hashtagRepository;
	private final ConnectHashtagRepository connectHashtagRepository;

	public PostService(
		final PostRepository postRepository,
		final MemberInfoRepository memberRepository,
		final CategoryRepository categoryRepository,
		final TopicRepository topicRepository,
		final HashtagRepository hashtagRepository,
		final ConnectHashtagRepository connectHashtagRepository
	) {
		this.postRepository = postRepository;
		this.memberRepository = memberRepository;
		this.categoryRepository = categoryRepository;
		this.topicRepository = topicRepository;
		this.hashtagRepository = hashtagRepository;
		this.connectHashtagRepository = connectHashtagRepository;
	}

	public void writeOnPost(final Long memberId, final PostRequestBean request) throws SQLException {
		final MemberInfo member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("Invalid Member, " + memberId);
		});
		final Post post = Post.to(memberId, request);
		postRepository.save(post);

		final List<Hashtag> hashtags = Optional.ofNullable(request.hashtags())
			.orElse(Collections.emptyList())
			.stream()
			.map(Hashtag::to)
			.toList();

		connectHashtagWithPost(post.getId(), hashtags);

		member.writePost();
		memberRepository.save(member);
	}

	private void connectHashtagWithPost(final Long postId, final List<Hashtag> request) {
		request.stream().forEach(it -> {
			try {
				final Optional<Hashtag> hasTag = hashtagRepository.findByName(it.getTag());
				if (hasTag.isEmpty()) {
					final Hashtag hashtag = hashtagRepository.save(it);
					connect(hashtag, postId);
				} else {
					final Hashtag hashtag = hasTag.get();
					connect(hashtag, postId);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}

	private void connect(final Hashtag hashtag, final Long postId) throws SQLException {
		final Optional<ConnectHashtag> connectInfo =
			connectHashtagRepository.findByHashtagIdAndPostId(hashtag.getId(), postId);

		if (connectInfo.isEmpty()){
			final ConnectHashtag connect = ConnectHashtag.to(postId, hashtag.getId());
			connectHashtagRepository.save(connect);
		}
	}

	public List<PostsResponseBean> getAllPosts(final Long postId) throws SQLException {
		final List<Post> posts = postRepository.findTop10ByIdDescId(postId);
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

	public PostResponseBean getPost(final Long postId) throws SQLException {
		final Post post = postRepository.findById(postId).orElseThrow(() -> {
			throw new RuntimeException("Not Found post : " + postId);
		});
		final MemberInfo memberInfo = memberRepository.findById(post.getMemberInfoId()).orElseThrow(() -> {
			throw new RuntimeException("Not Found member : " + post.getMemberInfoId());
		});
		final Topic topic = topicRepository.findById(post.getTopicId()).orElseThrow(() -> {
			throw new RuntimeException("Not Found Topic : " + post.getTopicId());
		});
		final Category category = categoryRepository.findById(topic.getCategoryId()).orElseThrow(() -> {
			throw new RuntimeException("Not Found Category : " + topic.getCategoryId());
		});
		final List<Hashtag> hashtags = hashtagRepository.findAllByPostId(postId);
		return PostResponseBean.of(memberInfo, post, category, topic, hashtags);
	}

	public List<CategoryResponseBean> getAllCategories() throws SQLException {
		final List<Category> categories = categoryRepository.findAll();
		return categories.stream()
			.map(CategoryResponseBean::of)
			.toList();
	}

	public List<TopicResponseBean> getAllTopicsByCategory(final Long categoryId) throws SQLException {
		final List<Topic> topics = topicRepository.findAllByCategoryId(categoryId);
		return topics.stream()
			.map(TopicResponseBean::of)
			.toList();
	}

}
