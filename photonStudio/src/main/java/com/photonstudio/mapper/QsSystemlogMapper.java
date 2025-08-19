package com.photonstudio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photonstudio.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QsSystemlogMapper extends BaseMapper<QsSystemlog> {

    void  SaveLog(String dBname, QsSystemlog qsSystemlog);

     Drinfo findByDrstat(String dBname, String tagnames);

     List<Reg> findByregState(String dBname,String tagname,String isenergy);

    List<Qslinkrw> findQslinByid(String dBname, Integer tjid, Integer jgid);

    List<Qslinkrw> findByids(String dBname,String [] ids);

    List<Energyinfo> findEnergyinfo(String dBname,String [] ids);

    List<QsAlarmlog>findQsAlarmalogBy(String dBname,String id);

    List<QsSystemlog> findByPage(@Param("dBname") String dBname,
                                 @Param("pageCurrent") Integer pageCurrent,
                                 @Param("pageSize") Integer pageSize,
                                 @Param("userCode") String userCode,
                                 @Param("type") String type,
                                @Param("operationType") String operationType,
                                @Param("starTime") String starTime,
                                 @Param("endTime") String endTime);

    Integer findBycount(@Param("dBname") String dBname,
                        @Param("pageCurrent") Integer pageCurrent,
                        @Param("pageSize") Integer pageSize,
                        @Param("userCode") String userCode,
                        @Param("type") String type,
                        @Param("operationType") String operationType,
                        @Param("starTime") String starTime,
                        @Param("endTime") String endTime);

    Qsmsrw findAll(String dBname,Integer id);


    List<Qsmsrw> findById(@Param("dBname")String dBname,String[] rwid);

    List<Reg> indRegfById(@Param("dBname")String dBname,String [] regid);

    List<Qsmsjg> findAllByid(@Param("dBname")String dBname,String[] ids);
}

