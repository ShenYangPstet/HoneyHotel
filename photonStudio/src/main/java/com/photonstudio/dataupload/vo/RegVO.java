package com.photonstudio.dataupload.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel("设备变量信息")
@Accessors(chain = true)
public class RegVO {



  @ApiModelProperty("id")
  private Integer regId;
  @TableField("reg_Name")
  @ApiModelProperty("变量名称")
  private String regName; //变量名称
  @TableField("dr_Id")
  @ApiModelProperty("设备id")
  private Integer drId; //设备id
  @TableField("reg_Units")
  @ApiModelProperty("单位")
  private String regUnits; //单位
  @TableField("reg_ReadWrite")
  @ApiModelProperty("读写属性 1只读 2读写")
  private String regReadWrite; //读写属性 1只读 2读写
  @TableField("tag_name")
  @ApiModelProperty("寄存器名称")
  private String tagName;
  @TableField("tag_value")
  @ApiModelProperty("初始值")
  private String tagValue; //初始值
  @TableField(exist = false)
  @ApiModelProperty("值说明")
  private String subname; //值说明
  @TableField(exist = false)
  @ApiModelProperty("实时值")
  private String newtagvalue;//实时值值

  @TableField("reg_sub")
  private String regSub;

  @TableField(exist = false)
  @ApiModelProperty("设备类型id")
  private Integer drtypeid;
}
