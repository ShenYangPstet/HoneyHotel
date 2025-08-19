package com.photonstudio.pojo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/** canvas编辑 */
@Data
public class Canvas {

  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.AUTO)
  private Integer id;
  /** 项目id */
  private Integer appId;
  /** 页面id */
  private Integer picId;
  /** 样式数据 */
  @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
  private String canvasStyle;
  /** 组件数据 */
  @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
  private String canvasData;
  /** canvas序列化 */
  @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
  private String canvasSerialize;
}
