package com.blog.tech.domain.comment.entity;

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
public class Comment extends BaseEntity {

	private Long id;
	private Long memberInfoId;
	private Long postId;
	private String content;
	private Integer reportCount;
	private Boolean alarm;
	private Status status;

}
