package com.photonstudio.common.enums;

/**
 * 状态码枚举对象
 */
public enum Status {

    // 公共
    SUCCESS(20000, "成功"),
    LOGIN_SUCCESS(20000, "登入成功"),
    LOGOUT_SUCCESS(20000, "登出成功"),
    CHANGE_SUCCESS(20000, "修改成功"),

    UNKNOWN_ERROR(9998,"网络异常，请重试"),
    SYSTEM_ERROR(9999, "系统异常"),


    INSUFFICIENT_PERMISSION(4003, "权限不足"),

    WARN(9000, "失败"),
    RECORD_NONEXIST(9001, "记录不存在"),
    API_ERROR(1001, "接口错误"),
    REQUEST_PARAMETER_ERROR(1002, "请求参数错误"),

    // 登录
    LOGIN_EXPIRE(2001, "未登录或者登录失效"),
    LOGIN_CODE_ERROR(2002, "登录验证码错误"),
    LOGIN_ERROR(2003, "用户名不存在或密码错误"),
    LOGIN_USER_STATUS_ERROR(2004, "用户状态不正确"),
    LOGOUT_ERROR(2005, "退出失败，token不存在"),
    LOGIN_USER_NOT_EXIST(2006, "该用户不存在"),
    LOGIN_USER_EXIST(2007, "该用户已存在"),
    LOGIN_USER_TIMEOUT(2008,"用户时间已到期"),

    //绑定
    USERID_EXIST(2009, "此用户名已被绑定，请先解除该用户名绑定");


    public int code;
    public String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
