package com.photonstudio.controller;

import cn.hutool.core.util.ObjectUtil;
import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.annotation.OperationLogAnnotation;
import com.photonstudio.common.enums.Status;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.Result;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.*;
import com.photonstudio.pojo.vo.AlarmChart;
import com.photonstudio.service.AppmanagerService;
import com.photonstudio.service.QsAlarmlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/zsqy/qsAlarmlog")
@Api(tags = "设备报警api")
public class QsAlarmlogController {
    @Autowired
    private QsAlarmlogService qsAlarmlogService;
    @Autowired
    private AppmanagerService appmanagerService;

    @RequestMapping("/{dBname}/findObject")
    public SysResult findObject(@PathVariable String dBname,
                                Integer pageCurrent, Integer pageSize,
                                String alarmtypelevel, String alarmanswer,
                                Integer drtypeid,
                                Date startTime, Date endTime, Integer floorId, String drName) {
        PageObject<QsAlarmlog> pageObject = new PageObject<>();
        try {
            pageObject = qsAlarmlogService.
                    findObject(dBname, pageCurrent, pageSize, alarmtypelevel,
                            alarmanswer, drtypeid, startTime, endTime, floorId, drName);
            return SysResult.oK(pageObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(50009, "查询失败");
    }

    @RequestMapping("/{dBname}/findObjectByDrid")
    public SysResult findObjectByDrid(@PathVariable String dBname,
                                      Integer pageCurrent, Integer pageSize, Integer drid,
                                      String alarmtypelevel, String alarmanswer,
                                      Date startTime, Date endTime) {
        List<QsAlarmlog> list = qsAlarmlogService.findObjectByDrid(dBname, pageCurrent, pageSize, drid, alarmtypelevel,
                alarmanswer, startTime, endTime);
        return SysResult.oK(list);
    }

    @RequestMapping("/{dBname}/findEchars")
    public SysResult findEchars(@PathVariable String dBname) {
        List<EcharsObject> list = qsAlarmlogService.findEchars(dBname);
        return SysResult.oK(list);
    }

    @RequestMapping("/findEcharsHome")
    public SysResult findEchars(Integer... appids) {
        List<Appmanager> list = appmanagerService.findAllByAppids(appids);
        List<String> dBlist = new ArrayList<>();
        if (list == null || list.size() == 0) return SysResult.build(50009, "未找到项目");
        for (Appmanager appmanager : list) {
            String dBname = appmanager.getAppid() + appmanager.getAppName();
            dBlist.add(dBname);
        }
        List<EcharsObject> echarslist = qsAlarmlogService.findEcharsHome(dBlist);
        return SysResult.oK(echarslist);
    }

    @RequestMapping("/{dBname}/findEcharsByAlarm")
    public SysResult findEcharsAlarm(@PathVariable String dBname) {
        List<EcharsObject> list = qsAlarmlogService.findEcharsByAlarm(dBname);
        return SysResult.oK(list);
    }

    @RequestMapping("/{dBname}/findSumAlarmBytype")//type 3 日报警 type 2 月报警 type 1 年报警
    public SysResult findSumenergyBytype(@PathVariable String dBname, String type, Date date) {
        Long starttime = System.currentTimeMillis();
        try {
            if (date == null) {
                date = new Date();
            }
            //System.out.println("date=="+date);
            List<EcharsZ> echarsList = qsAlarmlogService.findSumAlarmBytype(dBname, type, date);
            Long endtime = System.currentTimeMillis() - starttime;
            System.out.println("time is " + endtime + "毫秒");
            return SysResult.oK(echarsList);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(50009, "查询失败");
        }
    }

    @RequestMapping("/findAlarmHome")
    public SysResult findAlarmHome(String alarmtypelevel, Integer pageCurrent, Integer pageSize, Integer... appids) {
        List<Appmanager> list = appmanagerService.findAllByAppids(appids);
        List<EcharsObject> dBlist = new ArrayList<>();
        if (list == null || list.size() == 0) return SysResult.build(50009, "未找到项目");
        for (Appmanager appmanager : list) {
            EcharsObject db = new EcharsObject();
            String dBname = appmanager.getAppid() + appmanager.getAppName();
            db.setName(dBname);
            db.setValue(appmanager.getAppexplain());
            dBlist.add(db);
        }
        PageObject<QsAlarmlog> pageObject = new PageObject<>();
        try {
            pageObject = qsAlarmlogService.
                    findAlarmByHome(dBlist, alarmtypelevel, pageCurrent, pageSize);
            return SysResult.oK(pageObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(50009, "查询失败");
    }

    @RequestMapping("/findObjectHome")
    public SysResult findObjectHome(String alarmtypelevel, Date startTime, Date endTime, Integer pageCurrent, Integer pageSize, Integer... appids) {
        List<Appmanager> list = appmanagerService.findAllByAppids(appids);
        if (list == null || list.size() == 0) return SysResult.build(50009, "未找到项目");
        List<EcharsObject> dBlist = new ArrayList<>();
        for (Appmanager appmanager : list) {
            EcharsObject db = new EcharsObject();
            String dBname = appmanager.getAppid() + appmanager.getAppName();
            db.setName(dBname);
            db.setValue(appmanager.getAppexplain());
            dBlist.add(db);
        }
        PageObject<QsAlarmlog> pageObject = new PageObject<>();
        try {
            pageObject = qsAlarmlogService.
                    findObjectByHome(dBlist, alarmtypelevel, startTime, endTime, pageCurrent, pageSize);
            return SysResult.oK(pageObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(50009, "查询失败");
    }

    @RequestMapping("/{dBname}/findAlarm")
    public SysResult findObjectByAlarm(@PathVariable String dBname,
                                       String alarmtypelevel, String alarmanswer,
                                       Integer pageCurrent, Integer pageSize, Integer floorId, String drName,Integer[] drtypeid) {
        PageObject<QsAlarmlog> pageObject = new PageObject<>();
        try {
            pageObject = qsAlarmlogService.
                    findObjectByAlarm(dBname, alarmtypelevel, alarmanswer, drtypeid, pageCurrent, pageSize, floorId, drName);
            return SysResult.oK(pageObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(50009, "查询失败");
    }

    @RequestMapping("/{dBname}/findAlarmCurrentList")
    public SysResult findAlarmCurrentList(@PathVariable String dBname) {
        PageObject<QsAlarmlog> pageObject = new PageObject<>();
        try {
            pageObject = qsAlarmlogService.
                    findObjectByAlarm(dBname, null, null, null,
                            null, null, null, null);
            return SysResult.oK(pageObject.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(50009, "查询失败");
    }

    @RequestMapping("/{dBname}/findAlarmListHome")
    public SysResult findAlarmListHome(@PathVariable String dBname) {
        List<QsAlarmlog> list = new ArrayList<>();
        try {
            list = qsAlarmlogService.
                    findAlarmListHome(dBname);
            return SysResult.oK(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(50009, "查询失败");
    }

    @RequestMapping("/{dBname}/save")
    private SysResult saveObject(@PathVariable String dBname, QsAlarmlog qsAlarmlog) {
        int rows = qsAlarmlogService.saveObject(dBname, qsAlarmlog);
        if (rows == 1) {
            return SysResult.oK();
        }
        return SysResult.build(50009, "保存失败");
    }

    @RequestMapping("/{dBname}/update")
    @OperationLogAnnotation(operModul = "报警日志", operType = "报警应答操作", implementAlarm = true)
    public SysResult updateObject(@PathVariable String dBname, QsAlarmlog qsAlarmlog) {
        //System.out.println(qsAlarmlog);
        int rows = qsAlarmlogService.updateObject(dBname, qsAlarmlog);
        if (rows == 1) {
            return SysResult.oK();
        }
        return SysResult.build(50009, "更新失败");
    }

    @RequestMapping("/{dBname}/delete")
    public SysResult delete(@PathVariable String dBname, Integer... ids) {
        int rows = qsAlarmlogService.deleteObjectById(dBname, ids);
        if (rows == ids.length) {
            return SysResult.oK();
        }
        return SysResult.build(50009, "删除失败或者记录已经不存在");
    }

    @RequestMapping("/{dBname}/queryPicByDrid")
    public SysResult queryPicByDrid(@PathVariable String dBname, Integer drid) {
        try {
            List<Pic> piclist = qsAlarmlogService.queryPicByDrid(dBname, drid);
            return SysResult.oK(piclist);

        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(50009, "根据设备定位页面错误");
        }
    }


    @RequestMapping("/{dBname}/export")
    public void exportExcel(@PathVariable String dBname, Date startTime, Date endTime,
                            HttpServletResponse response) {
        List<QsAlarmlog> list = qsAlarmlogService.findAll(dBname, startTime, endTime);
        ExcelUtil.exportExcel(list, "报警日志", "报警日志", QsAlarmlog.class, dBname + "-报警日志.xls", response);
    }

    @GetMapping("/findByEcharSum")
    @ApiOperation("报警级别总数")
    public Result findByEcharSum(HttpServletRequest request) {
        if (ObjectUtil.isEmpty(request.getHeaders("database")))
            return Result.build(Status.REQUEST_PARAMETER_ERROR.code, "数据库不能为空");
        return Result.ok(qsAlarmlogService.findByEcharSum());
    }

    @GetMapping("getAlarmChart")
    public Result<AlarmChart> getAlarmChart() {
        return Result.ok(qsAlarmlogService.getAlarmChart());
    }

    @GetMapping("getAlarmCount")
    @ApiOperation("获取当前报警数量")
    public Result getAlarmCount(@RequestHeader("database")String dBname){
        return Result.ok(qsAlarmlogService.getAlarmCount(dBname));
    }
}
