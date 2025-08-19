package com.photonstudio.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.EcharsObject;
import com.photonstudio.pojo.EcharsZ;
import com.photonstudio.pojo.Pic;
import com.photonstudio.pojo.QsAlarmlog;
import com.photonstudio.pojo.vo.AlarmChart;

import java.util.Date;
import java.util.List;

public interface QsAlarmlogService extends IService<QsAlarmlog> {


    PageObject<QsAlarmlog> findObjectByAlarm(String dBname, String alarmtypelevel, String alarmanswer, Integer[] pageCurrent, Integer pageSize, Integer pageSize2, Integer floorId, String drId);

    int saveObject(String dBname, QsAlarmlog qsAlarmlog);

    int updateObject(String dBname, QsAlarmlog qsAlarmlog);

    int deleteObjectById(String dBname, Integer... ids);

    List<QsAlarmlog> findAll(String dBname, Date startTime, Date endTime);

    PageObject<QsAlarmlog> findObject(String dBname, Integer pageCurrent,
                                      Integer pageSize, String alarmtypelevel,
                                      String alarmanswer, Integer drtypeid, Date startTime, Date endTime, Integer floorId, String drName);

    List<EcharsObject> findEchars(String dBname);

    List<EcharsObject> findEcharsByAlarm(String dBname);

    List<QsAlarmlog> findObjectByDrid(String dBname, Integer pageCurrent, Integer pageSize, Integer drid,
                                      String alarmtypelevel, String alarmanswer, Date startTime, Date endTime);

    List<QsAlarmlog> findAlarmListHome(String dBname);

    List<Pic> queryPicByDrid(String dBname, Integer drid);

    List<EcharsZ> findSumAlarmBytype(String dBname, String type, Date date);

    PageObject<QsAlarmlog> findObjectByHome(List<EcharsObject> dBlist, String alarmtypelevel, Date startTime, Date endTime, Integer pageCurrent, Integer pageSize);

    PageObject<QsAlarmlog> findAlarmByHome(List<EcharsObject> dBlist, String alarmtypelevel, Integer pageCurrent, Integer pageSize);

    List<EcharsObject> findEcharsHome(List<String> dBlist);

    List<EcharsObject> findByEcharSum();

    /**
     * 查询最近30天报警统计图表
     *
     * @return 近30天报警统计图表
     */
    AlarmChart getAlarmChart();

    /**
     * 查询当前实时报警数量
     * @return
     */
    JSONObject getAlarmCount(String dBname);
}
