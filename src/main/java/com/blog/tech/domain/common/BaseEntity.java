package com.blog.tech.domain.common;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity {

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public void updateTime() {
		updatedAt = LocalDateTime.now();
	}

}
