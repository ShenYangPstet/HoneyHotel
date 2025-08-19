package com.photonstudio.pojo.vo.resp;

import lombok.Data;

/**
 * 看板模板
 *
 * @author bingo
 */
@Data
public class BoardTemplateResp {
  /** id */
  private Long id;
  /** 名称 */
  private String name;
  /** 编码 */
  private String code;
  /** 使用区域 */
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
