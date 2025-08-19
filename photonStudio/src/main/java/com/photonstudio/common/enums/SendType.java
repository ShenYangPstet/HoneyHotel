package com.photonstudio.common.enums;

/**
 * 控制下发类型
 */
public enum SendType {
    /**
     * CONSOLE 控制台下发  SINGLE 单个下发
     */
    CONSOLE("CONSOLE"),SINGLE("SINGLE");
    public String sendType;
    SendType(String sendType){
        this.sendType=sendType;
    }
}
