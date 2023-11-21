package com.lt.http;

import androidx.annotation.Nullable;

/**
 * 项目中特定异常使用该类进行标记
 * 一般用于 网络请求 可控异常抛出
 */
public final class HttpException extends RuntimeException {

    public int code;
    public String message;

    public HttpException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
