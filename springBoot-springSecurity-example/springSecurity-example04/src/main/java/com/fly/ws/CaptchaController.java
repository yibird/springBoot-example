package com.fly.ws;

import com.google.code.kaptcha.Producer;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码Controller
 */
@Controller
public class CaptchaController {

    private static final String BASE64_PREFIX = "data:image/jpg;base64,";
    @Autowired
    private Producer producer;

    @GetMapping("/login.html")
    public String login() {
        return "login";
    }


    /**
     * 生成验证码图片,通常用于传统web场景
     *
     * @param response http Servlet响应
     * @param session  http会话
     * @throws IOException
     */
    @GetMapping("/captcha.jpg")
    public void createCaptchaImage(HttpServletResponse response, HttpSession session) throws IOException {
        // 生成验证码
        String captcha = producer.createText();
        // 保存到Session中,用于用户认证时输入的验证码比较
        session.setAttribute("captcha", captcha);
        // 生成验证码图片
        BufferedImage bufferedImage = producer.createImage(captcha);
        response.setContentType("image/jpg");
        // 响应图片
        ServletOutputStream os = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", os);
    }

    /**
     * 生成验证码base64编码,通常用于前后端分离。
     *
     * @return 验证码图片base64编码
     */
    @GetMapping("/captcha")
    @ResponseBody
    public String createCaptchaBase64(HttpSession session) throws IOException {
        // 生成验证码
        String captcha = producer.createText();
        // 保存到Session中,用于用户认证时输入的验证码比较
        session.setAttribute("captcha", captcha);
        // 生成验证码图片
        BufferedImage bufferedImage = producer.createImage(captcha);
        /**
         * 创建FastByteArrayOutputStream,FastByteArrayOutputStream是
         * ByteArrayOutputStream(字节数组输出流)的快速替代方案
         */
        FastByteArrayOutputStream fos = new FastByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", fos);
        // 根据输出流的字节数组编码为base64字符串
        return BASE64_PREFIX + Base64.encodeBase64String(fos.toByteArray());
    }
}
