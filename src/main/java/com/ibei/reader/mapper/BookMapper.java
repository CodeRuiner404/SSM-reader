package com.ibei.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibei.reader.entity.Book;

public interface BookMapper extends BaseMapper<Book> {
    public void updateEvaluation();
}
