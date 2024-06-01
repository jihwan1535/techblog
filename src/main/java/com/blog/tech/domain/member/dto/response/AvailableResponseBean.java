package com.blog.tech.domain.member.dto.response;

public record AvailableResponseBean(
	String status
) {

	public static AvailableResponseBean of(final String status) {
		return new AvailableResponseBean(status);
	}

}
