package com.photonstudio.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.annotation.OperationLogAnnotation;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.QsLdJgr;
import com.photonstudio.service.AppmanagerService;
import com.photonstudio.service.QsLdJgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/zsqy/qsLdJgr/{dBname}")
@Validated
public class QsLdJgrController {

    private static final Log log = LogFactory.get();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private QsLdJgrService qsLdJgrService;
    @Autowired
    private AppmanagerService appmanagerService;


    @RequestMapping("/findObject")
    public SysResult findObject(@PathVariable String dBname, Integer ldLxId,
                                Integer pageCurrent, Integer pageSize) {
        //System.out.println(ldLxId);
        PageObject<QsLdJgr> pageObject = new PageObject<>();
        try {
            pageObject = qsLdJgrService.findObject(dBname, ldLxId, pageCurrent, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询失败");
        }
        return SysResult.oK(pageObject);
    }

    @RequestMapping("/save")
    public SysResult saveObject(@PathVariable String dBname, QsLdJgr qsLdJgr) {
        int rows = qsLdJgrService.saveObject(dBname, qsLdJgr);
        if (rows == 1) {
            return SysResult.oK();
        }
        return SysResult.build(50009, "保存失败");
    }

    @RequestMapping("/delete")
    public SysResult deleteObject(@PathVariable String dBname, Integer... ids) {
        int rows = qsLdJgrService.deleteObjectById(dBname, ids);
        if (rows == ids.length) {
            return SysResult.oK();
        }
        return SysResult.build(50009, "删除失败或者有记录已经不存在");
    }

    @RequestMapping("/update")
    public SysResult updateObject(@PathVariable String dBname, QsLdJgr qsLdJgr) {
        //System.out.println("联动结果更新==>>"+qsLdJgr);
        int rows = qsLdJgrService.updateObject(dBname, qsLdJgr);
        if (rows == 1) {
            return SysResult.oK();
        }
        return SysResult.build(50009, "更新失败");
    }

    @RequestMapping("/export")
    public void exportExcel(@PathVariable String dBname, Integer ldLxId,
                            HttpServletResponse response) {
        List<QsLdJgr> list = qsLdJgrService.findAllByRwType(dBname, ldLxId);
        ExcelUtil.exportExcel(list, "联动任务结果", "任务结果", QsLdJgr.class, dBname + "-联动任务结果.xls", response);
    }

    @RequestMapping("/import")
    public SysResult importExcel(@PathVariable String dBname, MultipartFile file) {
        List<QsLdJgr> list = ExcelUtil.importExcel(file, 1, 1, QsLdJgr.class);
        try {
            qsLdJgrService.importObjects(dBname, list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("导入失败");
        }
        return SysResult.oK();
    }

    @RequestMapping("/doQsLdJg")
    public SysResult doQsLdJg(@PathVariable String dBname, Integer ldRwId, Integer appid) {
        List<QsLdJgr> list = qsLdJgrService.findObjectByldRwId(dBname, ldRwId);
        if (list == null || list.size() == 0) return SysResult.build(50009, "该任务无相关执行结果");
        try {
            qsLdJgrService.doQsLdJg(dBname, list, appid, null);
            return SysResult.oK();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return SysResult.build(50009, "执行失败");
        }
    }

    @RequestMapping("/implement")
    @OperationLogAnnotation(operModul = "设备启停控制", operType = "下发操作-参数修改", singleShot = true)
    public SysResult doimplement(@PathVariable String dBname, @NotNull(message = "{required}") Integer appid, @NotBlank(message = "{required}") String tagname, @NotBlank(message = "{required}") String tagvalue,HttpServletRequest request) {
        String msg=tagname+":"+tagvalue;
        try {
            qsLdJgrService.doimplements(dBname, appid, msg, request);
            return SysResult.oK();
        } catch (RestClientException e) {
            log.error("报错信息", e);
            return SysResult.build(50009, "执行失败");
        }
    }

    @RequestMapping("/doimplements")
    @OperationLogAnnotation(operModul = "设备启停控制", operType = "下发操作-参数修改", Mass = true)
    public SysResult doimplements(@PathVariable String dBname, @NotNull(message = "{required}") Integer appid, @NotBlank(message = "{required}") String msg, HttpServletRequest request) {
        try {
            qsLdJgrService.doimplements(dBname, appid, msg, request);
            return SysResult.oK();
        } catch (Exception e) {
            log.error("报错信息", e);
        }
        return SysResult.build(50009, "失败");
    }

    @PostMapping("/doimplements")
    @OperationLogAnnotation(operModul = "设备启停控制", operType = "下发操作-参数修改", Mass = true)
    public SysResult doimplements(@PathVariable String dBname, @RequestBody JSONObject jsonObject, HttpServletRequest request) {
        Integer appid = jsonObject.getInteger("appid");
        String msg = jsonObject.getString("msg");
        return doimplements(dBname, appid, msg, request);
    }

    @RequestMapping("/doQsLdJgByJgId")
    public SysResult doQsLdJgByJgId(@PathVariable String dBname, Integer appid, Integer ldJgId) {

        qsLdJgrService.doQsLdJgByJgId(dBname, appid, ldJgId);
        return SysResult.oK();


    }
}
