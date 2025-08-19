package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Elemtmodle;
import com.photonstudio.pojo.Elemttype;
import com.photonstudio.pojo.Elemttypemode;

public interface ElemtmodleMapper {

	int getRowCount(@Param("dBname")String dBname);

	List<Elemtmodle> findObject(@Param("dBname")String dBname, @Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Elemtmodle elemtmodle);

	int deleteObjectById(@Param("dBname")String dBname, @Param("ids")Integer...ids);

	int updateObject(@Param("dBname")String dBname, Elemtmodle elemtmodle);

	List<Elemtmodle> findAll(@Param("dBname")String dBname);

	List<Elemttype> findObjectByTypeid(@Param("dBname")String dBname, @Param("typeid")Integer typeid,@Param("elemttypename")  String elemttypename);

	List<Elemttypemode> findElemttypemodeById(@Param("dBname")String dBname, @Param("elemttypeid")Integer elemttypeid);

}
