package com.photonstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.pojo.LowcodePagedata;

/**
 * 页面样式表service
 *
 * @author hx
 * @date 2022/6/9 16:12
 */
public interface LowcodePagedataSerice extends IService<LowcodePagedata> {


    /**
     * 根据样式id更新或修改页面样式
     *
     * @param lowcodePagedata 页面样式，页面id必填
     */
    void insertOrUpdate(LowcodePagedata lowcodePagedata);

    /**
     * 根据id查询 主题样式
     * @param id
     * @return
     */
    LowcodePagedata getById(Integer id);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据pageName查询
     * @param pageName
     * @return
     */
    LowcodePagedata getByPageName(String pageName);
}
