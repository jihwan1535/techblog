package com.blog.tech.domain.member.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.blog.tech.domain.common.BaseEntity;
import com.blog.tech.domain.member.entity.vo.MemberRole;
import com.blog.tech.domain.member.entity.vo.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
public class MemberInfo extends BaseEntity {

	private Long id;
	private Long memberId;
	private String nickname;
	private String image;
	private String aboutMe;
	private MemberRole role;
	private Integer postCount;
	private Integer commentCount;
	private Integer alarmCount;
	private MemberStatus status;

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

	public void postIncreasing() {
		this.postCount++;
	}

	public void commentIncreasing() {
		this.commentCount++;
	}
	public void commentDecreasing() {
		this.commentCount--;
	}

}
