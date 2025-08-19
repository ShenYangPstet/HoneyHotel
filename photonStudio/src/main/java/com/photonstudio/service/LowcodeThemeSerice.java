package com.photonstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.pojo.LowcodeTheme;

/**
 * 主题表service
 *
 * @author hx
 * @date 2022/6/9 16:12
 */
public interface LowcodeThemeSerice extends IService<LowcodeTheme> {
    /**
     * 根据主题id更新或修改页面样式
     *
     * @param lowcodeTheme 页面样式，页面id必填
     */
    void insertOrUpdate(LowcodeTheme lowcodeTheme);

    /**
     * 根据id查询 主题
     * @param id
     * @return
     */
    LowcodeTheme getById(Integer id);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);
}
