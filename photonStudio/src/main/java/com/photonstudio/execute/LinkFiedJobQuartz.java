package com.photonstudio.execute;

import cn.hutool.core.util.ObjectUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.QsLdTjMapUtil;
import com.photonstudio.common.enums.SendType;
import com.photonstudio.pojo.*;
import com.photonstudio.pojo.vo.SendVo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.mapper.AppmanagerMapper;
import com.photonstudio.mapper.PicMapper;
import com.photonstudio.mapper.QslinkMapper;

public class LinkFiedJobQuartz implements Job {

    @Autowired
    private AppmanagerMapper appmanagerMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private QslinkMapper qslinkMapper;
    @Autowired
    private PicMapper picMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        // TODO Auto-generated method stub
        JobDataMap jdMap = context.getJobDetail().getJobDataMap();
        String dBname = jdMap.getString("dBname");
        Integer rwId = jdMap.getInt("rwId");
        Integer appid = jdMap.getInt("appid");
        Appmanager appmanager = appmanagerMapper.findObjectByAppid(appid);
        String url = "http://" + appmanager.getIpaddr() + ":" + appmanager.getAppport();
        Qslinkrw qslinkrw = qslinkMapper.findLinkRwByRwid(dBname, rwId);
        if (null != qslinkrw) {
            //System.out.println(qslinkrw);
            Integer tjId = qslinkrw.getTjId();
            Integer jgId = qslinkrw.getJgId();
            List<Qslinktjinfo> tjinfolist = qslinkMapper.findLinkTjinfo(dBname, tjId);
            //System.out.println("条件==="+tjinfolist);
            if (tjinfolist.size() > 0) {
                int flag = 0;
                for (Qslinktjinfo tjinfo : tjinfolist) {
                    String tagname = tjinfo.getTagName();
                    float minValue = 0;
                    float maxValue = 0;
                    try {
                        minValue = Float.valueOf(tjinfo.getMinValue());
                        maxValue = Float.valueOf(tjinfo.getMaxValue());
                    } catch (NumberFormatException e) {
                        System.out.println("任务条件参数设置错误====" + "项目名称===" + dBname + "===条件参数===" + tjinfo);
                        continue;
                    }
                    Integer relation = tjinfo.getRelation();
                    String dbValueStr = qslinkMapper.findRegDbValue(dBname, tagname);
                    String key = appid + "_" + tjinfo.getId();
                    String oldValue = QsLdTjMapUtil.get(key);
                    if (dbValueStr == null || dbValueStr.equals("") || (oldValue != null && oldValue.equals(dbValueStr))) {
                        continue;
                    }
                    float dbValue = Float.valueOf(dbValueStr);
                    QsLdTjMapUtil.put(key, dbValueStr);
                    if (relation == 0)//or
                    {
                        if (dbValue < minValue || dbValue > maxValue) {
                            flag = 1;
                            break;
                        }
                    } else {
                        if (dbValue >= minValue && dbValue <= maxValue) {
                            flag = 1;
                            break;
                        }
                    }
                }

                if (flag == 1) {
                    List<Qslinkjginfo> list = qslinkMapper.findLinkJginfoByJgid(dBname, jgId);
                    //System.out.println(list);
                    if (list.size() > 0) {
                        for (Qslinkjginfo qslinkjginfo : list) {
                            QsLinkLog qsLinkLog = new QsLinkLog();
                            try {
                                qsLinkLog.setJgId(jgId);
                                qsLinkLog.setJgName(qslinkjginfo.getJgName());
                                qsLinkLog.setRwId(rwId);
                                qsLinkLog.setRwName(qslinkrw.getRwName());
                                qsLinkLog.setSendtime(new Date());
                                if (!StringUtils.isEmpty(qslinkjginfo.getTagName())
                                        && !StringUtils.isEmpty(qslinkjginfo.getValue())) {
//								try
//								{
                                    Map<String, String> sendValueMap = new HashMap<>();
                                    sendValueMap.put(qslinkjginfo.getTagName(), qslinkjginfo.getValue());
                                    SendVo sendVo = new SendVo(SendType.CONSOLE.sendType, sendValueMap);
                                    HttpHeaders headers = new HttpHeaders();
                                    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                                    headers.setContentType(type);
                                    HttpEntity<String> formEntity = new HttpEntity<>(JSONObject.toJSONString(sendVo), headers);
                                    //System.out.println("link tagname="+qslinkjginfo.getTagName()+"||value="+qslinkjginfo.getValue());
                                    //restTemplate.getForObject(url+"/caiji/reg/" + qslinkjginfo.getTagName() + "/" + qslinkjginfo.getValue(), SysResult.class);
                                    restTemplate.postForEntity(url+"/caiji/ConsoleSend", formEntity, String.class);
                                    qsLinkLog.setRegName(qslinkjginfo.getRegName());
                                    qsLinkLog.setRegValue(qslinkjginfo.getValue());
//								}catch (Exception e) {
//									// TODO: handle exception
//									e.printStackTrace();
//								}
                                }
                                if (ObjectUtil.isNotEmpty(qslinkjginfo.getSpId())) {
                                    //System.out.println("发送SPID=="+qslinkjginfo.getSpId()+url);
                                    restTemplate.getForObject(url + "/caiji/sp/" + qslinkjginfo.getSpId(), SysResult.class);
                                    qsLinkLog.setSpId(qslinkjginfo.getSpId());
                                }
                                if (qslinkjginfo.getPicId() != null) {
                                    Pic pic = picMapper.findObjectById(dBname, qslinkjginfo.getPicId());
                                    Integer picmodeid = -1;
                                    String parameter = "";
                                    if (ObjectUtil.isNotEmpty(qslinkjginfo.getPicParameter()))
                                        parameter = "?parameter=" + qslinkjginfo.getPicParameter().trim();
                                    if (pic != null) picmodeid = pic.getPicmodeid();
                                    restTemplate.getForObject(url + "/caiji/pic/" + qslinkjginfo.getPicId() + "/" + picmodeid + parameter, SysResult.class);
                                    qsLinkLog.setPicId(qslinkjginfo.getPicId());
                                    if (pic != null) qsLinkLog.setPicName(pic.getPicname());
                                    qsLinkLog.setPicParameter(qslinkjginfo.getPicParameter());


                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                                qsLinkLog.setExecuteState(1);
                                e.printStackTrace();
                            }
                            qsLinkLog.setExecuteState(0);
                            qslinkMapper.saveLog(dBname, qsLinkLog);
                        }
                    }
                }
            }
        }
    }

}
