package com.photonstudio.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.SubinfoMapper;
import com.photonstudio.pojo.Subinfo;
import com.photonstudio.service.SubinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubinfoServiceImpl extends ServiceImpl<SubinfoMapper, Subinfo> implements SubinfoService {
    @Autowired
    private SubinfoMapper subinfoMapper;

    @Override
    public PageObject<Subinfo> findObject(String dBname, Integer subid, Integer pageCurrent, Integer pageSize) {
        if (pageCurrent == null || pageCurrent < 1) pageCurrent = 1;
        if (pageSize == null || pageSize < 5) pageSize = 5;
        List<Subinfo> list = new ArrayList<>();
        int rowCount = subinfoMapper.getRowCount(dBname, subid);
        int startIndex = (pageCurrent - 1) * pageSize;
        list = subinfoMapper.findObject(dBname, subid, startIndex, pageSize);
        return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
    }

    @Override
    public List<Subinfo> findAll(String dBname, Integer subid) {
        List<Subinfo> list = new ArrayList<>();
        list = subinfoMapper.findObject(dBname, subid, null, null);
        return list;
    }

    @Override
    public int saveObject(String dBname, Subinfo subinfo) {
        int rows = 0;
        try {
            rows = subinfoMapper.insertObject(dBname, subinfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("保存失败");
        }
        return rows;
    }

    @Override
    public int updateObject(String dBname, Subinfo subinfo) {
        System.out.println(subinfo);
        int rows = 0;
        try {
            rows = subinfoMapper.updateObject(dBname, subinfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("更新失败");
        }
        return rows;
    }

    @Override
    public int deleteObjectById(String dBname, Integer... ids) {
        int rows = 0;
        try {
            rows = subinfoMapper.deleteObjectById(dBname, ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除失败");
        }
        return rows;
    }
}
