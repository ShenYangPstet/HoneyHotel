package com.photonstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.pojo.Canvas;

public interface CanvasService extends IService<Canvas> {

  /**
   * 根据picId查询页面样式
   *
   * @param picId 页面id
   * @return 页面样式
   */
  Canvas getByPicId(Integer picId);

  /**
   * 根据页面id更新或修改页面样式
   *
   * @param pageStyle 页面样式，页面id必填
   */
  void insertOrUpdate(Canvas canvas);

  /**
   * 根据picId删除页面样式
   *
   * @param picId 页面id
   */
  void deleteByPicId(Integer picId);
}
