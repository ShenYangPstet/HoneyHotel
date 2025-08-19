package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.DataInfo;
import com.photonstudio.pojo.Energyinfo;
import com.photonstudio.pojo.Reg;

public interface QueryAllenergyMapper {

	DataInfo queryAllenergy(@Param("dBname")String dBname, @Param("tagname")String tagname);

	String queryAllenergyByYMD(@Param("dBname")String dBname, @Param("tagname")String tagname, @Param("datestr")String datestr);

	List<DataInfo> findOneEnergyInfo(@Param("dBname")String dBname, @Param("tagname")String tagname, @Param("datestr1")String datestr1, @Param("datestr2")String datestr2);

	List<Energyinfo> queryAllenergyInfo(@Param("dBname")String dBname, @Param("type")int type);

	DataInfo queryAllenergyDay(@Param("dBname")String dBname, @Param("tagname")String tagname, @Param("strdate")String strdate);

	Energyinfo findObjectById(@Param("dBname")String dBname, Integer id);

	Energyinfo queryAppTotalenergyInfo(@Param("dBname")String dBname, @Param("energytypeid")int energytypeid, @Param("isapptotal")int isapptotal);

	List<Reg> findRegByTagname(@Param("dBname")String dBname,@Param("tagname")String tagname);

	String queryRegEnergyByYMD(@Param("dBname")String dBname, @Param("dateTable")String dateTable,@Param("tagname")String tagname,@Param("datestr")String datestr);

	DataInfo queryAllenergyNew(@Param("dBname")String dBname,@Param("tabName")String tabName, @Param("tagname")String tagname);

	String queryAllenergyByYMDNew(@Param("dBname")String dBname,@Param("tabName")String tabName, @Param("tagname")String tagname, @Param("dateStr")String dateStr);

	List<DataInfo> findOneEnergyInfoNew(@Param("dBname")String dBname, @Param("tableName")String tableName, @Param("datestr1")String datestr1, @Param("datestr2")String datestr2,
			@Param("tagname")String tagname);

}
