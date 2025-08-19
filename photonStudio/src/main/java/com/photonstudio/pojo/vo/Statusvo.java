package com.photonstudio.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel("设备在离线参数类")
public class Statusvo {

    @ApiModelProperty("设备类型数组参数")
    @NotEmpty(message = "设备类型不能为空")
    List<Integer> integerList;
}
