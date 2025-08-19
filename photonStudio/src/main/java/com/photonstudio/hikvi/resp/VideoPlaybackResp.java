package com.photonstudio.hikvi.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @author 沈景杨
 */
@Data
@ApiModel("获取监控点回放取流URLv2 返回类")
public class VideoPlaybackResp {

  @ApiModelProperty("分页标记 标记本次查询的全部标识符，用于查询分片时的多次查询")
  private String uuid;

  @ApiModelProperty("取流短url，注：rtsp的回放url后面要指定?playBackMode=1 在vlc上才能播放")
  private String url;

  @ApiModelProperty("录像片段信息")
  private List<ListBean> list;

  @Data
  @ApiModel("录像片段信息")
  public static class ListBean {
    @ApiModelProperty("查询录像的锁定类型")
    private Integer lockType;

    @ApiModelProperty("开始时间")
    private String beginTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("录像片段大小 （单位：Byte）")
    private Integer size;
  }
}
