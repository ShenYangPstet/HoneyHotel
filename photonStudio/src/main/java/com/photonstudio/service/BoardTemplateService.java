package com.photonstudio.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.photonstudio.pojo.vo.query.BoardTemplatePageQuery;
import com.photonstudio.pojo.vo.req.BoardTemplateSaveReq;
import com.photonstudio.pojo.vo.resp.BoardTemplateResp;

import java.util.List;

/**
 * 看板模板
 *
 * @author bingo
 */
public interface BoardTemplateService {

  /**
   * 分页查询
   *
   * @param pageQuery 查询条件
   * @return 分页结果
   */
  IPage<BoardTemplateResp> page(BoardTemplatePageQuery pageQuery);

  /**
   * 保存
   *
   * @param saveReq 保存参数
   */
  void save(BoardTemplateSaveReq saveReq);

  /**
   * 根据id删除看板模板及其生成的看板
   *
   * @param ids id列表
   */
  void deleteByIds(List<Integer> ids);
}
