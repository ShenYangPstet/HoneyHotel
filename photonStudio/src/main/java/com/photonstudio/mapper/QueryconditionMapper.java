package com.photonstudio.mapper;
/**
 * 
 * @author 16526
 *查询条件的 名称
 */

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Querycondition;

public interface QueryconditionMapper {
	List<Querycondition> findObject(@Param("dBname")String dBname);
	int insertObject(@Param("dBname")String dBname, Querycondition querycondition);
	int deleteObjectById(@Param("dBname")String dBname,@Param("id") Integer id);
	int updateObject(@Param("dBname")String dBname, Querycondition querycondition);
}
