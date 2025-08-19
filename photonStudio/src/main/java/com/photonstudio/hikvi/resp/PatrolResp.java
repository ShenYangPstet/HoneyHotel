package com.photonstudio.hikvi.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 巡更返回数据
 *
 * @author guohaoxing
 */
@Data
@Accessors(chain = true)
@ApiModel("巡更返回数据")
public class PatrolResp {

  @ApiModelProperty("排班ID")
  private String scheduleId;

  @ApiModelProperty("路线ID")
  private String routeId;

  @ApiModelProperty("巡更路线名称")
  private String routeName;

  @ApiModelProperty("巡更计划ID")
  private String planId;

  @ApiModelProperty("巡更计划名称")
  private String planName;

  @ApiModelProperty("计划开始巡更时间(ISO时间格式：2018-03-04T12:00:00.000+09:00)")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date planBeginTimeISO;

  @ApiModelProperty("实际开始巡更时间(ISO时间格式：2018-03-04T12:00:00.000+09:00)")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date actualBeginTimeISO;

  @ApiModelProperty("计划巡更时长(分钟：1-1440的整数)")
  private Integer planDuration;

  @ApiModelProperty("实际巡更时长（分钟）")
  private Integer actualDuration;

  @ApiModelProperty("巡更员姓名")
  private String personName;

  @ApiModelProperty("巡更状态（0-结束,1-开始,2-未开始）")
  private Integer status;

  @ApiModelProperty("巡更路线结果（0-准时,1-早巡,2-晚巡,3-补漏巡,4-漏巡）")
  private Integer routeResult;
}
