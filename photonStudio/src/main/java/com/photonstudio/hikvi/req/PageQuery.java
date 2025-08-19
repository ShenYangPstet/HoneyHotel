package com.photonstudio.hikvi.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("分页查询请求参数")
public class PageQuery {
  @ApiModelProperty("当前页码")
  @NotNull(message = "页码不能为空")
  private Integer pageNo;

  @ApiModelProperty("分页大小")
  @NotNull(message = "条数不能为空")
  private Integer pageSize;
}
