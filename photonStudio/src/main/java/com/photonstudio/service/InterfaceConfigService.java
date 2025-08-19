package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.InterfaceConfig;

public interface InterfaceConfigService {

	PageObject<InterfaceConfig> findObject(String explain, Integer pageCurrent, Integer pageSize, Integer interfaceType);

	List<InterfaceConfig> findAll(String explain, Integer interfaceType);

	int saveObject(InterfaceConfig interfaceConfig);

	int deleteObject(Integer... ids);

	int updateObject(InterfaceConfig interfaceConfig);

}
