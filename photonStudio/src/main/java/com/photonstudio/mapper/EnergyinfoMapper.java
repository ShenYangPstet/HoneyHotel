package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Energyinfo;

public interface EnergyinfoMapper {

	int getRowCount(@Param("dBname") String dBname);

	List<Energyinfo> findObject(@Param("dBname")String dBname, 
								@Param("startIndex")Integer startIndex, 
								@Param("pageSize")Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Energyinfo energyinfo);

	int deleteObjectById(@Param("dBname")String dBname, Integer... ids);

	int updateObject(@Param("dBname")String dBname, Energyinfo energyinfo);

	List<Energyinfo> findAllEnergyinfo(@Param("dBname")String dBname);

	int findApptotalCount(@Param("dBname")String dBname);

	Energyinfo findEnergyinfoByid(@Param("dBname")String dBname, @Param("id")Integer id);

}
