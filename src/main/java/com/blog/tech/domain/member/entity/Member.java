package com.blog.tech.domain.member.entity;

import com.blog.tech.domain.common.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Member extends BaseEntity {

	private Long id;
	private String email;
	private String password;

}
