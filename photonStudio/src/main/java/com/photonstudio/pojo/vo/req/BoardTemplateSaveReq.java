package com.photonstudio.pojo.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 看板模板保存参数
 *
 * @author bingo
 */
@Data
public class BoardTemplateSaveReq {
  /** id */
  private Integer id;
  /** 名称 */
  @NotBlank(message = "名称不能为空")
  private String name;
  /** 编码 */
  @NotBlank(message = "编码不能为空")
  private String code;
  /** 使用区域 */
  @NotBlank(message = "使用区域不能为空")
  private String area;
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
  /** 描述 */
  private String description;
}
