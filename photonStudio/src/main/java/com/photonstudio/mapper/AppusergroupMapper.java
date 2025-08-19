package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Appusergroup;

public interface AppusergroupMapper {
	List<Appusergroup>findObject(@Param("userid")Integer userid,
			@Param("region")String region,@Param("city") String city,
								@Param("startIndex")Integer startIndex,
								@Param("pageSize")Integer pageSize);

	int getRowCount(@Param("userid")Integer userid);

	int insertObject(Appusergroup appusergroup);

	int deleteObjectById(@Param("ids")Integer... ids);

	int updateObject(Appusergroup appusergroup);

	int updateObjectByAppuser(Appusergroup appusergroup);

	List<Appusergroup> findObjectByUserIds(@Param("ids")Integer[] userids);


    int updateSkinById(@Param("id")Integer id, @Param("skin")String skin);
}
