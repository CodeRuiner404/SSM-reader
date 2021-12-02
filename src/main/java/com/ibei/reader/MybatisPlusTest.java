package com.ibei.reader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibei.reader.entity.Test;
import com.ibei.reader.mapper.TestMapper;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MybatisPlusTest {
    @Resource
    private TestMapper testMapper;

    @org.junit.Test
    public void testInsert() {
        Test test = new Test();
        test.setContent("nm");
        //这里调用的是testMapper的父类BaseMapper中的insert方法
        testMapper.insert(test);
    }

    @org.junit.Test
    public void testUpdate() {
        Test test =testMapper.selectById(97);
        test.setContent("nmsl");
        testMapper.updateById(test);
    }

    @org.junit.Test
    public void testDelete() {
        Test test =testMapper.selectById(97);
        testMapper.deleteById(test);
    }

    @org.junit.Test
    public void testSelect(){
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        //查询id等于98的test数据
        queryWrapper.eq("id",98);
        //查询id大于500的test数据
        queryWrapper.gt("id",500);
        List<Test> list = testMapper.selectList(queryWrapper);//此时会查询到同时符合id=98和id大于500两个条件的数据
        System.out.println(list.get(0).getContent());
    }

}
