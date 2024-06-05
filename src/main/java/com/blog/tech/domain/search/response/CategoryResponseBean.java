package com.blog.tech.domain.search.response;

import com.blog.tech.domain.search.entity.Category;

public record CategoryResponseBean(
	Long id,
	String name
){

	public static CategoryResponseBean of(final Category category) {
		return new CategoryResponseBean(category.getId(), category.getName());
	}

}
