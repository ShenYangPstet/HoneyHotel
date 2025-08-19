package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 看板模板
 *
 * @author bingo
 */
@Data
public class BoardTemplate  implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 名称 */
  @TableField(condition = SqlCondition.LIKE)
  private String name;
  /** 编码 */
  private String code;
  /** 使用区域 */
  private String area;
  /** 描述 */
  private Integer minHeight;
  /** 最小宽度 */
  private Integer minWidth;
  /** 最大高度 */
  private Integer maxHeight;
  /** 最大宽度 */
  private Integer maxWidth;
  /** 是否可拖拽 */
  private Boolean isDraggable;
  /** 是否可调整大小 */
  private Boolean isResizable;
  /** 是否纯静态 */
  private Boolean isStatic;
  /** 图标 */
  private String icon;
  @TableField(condition = SqlCondition.LIKE)
  private String description;
}
