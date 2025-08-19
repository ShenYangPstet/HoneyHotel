package com.photonstudio.controller;

import cn.hutool.core.util.ObjectUtil;
import com.photonstudio.common.BindingResultMsg;
import com.photonstudio.common.FileUtil;
import com.photonstudio.common.enums.Status;
import com.photonstudio.common.vo.MasterTemplateVo;
import com.photonstudio.common.vo.ParameterVo;
import com.photonstudio.common.vo.PicUploadResult;
import com.photonstudio.common.vo.Result;
import com.photonstudio.pojo.vo.ParaVo;
import com.photonstudio.service.MasterTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/zsqy/masterTemplate")
@Api(tags = "模板文档")
@Validated
public class MasterTemplateController {

    @Autowired
    private MasterTemplateService masterTemplateService;

    @PostMapping("/saveObjcte")
    @ApiOperation("新增模板")
    public Result saveObjcte(@Validated @RequestBody MasterTemplateVo masterTemplateVo, BindingResult bindingResult, HttpServletRequest request) {
        String database = request.getHeader("database");
        if (ObjectUtil.isEmpty(database)) return Result.build(Status.REQUEST_PARAMETER_ERROR.code, "数据库不能为空");
        if (bindingResult.hasErrors()) {
            return BindingResultMsg.bindingResultMsg(bindingResult);
        } else {
            int reuslt = masterTemplateService.saveObject(masterTemplateVo, request);
            if (reuslt > 0) {
                return Result.build(Status.SUCCESS.code, "新增成功");
            }
            return Result.build(Status.API_ERROR.code, Status.API_ERROR.message);
        }
    }

    @GetMapping("/saveObjcteMode")
    @ApiOperation("将模板更新到项目库里面")
    public Result saveObjcteMode(@NotNull(message = "{required}") Integer id, HttpServletRequest request) {
        String database = request.getHeader("database");
        if (ObjectUtil.isEmpty(database)) return Result.build(Status.REQUEST_PARAMETER_ERROR.code, "数据库不能为空");
        int reuslt = masterTemplateService.saveObjectMode(id);
        if (reuslt > 0) {
            return Result.build(Status.SUCCESS.code, "更新成功");
        }
        return Result.build(Status.API_ERROR.code, "项目库只能发布一个模板", "");
    }

    @PostMapping("/findPage")
    @ApiOperation("分页查询模板")
    public Result findPage(@Validated @RequestBody ParameterVo parameter, BindingResult bindingResult, HttpServletRequest request) {
        String database = request.getHeader("database");
        if (ObjectUtil.isEmpty(database)) return Result.build(Status.REQUEST_PARAMETER_ERROR.code, "数据库不能为空");
        if (bindingResult.hasErrors()) {
            return BindingResultMsg.bindingResultMsg(bindingResult);
        } else {
            return Result.ok(masterTemplateService.findPage(parameter));
        }
    }

    @PostMapping("/updateThumbnail")
    @ApiOperation("封面缩略图接口")
    public Result updateThumbnail(@RequestHeader("database") String dBname, String baseStr) {
        PicUploadResult picUploadResult = FileUtil.base64Upload(dBname, baseStr, "thumbnail", "jpeg");
        if (picUploadResult.getStatus() != 20000 || ObjectUtil.isEmpty(picUploadResult.getUrl()))
            return Result.build(Status.WARN.code, Status.WARN.message);
        return Result.ok(picUploadResult.getUrl());
    }

    @PostMapping("/updateObject")
    @ApiOperation("修改模板")
    public Result updateObject(@Validated @RequestBody MasterTemplateVo masterTemplateVo, BindingResult bindingResult, HttpServletRequest request) {
        String database = request.getHeader("database");
        if (ObjectUtil.isEmpty(database)) return Result.build(Status.REQUEST_PARAMETER_ERROR.code, "数据库不能为空");
        if (bindingResult.hasErrors()) {
            return BindingResultMsg.bindingResultMsg(bindingResult);
        } else {
            int reuslt = masterTemplateService.updateObject(masterTemplateVo, request);
            if (reuslt > 0) {
                return Result.build(Status.SUCCESS.code, "修改成功");
            }
            return Result.build(Status.API_ERROR.code, "项目库只能发布一个模板", "");
        }
    }

    @PostMapping("/deleteObject")
    @ApiOperation("删除模板")
    public Result deleteObject(@Validated @RequestBody ParaVo paraVo, BindingResult bindingResult, HttpServletRequest request) {
        String database = request.getHeader("database");
        if (ObjectUtil.isEmpty(database)) return Result.build(Status.REQUEST_PARAMETER_ERROR.code, "数据库不能为空");
        if (bindingResult.hasErrors()) {
            return BindingResultMsg.bindingResultMsg(bindingResult);
        }
        masterTemplateService.deleteObject(paraVo);
        return Result.ok();
    }
}
