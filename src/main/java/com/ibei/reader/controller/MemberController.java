package com.ibei.reader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @GetMapping("/register.html")
    public ModelAndView showRegister(){
        return new ModelAndView("/register");
    }

    @PostMapping("/register")
    @ResponseBody
    public Map register(String vc, String username, String password, String nickname, HttpServletRequest request){
        //获取保存在session中的正确验证码
        String verifyCode =(String)request.getSession().getAttribute("kaptchaVerfyCode");
        //对比正确验证码和用户输入的验证码
        Map result = new HashMap();
        if(vc == null||verifyCode == null||!vc.equals(verifyCode)){
            result.put("code","VC01");
            result.put("msg","验证码错误");
        }else{
            result.put("code","0");
            result.put("msg","success");
        }
        return result;
    }
}
