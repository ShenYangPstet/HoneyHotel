package com.photonstudio.controller;

import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.enums.Status;
import com.photonstudio.common.vo.Result;
import com.photonstudio.pojo.vo.TypeRegOption;
import com.photonstudio.service.ConsoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 总控台
 */
@Api(tags = "总控台")
@RestController
@RequestMapping("/zsqy/console")
@RequiredArgsConstructor
public class ConsoleController {
    private final ConsoleService consoleService;

    /**
     * 根据模板选择设备下发命令
     *
     * @param jsonObject
     * @param dBname
     * @param appId
     * @return
     */
    @PostMapping("/TemplateInstructionSend")
    public Result TemplateInstructionSend(@RequestBody JSONObject jsonObject, @RequestHeader("database") String dBname, @RequestHeader("appId") Integer appId) {
        //System.out.println(jsonObject.toJSONString());
        try {
            consoleService.TemplateInstructionSend(dBname, appId, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ok(Status.WARN);
        }
        return Result.ok();
    }

    /**
     * 获取模板列表
     *
     * @return 模版列表
     */
    @ApiOperation("获取模版列表")
    @ApiImplicitParam(name = "database", value = "数据库名称", defaultValue = "8zwg", required = true, paramType = "header", dataType = "String")
    @GetMapping("/getConsoleMode")
    public Result<List<TypeRegOption>> getConsoleMode(HttpServletRequest request) {
        List<TypeRegOption> list = consoleService.getConsoleMode(request.getHeader("database"));
        return Result.ok(list);
    }
}
