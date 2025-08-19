package com.photonstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.*;

import java.util.List;

public interface QsSystemlogService extends IService<QsSystemlog> {

   void SaveLog(String dBname, QsSystemlog qsSystemlog);

   Drinfo findDrinfo(String dBname, String tagname);

   List<Reg> findByRegState(String dBname,String tagname,String isenergy);

   List<Qslinkrw> findQslinByid(String dBname,Integer tjid,Integer jgid);

   List<Qslinkrw> findByids(String dBname,String [] ids);

   List<Energyinfo> findEnergyinfo(String dBname,String [] ids);

   List<QsAlarmlog>findQsAlarmalogBy(String dBname,String id);


   List<Qsmsrw> findById(String dBname,String[] rwid);



   //分页查询
   PageObject<QsSystemlog> findByPage(String dBname, Integer pageCurrent,
                                      Integer pageSize, String userCode, String type, String operationType,
                                      String starTime, String endTime);


   List<QsSystemlog> findByIds(Integer [] ids);
}
