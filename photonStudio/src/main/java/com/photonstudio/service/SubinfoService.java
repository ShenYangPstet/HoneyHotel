package com.photonstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Subinfo;

import java.util.List;

public interface SubinfoService extends IService<Subinfo> {

    PageObject<Subinfo> findObject(String dBname, Integer subid, Integer pageCurrent, Integer pageSize);

    List<Subinfo> findAll(String dBname, Integer subid);

    int saveObject(String dBname, Subinfo subinfo);

    int updateObject(String dBname, Subinfo subinfo);

    int deleteObjectById(String dBname, Integer... ids);

}
