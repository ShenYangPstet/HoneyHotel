package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 看板
 *
 * @author bingo
 */
@Data
@TableName(autoResultMap = true)
public class Board  implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 名称 */
  private String name;
  /** 描述 */
  private String description;
  /** 模板id */
  private Integer boardTemplateId;
  /** 颜色 */
  private String color;
  /** 高度 */
  private Integer height;
  /** 宽度 */
  private Integer width;
  /** 位置横坐标 */
  private Integer positionX;
  /** 位置纵坐标 */
  private Integer positionY;
  /** 自定义配置 */
  @TableField(typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> customConfig;
}
