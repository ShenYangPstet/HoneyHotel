package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Regalarminfo;

public interface RegalarminfoMapper {

	int getRowCount(@Param("dBname")String dBname,@Param("regId") Integer regId);

	List<Regalarminfo> findObject(@Param("dBname")String dBname,@Param("regId") Integer regId,@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);

	List<Regalarminfo> findAllByRegId(@Param("dBname")String dBname,@Param("regId") Integer regId);

	int insertObject(@Param("dBname")String dBname, Regalarminfo regalarminfo);

	int deleteObjectByIds(@Param("dBname")String dBname, Integer[] ids);

	int updateObject(@Param("dBname")String dBname, Regalarminfo regalarminfo);

	int deleteObjectByRegIds(@Param("dBname")String dBname, Integer... regIds);

	int insertObjectByList(@Param("dBname")String dBname, List<Regalarminfo> regalarminfoslist,@Param("regId") Integer regId);

	void deleteObjectByRegIdlist(@Param("dBname")String dBname, List<Integer> regIdlist);

	int findCountById(@Param("dBname")String dBname,@Param("id") Integer id);

}
