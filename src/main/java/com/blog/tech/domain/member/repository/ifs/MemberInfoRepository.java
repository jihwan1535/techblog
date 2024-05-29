package com.blog.tech.domain.member.repository.ifs;

import java.sql.SQLException;
import java.util.Optional;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.member.entity.Member;
import com.blog.tech.domain.member.entity.MemberInfo;

public interface MemberInfoRepository extends CRUDIfs<MemberInfo> {

	Optional<MemberInfo> findByMemberId(final Long memberId) throws SQLException;

	Optional<MemberInfo> findByNickname(final String nickname) throws SQLException;
}
