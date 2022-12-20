package com.fly.config.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码匹配失败异常类
 */
public class CaptchaNotMatchException extends AuthenticationException {
    public CaptchaNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CaptchaNotMatchException(String msg) {
        super(msg);
    }
}
