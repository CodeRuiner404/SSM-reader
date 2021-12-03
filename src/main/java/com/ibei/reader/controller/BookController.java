package com.ibei.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ibei.reader.entity.*;
import com.ibei.reader.service.BookService;
import com.ibei.reader.service.CategoryService;
import com.ibei.reader.service.EvaluationService;
import com.ibei.reader.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private MemberService memberService;

    @GetMapping("/")
    public ModelAndView showIndex(){
        ModelAndView mav = new ModelAndView("/index");
        List<Category> list = categoryService.selectAll();
        mav.addObject("categoryList",list);
        return mav;
    }

    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBook(Long categoryId,String order, Integer p){
        if(p==null)p=1;
        IPage<Book> pageObject = bookService.paging(categoryId,order,p,10);
        return pageObject;
    }

    @GetMapping("/books/{id}")
    @ResponseBody
    public ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session){
        Book book = bookService.selectById(id);
        Member member=(Member) session.getAttribute("loginMember");
        ModelAndView mav = new ModelAndView("/detail");
        mav.addObject("book",book);
        List<Evaluation> list = evaluationService.selectByBookId(id);
        mav.addObject("evaluationList",list);
        //获取用户阅读状态
        if(member!=null){
            MemberReadState mr =memberService.selectMemberReadState(member.getMemberId(),id);
            mav.addObject("memberReadState",mr);
        }
        return mav;
    }
}
