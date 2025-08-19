package com.photonstudio.pojo.vo.query;

import lombok.Data;

/**
 * 看板模板分页查询条件
 *
 * @author bingo
 */
@Data
public class BoardTemplatePageQuery  extends  PageQuery{
  /** 名称 */
  private String name;
  /** 编码 */
  private String code;
  /** 使用区域 */
  private String area;
}
