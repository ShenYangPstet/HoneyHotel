package com.photonstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Drtypemode;

import java.util.List;

public interface DrtypemodeService extends IService<Drtypemode> {

    PageObject<Drtypemode> findObject(String dBname, Integer drtypeid, Integer pageCurrent, Integer pageSize);

    List<Drtypemode> findAllByDrtypeid(String dBname, Integer drtypeid);

    int saveObject(String dBname, Drtypemode drtypemode);

    int updateObject(String dBname, Drtypemode drtypemode);

    int deleteObjectById(String dBname, Integer... ids);

    void importObjects(String dBname, List<Drtypemode> list);

    List<Drtypemode> findAllWriteByDrtypeid(String dBname, Integer drtypeid);

    void writeDrtypeMode(String dBname, Integer drtypeid, Integer appid, String msg);


}
