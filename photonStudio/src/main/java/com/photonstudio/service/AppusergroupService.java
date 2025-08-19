package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Appusergroup;

public interface AppusergroupService {

	PageObject<Appusergroup> findObject(Integer userid, Integer pageCurrent,Integer pageSize);

	List<Appusergroup> findObjectByuserid(Integer userid, String region, String city);

	int saveObject(Appusergroup appusergroup);

	int deleteObjectById(Appusergroup appusergroup);

	int updateObject(Appusergroup appusergroup);

	List<Appusergroup> findObjectByUserIds(Integer[] userids);

    int updateSkinById(Integer id, String skin);
}
