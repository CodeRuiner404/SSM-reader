package com.ibei.reader.service.impl;

import com.ibei.reader.entity.Member;
import com.ibei.reader.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Override
    public Member createMember(String username, String password, String nickname) {
        return null;
    }
}
