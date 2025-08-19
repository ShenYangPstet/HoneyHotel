package com.photonstudio.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.pojo.StructureDiagramStyle;

import java.util.List;

public interface StructureDiagramStyleService extends IService<StructureDiagramStyle> {
    /**
     * 根据drtypeId查询页面样式
     *
     * @param drtypeId 设备类型
     * @return 结构图页面样式
     */
    StructureDiagramStyle getByDrtypeId(Integer drtypeId);
    /**
     * 根据设备类型id更新或修改结构图页面样式
     *
     * @param structureDiagramStyle 结构图页面样式，设备类型id drtypeId必填
     */
    void insertOrUpdate(StructureDiagramStyle structureDiagramStyle);
    /**
     * 根据drtypeId删除结构图页面样式
     *
     * @param drtypeId 设备类型id
     */
    void deleteByPicId(Integer drtypeId);

    /**
     * 根据设备ID查询结构图中元素绑定的变量和绑定的subinfo
     * @param drId
     * @return
     */
    List<JSONObject> findJgtSubinfoByDrId(Integer drId);
    /**
     * 根据drtypeId查询页面样式
     *
     * @param drtypeId 设备类型
     * @return 结构图页面样式
     */
    Object getByDrId(Integer drId);

    /**
     * 根据参数获取元素真实值
     * @param jsonObject
     * @return
     */
    JSONObject getRealTimeData(JSONObject jsonObject);
}
