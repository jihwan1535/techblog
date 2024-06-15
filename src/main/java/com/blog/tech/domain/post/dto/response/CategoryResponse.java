package com.blog.tech.domain.post.dto.response;

import com.blog.tech.domain.post.entity.Category;

public record CategoryResponse(
	Long id,
	String name
){

	public static CategoryResponse of(final Category category) {
		return new CategoryResponse(category.getId(), category.getName());
	}

}
