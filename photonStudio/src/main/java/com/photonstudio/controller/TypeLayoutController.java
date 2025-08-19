package com.photonstudio.controller;

import com.photonstudio.common.UserThreadLocal;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Appsysuser;
import com.photonstudio.pojo.TypeLayout;
import com.photonstudio.service.TypeLayoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 设备类型布局
 *
 * @author bingo
 */
@RestController
@RequestMapping("/zsqy/typeLayout")
@RequiredArgsConstructor
public class TypeLayoutController {
    private final TypeLayoutService typeLayoutService;

    @PostMapping("save")
    public SysResult save(@RequestBody List<TypeLayout> typeLayoutList) {
        typeLayoutService.saveByUserId(
                typeLayoutList,
                Optional.ofNullable(UserThreadLocal.get())
                        .orElse(new Appsysuser().setId(typeLayoutList.get(0).getUserId()))
                        .getId());
        return SysResult.oK();
    }

    @GetMapping("listTypeWithLayout")
    public SysResult insert(Integer userId) {
        return SysResult.oK(typeLayoutService.listTypeWithLayout(
                Optional.ofNullable(UserThreadLocal.get())
                        .orElse(new Appsysuser().setId(userId))
                        .getId())
        );
    }
}
