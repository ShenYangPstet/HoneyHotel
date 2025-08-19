package com.photonstudio.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.photonstudio.mapper.DrinfoMapper;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.mapper.RegMapper;
import com.photonstudio.mapper.RegdataMapper;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.Reg;
import com.photonstudio.pojo.Regdata;
import com.photonstudio.pojo.Subinfo;
import com.photonstudio.service.RegdataService;

@Service
public class RegdataServiceImpl implements RegdataService{
	@Autowired
	private RegdataMapper regdataMapper;
	@Autowired
	private RegMapper regMapper;

	@Autowired
	private DrinfoMapper drinfoMapper;
	@Override
	public List<Regdata> findObject(String dBname, String tagname, Date startTime, Date endTime) {
		List<Regdata>list =new ArrayList<>();
		list=regdataMapper.findObject(dBname,tagname,startTime,endTime);
		return list;
	}

	@Override
	public int saveObject(String dBname, String tagname, Regdata regdata) {
		int rows=0;
		try {
			rows=regdataMapper.insertObject(dBname,tagname,regdata);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, String tagname, Integer... ids) {
		int rows=0;
		try {
			rows=regdataMapper.deleteObjectById(dBname,tagname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, String tagname, Regdata regdata) {
		int rows=0;
		try {
			rows=regdataMapper.updateObject(dBname,tagname,regdata);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public List<Map<String, List<Regdata>>> findEcharsByTagname(String dBname, String regname,Date startTime, Date endTime,
			String... tagname) {
		String[] name=regname.split(",");
		List<Map<String, List<Regdata>>>list=new ArrayList<>();
		for (int i = 0; i < tagname.length; i++)
		 {

			if(!StringUtils.isEmpty(tagname[i])) {
				Map<String, List<Regdata>>map=new HashMap<>();
				List<Regdata>regdatalist=new ArrayList<>();
				regdatalist=regdataMapper.findObject(dBname+"_data",tagname[i],startTime,endTime);
				map.put(name[i], regdatalist);
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public List<Map<String, List<Regdata>>> findEcharsByTagnameNew(String dBname, String regname, Date startTime,
			Date endTime, String[] tagname) {
		// TODO Auto-generated method stub
		String[] name=regname.split(",");
		List<Map<String, List<Regdata>>>list=new ArrayList<>();
		List<Date> datelistDt = getBetweenDates(startTime,endTime);
		int size = datelistDt.size();
		for (int i = 0; i < tagname.length; i++)
		 {
			if(!StringUtils.isEmpty(tagname[i])) {
				Map<String, List<Regdata>>map=new HashMap<>();
				List<Regdata>regdatalist=new ArrayList<>();
				int cnt=1;
				if(size >1)
				{
					for(Date dt : datelistDt)
					{
						String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(dt);
						List<Regdata> regdatalistTmp = new ArrayList<>();
						if(cnt == 1)
						{
							regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,startTime,null,tagname[i]);
						}
						else if (cnt == size)
						{
							regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,endTime,tagname[i]);
						}
						else
						{
							regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,null,tagname[i]);
						}
						regdatalist.addAll(regdatalistTmp);
						cnt++;
					}
				}
				else
				{
					String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(datelistDt.get(0));
					regdatalist = regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,null,tagname[i]);
				}
				map.put(name[i], regdatalist);
				list.add(map);
			}
		}

		return list;
	}

	@Override
	public Map<String, List<Regdata>> exportNew(String dBname, String regname, Date startTime,
			Date endTime, String[] tagname) {
		// TODO Auto-generated method stub
		String[] name=regname.split(",");
		List<Date> datelistDt = getBetweenDates(startTime,endTime);
		int size = datelistDt.size();
		Map<String, List<Regdata>>map=new HashMap<>();
		for (int i = 0; i < tagname.length; i++)
		 {

			if(!StringUtils.isEmpty(tagname[i])) {
				List<Regdata>regdatalist=new ArrayList<>();
				int cnt=1;
				if(size >1)
				{
					for(Date dt : datelistDt)
					{
						String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(dt);
						List<Regdata> regdatalistTmp = new ArrayList<>();
						if(cnt == 1)
						{
							regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,startTime,null,tagname[i]);
						}
						else if (cnt == size)
						{
							regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,endTime,tagname[i]);
						}
						else
						{
							regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,null,tagname[i]);
						}
						regdatalist.addAll(regdatalistTmp);
						cnt++;
					}
				}
				else
				{
					String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(datelistDt.get(0));
					regdatalist = regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,null,tagname[i]);
				}
				map.put(name[i], regdatalist);
			}
		}
		return map;
	}

