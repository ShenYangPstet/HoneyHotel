package com.photonstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.pojo.TypeLayout;
import com.photonstudio.pojo.vo.TypeLayoutVO;

import java.util.List;

/**
 * 设备类型布局
 *
 * @author bingo
 */
public interface TypeLayoutService extends IService<TypeLayout> {
    /**
     * 根据用户id查询设备类型布局列表
     *
     * @param userId 用户id
     * @return 设备类型信息列表 包含布局信息、设备类型状态信息
     */
    List<TypeLayoutVO> listTypeWithLayout(Integer userId);

    /**
     * 根据用户id保存设备类型布局
     * 当用户id下设备类型布局不存在时新增布局
     * 设备类型布局已存在时删除旧布局再新增布局
     *
     * @param typeLayoutList 设备类型布局列表
     * @param userId         用户id
     */
    void saveByUserId(List<TypeLayout> typeLayoutList, Integer userId);
}
