package com.ibei.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibei.reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    public void insertSample();
}
