package com.blog.tech.domain.post.service;

import com.blog.tech.domain.common.TransactionManager;
import com.blog.tech.domain.common.TransactionTemplate;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.repository.factory.MemberRepositoryFactory;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.post.dto.request.PostRequest;
import com.blog.tech.domain.post.dto.response.AllPostResponse;
import com.blog.tech.domain.post.dto.response.CategoryResponse;
import com.blog.tech.domain.post.dto.response.HashtagInfoResult;
import com.blog.tech.domain.post.dto.response.PostResponse;
import com.blog.tech.domain.post.entity.*;
import com.blog.tech.domain.post.repository.factory.PostRepositoryFactory;
import com.blog.tech.domain.post.repository.ifs.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PostService {

	private final PostRepository postRepository = PostRepositoryFactory.getPostRepository();
	private final PostViewRepository postViewRepository = PostRepositoryFactory.getPostViewRepository();
	private final CategoryRepository categoryRepository = PostRepositoryFactory.getCategoryRepository();
	private final TopicRepository topicRepository = PostRepositoryFactory.getTopicRepository();
	private final HashtagRepository hashtagRepository = PostRepositoryFactory.getHashtagRepository();
	private final ConnectHashtagRepository connectHashtagRepository = PostRepositoryFactory.getConnectHashtagRepository();
	private final MemberInfoRepository memberRepository = MemberRepositoryFactory.getMemberInfoRepository();
	private final TransactionManager transactionManager;

	public PostService(final TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
    }

	public void writeOnPost(final Long memberId, final PostRequest request) {
		new TransactionTemplate(transactionManager).execute(() -> {
            final MemberInfo member;
            try {
                member = memberRepository.findById(memberId).orElseThrow(() -> {
                    throw new RuntimeException("Invalid Member, " + memberId);
                });
				final Post post = postParseHtmlFromMarkdown(memberId, request);
				postRepository.save(post);
				saveParsedHtml(post.getContent());

				final List<Hashtag> hashtags = Optional.ofNullable(request.hashtags())
						.orElse(Collections.emptyList())
						.stream()
						.map(Hashtag::to)
						.toList();
				connectHashtagWithPost(post.getId(), hashtags);

				member.postIncreasing();
				memberRepository.save(member);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
			return null;
		});
	}

	private Post postParseHtmlFromMarkdown(final Long memberId, final PostRequest request) {
		final Parser parser = Parser.builder().build();
		final HtmlRenderer renderer = HtmlRenderer.builder().build();
		final String htmlContent = renderer.render(parser.parse(request.content()));

		return Post.to(memberId, request, htmlContent);
	}

	private void saveParsedHtml(final String html) throws SQLException {
		final Document doc = Jsoup.parse(html);
		final String text = doc.text();

		final PostText postText = PostText.to(text);
		postRepository.saveText(postText);
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

	public List<AllPostResponse> getAllPosts(final Long postId) {
		return new TransactionTemplate(transactionManager).execute(() -> {
			final List<Post> posts;
            try {
                posts = postRepository.findTop10ByLessThanIdDescId(postId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return posts.stream()
                    .map(AllPostResponse::of)
                    .toList();
        });
	}

	public PostResponse getPost(final Long postId, final String ip) {
		return new TransactionTemplate(transactionManager).execute(() -> {
            final Post post;
            try {
                post = postRepository.findById(postId).orElseThrow(() -> {
                    throw new RuntimeException("Not Found post : " + postId);
                });
				final List<Hashtag> hashtags = hashtagRepository.findAllByPostId(postId);
				final Optional<PostView> postViewInfo = postViewRepository.findByPostIdAndIP(postId, ip);

				if (postViewInfo.isPresent()) {
					alreadyVisitor(post, postViewInfo.get());
				} else {
					firstGetPost(post, ip);
				}
				return PostResponse.of(post, hashtags);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
		});
	}

	private void firstGetPost(final Post post, final String ip) throws SQLException {
		final PostView postView = PostView.to(post.getId(), ip);
		post.viewIncreasing();
		postViewRepository.save(postView);
		postRepository.save(post);
	}

	private void alreadyVisitor(final Post post, final PostView postView) throws SQLException {
		final LocalDate today = LocalDate.now();

		if (postView.getViewAt().isBefore(today)) {
			post.viewIncreasing();
			postView.setViewAt(today);
			postViewRepository.save(postView);
		}
	}

	public List<CategoryResponse> getAllCategories() {
		return new TransactionTemplate(transactionManager).execute(() -> {
            final List<Category> categories;
            try {
                categories = categoryRepository.findAll();
				getAllTopicsByCategory(categories);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return categories.stream()
					.map(CategoryResponse::of)
					.toList();
		});
	}

	private void getAllTopicsByCategory(final List<Category> categories) {
		categories.stream().forEach(
			category -> {
				try {
					final List<Topic> topics = topicRepository.findAllByCategoryId(category.getId());
					category.setTopics(topics);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		);
	}

	public List<AllPostResponse> getAllPostsByTopic(final Long postId, final Long topicId) {
		return new TransactionTemplate(transactionManager).execute(() -> {
            final List<Post> posts;
            try {
                posts = postRepository.findTop10ByLessThanIdAndTopicIdDescId(postId, topicId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return posts.stream()
			.map(AllPostResponse::of)
			.toList();
		});
	}

	public List<HashtagInfoResult> getRandomHashtags() {
		return new TransactionTemplate(transactionManager).execute(() -> {
            final List<Hashtag> hashtags;
            try {
                hashtags = hashtagRepository.findTop20DescRandom();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return hashtags.stream()
					.map(HashtagInfoResult::of)
					.toList();
		});
	}

	public List<AllPostResponse> getAllPostsByCategory(final Long postId, final Long categoryId) {
		return new TransactionTemplate(transactionManager).execute(() -> {
            final List<Post> posts;
            try {
                posts = postRepository.findTop10ByLessThanIdAndCategoryIdDescId(postId, categoryId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return posts.stream()
					.map(AllPostResponse::of)
					.toList();
		});
	}

	public List<AllPostResponse> searchPosts(final Long postId, final String keyword) {

		return new TransactionTemplate(transactionManager).execute(() -> {
			final String keywordQuery = keyword + "*";
            final List<Post> posts;
            try {
                posts = postRepository.searchPostsContainKeyword(postId, keywordQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return posts.stream()
					.map(AllPostResponse::of)
					.toList();
		});
	}

}
