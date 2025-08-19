package com.photonstudio.service;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Energyparm;

public interface EnergyparmService {

	PageObject<Energyparm> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Energyparm energyparm);

	int deleteObjectById(String dBname, Integer id);

	int uptadeObject(String dBname, Energyparm energyparm);

}
