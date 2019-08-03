package org.fisco.bcos.model;

/**
 * 返回统一的格式结果
 * @param <T>
 */
public class CommonResult<T> {
    private String message;
    private T data;

    public CommonResult(String message) {
        this.message = message;
    }

    public CommonResult(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
