package com.photonstudio.pojo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 沈景杨
 */
@Data
@Accessors(chain = true)
@ApiModel("门禁卡信息参数")
public class CardInfoReq {

  @ApiModelProperty(value = "开始刷卡时间")
  private String startPassTime;
  @ApiModelProperty(value = "结束刷卡时间")
  private String endPassTime;
  @ApiModelProperty(value = "页码")
  private Integer pageNum;
  @ApiModelProperty(value = "每页条数")
  private Integer pageSize;
  @ApiModelProperty(value = "卡号")
  private String cardNumber;
  @ApiModelProperty(value = "姓名")
  private String fullName;
}
