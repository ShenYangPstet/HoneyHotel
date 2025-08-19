package com.photonstudio.controller;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.LowcodeTheme;
import com.photonstudio.service.LowcodeThemeSerice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主题表controller
 *
 * @author hx
 * @date 2022/6/10 17:26
 */
@RestController
@RequestMapping("/LowcodeTheme")
@RequiredArgsConstructor
public class LowcodeThemeController {
    private final LowcodeThemeSerice lowcodeThemeSerice;
    @PostMapping("/getById")
    public SysResult getByPicId(Integer id) {
        return SysResult.oK(lowcodeThemeSerice.getById(id));
    }

    @PostMapping("/insertOrUpdate")
    public SysResult insertOrUpdate(LowcodeTheme lowcodeTheme) {
        lowcodeThemeSerice.insertOrUpdate(lowcodeTheme);
        return SysResult.oK();
    }
    @PostMapping("/deleteById")
    public SysResult deleteById(Integer id){
        lowcodeThemeSerice.deleteById(id);
        return SysResult.oK();
    }
}
