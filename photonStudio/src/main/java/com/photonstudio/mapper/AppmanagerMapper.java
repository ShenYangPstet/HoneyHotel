package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Appmanager;

public interface AppmanagerMapper {
	

	

	List<Appmanager> findObjectByApptypeid(
			@Param(value = "apptypeid")Integer apptypeid,
			@Param("userid")Integer userid,
			@Param("region")String region, @Param("city")String city,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize") Integer pageSize, 
			@Param("state")String state);
	int deleteObjectsById(@Param("appid")Integer appid);
	int insertObject(Appmanager appmanager);
	int updateObjectById(Appmanager appmanager);
	int findCount(@Param("apptypeid")String apptypeid);
	void createtable();
	void createDatabase(@Param("dBname")String dBname);
	void deleteDBByid(@Param("appname")String appname);
	int getRowCount(@Param(value = "apptypeid")Integer apptypeid , @Param("userid")Integer userid,
			@Param("region")String region, @Param("city")String city,@Param("state") String state);
	int removeObject(String state, Integer... appids);
	int findObjectByappName(@Param("appName")String appName);
	List<Appmanager> findObjectByUesrid(@Param("userid")Integer userid);
	Appmanager findObjectByAppid(@Param("appid")Integer appid);
	int deleteAPPGroupById(@Param("appid")Integer appid);
	List<Appmanager> findObjectByState(@Param("appstate")String appstate,@Param("state") String state);
	List<Appmanager> findObjectByUserid(@Param("userid")Integer userid,@Param("appstate")String appstate,@Param("state")String state);
	List<Appmanager> findAllByAppids(@Param("appids")Integer[] appids);
	int getRowCountByLicense();
	List<Appmanager> findAllByAppidsAppstate(Integer[] appids,@Param("appstate") String appstate);
	
}
