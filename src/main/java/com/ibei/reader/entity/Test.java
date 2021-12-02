package com.ibei.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test")
//类与表名对应
public class Test {
    @TableId(type= IdType.AUTO)//主键，自动生成
    @TableField("id")//属性与表的字段对应
    private Integer id;
    @TableField("content")//如果属性与表的字段名相同或符合驼峰命名转化规则，则可省略@TableField
    private String content;

    public Test() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
