package com.photonstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.photonstudio.pojo.CardInfo;
import com.photonstudio.pojo.req.CardInfoReq;


/**
 * @author 沈景杨
 */
public interface CardInfoService extends IService<CardInfo> {

     PageInfo<CardInfo> findByPage(CardInfoReq cardInfoReq);

}
