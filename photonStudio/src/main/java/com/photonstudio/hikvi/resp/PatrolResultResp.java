package com.photonstudio.hikvi.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 巡更返回结果类
 *
 * @author guohaoxing
 */
@Data
@Accessors(chain = true)
@ApiModel("巡更返回结果类")
public class PatrolResultResp<T> {

  @ApiModelProperty("页码")
  private Integer page;

  @ApiModelProperty("条数")
  private Integer pageSize;

  @ApiModelProperty("总数")
  private Integer total;

  @ApiModelProperty("总页数")
  private Integer totalPage;

  @ApiModelProperty("是否为第一页")
  private boolean firstPage;

  @ApiModelProperty("是否为最后一页")
  private boolean lastPage;

  @ApiModelProperty("是否有前一页")
  private boolean hasPreviousPage;

  @ApiModelProperty("是否有下一页")
  private boolean hasNextPage;

  @ApiModelProperty("操作结果")
  private boolean opResult;

  @ApiModelProperty("结果信息")
  private String opMsg;

  @ApiModelProperty("返回数据")
  private T rows;
}
