package com.photonstudio.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.PageStyle;

import java.util.List;

/**
 * 页面样式表service
 *
 * @author bingo
 * @date 2022/5/7 15:12
 */
public interface PageStyleService extends IService<PageStyle> {
    /**
     * 根据picId查询页面样式
     *
     * @param picId 页面id
     * @return 页面样式
     */
    PageStyle getByPicId(Integer picId);

    /**
     * 根据picId删除页面样式
     *
     * @param picId 页面id
     */
    void deleteByPicId(Integer picId);

    /**
     * 根据页面id更新或修改页面样式
     *
     * @param pageStyle 页面样式，页面id必填
     */
    void insertOrUpdate(PageStyle pageStyle);

    /**
     * 根据页面ID查询页面设备的设备状态
     *
     * @param picId 页面ID
     * @return
     */
    List<Drinfo> getdrStartByPicId(Integer picId);

    /**
     * 根据页面ID查询页面设备的icon图标
     *
     * @param picId
     * @return
     */
    List<JSONObject> getIconTypeByPicId(Integer picId);

    /**
     * 根据页面ID 和设备类型ID 查询页面设备ID合集
     *
     * @param picId
     * @param drtypeId
     * @return
     */
    List<Integer> getDrIdListByType(Integer picId, Integer drtypeId);

    /**
     * 更新页面样式表中指定设备信息
     *
     * @param database 数据库名
     * @param deviceId 设备ID
     */
    void updatePageStyleByDeviceId(String database, Integer deviceId);

    /**
     * 更新页面样式表中的所有设备信息
     *
     * @param database 数据库名
     */
    void updatePageStyleAllDeviceInfo(String database);

    /**
     * 清空页面编辑
     */
    void deletePageStle(String drid);
}
