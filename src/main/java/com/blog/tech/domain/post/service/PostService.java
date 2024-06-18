package com.blog.tech.domain.post.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.blog.tech.domain.post.dto.response.CategoryResponse;
import com.blog.tech.domain.post.dto.response.HashtagInfoResult;
import com.blog.tech.domain.post.dto.response.TopicResponse;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.post.dto.request.PostRequest;
import com.blog.tech.domain.post.dto.response.PostResponse;
import com.blog.tech.domain.post.dto.response.AllPostResponse;
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

	public void writeOnPost(final Long memberId, final PostRequest request) throws SQLException {
		final MemberInfo member = memberRepository.findById(memberId).orElseThrow(() -> {
			throw new RuntimeException("Invalid Member, " + memberId);
		});
		final Post post = postParseHtmlFromMarkdown(memberId, request);
		postRepository.save(post);

		final List<Hashtag> hashtags = Optional.ofNullable(request.hashtags())
			.orElse(Collections.emptyList())
			.stream()
			.map(Hashtag::to)
			.toList();

		connectHashtagWithPost(post.getId(), hashtags);

		member.postIncreasing();
		memberRepository.save(member);
	}

	private Post postParseHtmlFromMarkdown(final Long memberId, final PostRequest request) {
		final Parser parser = Parser.builder().build();
		final HtmlRenderer renderer = HtmlRenderer.builder().build();
		final String htmlContent = renderer.render(parser.parse(request.content()));
		final Post post = Post.to(memberId, request, htmlContent);

		return post;
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

	public List<AllPostResponse> getAllPosts(final Long postId) throws SQLException {
		final List<Post> posts = postRepository.findTop10ByLessThanIdDescId(postId);

		return posts.stream()
			.map(AllPostResponse::of)
			.toList();
	}

	public PostResponse getPost(final Long postId) throws SQLException {
		final Post post = postRepository.findById(postId).orElseThrow(() -> {
			throw new RuntimeException("Not Found post : " + postId);
		});
		final List<Hashtag> hashtags = hashtagRepository.findAllByPostId(postId);

		return PostResponse.of(post, hashtags);
	}

	public List<CategoryResponse> getAllCategories() throws SQLException {
		final List<Category> categories = categoryRepository.findAll();

		return categories.stream()
			.map(CategoryResponse::of)
			.toList();
	}

	public List<TopicResponse> getAllTopicsByCategory(final Long categoryId) throws SQLException {
		final List<Topic> topics = topicRepository.findAllByCategoryId(categoryId);

		return topics.stream()
			.map(TopicResponse::of)
			.toList();
	}

	public List<AllPostResponse> getAllPostsByTopic(final Long postId, final Long topicId) throws SQLException {
		final List<Post> posts = postRepository.findTop10ByLessThanIdAndTopicIdDescId(postId, topicId);

		return posts.stream()
			.map(AllPostResponse::of)
			.toList();
	}

	public List<HashtagInfoResult> getRandomHashtags() throws SQLException {
		final List<Hashtag> hashtags = hashtagRepository.findTop20DescRandom();

		return hashtags.stream()
			.map(HashtagInfoResult::of)
			.toList();
	}
}
