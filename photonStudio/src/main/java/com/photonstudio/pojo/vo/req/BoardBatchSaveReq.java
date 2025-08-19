package com.photonstudio.pojo.vo.req;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 看板批量保存
 *
 * @author bingo
 */
@Data
public class BoardBatchSaveReq {
  /** 使用区域 */
  @NotBlank(message = "使用区域不能为空")
  private String area;
  /** 看板列表 */
  @Valid private List<BoardSaveReq> boardList;
}
