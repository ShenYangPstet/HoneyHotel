package com.photonstudio.dataupload.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("设备信息")
public class DeviceVO {

  @ApiModelProperty("设备id")
  private Integer drid;

  @TableField("drname")
  @ApiModelProperty("设备名称")
  private String drname;

  @TableField("drtypeid")
  @ApiModelProperty("设备类型id")
  private Integer drtypeid;

  @TableField("spid")
  @ApiModelProperty("视频id")
  private String spid;

  @TableField("dr_ManufactureStyle")
  @ApiModelProperty("设备型号")
  private String drManufactureStyle;

  @TableField("dr_ManufactureFactory")
  @ApiModelProperty("设备生产厂家")
  private String drManufactureFactory;

  @TableField("dr_InstallPhone")
  @ApiModelProperty("生产厂家电话")
  private String drInstallPhone;

  @TableField("MDcode")
  @ApiModelProperty("设备编号")
  private String mdcode;
}
