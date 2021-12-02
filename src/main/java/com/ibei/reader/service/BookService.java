package com.ibei.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ibei.reader.entity.Book;

public interface BookService {
    /**
     * 分页查询图书
     * @param categoryId 分类页号
     * @param order 排序规则
     * @param page 页号
     * @param rows 每页记录数
     *
     * @return 分页对象
     */
    public IPage<Book> paging(Long categoryId,String order, Integer page,Integer rows);

    /**
     * 根据图书编号查找图书对象
     * @param bookId
     * @return Book
     */
    public Book selectById(Long bookId);
}
