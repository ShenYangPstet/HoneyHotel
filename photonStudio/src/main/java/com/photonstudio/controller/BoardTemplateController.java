package com.photonstudio.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;

import com.photonstudio.common.vo.Result;
import com.photonstudio.pojo.vo.query.BoardTemplatePageQuery;
import com.photonstudio.pojo.vo.req.BoardTemplateSaveReq;
import com.photonstudio.pojo.vo.resp.BoardTemplateResp;
import com.photonstudio.service.BoardTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 看板模板
 *
 * @author bingo
 */
@RestController
@RequestMapping("/zsqy/boardTemplate")
@RequiredArgsConstructor
public class BoardTemplateController {
  private final BoardTemplateService boardTemplateService;

  /** 分页查询 */
  @GetMapping("/page")
  public Result<IPage<BoardTemplateResp>> page(@Validated BoardTemplatePageQuery pageQuery) {
    return Result.ok("查询成功", boardTemplateService.page(pageQuery));
  }

  /** 保存 */
  @PostMapping("/save")
  public Result<Void> save(@RequestBody @Validated BoardTemplateSaveReq saveReq) {
    boardTemplateService.save(saveReq);
    return Result.ok("保存成功", null);
  }

  /**
   * 删除
   *
   * @apiNote 会删除看板模板及其生成的看板
   */
  @DeleteMapping("/delete")
  public Result<Void> delete(@RequestParam List<Integer> ids) {
    boardTemplateService.deleteByIds(ids);
    return Result.ok("删除成功", null);
  }
}
