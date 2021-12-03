package com.ibei.reader.service;

import com.ibei.reader.entity.Evaluation;
import com.ibei.reader.entity.Member;
import com.ibei.reader.entity.MemberReadState;

public interface MemberService {
    /**
     * 会员注册 创建新会员
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    public Member createMember(String username,String password,String nickname);

    /**
     * 登录检查
     * @param username 用户名
     * @param password 密码
     * @return 登录对象
     */
    public Member checkLogin(String username,String password);

    /**
     * 会员阅读状态
     * @param memberId 会员Id
     * @param bookId    书id
     * @return 会员阅读状态
     */
    public MemberReadState selectMemberReadState(Long memberId,Long  bookId);

    /**
     *
     * @param memberId
     * @param bookId
     * @param readState  状态编号
     * @return 阅读状态
     */
    public  MemberReadState updateMemberReadState(Long memberId,Long bookId,Integer readState);

    /**
     *  发布新的短评
     * @param memberId 会员编号
     * @param bookId 图书编号
     * @param score  评分
     * @param content 短评内容
     * @return 短评对象
     */
    public Evaluation evaluate(Long memberId,Long bookId,Integer score,String content);

    /**
     *  短评点赞
     * @param evaluationId 短评编写
     * @return  短评对象
     */
    public Evaluation enjoy(Long evaluationId);

}
