package com.blog.tech.domain.member.repository.ifs;

import java.sql.SQLException;
import java.util.Optional;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.member.entity.Member;
public interface MemberRepository extends CRUDIfs<Member> {
	Optional<Member> findByEmail(final String email) throws SQLException;
}
