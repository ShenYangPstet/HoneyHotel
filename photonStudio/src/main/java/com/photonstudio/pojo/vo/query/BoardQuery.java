package com.photonstudio.pojo.vo.query;

import lombok.Data;

/**
 * 看板条件查询
 *
 * @author bingo
 */
@Data
public class BoardQuery {
  /** 名称 */
  private String name;
  /** 编码 */
  private String code;
  /** 使用区域 */
  private String area;
}
