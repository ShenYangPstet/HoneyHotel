package com.photonstudio.dataupload.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("设备类型")
public class DrtypeinfoVO {

  @ApiModelProperty(value = "设备类型id")
  private Integer drtypeid;
  @ApiModelProperty(value = "设备类型名称")
  private String drtypename;
}
