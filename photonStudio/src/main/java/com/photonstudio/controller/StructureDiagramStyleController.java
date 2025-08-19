package com.photonstudio.controller;

import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.StructureDiagramStyle;
import com.photonstudio.service.StructureDiagramStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 结构图样式表实体类
 *
 * @author hanxi
 * @date 2022-05-19
 */
@RestController
@RequestMapping("/structureDiagramStyle")
@RequiredArgsConstructor
public class StructureDiagramStyleController {
    private final StructureDiagramStyleService structureDiagramStyleService;

    /**
     * 根据设备类型id查询结构图
     * @param drtypeId
     * @return
     */
    @PostMapping("/getBydrtypeId")
    public SysResult getByPicId(Integer drtypeId) {
        return SysResult.oK(structureDiagramStyleService.getByDrtypeId(drtypeId));
    }
    /**
     * 根据设备id查询结构图真实参数值
     * @param drId
     * @return
     */
    @GetMapping("/getBydrId")
    public SysResult getBydrId(Integer drId) {
        return SysResult.oK(structureDiagramStyleService.getByDrId(drId));
    }

    /**
     * 修改增加结构图
     * @param structureDiagramStyle
     * @return
     */
    @PostMapping("/insertOrUpdate")
    public SysResult insertOrUpdate(StructureDiagramStyle structureDiagramStyle) {
        structureDiagramStyleService.insertOrUpdate(structureDiagramStyle);
        return SysResult.oK();
    }
    @GetMapping("/findJgtSubinfoByDrId")
    public SysResult findJgtSubinfoByDrId(Integer drId){
        List<JSONObject> list=structureDiagramStyleService.findJgtSubinfoByDrId(drId);
        return SysResult.oK(list);
    }
    @PostMapping("/getRealTimeData")
    public SysResult getRealTimeData(@RequestBody JSONObject jsonObject){
        JSONObject jsonData=structureDiagramStyleService.getRealTimeData(jsonObject);
        return SysResult.oK(jsonData);
    }
}
