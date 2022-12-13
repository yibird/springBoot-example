package com.fly.common.model;

import com.fly.enums.http.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class R<T> {
    private T data;
    private int code;
    private String message;

    public static final int SUCCESS_CODE = ResponseStatus.SUCCESS.getCode();

    private static final int ERROR_CODE = ResponseStatus.ERROR.getCode();

    public static <T> R ok() {
        return R.ok(null);
    }

    public static <T> R ok(T data) {
        return R.ok(data, SUCCESS_CODE, ResponseStatus.getMessageByCode(SUCCESS_CODE));
    }

    public static <T> R ok(int code) {
        return R.ok(null, code, ResponseStatus.getMessageByCode(code));
    }

    public static <T> R ok(String message) {
        return R.ok(null, SUCCESS_CODE, message);
    }

    public static <T> R ok(T data, int code) {
        return R.ok(data, code, ResponseStatus.getMessageByCode(code));
    }

    public static <T> R ok(T data, ResponseStatus status) {
        return R.ok(data, status.getCode(), status.getMessage());
    }

    public static <T> R ok(T data, String message) {
        return R.ok(data, SUCCESS_CODE, ResponseStatus.getMessageByCode(SUCCESS_CODE));
    }

    public static <T> R ok(int code, String message) {
        return R.ok(null, SUCCESS_CODE, ResponseStatus.getMessageByCode(SUCCESS_CODE));
    }

    public static <T> R ok(T data, int code, String message) {
        return R.builder().data(data).code(code).message(message).build();
    }


    public static <T> R<T> error() {
        return R.error(null);
    }

    public static <T> R error(T data) {
        return R.error(data, ERROR_CODE, ResponseStatus.getMessageByCode(ERROR_CODE));
    }

    public static <T> R error(int code) {
        return R.error(null, code, ResponseStatus.getMessageByCode(code));
    }

    public static <T> R error(ResponseStatus status) {
        return R.error(null, status.getCode(), status.getMessage());
    }

    public static <T> R error(T data, int code) {
        return R.error(data, code, ResponseStatus.getMessageByCode(code));
    }

    public static <T> R error(T data, ResponseStatus status) {
        return R.error(data, status.getCode(), status.getMessage());
    }

    public static <T> R error(T data, String message) {
        return R.error(data, ERROR_CODE, ResponseStatus.getMessageByCode(ERROR_CODE));
    }

    public static <T> R error(int code, String message) {
        return R.ok(null, ERROR_CODE, ResponseStatus.getMessageByCode(ERROR_CODE));
    }

    public static <T> R error(T data, int code, String message) {
        return R.builder().data(data).code(code).message(message).build();
    }
}
