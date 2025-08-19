package com.photonstudio.controller;


import com.photonstudio.common.vo.Result;
import com.photonstudio.pojo.vo.query.BoardQuery;
import com.photonstudio.pojo.vo.req.BoardBatchSaveReq;
import com.photonstudio.pojo.vo.resp.BoardResp;
import com.photonstudio.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 看板
 *
 * @author bingo
 */
@RestController
@RequestMapping("/zsqy/board")
@RequiredArgsConstructor
public class BoardController {
  private final BoardService boardService;

  /** 条件查询 */
  @GetMapping("/list")
  public Result<List<BoardResp>> list(BoardQuery boardQuery) {
    return Result.ok("查询成功", boardService.list(boardQuery));
  }

  /**
   * 批量保存
   *
   * @apiNote 只允许批量保存同一个使用区域的看板；使用区域下看板，在此列表中的将会新增或更新，不在此列表中的将会删除
   */
  @PostMapping("/batchSave")
  public Result<Void> batchSave(@RequestBody @Validated BoardBatchSaveReq saveReq) {
    boardService.batchSave(saveReq);
    return Result.ok("保存成功", null);
  }
}
