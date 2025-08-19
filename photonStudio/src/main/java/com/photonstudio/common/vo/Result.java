package com.photonstudio.common.vo;

import com.photonstudio.common.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统一返回类封装
 *
 * @author bingo
 */
@Data
public class Result<T> {
    @ApiModelProperty("状态码")
    private Integer status;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("错误详细消息")
    private String errorMsg;

    @ApiModelProperty("数据")
    private T data;

    public static <T> Result<T> build(Integer status, String msg, T data) {
        return new Result<>(status, msg, data);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(Status.SUCCESS.code, msg, data);
    }

    public static <T> Result<T> ok() {
        return new Result<>(null);
    }

    public static <T> Result<T> build(Integer status, String msg) {
        return new Result<>(status, msg, null);
    }

    public static <T> Result<T> build(Integer status, String msg, String errorMsg) {
        return new Result<>(status, msg, errorMsg, null);
    }


    public Result() {
    }

    public Result(T data) {
        this.status = 20000;
        this.msg = "OK";
        this.data = data;
    }

    public Result(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Result(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer status, String msg, String errorMsg, T data) {
        this.status = status;
        this.msg = msg;
        this.errorMsg = errorMsg;
        this.data = data;
    }
}
