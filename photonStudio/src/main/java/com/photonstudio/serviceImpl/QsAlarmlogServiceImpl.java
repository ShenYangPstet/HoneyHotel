package com.photonstudio.serviceImpl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.AlarmtypeMapper;
import com.photonstudio.mapper.PageStyleMapper;
import com.photonstudio.mapper.QsAlarmlogMapper;
import com.photonstudio.pojo.*;
import com.photonstudio.pojo.vo.AlarmChart;
import com.photonstudio.pojo.vo.Curve;
import com.photonstudio.service.AlarmtypeService;
import com.photonstudio.service.DrtypeinfoService;
import com.photonstudio.service.PicService;
import com.photonstudio.service.QsAlarmlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QsAlarmlogServiceImpl extends ServiceImpl<QsAlarmlogMapper, QsAlarmlog> implements QsAlarmlogService {
    @Autowired
    private QsAlarmlogMapper qsAlarmlogMapper;
    @Autowired
    private AlarmtypeService alarmtypeService;
    @Autowired
    private PageStyleMapper pageStyleMapper;
    @Autowired
    private PicService picService;

    @Autowired
    private AlarmtypeMapper alarmtypeMapper;
    @Autowired
    private DrtypeinfoService drtypeinfoService;
    @Override
    public PageObject<QsAlarmlog> findObject(String dBname, Integer pageCurrent,
                                             Integer pageSize, String alarmtypelevel,
                                             String alarmanswer, Integer drtypeid, Date startTime, Date endTime, Integer floorId, String drName) {
        if (pageCurrent == null || pageCurrent < 1) pageCurrent = 1;
        if (pageSize == null || pageSize < 5) pageSize = 5;
        List<Integer> drTypeIds = drtypeinfoService.listDrTypeIdByParentId(drtypeid);
        List<QsAlarmlog> list = new ArrayList<>();
        int rowCount = qsAlarmlogMapper.getRowCount(dBname, 1, alarmtypelevel, alarmanswer, drTypeIds, startTime, endTime, floorId, drName);
        int startIndex = (pageCurrent - 1) * pageSize;
        list = qsAlarmlogMapper.findObject(dBname, 1, startIndex, pageSize, alarmtypelevel, alarmanswer, drTypeIds, startTime, endTime, floorId, drName);
        return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
    }

    @Override
    public PageObject<QsAlarmlog> findObjectByAlarm(String dBname, String alarmtypelevel, String alarmanswer,
                                                    Integer[] drtypeid, Integer pageCurrent, Integer pageSize, Integer floorId, String drName) {
        if (pageCurrent == null || pageCurrent < 1) pageCurrent = 1;
        if (pageSize == null || pageSize < 5) pageSize = 5;
        List<Integer> parentids=new ArrayList<>();
        if (ObjectUtil.isNotEmpty(drtypeid)) {
        Collections.addAll(parentids, drtypeid);
        }
        List<Integer> drTypeIds = drtypeinfoService.listDrTypeIdByParentId(parentids);
        List<QsAlarmlog> list = new ArrayList<>();
        int rowCount = qsAlarmlogMapper.getRowCountByAlarm(dBname, alarmtypelevel, alarmanswer, drTypeIds, floorId, drName);
        int startIndex = (pageCurrent - 1) * pageSize;
        list = qsAlarmlogMapper.findObjectByAlarm(dBname, alarmtypelevel, alarmanswer, drTypeIds, startIndex, pageSize, floorId, drName);
        return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);

    }

    @Override
    public int saveObject(String dBname, QsAlarmlog qsAlarmlog) {
        int rows = 0;
        try {
            rows = qsAlarmlogMapper.insertObject(dBname, qsAlarmlog);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("保存失败");
        }
        return rows;
    }

    @Override
    public int updateObject(String dBname, QsAlarmlog qsAlarmlog) {
        int rows = 0;
        try {
            qsAlarmlog.setUsertime(new Date());
            rows = qsAlarmlogMapper.updateObject(dBname, qsAlarmlog);
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
            qsAlarmlogMapper.deleteObjectById(dBname, ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除失败");
        }
        return rows;
    }

    @Override
    public List<QsAlarmlog> findAll(String dBname, Date startTime, Date endTime) {
        List<QsAlarmlog> list = new ArrayList<>();
        try {
            list = qsAlarmlogMapper.findObject(dBname, 1, null, null, null, null, null, startTime, endTime, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询失败");
        }
        return list;
    }

    @Override
    public List<EcharsObject> findEchars(String dBname) {
        List<EcharsObject> list = new ArrayList<>();
        try {
            list = qsAlarmlogMapper.findEchars(dBname);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询失败");
        }
        return list;
    }

    @Override
    public List<EcharsObject> findEcharsByAlarm(String dBname) {
        List<EcharsObject> list = new ArrayList<>();
        try {
            list = qsAlarmlogMapper.findEcharsByAlarm(dBname);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询失败");
        }
        return list;
    }

    @Override
    public List<QsAlarmlog> findObjectByDrid(String dBname, Integer pageCurrent, Integer pageSize, Integer drid,
                                             String alarmtypelevel, String alarmanswer, Date startTime, Date endTime) {
        List<QsAlarmlog> list = new ArrayList<>();
        try {
            list = qsAlarmlogMapper.findObjectByDrid(dBname, 1, pageCurrent, pageSize, drid,
                    alarmtypelevel, alarmanswer, startTime, endTime);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<QsAlarmlog> findAlarmListHome(String dBname) {
        // TODO Auto-generated method stub
        return qsAlarmlogMapper.findObjectByAlarm(dBname, null, null, null, 0, 20, null, null);
    }

    @Override
    public List<Pic> queryPicByDrid(String dBname, Integer drid) {
        // TODO Auto-generated method stub
        List<PageStyle> pageStyles = pageStyleMapper.selectList(null);
        List<Pic> drIdList = new ArrayList<>();
        for (PageStyle pageStyle : pageStyles) {
            List<JSONObject> list = JSONArray.parseArray(pageStyle.getCanvasData(), JSONObject.class);
            for (JSONObject jsonObject : list) {
                if (jsonObject.getString("component").equals("Device")) {
                    Integer dataDrId = jsonObject.getJSONObject("drInfo").getInteger("drid");
                    if (dataDrId.equals(drid)) {
                        drIdList.add(picService.findObjectById(dBname, pageStyle.getPicId()));
                    }
                }
            }
        }
        return drIdList;
    }

    @Override
    public List<EcharsZ> findSumAlarmBytype(String dBname, String type, Date date) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub

        List<EcharsZ> alarmEcharsList = new ArrayList<>();
        List<Alarmtype> alarmtypesList = alarmtypeService.findAll(dBname);
        for (Alarmtype alarmtype : alarmtypesList) {
            //float sumvalueFlag = 0;
            int flag = 1;
            EcharsZ alarmEchars = new EcharsZ();
            Map<String, String> alarmtmap = new LinkedHashMap<String, String>();
            //String alarmnames = energyinfo.getAlarmtypename();
            String alarmtypename = alarmtype.getAlarmtypename();
            alarmEchars.setName(alarmtypename);
            //String[] tagnameArry = tagnames.split(",",-1);
            List<String> dateList = getDateYMD(date, type);
            System.out.println("dateList==" + dateList);
            for (int i = 0; i < dateList.size() - 1; i++) {
                //int sumTagvalue = 0;
                //for(String tagname : tagnameArry)
                //{
                int sumStr = qsAlarmlogMapper.findEcharsByYMD(dBname,
                        alarmtype.getAlarmtypelevel(), dateList.get(i), dateList.get(i + 1));
                //int tagvlue=0;
                //if(tagvalueStr != null && !"".equals(tagvalueStr))
                //{
                //	tagvlue = Float.parseFloat(tagvalueStr);
                //}
                //sumTagvalue = sumTagvalue+tagvlue;
                //}
                //if(flag>0)
                //{
                //float value = sumTagvalue-sumvalueFlag;
                //if(value<0)
                //{
                //value=0;
                //}
                if (type.equals("1")) {
                    alarmtmap.put(flag + "月", String.valueOf(sumStr));
                } else if (type.equals("2")) {
                    alarmtmap.put(flag + "日", String.valueOf(sumStr));
                } else {
                    alarmtmap.put(flag + "时", String.valueOf(sumStr));
                }
                //}
                //sumvalueFlag=sumTagvalue;
                flag++;
            }
            alarmEchars.setQeuryMap(alarmtmap);
            alarmEchars.setDateStr(getDate(date, type));
            alarmEcharsList.add(alarmEchars);
        }
        return alarmEcharsList;
    }

    private String getDate(Date date, String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int ymd = 0;
        String dateStr = "";
        if (type.equals("1"))//年
        {
            ymd = calendar.get(Calendar.YEAR);
            dateStr = ymd + "年";
        } else if (type.equals("2"))//月
        {
            int year = calendar.get(Calendar.YEAR);
            ymd = calendar.get(Calendar.MONTH) + 1;
            dateStr = year + "年" + ymd + "月";
        } else//日
        {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            ymd = calendar.get(Calendar.DATE);
            dateStr = year + "年" + month + "月" + ymd + "日";
        }
        return dateStr;
    }

    private List<String> getDateYMD(Date date, String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int ymd = 0;
        List<String> dateList = new ArrayList<String>();
        if (type.equals("1"))//年
        {
            ymd = calendar.get(Calendar.YEAR);
            dateList = getDateYMDmonth(ymd);
        } else if (type.equals("2"))//月
        {
            int year = calendar.get(Calendar.YEAR);
            ymd = calendar.get(Calendar.MONTH) + 1;
            dateList = getDateYMDday(year, ymd);
        } else//日
        {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            ymd = calendar.get(Calendar.DATE);
            dateList = getDateYMDhour(year, month, ymd);
        }
        return dateList;
    }

    private List<String> getDateYMDhour(int year, int month, int ymd) {
        // TODO Auto-generated method stub
        List<String> dateList = new ArrayList<String>();
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = year + "-" + month + "-" + ymd + " 00:00:00";
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dateList.add(formatter.format(date));
        for (int i = 1; i < 24 + 1; i++) {
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            rightNow.add(Calendar.HOUR, i);
            Date dt = rightNow.getTime();
            dateList.add(formatter.format(dt));
        }
        return dateList;
    }

    private List<String> getDateYMDday(int year, int ymd) {
        // TODO Auto-generated method stub
        List<String> dateList = new ArrayList<String>();
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = year + "-" + ymd + "-1 00:00:00";
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dateList.add(formatter.format(date));
        int days = getdays(ymd, year);
        for (int i = 1; i < days + 1; i++) {
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            rightNow.add(Calendar.DATE, i);
            Date dt = rightNow.getTime();
            dateList.add(formatter.format(dt));
        }
        return dateList;
    }


    private List<String> getDateYMDmonth(int ymd) {
        // TODO Auto-generated method stub
        List<String> dateList = new ArrayList<String>();
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = ymd + "-1-1 00:00:00";
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dateList.add(formatter.format(date));
        for (int i = 1; i < 13; i++) {
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            rightNow.add(Calendar.MONTH, i);
            Date dt = rightNow.getTime();
            dateList.add(formatter.format(dt));
        }
        return dateList;
    }

    private int getdays(int ymd, int year) {
        // TODO Auto-generated method stub
        switch (ymd) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            //对于2月份需要判断是否为闰年
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }

            default:
                return 0;

        }
    }

    @Override
    public PageObject<QsAlarmlog> findObjectByHome(List<EcharsObject> dBlist, String alarmtypelevel, Date startTime, Date endTime, Integer pageCurrent,
                                                   Integer pageSize) {
        // TODO Auto-generated method stub
        if (pageCurrent == null || pageCurrent < 1) pageCurrent = 1;
        if (pageSize == null || pageSize < 5) pageSize = 5;
        List<QsAlarmlog> list = new ArrayList<>();
        int rowCount = qsAlarmlogMapper.getRowCountByHome(dBlist, alarmtypelevel, startTime, endTime);
        int startIndex = (pageCurrent - 1) * pageSize;
        list = qsAlarmlogMapper.findObjectByHome(dBlist, alarmtypelevel, startTime, endTime, startIndex, pageSize);
        return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
    }

    @Override
    public PageObject<QsAlarmlog> findAlarmByHome(List<EcharsObject> dBlist, String alarmtypelevel, Integer pageCurrent,
                                                  Integer pageSize) {
        // TODO Auto-generated method stub
        if (pageCurrent == null || pageCurrent < 1) pageCurrent = 1;
        if (pageSize == null || pageSize < 5) pageSize = 5;
        List<QsAlarmlog> list = new ArrayList<>();
        int rowCount = qsAlarmlogMapper.getAlarmRowCountByHome(dBlist, alarmtypelevel);
        int startIndex = (pageCurrent - 1) * pageSize;
        list = qsAlarmlogMapper.findAlarmByHome(dBlist, alarmtypelevel, startIndex, pageSize);
        return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
    }

    @Override
    public List<EcharsObject> findEcharsHome(List<String> dBlist) {
        // TODO Auto-generated method stub
        List<EcharsObject> list = new ArrayList<>();
        try {
            list = qsAlarmlogMapper.findEcharsHome(dBlist);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询失败");
        }
        return list;
    }

    @Override
    public List<EcharsObject> findByEcharSum() {
        JSONObject jsonObject = new JSONObject();
        List<Alarmtype> alarmTypeList = alarmtypeMapper.selectList(new LambdaQueryWrapper<Alarmtype>().orderByAsc(Alarmtype::getAlarmtypelevel));
        List<QsAlarmlog> alarmLogList = qsAlarmlogMapper.EcharsCountList();
        Map<String, Long> map = alarmLogList.stream().collect(Collectors.groupingBy(QsAlarmlog::getAlarmLevel, Collectors.counting()));
        List<EcharsObject> ECharsObjectList = new ArrayList<>();
        for (Alarmtype alarmtype : alarmTypeList) {
            EcharsObject echarsObject = new EcharsObject().
                    setName(alarmtype.getAlarmtypeExpain()).
                    setValue(String.valueOf(map.get(alarmtype.getAlarmtypelevel())))
                    .setAlarmtypelevel(alarmtype.getAlarmtypelevel());
            if (ObjectUtil.isEmpty(map.get(alarmtype.getAlarmtypelevel()))) {
                echarsObject.setValue("1");
            }
            ECharsObjectList.add(echarsObject);
        }
        return ECharsObjectList;
    }

    @Override
    public AlarmChart getAlarmChart() {
        List<QsAlarmlog> qsAlarmLogList = lambdaQuery()
                .eq(QsAlarmlog::getAlarmstate, 1)
                .between(QsAlarmlog::getTime, DateUtil.offsetDay(new Date(), -59), new Date())
                .list();
        Map<Date, Long> date2Count = qsAlarmLogList.stream()
                .map(qsAlarmLog -> qsAlarmLog.setTime(DateUtil.beginOfDay(qsAlarmLog.getTime())))
                .collect(Collectors.groupingBy(QsAlarmlog::getTime, Collectors.counting()));
        DateTime middleTime = DateUtil.offsetDay(DateUtil.beginOfDay(new Date()), -29);
        long lastMonthTotal = qsAlarmLogList.stream()
                .filter(qsAlarmLog -> middleTime.isAfter(qsAlarmLog.getTime()))
                .count();
        long thisMonthTotal = qsAlarmLogList.stream()
                .filter(qsAlarmLog -> middleTime.isBeforeOrEquals(qsAlarmLog.getTime()))
                .count();
        List<String> xList = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            xList.add(DateUtil.format(middleTime, "MM-dd"));
            values.add(Convert.toInt(date2Count.get(middleTime)));
            middleTime.offset(DateField.DAY_OF_YEAR, 1);
        }
        return (AlarmChart) new AlarmChart().setTotalAlarm(qsAlarmLogList.size())
                .setXList(xList)
                .setCurveList(CollUtil.toList(new Curve().setValues(values)))
                .setMonthCompareRate(lastMonthTotal == 0 ? null : (thisMonthTotal - lastMonthTotal) / (double) lastMonthTotal);
    }

    @Override
    public JSONObject getAlarmCount(String dBname) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("AlarmCount",qsAlarmlogMapper.getRowCountByAlarm(dBname, null, null, null, null, null));
        return jsonObject;
    }
}
