package com.ibei.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ibei.reader.entity.Book;
import com.ibei.reader.entity.Evaluation;
import com.ibei.reader.entity.MemberReadState;
import com.ibei.reader.mapper.BookMapper;
import com.ibei.reader.mapper.EvaluationMapper;
import com.ibei.reader.mapper.MemberReadStateMapper;
import com.ibei.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    @Override
    public IPage<Book> paging(Long categoryId,String order,Integer page, Integer rows) {
        Page<Book> p = new Page<>(page,rows);
        QueryWrapper<Book> qw = new QueryWrapper<>();
        if(categoryId != null && categoryId != -1){
            qw.eq("category_id",categoryId);//查找某一类的图书
        }
        if(order != null){
            if(order.equals("quantity"))qw.orderByDesc("evaluation_quantity");//按评价人数降序排列
            else if(order.equals("score"))qw.orderByDesc("evaluation_score");//按评价分数降序排列
        }
        IPage<Book> pageObject = bookMapper.selectPage(p,qw);
        return pageObject;
    }

    @Override
    public Book selectById(Long bookId) {
        return bookMapper.selectById(bookId);
    }

    @Override
    @Transactional
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    @Override
    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        QueryWrapper<MemberReadState> mqw = new QueryWrapper<>();
        mqw.eq("book_id",bookId);
        memberReadStateMapper.deleteById(mqw);
        QueryWrapper<Evaluation> eqw = new QueryWrapper<>();
        eqw.eq("book_id",bookId);
        evaluationMapper.delete(eqw);
    }
}
