package com.blog.tech.domain.post.entity;

import java.time.LocalDateTime;

import com.blog.tech.domain.common.BaseEntity;
import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.post.dto.request.PostRequest;
import com.blog.tech.domain.post.entity.vo.Status;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
public class Post extends BaseEntity {

	private Long id;
	private Long memberInfoId;
	private Long topicId;
	private Long categoryId;
	private String tile;
	private String content;
	private Integer commentCount;
	private Integer replyCount;
	private Integer viewCount;
	private Integer reportCount;
	private Integer scrapCount;
	private Boolean alarm;
	private Status status;
	private MemberInfo memberInfo;
	private Category category;
	private Topic topic;

	public static Post to(final Long memberId, final PostRequest bean, final String htmlContent) {
		return Post.builder()
			.id(0L)
			.memberInfoId(memberId)
			.topicId(bean.topicId())
			.categoryId(bean.categoryId())
			.tile(bean.title())
			.content(htmlContent)
			.status(Status.REGISTERED)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	public void commentIncreasing() {
		this.commentCount++;
	}
	public void commentDecreasing() {
		this.commentCount--;
	}

	public void replyIncreasing() {
		this.replyCount++;
	}
	public void replyDecreasing() {
		this.replyCount--;
	}

}
