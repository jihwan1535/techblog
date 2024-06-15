package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.entity.vo.MemberRole;
import com.blog.tech.domain.member.entity.vo.MemberStatus;
import com.blog.tech.domain.post.entity.ConnectHashtag;

public class ConnectHashTagMapper {

	public static ConnectHashtag from(final ResultSet rs, final int i) throws SQLException {
		return ConnectHashtag.builder()
			.id(rs.getLong(i + 1))
			.hashtagId(rs.getLong(i + 2))
			.postId(rs.getLong(i + 3))
			.build();
	}

}
