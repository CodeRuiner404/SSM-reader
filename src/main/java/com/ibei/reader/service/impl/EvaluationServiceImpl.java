package com.ibei.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibei.reader.entity.Evaluation;
import com.ibei.reader.mapper.BookMapper;
import com.ibei.reader.mapper.EvaluationMapper;
import com.ibei.reader.mapper.MemberMapper;
import com.ibei.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private MemberMapper memberMapper;
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        QueryWrapper<Evaluation> qw = new QueryWrapper<>();
        qw.eq("book_id",bookId);
        qw.eq("state","enable");
        qw.orderByDesc("create_time");
        List<Evaluation> list = evaluationMapper.selectList(qw);
        for (Evaluation e:list
             ) {
            e.setMember(memberMapper.selectById(e.getMemberId()));
            e.setBook(bookMapper.selectById(e.getBookId()));
        }
        return list;
    }
}
