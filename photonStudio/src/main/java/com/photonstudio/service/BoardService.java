package com.photonstudio.service;


import com.photonstudio.pojo.vo.query.BoardQuery;
import com.photonstudio.pojo.vo.req.BoardBatchSaveReq;
import com.photonstudio.pojo.vo.resp.BoardResp;

import java.util.List;

/**
 * 看板
 *
 * @author bingo
 */
public interface BoardService {

  /**
   * 查询看板列表
   *
   * @param boardQuery 查询条件
   * @return 看板列表
   */
  List<BoardResp> list(BoardQuery boardQuery);

  /**
   * 批量保存
   *
   * @apiNote 只允许批量保存同一个使用区域的看板；使用区域下看板，在此列表中的将会新增或更新，不在此列表中的将会删除
   */
  void batchSave(BoardBatchSaveReq saveReq);
}
