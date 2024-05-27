package com.blog.tech.domain.member.entity;

import com.blog.tech.domain.common.BaseEntity;

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
public class Member extends BaseEntity {

	private Long id;
	private String email;
	private String password;

}
