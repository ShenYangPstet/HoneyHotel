package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Appuser;
import com.photonstudio.pojo.Pic;

public interface PicMapper {

	List<Pic> findObject(@Param("dBname")String dBname, 
						 @Param("parentid")Integer parentid);
	int deleteObjectById(@Param("dBname")String dBname,
						 @Param("picid")Integer picid);
	List<Integer> findChildrenId(@Param("dBname") String dBname,
			 @Param("picid")Integer picid);
	int insertObject(@Param("dBname") String dBname,Pic pic);
	int updateObject(@Param("dBname") String dBname,Pic pic);
	List<Pic> findAllPic(@Param("dBname")String dBname);
	List<Pic> findObjectInPicmodeid(@Param("dBname")String dBname,@Param("picname") String picname);
	Pic findObjectById(@Param("dBname")String dBname, @Param("picid")Integer picid);
	List<Pic> findAllPicNameid(@Param("dBname")String dBname);
	Appuser findDepartmentid(@Param("dBname")String dBname, @Param("userid")Integer userid);
	List<Pic> findPiclistBydpid(@Param("dBname")String dBname, @Param("departmentid")Integer departmentid);
	List<Pic> findPiclistAll(@Param("dBname")String dBname);
	int updateColor(@Param("dBname")String dBname, @Param("picid")Integer picid, @Param("color")String color);

    List<Pic> findExportPic(@Param("dBname")String dBname);
}
