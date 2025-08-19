package com.photonstudio.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.Reg;
import com.photonstudio.pojo.Regdata;
import com.photonstudio.pojo.Subinfo;

public interface RegdataMapper {

	List<Regdata> findObject(@Param("dBname")String dBname, 
							 @Param("tagname")String tagname, 
							 @Param("startTime")Date startTime, 
							 @Param("endTime")Date endTime);

	int insertObject(@Param("dBname")String dBname, @Param("tagname")String tagname, 
			         @Param("regdata")Regdata regdata);

	int deleteObjectById(@Param("dBname")String dBname, @Param("tagname")String tagname, 
						@Param("ids")Integer... ids);

	int updateObject(@Param("dBname")String dBname, @Param("tagname")String tagname, 
					 @Param("regdata")Regdata regdata);

	String findSavetimeByTagname(@Param("dBname")String dBname,@Param("tagname") String tagname);
	
	int createtable(@Param("dBname")String dBname,@Param("tagname") String tagname);
	
	int updatetable(@Param("dBname")String dBname,@Param("tagname")String tagname,
							@Param("newtagname")String newtagname);

	int getDataBase(@Param("dBname")String dBname);

	int getTable(@Param("dBname")String dBname,@Param("tagname") String tagname);

	List<Reg> findRegByDrid(@Param("dBname")String dBname, @Param("drid")Integer drid);

	List<Subinfo> findsubinfo(@Param("dBname")String dBname);

	List<Regdata> findRegDataBYDate(@Param("dBname")String dBname, @Param("tableName")String tableName,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("tagName")String tagName);

	Drtypeinfo gettypeid1(@Param("dBname")String dBname);

	Drtypeinfo gettypeid2(@Param("dBname")String dBname, @Param("drtypeid")Integer drtypeid);
}

