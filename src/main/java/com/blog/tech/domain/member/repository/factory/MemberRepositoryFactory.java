package com.blog.tech.domain.member.repository.factory;

import com.blog.tech.domain.member.repository.dao.MemberDao;
import com.blog.tech.domain.member.repository.dao.MemberInfoDao;
import com.blog.tech.domain.member.repository.ifs.MemberInfoRepository;
import com.blog.tech.domain.member.repository.ifs.MemberRepository;

public class MemberRepositoryFactory {

    private static MemberRepository memberRepository;
    private static MemberInfoRepository memberInfoRepository;

    public static MemberRepository getMemberRepository() {
        if (memberRepository == null) {
            synchronized (MemberDao.class) {
                if (memberRepository == null) {
                    memberRepository = new MemberDao();
                }
            }
        }
        return memberRepository;
    }

    public static MemberInfoRepository getMemberInfoRepository() {
        if (memberInfoRepository == null) {
            synchronized (MemberInfoRepository.class) {
                if (memberInfoRepository == null) {
                    memberInfoRepository = new MemberInfoDao();
                }
            }
        }
        return memberInfoRepository;
    }

}
