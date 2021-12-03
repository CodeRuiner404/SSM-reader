package com.ibei.reader.controller;

import com.ibei.reader.entity.Member;
import com.ibei.reader.service.MemberService;
import com.ibei.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @Resource
    private MemberService memberService;
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
            try {
                memberService.createMember(username,password,nickname);
                result.put("code","0");
                result.put("msg","success");
            }catch (BussinessException ex){
                ex.printStackTrace();
                result.put("code",ex.getCode());
                result.put("msg",ex.getMsg());
            }
        }
        return result;
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin(){
        return new ModelAndView("/login");
    }

    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password,String vc, HttpSession session){
        //获取保存在session中的正确验证码
        String verifyCode =(String)session.getAttribute("kaptchaVerfyCode");
        //对比正确验证码和用户输入的验证码
        Map result = new HashMap();
        if(vc == null||verifyCode == null||!vc.equals(verifyCode)){
            result.put("code","VC01");
            result.put("msg","验证码错误");
        }else{
            try {
                Member member=memberService.checkLogin(username, password);
                session.setAttribute("loginMember",member);
                result.put("code","0");
                result.put("msg","success");
            }catch (BussinessException ex){
                ex.printStackTrace();
                result.put("code",ex.getCode());
                result.put("msg",ex.getMsg());
            }
        }
        return result;
    }

    @PostMapping("/update_read_state")
    @ResponseBody // 返回 json 序列化字符
    public  Map updateReadState(Long memberId,Long bookId,Integer readState) {
        Map result = new HashMap();
        try {
            memberService.updateMemberReadState(memberId, bookId, readState);
            result.put("code","0");
            result.put("msg","success");

        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code",ex.getCode());
            result.put("msg",ex.getMsg());
        }
        return result;
    }
}
