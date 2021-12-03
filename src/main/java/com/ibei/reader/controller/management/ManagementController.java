package com.ibei.reader.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台管理功能控制器
 */
@Controller
@RequestMapping("/management")
public class ManagementController {

    @RequestMapping("/index.html")
    public ModelAndView showIndex(){
        return new ModelAndView("/management/index");
    }

}
