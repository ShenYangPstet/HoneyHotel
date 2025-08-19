package com.photonstudio.hikvi.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * @author 沈景杨
 */
@Data
@ApiModel("任务详情请求参数")
public class TaskDetailsReq {

//  @ApiModelProperty("任务id")
//  private String taskId;
  @ApiModelProperty("任务开始时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date startTime;
  @ApiModelProperty("任务结束时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date endTime;
}
