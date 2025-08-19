package com.photonstudio.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.photonstudio.mapper.CardInfoMapper;
import com.photonstudio.pojo.CardInfo;
import com.photonstudio.pojo.req.CardInfoReq;
import com.photonstudio.service.CardInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 沈景杨
 */
@Service
@RequiredArgsConstructor
public class CardInfoServiceImpl extends ServiceImpl<CardInfoMapper, CardInfo> implements
    CardInfoService {

  @Override
  public PageInfo<CardInfo> findByPage(CardInfoReq cardInfoReq) {
    PageHelper.startPage(cardInfoReq.getPageNum(), cardInfoReq.getPageSize());
    List<CardInfo> list = lambdaQuery().ge(ObjectUtil.isNotEmpty(cardInfoReq.getStartPassTime()),
            CardInfo::getPassTime,
            cardInfoReq.getStartPassTime())
        .le(ObjectUtil.isNotEmpty(cardInfoReq.getEndPassTime()), CardInfo::getPassTime,
            cardInfoReq.getEndPassTime()).like(ObjectUtil.isNotEmpty(cardInfoReq.getCardNumber()),
            CardInfo::getCardNumber, cardInfoReq.getCardNumber())
        .like(ObjectUtil.isNotEmpty(cardInfoReq.getFullName()), CardInfo::getFullName,
            cardInfoReq.getFullName()).list();
    return new PageInfo<>(list);
  }
}
