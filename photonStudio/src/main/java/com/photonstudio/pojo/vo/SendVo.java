package com.photonstudio.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 驱动下发参数
 */
@ApiModel("下发命令参数")
@Data
public class SendVo<T> {
    @ApiModelProperty("下发命令类型")
    private String sendType;
    @ApiModelProperty("数据")
    private T data;
    public SendVo(String sendType, T data){
        this.sendType=sendType;
        this.data=data;
    }
}
