package com.photonstudio.serviceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.QsSystemlogMapper;
import com.photonstudio.pojo.*;
import com.photonstudio.service.QsSystemlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QsSystemlogServiceImpl extends ServiceImpl<QsSystemlogMapper,QsSystemlog> implements QsSystemlogService {

    @Autowired
    private QsSystemlogMapper qsSystemlogMapper;
    @Override
    public void SaveLog(String dBname, QsSystemlog qsSystemlog) {
           qsSystemlogMapper.SaveLog(dBname,qsSystemlog);
    }

    @Override
    public Drinfo findDrinfo(String dBname, String  tagname) {
        return qsSystemlogMapper.findByDrstat(dBname,tagname);
    }

    @Override
    public List<Reg> findByRegState(String dBname, String tagname,String isenergy) {
        return qsSystemlogMapper.findByregState(dBname,tagname,isenergy);
    }

    @Override
    public List<Qslinkrw> findQslinByid(String dBname, Integer tjid, Integer jgid) {
        return qsSystemlogMapper.findQslinByid(dBname,tjid,jgid);
    }

    @Override
    public List<Qslinkrw> findByids(String dBname, String[] ids) {
        return qsSystemlogMapper.findByids(dBname,ids);
    }

    @Override
    public List<Energyinfo> findEnergyinfo(String dBname, String[] ids) {
        return qsSystemlogMapper.findEnergyinfo(dBname,ids);
    }

    @Override
    public List<QsAlarmlog> findQsAlarmalogBy(String dBname, String id) {
        return qsSystemlogMapper.findQsAlarmalogBy(dBname,id);
    }



    @Override
    public PageObject<QsSystemlog> findByPage(String dBname, Integer pageCurrent, Integer pageSize, String userCode, String type, String operationType, String starTime, String endTime) {
        if (pageCurrent==null ||pageCurrent<1) pageCurrent=1;
        if (pageSize==null || pageSize<5) pageSize=5;
        Integer  startIndex=(pageCurrent-1)*pageSize;
        Integer count = qsSystemlogMapper.findBycount(dBname, pageCurrent, pageSize, userCode, type, operationType, starTime, endTime);
        List<QsSystemlog> qsSystemlogs = qsSystemlogMapper.findByPage(dBname,startIndex,pageSize,userCode,type,operationType,starTime,endTime);
        return PagesUtil.getPageObject(qsSystemlogs,count,pageSize,pageCurrent);
    }

    @Override
    public List<QsSystemlog> findByIds(Integer [] ids) {
        List<String> list = new ArrayList<>();
        if (!ObjectUtil.isEmpty(ids)){
            for (int i = 0; i < ids.length; i++) {
                list.add(String.valueOf(ids[i]));
            }
        }
        LambdaQueryWrapper<QsSystemlog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(!ObjectUtil.hasEmpty(list),QsSystemlog::getId,list);
        List<QsSystemlog>  qsSystemlogList = qsSystemlogMapper.selectList(queryWrapper);
        return qsSystemlogList;
    }


    @Override
    public List<Qsmsrw> findById(String dBname, String[] rwid) {
        return qsSystemlogMapper.findById(dBname,rwid);
    }
}
