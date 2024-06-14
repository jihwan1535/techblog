package com.blog.tech.domain.member.entity;

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
