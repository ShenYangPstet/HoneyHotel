package com.photonstudio.controller;

import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.PageStyle;
import com.photonstudio.service.PageStyleService;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 页面样式表controller
 *
 * @author bingo
 * @date 2022/5/7 15:16
 */
@RestController
@RequestMapping("/pageStyle")
@RequiredArgsConstructor
@Validated
public class PageStyleController {
    private final PageStyleService pageStyleService;

    @PostMapping("/getByPicId")
    public SysResult getByPicId(Integer picId) {
        return SysResult.oK(pageStyleService.getByPicId(picId));
    }

    @PostMapping("/insertOrUpdate")
    public SysResult insertOrUpdate(PageStyle pageStyle) {
        pageStyleService.insertOrUpdate(pageStyle);
        return SysResult.oK();
    }

    @GetMapping("/getdrStartByPicId")
    public SysResult getdrStartByPicId(Integer picId) {
        List<Drinfo> drinfoList = pageStyleService.getdrStartByPicId(picId);
        return SysResult.oK(drinfoList);
    }

    @GetMapping("/getIconTypeByPicId")
    public SysResult getIconTypeByPicId(Integer picId) {
        List<JSONObject> jsonObjectList = pageStyleService.getIconTypeByPicId(picId);
        return SysResult.oK(jsonObjectList);
    }

    @GetMapping("/updatePageStyleByDeviceId")
    public SysResult updatePageStle(@NotNull(message = "{required}") Integer deviceId) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String database = request.getHeader("database");
        if (StringUtils.isEmpty(database)) {
            throw new RuntimeException("数据库名不能为空");
        }
        pageStyleService.updatePageStyleByDeviceId(database, deviceId);
        return SysResult.oK();
    }

    @GetMapping("/deletePageStle")
    public SysResult deletePageStle(@NotBlank(message = "{required}") String drid) {
        pageStyleService.deletePageStle(drid);
        return SysResult.oK();
    }
}
