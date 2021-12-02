package com.ibei.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Resource
    private Producer kaptchaProducer;

    @GetMapping("verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //响应立即过期
        response.setDateHeader("Expires",0);
        //不缓存任何图片数据(不存储，不缓存，必须重新校验)
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control","post-check=0,pre-check=0");//服务于IE5
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/png");
        //生成验证码字符文本
        String verifyCode = kaptchaProducer.createText();//生成验证码的字符
        request.getSession().setAttribute("kaptchaVerfyCode",verifyCode);//把字符存入session
        BufferedImage image = kaptchaProducer.createImage(verifyCode);//生成带字符的图片
        ServletOutputStream out = response.getOutputStream();//创建输出流
        ImageIO.write(image,"png",out);//输出图片
        out.flush();//立即输出
        out.close();//关闭输出流
    }
}
