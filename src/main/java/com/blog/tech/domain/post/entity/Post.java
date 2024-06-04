package com.blog.tech.domain.post.entity;

import com.blog.tech.domain.common.BaseEntity;
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
	private Integer viewCount;
	private Integer reportCount;
	private Integer scrapCount;
	private Boolean alarm;
	private Status status;

}
