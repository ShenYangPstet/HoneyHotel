package com.photonstudio.controller;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.LowcodePagedata;
import com.photonstudio.service.LowcodePagedataSerice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 主题样式表controller
 *
 * @author hx
 * @date 2022/6/10 17:16
 */
@RestController
@RequestMapping("/lowcodePagedata")
@RequiredArgsConstructor
public class LowcodePagedataController {
    private final LowcodePagedataSerice lowcodePagedataSerice;

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @PostMapping("/getById")
    public SysResult getByPicId(Integer id) {
        return SysResult.oK(lowcodePagedataSerice.getById(id));
    }

    /**
     * 新增或者修改
     * @param lowcodePagedata
     * @return
     */
    @PostMapping("/insertOrUpdate")
    public SysResult insertOrUpdate(LowcodePagedata lowcodePagedata) {
        lowcodePagedataSerice.insertOrUpdate(lowcodePagedata);
        return SysResult.oK();
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public SysResult deleteById(Integer id){
        lowcodePagedataSerice.deleteById(id);
        return SysResult.oK();
    }

    /**
     * 根据pageName查询
     * @param pageName
     * @return
     */
    @PostMapping("/getByPageName")
    public SysResult getByPageName(String pageName){
        return SysResult.oK(lowcodePagedataSerice.getByPageName(pageName));
    }
}
