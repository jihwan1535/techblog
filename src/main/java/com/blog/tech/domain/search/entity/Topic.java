package com.blog.tech.domain.search.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Topic {

	private Long id;
	private Long categoryId;
	private String name;

}
