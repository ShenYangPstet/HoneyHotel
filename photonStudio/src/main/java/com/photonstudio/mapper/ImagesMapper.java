package com.photonstudio.mapper;

import com.photonstudio.pojo.Images;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImagesMapper {

	int insertObjcet(@Param("dBname") String dBname, Images images);

	int getRowCount(@Param("dBname") String dBname, @Param("imgtype") String imgtype, String imgname);

	List<Images> findObjectByType(@Param("imgtype") String imgtype,
								  @Param("dBname") String dBname,
								  @Param("imgname") String imgname, @Param("startIndex") Integer startIndex,
								  @Param("pageSize") Integer pageSize);
	int deleteObjectById(@Param("imgid")Integer imgid,
						 @Param("dBname") String dBname);
	Images findObjectById(@Param("dBname") String dBname,
						  @Param("imgid")Integer imgid);
	int updateObject(@Param("dBname") String dBname, Images images);

	int getRowCountP(@Param("dBname")String dBname, @Param("imgtype")String imgtype);

	List<Images> findObjectPByType(@Param("imgtype")String imgtype, @Param("dBname")String dBname, @Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);

	int updateObjectP(@Param("dBname") String dBname, Images images);

	int insertObjcetP(@Param("dBname") String dBname, Images images);

	Images findObjectPById(@Param("dBname")String dBname, @Param("imgid")Integer imgid);

	int deleteObjectPById(@Param("imgid")Integer imgid, @Param("dBname")String dBname);
}
