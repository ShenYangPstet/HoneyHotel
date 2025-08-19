package com.photonstudio.hikvi.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 巡更请求参数
 *
 * @author guohaoxing
 */
@Data
@Accessors(chain = true)
@ApiModel("巡更请求参数")
public class PatrolReq {

  @ApiModelProperty(value = "页码", required = true)
  @NotNull(message = "页码不能为空")
  private Integer pageNo;

  @ApiModelProperty(value = "条数", required = true)
  @NotNull(message = "条数不能为空")
  private Integer pageSize;

  @ApiModelProperty(
      value = "巡更开始时间(ISO时间格式：2018-03-04T12:00:00.000+09:00,开始时间和结束时间不能超过90天)",
      required = true)
  @NotBlank(message = "开始时间不能为空")
  private String beginTime;
  @ApiModelProperty(
      value = "巡更结束时间(ISO时间格式：2018-03-04T12:00:00.000+09:00,开始时间和结束时间不能超过90天)",
      required = true)
  @NotBlank(message = "结束时间不能为空")
  private String endTime;

  @ApiModelProperty("巡更员ID列表（列表长度不能大于1000）")
  private List<String> personIdList;

  @ApiModelProperty("路线ID列表（列表长度不能大于1000）")
  private List<String> routeIdList;

  @ApiModelProperty("巡更结果列表(0-准时,1-早巡,2-晚巡,3-补漏巡,4-漏巡，列表为空表示查询所有状态数据)")
  private List<Integer> resultTypeList;

  @ApiModelProperty("巡更计划ID列表（列表长度不能大于1000）")
  private List<String> planIdList;
}
