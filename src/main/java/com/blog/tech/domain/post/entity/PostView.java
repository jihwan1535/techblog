package com.blog.tech.domain.post.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class PostView {

	private Long id;
	private Long postId;
	private String ip;
	private LocalDate viewAt;
	private Post post;

	public static PostView to(final Long postId, final String ip) {
		return PostView.builder()
			.id(0L)
			.postId(postId)
			.ip(ip)
			.viewAt(LocalDate.now())
			.build();
	}

	public static PostView from(final ResultSet rs, final Integer i) throws SQLException {
		return PostView.builder()
			.id(rs.getLong(i + 1))
			.postId(rs.getLong(i + 2))
			.ip(rs.getString(i + 3))
			.viewAt(rs.getDate(i + 4).toLocalDate())
			.build();
	}

}
