package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.QsLdTgr;
import com.photonstudio.pojo.Timemode;
import com.photonstudio.pojo.Timemodeinfo;
import com.photonstudio.pojo.Timemodejob;
import com.photonstudio.pojo.YmdJob;

public interface TimemodeMapper {

	int getRowCount(@Param("dBname")String dBname);

	List<Timemode> findObject(@Param("dBname")String dBname, @Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);

	int save(@Param("dBname")String dBname, Timemode timemode);

	int deleteObjectById(@Param("dBname")String dBname, Integer[] ids);

	int updateObject(@Param("dBname")String dBname, Timemode timemode);

	List<Timemodeinfo> findModeinfoByid(@Param("dBname")String dBname, @Param("id")Integer id);

	int getRowCountinfo(@Param("dBname")String dBname);

	List<Timemodeinfo> findObjectinfo(@Param("dBname")String dBname, @Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);

	int saveinfo(@Param("dBname")String dBname, Timemodeinfo timemodeinfo);

	int updateObjectinfo(@Param("dBname")String dBname, Timemodeinfo timemodeinfo);

	int deleteObjectinfoById(@Param("dBname")String dBname, Integer[] ids);

	int getRowCountjob(@Param("dBname")String dBname);

	List<Timemode> findAllMode(@Param("dBname")String dBname);

	List<QsLdTgr> findAllTg(@Param("dBname")String dBname);

	QsLdTgr findTgByTjid(@Param("dBname")String dBname, @Param("ldTjId")Integer ldTjId);

	void savejob(@Param("dBname")String dBname, Timemodejob timemodejob);

	void deletemodejobBytime(@Param("dBname")String dBname, @Param("ymd")String ymd);

	List<YmdJob> findTimemodejob(@Param("dBname")String dBname, @Param("yearMonth")String yearMonth);

	List<Timemodeinfo> findModeinfoByTjIds(@Param("dBname")String dBname, Integer[] ids);

	List<Timemodeinfo> findObjectinfoByIds(@Param("dBname")String dBname, Integer[] ids);

	void deleteObjectinfoByModeIds(@Param("dBname")String dBname, Integer[] ids);

}
