package com.photonstudio.controller;

import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.QsSystemlog;
import com.photonstudio.service.QsSystemlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/*
用户操作日志
 */
@RestController
@RequestMapping("/zsqy/qsSystemlog")
public class QsSystemlogController {

     @Autowired
    private QsSystemlogService qsSystemlogService;

    /**
     * 分页查询
     * @return
     */
    @GetMapping("/{dBname}/findPage")
     public SysResult findPage(@PathVariable String dBname,Integer pageCurrent,
                               Integer pageSize,String userCode,String type,String operationType,
                               String starTime,String endTime){
        PageObject<QsSystemlog> qsSystemlogPageObject = qsSystemlogService.findByPage(dBname, pageCurrent, pageSize, userCode, type, operationType, starTime, endTime);
        return SysResult.oK(qsSystemlogPageObject);
     }


     @PostMapping("/importExcel")
     public void importExcel(HttpServletResponse response, Integer [] ids){
        List<QsSystemlog> list=qsSystemlogService.findByIds(ids);
        ExcelUtil.exportExcel(list, "用户操作日志","用户操作日志" ,QsSystemlog.class,"用户操作日志.xls", response);
     }

}
