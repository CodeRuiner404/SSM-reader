package com.ibei.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibei.reader.entity.Evaluation;
import com.ibei.reader.entity.Member;
import com.ibei.reader.entity.MemberReadState;
import com.ibei.reader.mapper.MemberMapper;
import com.ibei.reader.mapper.MemberReadStateMapper;
import com.ibei.reader.service.MemberService;
import com.ibei.reader.service.exception.BussinessException;
import com.ibei.reader.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Override
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username",username);
        List<Member> list = memberMapper.selectList(queryWrapper);
        if(list.size()>0){
            System.out.println(list.size());
            throw new BussinessException("M01","用户已存在");
        }
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        int salt = new Random().nextInt(1000)+1000;
        String md5 = MD5Utils.md5Digest(password,(long)salt);
        member.setPassword(md5);
        member.setSalt((long)salt);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }

    @Override
    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Member member = memberMapper.selectOne(queryWrapper);
        if(member==null)throw  new BussinessException("M02","用户不存在");
        String md5 =MD5Utils.md5Digest(password,member.getSalt());
        if(!md5.equals(member.getPassword()))throw new BussinessException("M03","输入密码有误");
        return member;
    }

    @Override
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("book_id",bookId);
        MemberReadState mr = memberReadStateMapper.selectOne(queryWrapper);
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("book_id",bookId);
        MemberReadState mr = memberReadStateMapper.selectOne(queryWrapper);
        if(mr==null){
            mr = new MemberReadState();
            mr.setMemberId(memberId);
            mr.setBookId(bookId);
            mr.setReadState(readState);
            mr.setCreateTime(new Date());
            memberReadStateMapper.insert(mr);
        }else{
            mr.setReadState(readState);
            memberReadStateMapper.updateById(mr);
        }
        return mr;
    }

    @Override
    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content) {
        return null;
    }

    @Override
    public Evaluation enjoy(Long evaluationId) {
        return null;
    }

}
