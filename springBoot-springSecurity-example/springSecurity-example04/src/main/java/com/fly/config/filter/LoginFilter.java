package com.fly.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录过滤器,支持username、password和验证码认证
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String FROM_USERNAME_KEY = "username";
    private static final String FROM_PASSWORD_KEY = "password";
    private static final String FROM_CAPTCHA_KEY = "captcha";

    private String usernameParameter = FROM_USERNAME_KEY;
    private String passwordParameter = FROM_PASSWORD_KEY;
    private String captchaParameter = FROM_CAPTCHA_KEY;


    /**
     * attemptAuthentication()用于尝试身份验证,
     * attemptAuthentication()会将认证信息(用户名、密码等信息)封装成
     * UsernamePasswordAuthenticationToken对象,并将该对象传递给
     * AuthenticationManager接口的authenticate()进行认证。
     *
     * @param request  HttpServletRequest,用于从请求中提取参数并执行身份验证
     * @param response HttpServletResponse,如果实现必须执行
     * @return
     * @throws AuthenticationException 身份认证异常
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 非post请求将抛出异常
        if (!request.getMethod().toUpperCase().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            // 1.获取请求数据
            String username = request.getParameter(getUsernameParameter());
            String password = request.getParameter(getPasswordParameter());
            String captcha = request.getParameter(getCaptchaParameter());
            // 2.从session获取生成的验证码,与输入的验证码进行比较
            String sessionCaptcha = (String) request.getSession().getAttribute("captcha");
            // 如果输入验证码和session中的验证码不为空且相等,则表示认证成功
            if (!ObjectUtils.isEmpty(captcha) && ObjectUtils.isEmpty(sessionCaptcha)
                    && captcha.equalsIgnoreCase(sessionCaptcha)) {
                System.out.println("error");
                return super.attemptAuthentication(request, response);
            }
            /**
             * 3.认证用户名和密码。将用户名和密码封装成UsernamePasswordAuthenticationToken对象,
             * 并将该对象作为AuthenticationManager接口(身份验证管理器)的authenticate方法参数,
             * authenticate()会验证用户名和密码是否合法。
             */
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.attemptAuthentication(request, response);
    }


    @Override
    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    @Override
    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    public String getCaptchaParameter() {
        return captchaParameter;
    }

    public void setCaptchaParameter(String captchaParameter) {
        this.captchaParameter = captchaParameter;
    }
}
