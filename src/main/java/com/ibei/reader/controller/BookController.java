package com.ibei.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ibei.reader.entity.Book;
import com.ibei.reader.entity.Category;
import com.ibei.reader.entity.Evaluation;
import com.ibei.reader.service.BookService;
import com.ibei.reader.service.CategoryService;
import com.ibei.reader.service.EvaluationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;

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
    public ModelAndView showDetail(@PathVariable("id") Long id){
        Book book = bookService.selectById(id);
        ModelAndView mav = new ModelAndView("/detail");
        mav.addObject("book",book);
        List<Evaluation> list = evaluationService.selectByBookId(id);
        mav.addObject("evaluationList",list);
        return mav;
    }
}
