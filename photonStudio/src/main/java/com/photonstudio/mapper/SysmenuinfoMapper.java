package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Sysmenuinfo;

public interface SysmenuinfoMapper {

	int deleteAll(@Param("dBname") String dBname);

	int insetAll(@Param("dBname")String dBname, List<Sysmenuinfo> list);

	List<Sysmenuinfo> findObject(@Param("dBname")String dBname, @Param("parentId")Integer parentId);

	int updateObject(@Param("dBname")String dBname, Sysmenuinfo sysmenuinfo);

	List<Sysmenuinfo> findObjectByGroupid(@Param("dBname")String dBname,@Param("groupid")Integer groupid,
										  @Param("parentId")Integer parentId);

	List<Integer> findMenuIdByGroupid(@Param("dBname")String dBname, 
									  @Param("groupid")Integer groupid);

	Sysmenuinfo findPicByMenuId(@Param("dBname")String dBname, @Param("id")Integer id);
	
}
