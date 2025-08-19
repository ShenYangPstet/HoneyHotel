package com.photonstudio.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.mapper.LowcodePagedataMapper;
import com.photonstudio.pojo.LowcodePagedata;
import com.photonstudio.service.LowcodePagedataSerice;
import org.springframework.stereotype.Service;

@Service
public class LowcodePagedataSericeImpl extends ServiceImpl<LowcodePagedataMapper, LowcodePagedata> implements LowcodePagedataSerice {
    @Override
    public void insertOrUpdate(LowcodePagedata lowcodePagedata) {
        if (lowcodePagedata.getPageName() == null) {
            save(lowcodePagedata);
        } else {
            lambdaUpdate().eq(LowcodePagedata::getPageName, lowcodePagedata.getId()).update(lowcodePagedata);
        }
    }

    @Override
    public LowcodePagedata getById(Integer id) {
        return lambdaQuery().eq(LowcodePagedata::getId,id).one();
    }

    @Override
    public void deleteById(Integer id) {
        lambdaUpdate().eq(LowcodePagedata::getId,id).remove();
    }

    @Override
    public LowcodePagedata getByPageName(String pageName) {
        return lambdaQuery().eq(LowcodePagedata::getPageName,pageName).one();
    }
}
