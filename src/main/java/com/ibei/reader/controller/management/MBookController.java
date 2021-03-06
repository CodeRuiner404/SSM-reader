package com.ibei.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ibei.reader.entity.Book;
import com.ibei.reader.service.BookService;
import com.ibei.reader.service.exception.BussinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")
public class MBookController {
    @Resource
    private BookService bookService;

    @GetMapping("/index.html")
    public ModelAndView showBook(){
        return new ModelAndView("/management/book");
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("img")MultipartFile file, HttpServletRequest request) throws IOException {
        //上传目录
        String uploadPath = request.getServletContext().getResource("/").getPath()+"/upload/";
        //文件名
        String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //拓展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //保存文件到指定的目录
        file.transferTo(new File(uploadPath+filename+suffix));
        Map result = new HashMap();
        result.put("errno",0);
        result.put("data",new String[]{"/upload/"+filename+suffix});
        return result;
    }
    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book){
        Map map = new HashMap();
        try {
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0f);
            Document doc = Jsoup.parse(book.getDescription());//解析图书详情
            Element img =doc.select("img").first();//获取图书详情第一图的元素对象
            String cover =img.attr("src");
            book.setCover(cover);// 把第一幅图片设置为封面
            bookService.createBook(book);
            map.put("code","0");
            map.put("msg","success");
        }catch (BussinessException ex){
            ex.printStackTrace();
            map.put("code",ex.getCode());
            map.put("msg",ex.getMsg());
        }
        return map;
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page,Integer limit){
        page = page==null?1:page;
        limit= limit==null?10:limit;
        IPage<Book> pageObject = bookService.paging(null,null,page,limit);
        Map result = new HashMap();
        result.put("code","0");
        result.put("msg","success");
        result.put("data",pageObject.getRecords());//当前页面数据
        result.put("count",pageObject.getTotal());//未分页时的记录总数
        return result;
    }

    @GetMapping("id/{id}")
    @ResponseBody
    public Map selectById(@PathVariable("id")Long bookId){
        Book book = bookService.selectById(bookId);
        Map result = new HashMap();
        result.put("code","0");
        result.put("msg","success");
        result.put("data",book);
        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book)
    {
        Map result = new HashMap();
        try {
            Book rawbook = bookService.selectById(book.getBookId());
            rawbook.setBookName(book.getBookName());
            rawbook.setSubTitle(book.getSubTitle());
            rawbook.setAuthor(book.getAuthor());
            rawbook.setCategoryId(book.getCategoryId());
            rawbook.setDescription(book.getDescription());
            Document doc = Jsoup.parse(book.getDescription());
            String cover = doc.select("img").first().attr("src");
            rawbook.setCover(cover);
            bookService.updateBook(rawbook);
            result.put("code","0");
            result.put("msg","success");
        }catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map deleteBook(@PathVariable("id") Long bookId)
    {
        Map result = new HashMap();
        try {
            bookService.deleteBook(bookId);
            result.put("code","0");
            result.put("msg","success");
        }
        catch (BussinessException ex) {
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }


}
