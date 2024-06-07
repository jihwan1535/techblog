package com.blog.tech.domain.member.entity;

import com.blog.tech.domain.common.BaseEntity;
import com.blog.tech.domain.member.entity.vo.MemberRole;
import com.blog.tech.domain.member.entity.vo.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
	private int postCount;
	private int commentCount;
	private int alarmCount;
	private MemberStatus status;

	public void writePost() {
		this.postCount++;
	}

}
