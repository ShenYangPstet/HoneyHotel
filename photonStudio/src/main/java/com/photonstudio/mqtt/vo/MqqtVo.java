package com.photonstudio.mqtt.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class MqqtVo {

    @ApiModelProperty("订阅的主题")
    public   String topic ;

    @ApiModelProperty("发送的内容")
    public   String payload ;

}
