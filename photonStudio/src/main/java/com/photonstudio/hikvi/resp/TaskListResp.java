package com.photonstudio.hikvi.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 沈景杨
 */
@Data
@ApiModel("任务列表返回数据")
public class TaskListResp {


  @ApiModelProperty("任务id")
  private String patrolTaskId;
  @ApiModelProperty("任务名称")
  private String patrolTaskName;
//  @ApiModelProperty("任务开始时间")
//  @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSS", timezone = "GMT+8")
//  private Date startTime;
  @ApiModelProperty("开始时间戳")
  private Long startTimeStamp;
  @ApiModelProperty("开始时间时区")
  private String startTimeZone;
//  @ApiModelProperty("结束时间")
//  @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSS", timezone = "GMT+8")
//  private Date endTime;
  @ApiModelProperty("结束时间戳")
  private Long endTimeStamp;
  @ApiModelProperty("结束时间时区")
  private String endTimeZone;
  @ApiModelProperty("任务状态 0-未开始 1-执行中 3-已完成 4-暂停中 5-已取消")
  private Integer status;
  @ApiModelProperty("时间状态 0-未过期 1-已过期")
  private Integer timeStatus;
  @ApiModelProperty("是否抓图计划 0-否 1-是")
  private Integer isCapture;
  @ApiModelProperty("所属计划ID")
  private String planId;
  @ApiModelProperty("所属计划名称")
  private String patrolPlanName;
  @ApiModelProperty("任务类型 0-普通任务 1-临时任务")
  private Integer taskType;
  @ApiModelProperty("所属区域ID")
  private String regionId;
  @ApiModelProperty("所属区域名称")
  private String regionName;
  private String regionPath;
  private String regionPathName;
  @ApiModelProperty("巡查模板ID")
  private String psId;
  @ApiModelProperty("模板名称")
  private String psName;
  @ApiModelProperty("执行方式 0-自动巡检 1-人工在线巡检 2-移动线下巡检")
  private Integer execType;
  @ApiModelProperty("是否计分 0-否1-是")
  private Integer scoreType;
  @ApiModelProperty("巡检对象数")
  private Integer patrolObjNum;
  @ApiModelProperty("问题数")
  private Integer problemNum;
//  @ApiModelProperty("创建时间")
// @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSS", timezone = "GMT+8")
//  private Date createTime;
//  @ApiModelProperty("更新时间")
//@JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSS", timezone = "GMT+8")
//  private Date updateTime;
  @ApiModelProperty("实际任务结束时间")
  private String finishTime;
  @ApiModelProperty("分组ID")
  private String groupId;
  @ApiModelProperty("任务执行人")
  private String currentPerson;
  @ApiModelProperty("任务执行人名称")
  private String currentPersonName;
  @ApiModelProperty("任务提交人")
  private Object submitPersonIds;
  @ApiModelProperty("任务提交人名称")
  private Integer scanCodeOn;
  @ApiModelProperty("是否强制扫码")
  private Integer isCmpelCode;
  @ApiModelProperty("打卡数据")
  private String punchNum;
  @ApiModelProperty("是否允许调整执行人")
  private Integer allowAdjustExecutor;
  @ApiModelProperty("计划模板任务拆分类型")
  private Integer pattern;
  @ApiModelProperty("任务备注")
  private String taskDesc;
  @ApiModelProperty("任务来源 0-计划 1-临时")
  private Integer isAutoFinish;
  @ApiModelProperty("是否自动完成 0-否 1-是")
  private String previousVersionData;
  @ApiModelProperty("任务开始时间iso时间字符串")
  private String startTimeString;
  @ApiModelProperty("任务截止时间iso时间字符串")
  private String endTimeString;
  @ApiModelProperty("任务完成时间iso时间字符串")
  private String finishTimeString;
  @ApiModelProperty("完成时间时间戳")
  private String finishTimeStamp;
  @ApiModelProperty("当前系统显示的时区")
  private String localDateTimeZone;
  @ApiModelProperty("任务生成时间iso时间字符串")
  private String createTimeString;
  @ApiModelProperty("任务生成时间时间戳")
  private Long createTimeStamp;
  @ApiModelProperty("计划历史名称")
  private String patrolPlanNameHis;
  @ApiModelProperty("任务是否被拆分 0 -未拆分 1-已拆分")
  private Integer taskClear;
  @ApiModelProperty("任务拆分数量")
  private Integer taskSplit;
  @ApiModelProperty("任务是否暂停 0 -否 1-是")
  private Integer isSuspend;


}
