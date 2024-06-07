package com.blog.tech.domain.post.entity;

import com.blog.tech.domain.common.BaseEntity;
import com.blog.tech.domain.post.entity.vo.Status;

public class Reply extends BaseEntity {

	private Long id;
	private Long memberInfoId;
	private Long postId;
	private String content;
	private Integer reportCount;
	private Status status;

}
