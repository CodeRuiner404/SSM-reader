package com.ibei.reader.service.impl;

import com.ibei.reader.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MemberServiceImplTest {

    @Resource
    private MemberService memberService;
    @Test
    public void createMember() {
        memberService.createMember("刘秀","123456","yangwei");
    }

}