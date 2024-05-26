package com.blog.tech.domain.member.repository.ifs;

import com.blog.tech.domain.common.CRUDIfs;
import com.blog.tech.domain.member.entity.Member;
public interface MemberRepository extends CRUDIfs<Member> {
	Member findByEmail(final String email);
}
