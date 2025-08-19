package com.photonstudio.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Qstag;

public interface QstagMapper extends BaseMapper<Qstag> {
	List<Qstag> findObject (@Param("dBname") String dBname,@Param("tagname") String tagname
				,@Param("itemdrid")Integer itemdrid,@Param("startIndex")Integer startIndex
				,@Param("pageSize")Integer pageSize);
	int getRowCount(@Param("dBname") String dBname,@Param("tagname") String tagname
			,@Param("itemdrid")Integer itemdrid);
	int insertObject(@Param("dBname") String dBname,Qstag qstag);
	
	int deleteObjectById(@Param("dBname") String dBname,Integer...ids);
	
	int updateObject(@Param("dBname") String dBname,Qstag qstag);
	int findObjectById(@Param("dBname")String dBname, @Param("tagid") Integer tagid);
	
	String findTagnameById(@Param("dBname")String dBname, @Param("tagid") Integer tagid);
	List<Object> selectListForExcelExport(@Param("dBname")String  dBname,@Param("tagname") String tagname,@Param("itemdrid") Integer itemdrid,@Param("startIndex") Integer startIndex);
	int selectCountByTagname(@Param("dBname")String dBname,@Param("tagname") String tagname);
	int updateObjectByTagname(@Param("dBname")String dBname, List<Qstag> list);
}
