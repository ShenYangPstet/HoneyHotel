package com.photonstudio.hikvi.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 海康威视接口返回结果
 *
 * @author 沈景杨
 */
@Data
@ApiModel("海康威视接口返回结果")
public class HiKviResult<T> {
  @ApiModelProperty("状态码")
  private String code;

  @ApiModelProperty("状态描述")
  private String msg;

  @ApiModelProperty("数据")
  private T data;

  /** 是否成功 */
  public Boolean isSuccess() {
    return "0".equals(code);
  }
}
