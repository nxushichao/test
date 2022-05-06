package com.gkdz.server.common.utils;

import com.gkdz.server.common.enums.HttpResponseMessage;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回数据
 *
 * @author Mark
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T data;

    public R() {
        this.code = HttpResponseMessage.Success.getKey();
        this.message = HttpResponseMessage.Success.getValue();
        this.data = null;
    }

    public R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public R(T data) {
        this.code = HttpResponseMessage.Success.getKey();
        this.message = HttpResponseMessage.Success.getValue();
        this.data = data;
    }

    public static R ok() {
        return new R<>(HttpResponseMessage.Success.getKey(), HttpResponseMessage.Success.getValue(), null);
    }

    public static R error() {
        return new R<>(HttpResponseMessage.Server_error.getKey(), HttpResponseMessage.Server_error.getValue(), null);
    }

    public static R error(String message) {
        return new R<>(HttpResponseMessage.Server_error.getKey(), message, null);
    }

    public static R error(int code, String message) {
        return new R<>(code, message, null);
    }

    public static R error(HttpResponseMessage httpResponseMessage) {
        return new R<>(httpResponseMessage.getKey(), httpResponseMessage.getValue(), null);
    }

    public static R ok(String message) {
        return new R<>(HttpResponseMessage.Success.getKey(), message, null);
    }

    public R put(T data) {
        this.data = data;
        return this;
    }

}
