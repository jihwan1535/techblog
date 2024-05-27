package com.blog.tech.global.utility.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.member.entity.MemberInfo;
import com.blog.tech.domain.member.entity.vo.MemberRole;
import com.blog.tech.domain.member.entity.vo.MemberStatus;

public class MemberInfoMapper {

	public static MemberInfo from(final ResultSet rs) throws SQLException {
		return MemberInfo.builder()
			.id(rs.getLong("id"))
			.memberId(rs.getLong("member_id"))
			.nickname(rs.getString("nickname"))
			.image(rs.getString("image"))
			.aboutMe(rs.getString("about_me"))
			.role(MemberRole.valueOf(rs.getString("role")))
			.postCount(rs.getInt("post_count"))
			.commentCount(rs.getInt("comment_count"))
			.alarmCount(rs.getInt("alarm_count"))
			.status(MemberStatus.valueOf(rs.getString("status")))
			.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
			.updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
			.build();
	}

}
