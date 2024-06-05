package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.post.entity.Category;

public record CategoryResponseBean(
	Long id,
	String name
){

	public static CategoryResponseBean of(final Category category) {
		return new CategoryResponseBean(category.getId(), category.getName());
	}

}
