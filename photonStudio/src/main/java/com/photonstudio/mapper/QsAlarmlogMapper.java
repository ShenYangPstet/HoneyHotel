package com.photonstudio.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.EcharsObject;
import com.photonstudio.pojo.Pic;
import com.photonstudio.pojo.QsAlarmlog;

public interface QsAlarmlogMapper extends BaseMapper<QsAlarmlog> {

	int getRowCount(@Param("dBname") String dBname,
					@Param("alarmstate") Integer alarmstate,
					@Param("alarmtypelevel") String alarmtypelevel,
					@Param("alarmanswer") String alarmanswer,
					@Param("drtypeids") List<Integer> drtypeids, Date startTime, Date endTime, Integer floorId,String drName);

	List<QsAlarmlog> findObject(@Param("dBname")String dBname, 
			                    @Param("alarmstate")Integer alarmstate, 
			                    @Param("startIndex")Integer startIndex, 
			                    @Param("pageSize")Integer pageSize, 
			                    @Param("alarmtypelevel")String alarmtypelevel, 
			                    @Param("alarmanswer")String alarmanswer, 
			                    @Param("drtypeids")List<Integer> drtypeids,
			                    @Param("startTime")Date startTime, 
			                    @Param("endTime") Date endTime,@Param("floorId") Integer floorId,@Param("drName") String drName);

	List<QsAlarmlog> findObjectByAlarm(@Param("dBname")String dBname,
									   @Param("alarmtypelevel")String alarmtypelevel,
									   @Param("alarmanswer") String alarmanswer,
									   @Param("drtypeids")List<Integer> drtypeids ,
									   @Param("startIndex")Integer startIndex, 
									   @Param("pageSize")Integer pageSize,@Param("floorId") Integer floorId,@Param("drName") String drName);

	int getRowCountByAlarm(@Param("dBname")String dBname,@Param("alarmtypelevel") String alarmtypelevel,
						   @Param("alarmanswer") String alarmanswer,
						   @Param("drtypeids") List<Integer> drtypeids,@Param("floorId") Integer floorId,@Param("drName") String drName);

	int insertObject(@Param("dBname")String dBname, QsAlarmlog qsAlarmlog);

	int updateObject(@Param("dBname")String dBname, QsAlarmlog qsAlarmlog);

	void deleteObjectById(@Param("dBname")String dBname,@Param("ids") Integer... ids);

	List<EcharsObject> findEchars(@Param("dBname")String dBname);
	List<EcharsObject> findEcharsByAlarm(@Param("dBname")String dBname);

	List<QsAlarmlog> findObjectByDrid(@Param("dBname")String dBname, 
			                    @Param("alarmstate")Integer alarmstate, 
			                    @Param("startIndex")Integer startIndex, 
			                    @Param("pageSize")Integer pageSize, 
			                    @Param("drid") Integer drid,
			                    @Param("alarmtypelevel")String alarmtypelevel, 
			                    @Param("alarmanswer")String alarmanswer, 
			                    @Param("startTime")Date startTime, 
			                    @Param("endTime") Date endTime);

	List<Pic> queryPicByDrid(@Param("dBname")String dBname, @Param("drid")Integer drid);

	int findEcharsByYMD(@Param("dBname")String dBname, 
			@Param("alarmtypelevel")String alarmtypelevel, 
			@Param("startTime")String startTime, 
			@Param("endTime")String endTime);

	int getRowCountByHome(@Param("dBlist")List<EcharsObject> dBlist,@Param("alarmtypelevel") String alarmtypelevel,@Param("startTime")Date startTime, @Param("endTime") Date endTime);

	List<QsAlarmlog> findObjectByHome(List<EcharsObject> dBlist,@Param("alarmtypelevel")String alarmtypelevel,@Param("startTime") Date startTime,@Param("endTime") Date endTime, @Param("startIndex")Integer  startIndex, @Param("pageSize")Integer pageSize);

	List<QsAlarmlog> findAlarmByHome(List<EcharsObject> dBlist,@Param("alarmtypelevel")String alarmtypelevel, @Param("startIndex") Integer startIndex, @Param("pageSize")Integer pageSize);

	int getAlarmRowCountByHome(@Param("dBlist")List<EcharsObject> dBlist,@Param("alarmtypelevel") String alarmtypelevel);

	List<EcharsObject> findEcharsHome(@Param("dBlist")List<String> dBlist);

//	@Select("select count(*) from qs_alarmlog a LEFT JOIN reg b ON a.reg_id=b.reg_id left join alarmtype d on a.alarm_level=d.alarmtypelevel where a.alarmstate=1 and d.alarmtypelevel=#{alarmtypelevelId}")
//	Integer EcharsCount(@Param("alarmtypelevelId") Integer alarmtypelevelId);

	List<QsAlarmlog> EcharsCountList();
}
