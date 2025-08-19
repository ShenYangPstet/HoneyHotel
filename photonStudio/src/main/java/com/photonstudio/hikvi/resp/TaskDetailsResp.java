package com.photonstudio.hikvi.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  任务详情返回参数
 * @author 沈景杨
 */
@Data
@ApiModel("任务详情返回参数")
public class TaskDetailsResp {

  @ApiModelProperty("创建时间")
  private String createTime;
  @ApiModelProperty("当前处理人")
  private String currentPerson;
  @ApiModelProperty("任务结束时间")
  private String endTime;
  @ApiModelProperty("结束时间戳")
  private Long endTimeStamp;
  @ApiModelProperty("结束时间时区")
  private String endTimeZone;
  @ApiModelProperty("已执行巡查项数")
  private Integer execItemNum;
  @ApiModelProperty("执行中状态的关联检测点的巡检项数")
  private Integer execPointItemNum;
  @ApiModelProperty("执行方式 0-自动巡检 1-人工在线巡检 2-移动线下巡检")
  private Integer execType;
  @ApiModelProperty("叶子巡检项完成数")
  private Integer finishItemNum;
  @ApiModelProperty("已完成巡查对象数")
  private Integer finishObjNum;
  @ApiModelProperty("完成状态的关联检测点的巡检项数")
  private Integer finishPointItemNum;
  @ApiModelProperty("巡检点完成数")
  private Integer finishPointNum;
  private String finishTime;
  @ApiModelProperty("结束时间戳")
  private Long finishTimeStamp;
  @ApiModelProperty("finishTime")
  private String groupId;
  @ApiModelProperty("是否自动完成 0-否 1-是")
  private Integer isAutoFinish;
  @ApiModelProperty("是否抓图计划 0-否 1-是")
  private Integer isCapture;
  @ApiModelProperty("是否暂停 0-否 1-是")
  private Integer isSuspend;
  @ApiModelProperty("叶子巡检项数")
  private Integer patrolItemNum;
  @ApiModelProperty("巡查对象数")
  private Integer patrolObjNum;
  @ApiModelProperty("巡检点数")
  private Integer patrolPointNum;
  @ApiModelProperty("任务ID")
  private String patrolTaskId;
  @ApiModelProperty("任务名称")
  private String patrolTaskName;
  @ApiModelProperty("计划模板任务拆分类型 0自动 1远程 2现场")
  private Integer pattern;
  @ApiModelProperty("所属计划ID")
  private String planId;
  @ApiModelProperty("所属计划名称")
  private String planName;
  @ApiModelProperty("关联检测点的巡检项数")
  private Integer pointItemNum;
  @ApiModelProperty("问题数")
  private Integer problemNum;
  @ApiModelProperty("巡查模板ID")
  private String psId;
  @ApiModelProperty("模板名称")
  private String psName;
  @ApiModelProperty("所属区域ID")
  private String regionId;
  @ApiModelProperty("所属区域名称")
  private String regionName;
  @ApiModelProperty("区域路径 @分隔")
  private String regionPath;
  @ApiModelProperty("区域路径名称 @分隔")
  private String regionPathName;
  @ApiModelProperty("是否计分 0-否1-是")
  private Integer scoreType;
  @ApiModelProperty("任务开始时间")
  private String startTime;
  @ApiModelProperty("开始时间戳")
  private Long startTimeStamp;
  @ApiModelProperty("开始时间时区")
  private String startTimeZone;
  @ApiModelProperty("任务状态 0-未开始 1-执行中 3-已完成 4-暂停中 5-已取消")
  private Integer status;
  @ApiModelProperty("任务提交人")
  private String submitPersonIds;
  @ApiModelProperty("任务描述")
  private String taskDesc;
  @ApiModelProperty("任务类型 0-临时任务 1-计划任务")
  private Integer taskType;
  @ApiModelProperty("时间状态 0-未过期 1-已过期")
  private Integer timeStatus;
  @ApiModelProperty("更新时间")
  private String updateTime;
}
