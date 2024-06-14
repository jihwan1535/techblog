package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.entity.vo.MemberRole;
import com.blog.tech.domain.member.entity.vo.MemberStatus;

public class MemberInfoMapper {

	public static MemberInfo from(final ResultSet rs, final int i) throws SQLException {
		return MemberInfo.builder()
			.id(rs.getLong(i + 1))
			.memberId(rs.getLong(i + 2))
			.nickname(rs.getString(i + 3))
			.image(rs.getString(i + 4))
			.aboutMe(rs.getString(i + 5))
			.role(MemberRole.valueOf(rs.getString(i + 6)))
			.postCount(rs.getInt(i + 7))
			.commentCount(rs.getInt(i + 8))
			.alarmCount(rs.getInt(i + 9))
			.status(MemberStatus.valueOf(rs.getString(i + 10)))
			.createdAt(rs.getTimestamp(i + 11).toLocalDateTime())
			.updatedAt(rs.getTimestamp(i + 12).toLocalDateTime())
			.build();
	}

}
