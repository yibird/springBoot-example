//package com.fly.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 验证码过滤器,认证时用于验证验证码输出是否正确
// */
//public class CaptchaFilter extends UsernamePasswordAuthenticationFilter {
//
//    private static final String FROM_CAPTCHA_KEY = "captcha";
//
//    private String captchaParameter = FROM_CAPTCHA_KEY;
//
//    public void setCaptchaParameter(String captchaParameter) {
//        this.captchaParameter = captchaParameter;
//    }
//
//    public String getCaptchaParameter() {
//        return captchaParameter;
//    }
//
//    @Override
//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        if (!request.getMethod().toUpperCase().equals("POST")) {
//            throw new AuthenticationServiceException("请求方法错误");
//        }
//        // 从请求中获取输入的验证码
//        String captcha = request.getParameter(captchaParameter);
//        // 从session获取生成的验证码,与输入的验证码进行比较
//        Object sessionCaptcha = request.getSession().getAttribute("captcha");
//        // 如果输入验证码和session中的验证码不为空且相等,则表示认证成功
//        if (!ObjectUtils.isEmpty(captcha) && ObjectUtils.isEmpty(sessionCaptcha)
//                && captcha.equals(sessionCaptcha)) {
//            return super.attemptAuthentication(request, response);
//        }
//        throw new CaptchaNotMatchException("验证码输入错误");
//    }
//}
