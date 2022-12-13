package com.fly.exception;

import com.fly.constant.ApiConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * 参数异常类
 */
@Getter
@Setter
public class ParameterException extends RuntimeException {
    // 错误码
    private Integer codeError;

    public ParameterException() {
        super(ApiConstant.PARAMETER_MESSAGE);
        this.codeError = ApiConstant.PARAMETER_ERROR;
    }

    public ParameterException(Integer code) {
        this.codeError = code;
    }

    public ParameterException(String message) {
        super(message);
        this.codeError = ApiConstant.PARAMETER_ERROR;
    }

    public ParameterException(Integer code, String message) {
        super(message);
        this.codeError = code;
    }
}
