package com.blog.tech.domain.common;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
public abstract class BaseEntity {

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public BaseEntity() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public void updateTime() {
		updatedAt = LocalDateTime.now();
	}

}
