package com.photonstudio.dataupload.req;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegReq {

  @NotNull(message = "不能为空")
  @ApiModelProperty(value = "页码", required = true)
  private Integer pageNum;

  @NotNull(message = "不能为空")
  @ApiModelProperty(value = "每页条数", required = true)
  private Integer pageSize;

  @ApiModelProperty(value = "设备id")
  private Integer drId;

  @ApiModelProperty(value = "设备类型id")
  private Integer drtypeid;
}
