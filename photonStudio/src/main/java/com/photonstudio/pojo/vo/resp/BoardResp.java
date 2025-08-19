package com.photonstudio.pojo.vo.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.Map;

/**
 * 看板查询结果
 *
 * @author bingo
 */
@Data
public class BoardResp {
  /** id */
  private Long id;
  /** 名称 */
  private String name;
  /** 描述 */
  private String description;
  /** 模板id */
  private BoardTemplateResp boardTemplate;
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
