package com.photonstudio.hikvi.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 沈景杨
 */
@Data
public class VideoPlaybackReq {

  @ApiModelProperty("监控点唯一标识")
  @NotBlank(message = "不能为空")
  private String cameraIndexCode;

  @ApiModelProperty("0：中心存储 1：设备存储  默认为中心存储")
  private Integer recordLocation;

  @ApiModelProperty("取流协议（应用层协议)")
  @JsonIgnore
  private String protocol;

  @ApiModelProperty("传输协议")
  @JsonIgnore
  private Integer transmode;

  @ApiModelProperty("开始查询时间（IOS8601格式：yyyy-MM-dd’T’HH:mm:ss.SSSXXX）")
  @NotBlank(message = "不能为空")
  private String beginTime;

  @ApiModelProperty("结束查询时间，开始时间和结束时间相差不超过三天")
  @NotBlank(message = "不能为空")
  private String endTime;

  @ApiModelProperty("分页查询id，上一次查询返回的uuid，用于继续查询剩余片段，默认为空字符串。当存储类型为设备存储时，该字段生效，中心存储会一次性返回全部片段")
  private String uuid;

  @ApiModelProperty("扩展内容")
  @JsonIgnore
  private String expand;

  @ApiModelProperty("输出码流转封装格式 ")
  @JsonIgnore
  private String streamform;

  @ApiModelProperty("查询录像的锁定类型，0-查询全部录像；1-查询未锁定录像；2-查询已锁定录像，不传默认值为0。通过录像锁定与解锁接口来进行录像锁定与解锁。")
  private Integer lockType;
}
