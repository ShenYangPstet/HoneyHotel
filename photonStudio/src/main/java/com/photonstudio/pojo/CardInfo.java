package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author www.itgongju.com
 * @description card_info
 * @date 2023-05-08
 */
@Data
@Accessors(chain = true)
@ApiModel("门禁卡信息")
public class CardInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @TableId(type = IdType.AUTO)
  private Integer id;

  /**
   * 刷卡时间
   */
  @ApiModelProperty(value = "刷卡时间")
  private String passTime;

  /**
   * 卡号
   */
  @ApiModelProperty(value = "卡号")
  private String cardNumber;

  /**
   * 用户全称
   */
  @ApiModelProperty(value = "用户全称")
  private String fullName;

  /**
   * 地址
   */
  @ApiModelProperty(value = "地址")
  private String rp;


}
