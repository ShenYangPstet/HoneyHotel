package com.photonstudio.common.vo;

public class SysResult {
	// 响应业务状态
    /*
     * 20000	成功
     * 50008	token错误
     * 50009    错误
     * 400	参数错误
     */
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static SysResult build(Integer status, String msg, Object data) {
        return new SysResult(status, msg, data);
    }

    public static SysResult oK(Object data) {
        return new SysResult(data);
    }

    public static SysResult oK() {
        return new SysResult(null);
    }

    public SysResult() {

    }

    public static SysResult build(Integer status, String msg) {
        return new SysResult(status, msg, null);
    }

    public SysResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public SysResult(Object data) {
        this.status = 20000;
        this.msg = "OK";
        this.data = data;
    }

    
    public Boolean isOk() {
        return this.status == 20000;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
