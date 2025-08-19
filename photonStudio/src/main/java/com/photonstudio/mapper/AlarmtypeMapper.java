package com.photonstudio.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Alarmtype;
import com.photonstudio.pojo.UserAlarmlevel;

public interface AlarmtypeMapper  extends BaseMapper<Alarmtype> {

	List<Alarmtype> findAll(@Param("dBname")String dBname);

	int insertObject(@Param("dBname")String dBname, Alarmtype alarmtype);

	int deleteObjectById(@Param("dBname")String dBname, Integer...ids);

	int updateObject(@Param("dBname")String dBname, Alarmtype alarmtype);

	int getRowCount(@Param("dBname")String dBname);

	List<Alarmtype> findObject(@Param("dBname")String dBname, 
							   @Param("startIndex")Integer startIndex, 
							   @Param("pageSize")Integer pageSize);

	int findcntByUserid(@Param("dBname")String dBname, @Param("userid")Integer userid);

	void deleteByUserid(@Param("dBname")String dBname, @Param("userid")Integer userid);

	void insertUserAlarm(@Param("dBname")String dBname, @Param("userid")Integer userid, @Param("alarmlevel")Integer alarmlevel);

	List<UserAlarmlevel> findUserAlarm(@Param("dBname")String dBname, @Param("userid")Integer userid);


}
