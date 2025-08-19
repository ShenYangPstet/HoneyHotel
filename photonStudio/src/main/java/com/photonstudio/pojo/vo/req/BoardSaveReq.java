package com.photonstudio.pojo.vo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 看板保存
 *
 * @author bingo
 */
@Data
public class BoardSaveReq {
  /** id */
  private Integer id;
  /** 名称 */
  @NotBlank(message = "名称不能为空")
  private String name;
  /** 描述 */
  private String description;
  /** 模板id */
  @NotNull(message = "模板id不能为空")
  private Integer boardTemplateId;
  /** 颜色 */
  private String color;
  /** 高度 */
  @NotNull(message = "高度不能为空")
  private Integer height;
  /** 宽度 */
  @NotNull(message = "宽度不能为空")
  private Integer width;
  /** 位置横坐标 */
  @NotNull(message = "位置横坐标不能为空")
  private Integer positionX;
  /** 位置纵坐标 */
  @NotNull(message = "位置纵坐标不能为空")
  private Integer positionY;
  /** 自定义配置 */
  @TableField(typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> customConfig;
}