	@Override
	public List<JSONObject> findRegDataByRegs(String dBname, Date startTime, Date endTime, Integer[] regIds) {
		List<Reg> regList = regMapper.findRegById(dBname, regIds);
		Map<Integer,List<Reg>>map=new HashMap<>();
		List<Date> datelistDt = getBetweenDates(startTime,endTime);
		List<JSONObject> drRegObjList=new ArrayList<>();
		int size = datelistDt.size();
		for (Reg reg : regList) {
			if (map.get(reg.getDrId())==null)map.put(reg.getDrId(),new ArrayList<Reg>());
			map.get(reg.getDrId()).add(reg);
		}
		for (Map.Entry<Integer, List<Reg>> regEntry : map.entrySet()) {
			JSONObject drRegObj=new JSONObject();
			List<Map<String,List<Regdata>>> drRegList=new ArrayList<>();
			for (Reg reg : regEntry.getValue()) {
				if(!StringUtils.isEmpty(reg.getTagName())) {
					Map<String, List<Regdata>>dataMap=new HashMap<>();
					List<Regdata>regdatalist=new ArrayList<>();
					int cnt=1;
					if(size >1)
					{
						for(Date dt : datelistDt)
						{
							String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(dt);
							List<Regdata> regdatalistTmp = new ArrayList<>();
							if(cnt == 1)
							{
								regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,startTime,null,reg.getTagName());
							}
							else if (cnt == size)
							{
								regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,endTime,reg.getTagName());
							}
							else
							{
								regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,null,reg.getTagName());
							}
							regdatalist.addAll(regdatalistTmp);
							cnt++;
						}
					}
					else
					{
						String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(datelistDt.get(0));
						regdatalist = regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,null,reg.getTagName());
					}
					dataMap.put(reg.getRegName(), regdatalist);
					drRegList.add(dataMap);
				}
			}
			Drinfo drByDrid = drinfoMapper.findDrByDrid(dBname, regEntry.getKey());
			drRegObj.put(drByDrid.getDrname(),drRegList);
			drRegObjList.add(drRegObj);
		}
		return drRegObjList;
	}

	@Override
	public Map<String, List<String>> findReportByRegIds(String dBname, Integer times, Date startTime, Date endTime, Integer[] regids) {
		// TODO Auto-generated method stub
		Map<String, List<String>> resultMap = new HashMap<>();
		List<String> datelist = getDateList(startTime, endTime, times);
		List<Reg> listReg = regMapper.findRegById(dBname,regids);
		List<Date> datelistDt = getBetweenDates(startTime,endTime);
		List<String> listName = new ArrayList<>();
		listName.add("记录时间");
		for(Reg reg : listReg)
		{
			String[] regName = reg.getRegName().split(":",-1);
			String unit="";
			if(!StrUtil.isEmpty(reg.getRegUnits()))
			{
				unit=reg.getRegUnits();
			}
			if(regName.length<2)
			{
				listName.add(regName[0]+unit);
			}
			else
			{
				listName.add(regName[1]+unit);
			}

		}
		resultMap.put("report", listName);
		Map<String,Map<String,String>> timeVuleMap = new HashMap<>();
		List<Subinfo> subinfoList = regdataMapper.findsubinfo(dBname);
		Map<String,String> subinfoMap = new HashMap<>();
		for(Subinfo subinfo : subinfoList)
		{
			subinfoMap.put(subinfo.getSubid()+"_"+subinfo.getValue(), subinfo.getText());
		}
		for(Reg reg : listReg)
		{
			String tagName = reg.getTagName();
			if(null != tagName && !"".equals(tagName))
			{
				Map<String,String> vuleMap = new HashMap<>();
				//List<Regdata> regdatalist = regdataMapper.findObject(dBname+"_data",tagName,startTime,endTime);
				List<Regdata> regdatalist =  new ArrayList<>();
				for(Date dt : datelistDt)
				{
					String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(dt);
					List<Regdata> regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,null,tagName);
					regdatalist.addAll(regdatalistTmp);
				}
				if(null != regdatalist)
				{
					for(Regdata regdata : regdatalist)
					{
						vuleMap.put(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(regdata.getTime()), regdata.getTagvalue());
					}
				}
				timeVuleMap.put(tagName, vuleMap);
			}
		}

		for(String datestr : datelist)
		{
			List<String> listValue = new ArrayList<String>();
			for(Reg reg : listReg)
			{
				String tagName = reg.getTagName();
				String value = reg.getTagValue();
				if(null != tagName && !"".equals(tagName)&&timeVuleMap.get(tagName).get(datestr)!=null)
				{
					value = timeVuleMap.get(tagName).get(datestr);
				}
				if(null != reg.getRegSub() && !"".equals(reg.getRegSub()))
				{
					value = subinfoMap.get(reg.getRegSub()+"_"+value);
				}
				listValue.add(value);
			}
			resultMap.put(datestr, listValue);
		}

		return resultMap;
	}

    @Override
    public JSONObject findCurvetByRegIds(String dBname, Integer times, Date startTime, Date endTime, Integer[] regids) {
		String format = DateUtil.format(startTime, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		String dateTimeRoundDown = dateTimeRoundDown(format);//向上取整
		Date dateTime = DateUtil.parse(dateTimeRoundDown);
        JSONObject curve = new JSONObject();
        List<String> datelist = getDateList(dateTime, endTime, times);
		List<Reg> listReg = regMapper.findRegById(dBname, regids);
        List<Date> datelistDt = getBetweenDates(dateTime, endTime);
        Map<String, Map<String, String>> timeVuleMap = new HashMap<>();
        curve.put("curveX", datelist);
        for (Reg reg : listReg) {
            String tagName = reg.getTagName();
            if (null != tagName && !"".equals(tagName)) {
                Map<String, String> vuleMap = new HashMap<>();
                //List<Regdata> regdatalist = regdataMapper.findObject(dBname+"_data",tagName,startTime,endTime);
                List<Regdata> regdatalist = new ArrayList<>();
                for (Date dt : datelistDt) {
                    String tableName = "tagdata_" + new SimpleDateFormat("yyyyMMdd").format(dt);
                    List<Regdata> regdatalistTmp = regdataMapper.findRegDataBYDate(dBname + "_data", tableName, null, null, tagName);
                    regdatalist.addAll(regdatalistTmp);
                }
                if (null != regdatalist) {
                    for (Regdata regdata : regdatalist) {
                        vuleMap.put(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(regdata.getTime()), regdata.getTagvalue());
                    }
                }
                timeVuleMap.put(tagName, vuleMap);
            }
        }
        List<JSONObject> curveY = new ArrayList<>();
        for (Reg reg : listReg) {
            List<String> listValue = new ArrayList<>();
            JSONObject regData = new JSONObject();
            String tagName = reg.getTagName();
            for (String datestr : datelist) {
                String value = reg.getTagValue();
                if (null != tagName && !"".equals(tagName) && timeVuleMap.get(tagName).get(datestr) != null) {
                    value = timeVuleMap.get(tagName).get(datestr);
                }
                listValue.add(value);
            }
            regData.put(reg.getRegId() + "_" + reg.getRegName(), listValue);
            curveY.add(regData);
        }
        curve.put("curveY", curveY);
        return curve;
    }

	@Override
	public List<Map<String, List<Regdata>>> findEcharsByDrid(String dBname,Integer drid, Date startTime, Date endTime) {
		//System.out.println("endTime===>"+endTime);
		List<Reg>reglist=regMapper.findRegByDrid(dBname, drid, null);
		if(startTime==null||endTime==null) {
			Calendar cal = Calendar.getInstance();
			 cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
			 cal.set(Calendar.MINUTE, 0);//控制分
			 cal.set(Calendar.SECOND, 0);//控制秒
			startTime=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sdf.format(startTime));
			endTime=new Date();
		}
		List<Map<String, List<Regdata>>>list=new ArrayList<>();
		List<Date> datelistDt = getBetweenDates(startTime,endTime);
		int size = datelistDt.size();
		for (Reg name : reglist) {
			Map<String, List<Regdata>>map=new HashMap<>();
			List<Regdata>regdatalist=new ArrayList<>();
			String tagname=name.getTagName();
			String regname=name.getRegName();
			if(!StringUtils.isEmpty(tagname)) {
				//String savetime=regdataMapper.findSavetimeByTagname(dBname,tagname);
//				regdatalist=regdataMapper.findObject(dBname+"_data",tagname,startTime,endTime);
				int cnt=1;
				if(size >1)
				{
					for(Date dt : datelistDt)
					{
						String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(dt);
						List<Regdata> regdatalistTmp = new ArrayList<>();
						if(cnt == 1)
						{
							regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,startTime,null,tagname);
						}
						else if (cnt == size)
						{
							regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,endTime,tagname);
						}
						else
						{
							regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,null,tagname);
						}
						regdatalist.addAll(regdatalistTmp);
						cnt++;
					}
					map.put(regname, regdatalist);
					list.add(map);
				}
				else
				{
					String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(datelistDt.get(0));
					regdatalist = regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,null,tagname);
					map.put(regname, regdatalist);
					list.add(map);
				}
			}else {
				map.put(regname+"_"+"", regdatalist);
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public List<Map<String, List<Regdata>>> findRegdataById(String dBname, Integer[] ids) {
			if(ids==null||ids.length==0)throw new IllegalArgumentException("未绑定变量");
			Calendar cal = Calendar.getInstance();
			 cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
			 cal.set(Calendar.MINUTE, 0);//控制分
			 cal.set(Calendar.SECOND, 0);//控制秒
			Date startTime=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sdf.format(startTime));
			Date endTime=new Date();
			List<Reg>list=regMapper.findRegById(dBname, ids);
			List<Map<String, List<Regdata>>>taglist=new ArrayList<>();
			for (Reg reg : list) {
				Map<String, List<Regdata>>map=new HashMap<>();
				List<Regdata>regdatalist=new ArrayList<>();
				String tagname=reg.getTagName();
				if(!StringUtils.isEmpty(tagname)) {
					regdatalist=regdataMapper.findObject(dBname+"_data",tagname,startTime,endTime);
					map.put(tagname, regdatalist);
					taglist.add(map);
				}
			}
		return taglist;
	}

	@Override
	public Map<String, List<String>> findDridReport(String dBname, Integer drid, Integer times, Date startTime,
			Date endTime) {
		// TODO Auto-generated method stub
		Map<String, List<String>> resultMap = new HashMap<>();
		List<String> datelist = getDateList(startTime, endTime, times);
		List<Reg> listReg = regdataMapper.findRegByDrid(dBname,drid);
		List<String> listName = new ArrayList<String>();
		listName.add("记录时间");
		for(Reg reg : listReg)
		{
			String[] regName = reg.getRegName().split(":",-1);
			if(null == reg.getRegUnits())
			{
				reg.setRegUnits("");
			}
			if(regName.length<2)
			{
				listName.add(regName[0]+reg.getRegUnits());
			}
			else
			{
				listName.add(regName[1]+reg.getRegUnits());
			}

		}
		resultMap.put("report", listName);
		Map<String,Map<String,String>> timeVuleMap = new HashMap<>();
		List<Subinfo> subinfoList = regdataMapper.findsubinfo(dBname);
		Map<String,String> subinfoMap = new HashMap<>();
		for(Subinfo subinfo : subinfoList)
		{
			subinfoMap.put(subinfo.getSubid()+"_"+subinfo.getValue(), subinfo.getText());
		}
		for(Reg reg : listReg)
		{
			String tagName = reg.getTagName();
			if(null != tagName && !"".equals(tagName))
			{
				Map<String,String> vuleMap = new HashMap<>();
				List<Regdata> regdatalist = regdataMapper.findObject(dBname+"_data",tagName,startTime,endTime);
				if(null != regdatalist)
				{
					for(Regdata regdata : regdatalist)
					{
						vuleMap.put(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(regdata.getTime()), regdata.getTagvalue());
					}
				}
				timeVuleMap.put(tagName, vuleMap);
			}
		}

		for(String datestr : datelist)
		{
			List<String> listValue = new ArrayList<String>();
			for(Reg reg : listReg)
			{
				String tagName = reg.getTagName();
				String value = reg.getTagValue();
				if(null != tagName && !"".equals(tagName))
				{
					value = timeVuleMap.get(tagName).get(datestr);
				}
				if(null != reg.getRegSub() && !"".equals(reg.getRegSub()))
				{
					value = subinfoMap.get(reg.getRegSub()+"_"+value);
				}
				listValue.add(value);
			}
			resultMap.put(datestr, listValue);
		}

		return resultMap;
	}

	@Override
	public Map<String, List<String>> findDridReportNew(String dBname, Integer drid, Integer times, Date startTime,
			Date endTime) {
		// TODO Auto-generated method stub
		Map<String, List<String>> resultMap = new HashMap<>();
		List<String> datelist = getDateList(startTime, endTime, times);
		List<Date> datelistDt = getBetweenDates(startTime,endTime);
		List<Reg> listReg = regdataMapper.findRegByDrid(dBname,drid);
		List<String> listName = new ArrayList<String>();
		listName.add("记录时间");
		for(Reg reg : listReg)
		{
			String[] regName = reg.getRegName().split(":",-1);
			if(null == reg.getRegUnits())
			{
				reg.setRegUnits("");
			}
			if(regName.length<2)
			{
				listName.add(regName[0]+reg.getRegUnits());
			}
			else
			{
				listName.add(regName[1]+reg.getRegUnits());
			}

		}
		resultMap.put("report", listName);
		Map<String,Map<String,String>> timeVuleMap = new HashMap<>();
		List<Subinfo> subinfoList = regdataMapper.findsubinfo(dBname);
		Map<String,String> subinfoMap = new HashMap<>();
		for(Subinfo subinfo : subinfoList)
		{
			subinfoMap.put(subinfo.getSubid()+"_"+subinfo.getValue(), subinfo.getText());
		}
		for(Reg reg : listReg)
		{
			String tagName = reg.getTagName();
			if(null != tagName && !"".equals(tagName))
			{
				Map<String,String> vuleMap = new HashMap<>();
				//List<Regdata> regdatalist = regdataMapper.findObject(dBname+"_data",tagName,startTime,endTime);
				List<Regdata> regdatalist =  new ArrayList<>();
				for(Date dt : datelistDt)
				{
					String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(dt);
					List<Regdata> regdatalistTmp =  regdataMapper.findRegDataBYDate(dBname+"_data",tableName,null,null,tagName);
					regdatalist.addAll(regdatalistTmp);
				}
				if(null != regdatalist)
				{
					for(Regdata regdata : regdatalist)
					{
						vuleMap.put(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(regdata.getTime()), regdata.getTagvalue());
					}
				}
				timeVuleMap.put(tagName, vuleMap);
			}
		}

		for(String datestr : datelist)
		{
			List<String> listValue = new ArrayList<String>();
			for(Reg reg : listReg)
			{
				String tagName = reg.getTagName();
				String value = reg.getTagValue();
				if(null != tagName && !"".equals(tagName))
				{
					value = timeVuleMap.get(tagName).get(datestr);
				}
				if(null != reg.getRegSub() && !"".equals(reg.getRegSub()))
				{
					value = subinfoMap.get(reg.getRegSub()+"_"+value);
				}
				listValue.add(value);
			}
			resultMap.put(datestr, listValue);
		}

		return resultMap;
	}

	public List<String> getDateList(Date startTime,Date endTime,Integer times)
	{
		List<String> datelist = new ArrayList<String>();
		Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(startTime);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endTime);
		while(calEnd.after(calBegin))
		{
			switch (times) {//0--间隔5分钟一次 1--间隔半小时 2--间隔1小时 3--间隔2小时
				case 0:
					calBegin.add(Calendar.MINUTE, 5);break;
				case 1:
					calBegin.add(Calendar.MINUTE, 30);break;
				case 2:
					calBegin.add(Calendar.HOUR, 1);break;
				case 3:
					calBegin.add(Calendar.HOUR, 2);break;
			}
			datelist.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calBegin.getTime()));
		}
		//System.out.println("datelist=="+datelist);
		return datelist;
	}

	/**
	 * 获取两个日期之间的日期，包括开始结束日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	private List<Date> getBetweenDates(Date start, Date end) {
	    List<Date> result = new ArrayList<Date>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);

	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    result.add(start);
	    while (tempStart.before(tempEnd)) {
	        result.add(tempStart.getTime());
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return result;
	}

	@Override
	public List<Integer> findMorenDrid(String dBname) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		Drtypeinfo typeid1 = regdataMapper.gettypeid1(dBname);
		Integer drtypeid;
		if(typeid1 != null)
		{
			while(true)
			{
				drtypeid = typeid1.getDrtypeid();
				typeid1 = regdataMapper.gettypeid2(dBname,drtypeid);
				if(typeid1 == null)
				{
					list.add(drtypeid);
					break;
				}
			}
		}
		return null;
	}


	/**
	 * 把日期时间的分向下取整， 例如： 2019-09-19 10:49  --->  2019-09-19 10:40 （分向下取整）
	 *
	 *     返回 2019-09-19 10:40:00 格式的， （秒为00）
	 * **/
	public static String dateTimeRoundDown(String inputDateTime){
		String longTime = "";
		try{
			//TODO String longTime  格式是：2019-09-14 11:00
			if (!TextUtils.isEmpty(inputDateTime)){
				String firstStr = inputDateTime.substring(0,inputDateTime.indexOf(":") + 1);
				String minuteStr = "";
				int hasDot = 0;
				for (int i = 0; i < inputDateTime.length(); i++) {
					String charstr = inputDateTime.substring(i,i+1);
//                    LogUtil.e(TAG,"获得选中的时间 原始的日期时间 charstr:" + charstr + "。");
					if (":".equalsIgnoreCase(charstr)){
						hasDot ++;
					}
				}
//                LogUtil.e(TAG,"获得选中的时间 原始的日期时间(包含:的个数) hasDot: " + hasDot);
				if (hasDot == 1){
					minuteStr = inputDateTime.substring(inputDateTime.indexOf(":") + 1);
				}else if (hasDot == 2){
					minuteStr = inputDateTime.substring(inputDateTime.indexOf(":") + 1 , inputDateTime.lastIndexOf(":"));
//                    LogUtil.e(TAG,"获得选中的时间 原始的日期时间(包含:的个数) hasDot == 2  minuteStr: " + minuteStr);
				}
//                LogUtil.e(TAG,"获得选中的时间 minuteStr: " + minuteStr);
				int minuteInt = Integer.parseInt(minuteStr);
//                LogUtil.e(TAG,"获得选中的时间 minuteInt: " + minuteInt);
				int minuteInt2 = (minuteInt / 10) * 10;
//                LogUtil.e(TAG,"获得选中的时间 向下取整后的分 minuteInt2: " + minuteInt2);
				String minuteInt2Str = "00";
				if (minuteInt2 < 10){
					minuteInt2Str = "00";
				}else {
					minuteInt2Str = minuteInt2 + "";
				}
				longTime = firstStr + minuteInt2Str;
				longTime = longTime + ":00";
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return longTime;
	}
}
