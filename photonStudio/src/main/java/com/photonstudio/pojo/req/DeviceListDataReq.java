package com.photonstudio.pojo.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@ApiModel("设备列表参数")
public class DeviceListDataReq {

    @ApiModelProperty("设备类型id")
    @NotNull(message = "required")
    private Integer drtypeid;
}
