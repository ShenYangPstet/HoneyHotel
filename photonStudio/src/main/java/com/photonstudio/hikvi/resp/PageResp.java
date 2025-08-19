package com.photonstudio.hikvi.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("分页查询返回数据")
public class PageResp<T> {
  @ApiModelProperty("记录总数")
  private Integer total;

  @ApiModelProperty("当前页码")
  private Integer pageNo;

  @ApiModelProperty("分页大小")
  private Integer pageSize;

  @ApiModelProperty("资源数据列表")
  private List<T> list;
}
