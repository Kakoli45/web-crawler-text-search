package com.web.crawler.response;

import java.io.Serializable;

import com.web.crawler.enums.APIAction;


public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String action = APIAction.SUCCESS.action();

    private T data;

    private ErrorResponse error;

    private Response(String action, T data) {
        this.action = action;
        this.data = data;
    }

    private Response(T data) {
        this.data = data;
    }

    private Response(ErrorResponse error) {
        this.action = APIAction.FAILURE.action();
        this.error = error;
    }

    public String getAction() {
        return action;
    }

    public T getData() {
        return data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public static <P> Response<P> response(P data) {
        return new Response<>(data);
    }

    public static <P> Response<P> response(String action, P data) {
        return new Response<>(action, data);
    }

    public static <P> Response<P> error(ErrorResponse error) {
        return new Response<>(error);
    }

    @Override
    public String toString() {
        return "Response [action=" + action + ", data=" + data + "]";
    }
}
