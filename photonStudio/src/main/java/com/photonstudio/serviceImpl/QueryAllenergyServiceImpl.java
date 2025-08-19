package com.photonstudio.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.photonstudio.mapper.QueryAllenergyMapper;
import com.photonstudio.pojo.DataInfo;
import com.photonstudio.pojo.EnergyQeury;
import com.photonstudio.pojo.Energyinfo;
import com.photonstudio.pojo.Reg;
import com.photonstudio.service.QueryAllenergyService;

@Service
public class QueryAllenergyServiceImpl implements QueryAllenergyService{
	
	@Autowired
	private QueryAllenergyMapper queryAllenergyMapper;
	@Override
	public List<List<DataInfo>> findOneEnergyInfo(String dBname, String tagnames, Date date1, Date date2) {
		// TODO Auto-generated method stub
		List<List<DataInfo>> dataInfoListlist = new ArrayList<List<DataInfo>>();
		String[] tagnameArray = tagnames.split(",",-1);
		dBname = dBname+"_data";
		for(String tagname : tagnameArray)
		{
			List<DataInfo> dataInfoList = new ArrayList<DataInfo>();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String datestr1 = formatter.format(date1);
			String datestr2 = formatter.format(date2);
			dataInfoList = queryAllenergyMapper.findOneEnergyInfo(dBname,tagname,datestr1,datestr2);
			dataInfoListlist.add(dataInfoList);
		}
		return dataInfoListlist;
	}
	
	@Override
	public List<List<DataInfo>> findOneEnergyInfoNew(String dBname, String tagnames, Date date1, Date date2) {
		// TODO Auto-generated method stub
		List<List<DataInfo>> dataInfoListlist = new ArrayList<List<DataInfo>>();
		String[] tagnameArray = tagnames.split(",",-1);
		dBname = dBname+"_data";
		List<Date> datelistDt = getBetweenDates(date1,date2);
		int size = datelistDt.size();
		for(String tagname : tagnameArray)
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String datestr1 = formatter.format(date1);
			String datestr2 = formatter.format(date2);
			int cnt=1;
			if(size >1)
			{
				for(Date dt : datelistDt)
				{
					String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(dt);
					List<DataInfo> dataInfoList = new ArrayList<DataInfo>();
					if(cnt == 1)
					{
						dataInfoList =  queryAllenergyMapper.findOneEnergyInfoNew(dBname,tableName,datestr1,null,tagname);
					}
					else if (cnt == size)
					{
						dataInfoList =  queryAllenergyMapper.findOneEnergyInfoNew(dBname,tableName,null,datestr2,tagname);
					}
					else
					{
						dataInfoList =  queryAllenergyMapper.findOneEnergyInfoNew(dBname,tableName,null,null,tagname);
					}
					dataInfoListlist.add(dataInfoList);
					cnt++;
				}
			}else
			{
				List<DataInfo> dataInfoList = new ArrayList<DataInfo>();
				String tableName = "tagdata_"+new SimpleDateFormat("yyyyMMdd").format(datelistDt.get(0));
				dataInfoList =  queryAllenergyMapper.findOneEnergyInfoNew(dBname,tableName,datestr1,datestr2,tagname);
				dataInfoListlist.add(dataInfoList);
			}
		}
		return dataInfoListlist;
	}

	@Override
	public List<List<EnergyQeury>> queryAllenergy(String dBname, List<Energyinfo> energytypeinfoList) {
		// TODO Auto-generated method stub
		dBname = dBname+"_data";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<List<EnergyQeury>> energyQeuryListlist = new ArrayList<List<EnergyQeury>>();
		Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);	
    	Date time = calendar.getTime();
    	String dateStr = formatter.format(time);
		for(Energyinfo energyinfo : energytypeinfoList)
		{
			List<EnergyQeury> energyQeuryList = new ArrayList<EnergyQeury>();
			EnergyQeury energyQeury = new EnergyQeury();
			EnergyQeury energyQeuryDay = new EnergyQeury();
			Map<String,String> energyQeuryMap = new HashMap<String,String>();
			Map<String,String> energyQeuryMapDay = new HashMap<String,String>();
			String tagnames = energyinfo.getTagnames();
			String energytypename = energyinfo.getEnergytypename();
			String[] tagnameArry = tagnames.split(",",-1);
			Date date = new Date();
			float sumTagvlue = 0;
			float sumDayvlue = 0;
			for(String tagname : tagnameArry)
			{
				DataInfo dataInfo = queryAllenergyMapper.queryAllenergy(dBname,tagname);
				System.out.println("tagname==="+tagname);
				float tagvlue = Float.parseFloat(dataInfo.getTagvalue());
				sumTagvlue = sumTagvlue+tagvlue;
				date = dataInfo.getTime();
				String  tagvalueStr = queryAllenergyMapper.queryAllenergyByYMD(dBname,tagname,dateStr);
				System.out.println("tagvalueStr=="+tagvalueStr);
				float tagvlueDay =0;
				if(!StringUtils.isEmpty(tagvalueStr))
				tagvlueDay = Float.parseFloat(tagvalueStr);
				sumDayvlue = sumDayvlue+(tagvlue-tagvlueDay);
			}
			energyQeuryMap.put(formatter.format(date), floatToString(sumTagvlue));
			energyQeury.setEnergytypename(energytypename);
			energyQeury.setEnergyQeuryMap(energyQeuryMap);
			energyQeury.setResultType("0");
			energyQeuryList.add(energyQeury);
			energyQeuryMapDay.put(formatter.format(date), floatToString(sumDayvlue));
			energyQeuryDay.setEnergytypename("当日"+energytypename);
			energyQeuryDay.setEnergyQeuryMap(energyQeuryMapDay);
			energyQeuryDay.setResultType("1");
			energyQeuryList.add(energyQeuryDay);
			energyQeuryListlist.add(energyQeuryList);
		}
		return energyQeuryListlist;
	}
	
	@Override
	public List<List<EnergyQeury>> queryAllenergyNew(String dBname, List<Energyinfo> energytypeinfoList) {
		// TODO Auto-generated method stub
		dBname = dBname+"_data";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<List<EnergyQeury>> energyQeuryListlist = new ArrayList<List<EnergyQeury>>();
		Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);	
    	Date time = calendar.getTime();
    	String dateStr = formatter.format(time);
    	String tabName="tagdata_"+new SimpleDateFormat("yyyyMMdd").format(time);
		for(Energyinfo energyinfo : energytypeinfoList)
		{
			List<EnergyQeury> energyQeuryList = new ArrayList<EnergyQeury>();
			EnergyQeury energyQeury = new EnergyQeury();
			EnergyQeury energyQeuryDay = new EnergyQeury();
			Map<String,String> energyQeuryMap = new HashMap<String,String>();
			Map<String,String> energyQeuryMapDay = new HashMap<String,String>();
			String tagnames = energyinfo.getTagnames();
			String energytypename = energyinfo.getEnergytypename();
			String[] tagnameArry = tagnames.split(",",-1);
			Date date = new Date();
			float sumTagvlue = 0;
			float sumDayvlue = 0;
			for(String tagname : tagnameArry)
			{
				DataInfo dataInfo = queryAllenergyMapper.queryAllenergyNew(dBname,tabName,tagname);
				System.out.println("tagname==="+tagname);
				float tagvlue =0;
				if(dataInfo!=null&&!StringUtils.isEmpty(dataInfo.getTagvalue())) tagvlue=Float.parseFloat(dataInfo.getTagvalue());
				sumTagvlue = sumTagvlue+tagvlue;
				try {
					date = dataInfo.getTime();
				} catch (Exception e) {
					e.printStackTrace();
				}
				String  tagvalueStr="";
				try
				{
				tagvalueStr = queryAllenergyMapper.queryAllenergyByYMDNew(dBname,tabName,tagname,dateStr);
				System.out.println("tagvalueStr=="+tagvalueStr);
				}catch (Exception e) {
					// TODO: handle exception
					tagvalueStr="0";
				}
				float tagvlueDay =0;
				if(!StringUtils.isEmpty(tagvalueStr))
				tagvlueDay = Float.parseFloat(tagvalueStr);
				sumDayvlue = sumDayvlue+(tagvlue-tagvlueDay);
			}
			energyQeuryMap.put(formatter.format(date), floatToString(sumTagvlue));
			energyQeury.setEnergytypename(energytypename);
			energyQeury.setCompany(energyinfo.getCompany());
			energyQeury.setEnergyQeuryMap(energyQeuryMap);
			energyQeury.setResultType("0");
			energyQeuryList.add(energyQeury);
			energyQeuryMapDay.put(formatter.format(date), floatToString(sumDayvlue));
			energyQeuryDay.setEnergytypename("当日"+energytypename);
			energyQeuryDay.setEnergyQeuryMap(energyQeuryMapDay);
			energyQeuryDay.setResultType("1");
			energyQeuryList.add(energyQeuryDay);
			energyQeuryListlist.add(energyQeuryList);
		}
		return energyQeuryListlist;
	}

	@Override
	public List<EnergyQeury> findSumenergyBytype(String dBname, List<Energyinfo> energytypeinfoList, String type,
			Date date) {
		// TODO Auto-generated method stub
		dBname = dBname+"_data";
		List<EnergyQeury> energyQeuryList = new ArrayList<EnergyQeury>();
		for(Energyinfo energyinfo : energytypeinfoList)
		{
			float sumvalueFlag = 0;
			int flag=0;
			EnergyQeury energyQeury = new EnergyQeury();
			energyQeury.setEnergyid(energyinfo.getId());
			Map<String,String> energyQeuryMap = new LinkedHashMap<String,String>();
			String tagnames = energyinfo.getTagnames();
			String energytypename = energyinfo.getEnergytypename();
			energyQeury.setEnergytypename(energytypename);
			String[] tagnameArry = tagnames.split(",",-1);
			List<String> dateList = getDateYMD(date,type);
			List<String> dateSeven = beforeSeven2(7);
			Collections.reverse(dateSeven);
			for(String datestr : dateList)
			{
				float sumTagvalue = 0;
				for(String tagname : tagnameArry)
				{
					String  tagvalueStr = queryAllenergyMapper.queryAllenergyByYMD(dBname,tagname,datestr);
					float tagvlue=0;
					if(tagvalueStr != null && !"".equals(tagvalueStr))
					{
						tagvlue = Float.parseFloat(tagvalueStr);
					}
					sumTagvalue = sumTagvalue+tagvlue;
				}
				if(flag>0)
				{
					float value = sumTagvalue-sumvalueFlag;
					if(value<0)
					{
						value=0;
					}
					if(type.equals("1"))
					{
						energyQeuryMap.put(flag+"月", floatToString(value));
					}
					else if(type.equals("2"))
					{
						energyQeuryMap.put(flag+"日", floatToString(value));
					}
					else if(type.equals("4"))
					{
						energyQeuryMap.put(dateSeven.get(flag-1)+"日", floatToString(value));
					}
					else
					{
						energyQeuryMap.put(flag+"时", floatToString(value));
					}
				}
				sumvalueFlag=sumTagvalue;
				flag++;
			}
			energyQeury.setEnergyQeuryMap(energyQeuryMap);
			energyQeury.setDateStr(getDate(date,type));
			energyQeuryList.add(energyQeury);
		}
		return energyQeuryList;
	}
	
	@Override
	public List<EnergyQeury> findSumenergyBytypeNew(String dBname, List<Energyinfo> energytypeinfoList, String type,
			Date date) {
		// TODO Auto-generated method stub
		dBname = dBname+"_data";
		List<EnergyQeury> energyQeuryList = new ArrayList<EnergyQeury>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

		for(Energyinfo energyinfo : energytypeinfoList)
		{
			float sumvalueFlag = 0;
			int flag=0;
			EnergyQeury energyQeury = new EnergyQeury();
			energyQeury.setEnergyid(energyinfo.getId());
			Map<String,String> energyQeuryMap = new LinkedHashMap<String,String>();
			String tagnames = energyinfo.getTagnames();
			String energytypename = energyinfo.getEnergytypename();
			energyQeury.setEnergytypename(energytypename);
			String[] tagnameArry = tagnames.split(",",-1);
			List<String> dateList = getDateYMD(date,type);
			List<String> dateSeven = beforeSeven2(7);
			Collections.reverse(dateSeven);
			for(String datestr : dateList)
			{
				float sumTagvalue = 0;
				String tabName="";
				try {
					Date dt = sdf.parse(datestr);
					tabName="tagdata_"+sdf2.format(dt);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(String tagname : tagnameArry)
				{
					String  tagvalueStr = "";
					try
					{
						tagvalueStr = queryAllenergyMapper.queryAllenergyByYMDNew(dBname,tabName,tagname,datestr);
					}catch (Exception e) {
						// TODO: handle exception
						tagvalueStr="0";
					}
					float tagvlue=0;
					if(tagvalueStr != null && !"".equals(tagvalueStr))
					{
						tagvlue = Float.parseFloat(tagvalueStr);
					}
					sumTagvalue = sumTagvalue+tagvlue;
				}
				if(flag>0)
				{
					float value = sumTagvalue-sumvalueFlag;
					if(value<0)
					{
						value=0;
					}
					if(type.equals("1"))
					{
						energyQeuryMap.put(flag+"月", floatToString(value));
					}
					else if(type.equals("2"))
					{
						energyQeuryMap.put(flag+"日", floatToString(value));
					}
					else if(type.equals("4"))
					{
						energyQeuryMap.put(dateSeven.get(flag-1)+"日", floatToString(value));
					}
					else
					{
						energyQeuryMap.put(flag+"时", floatToString(value));
					}
				}
				sumvalueFlag=sumTagvalue;
				flag++;
			}
			energyQeury.setEnergyQeuryMap(energyQeuryMap);
			energyQeury.setCompany(energyinfo.getCompany());
			energyQeury.setDateStr(getDate(date,type));
			energyQeuryList.add(energyQeury);
		}
		return energyQeuryList;
	}
	
	private String getDate(Date date,String type)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int ymd = 0;
		String dateStr = "";
		if(type.equals("1"))//年
		{
			ymd = calendar.get(Calendar.YEAR);
			dateStr=ymd+"年";
		}
		else if(type.equals("2"))//月
		{
			int year = calendar.get(Calendar.YEAR);
			ymd =  calendar.get(Calendar.MONTH)+1;
			dateStr=year+"年"+ymd+"月";
		}
		else if(type.equals("3"))//日
		{
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+1;
			ymd =  calendar.get(Calendar.DATE);
			dateStr=year+"年"+month+"月"+ymd+"日";
		}
		else {
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+1;
			int day = calendar.get(Calendar.DATE);
			 ymd=calendar.get(Calendar.HOUR_OF_DAY);
			 dateStr=year+"年"+month+"月"+day+"日"+ymd+"时";
		}
		return dateStr;
	}
	
	private List<String> getDateYMD(Date date,String type)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int ymd = 0;
		List<String> dateList = new ArrayList<String>();
		if(type.equals("1"))//年
		{
			ymd = calendar.get(Calendar.YEAR);
			dateList = getDateYMDmonth(ymd);
		}
		else if(type.equals("2"))//月
		{
			int year = calendar.get(Calendar.YEAR);
			ymd =  calendar.get(Calendar.MONTH)+1;
			dateList = getDateYMDday(year,ymd);
		}
		else if(type.equals("4"))//前7天数据
		{
			dateList = beforeSeven(8);
			Collections.reverse(dateList);
		}
		else//日
		{
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+1;
			ymd =  calendar.get(Calendar.DATE);
			dateList = getDateYMDhour(year,month,ymd);
		}
		return dateList;
	}
	
	private List<String> beforeSeven(int intervals ) 
	{  
        List<String> pastDaysList = new ArrayList<>();  
        for (int i = 0; i <intervals; i++) {  
            pastDaysList.add(getPastDate(i));  
        }  
        return pastDaysList;  
	}
	
	private String getPastDate(int past) 
	{  
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);  
		Date today = calendar.getTime();  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
		String result = format.format(today);  
		return result;  
	}
	
	private List<String> beforeSeven2(int intervals ) 
	{  
        List<String> pastDaysList = new ArrayList<>();  
        for (int i = 1; i <intervals+1; i++) {  
            pastDaysList.add(getPastDate2(i));  
        }  
        return pastDaysList;  
	}
	
	private String getPastDate2(int past) 
	{  
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);  
		Date today = calendar.getTime();  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		String result = format.format(today);  
		return result;  
	}  

	private List<String> getDateYMDhour(int year, int month, int ymd) {
		// TODO Auto-generated method stub
		List<String> dateList = new ArrayList<String>();
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = year+"-"+month+"-"+ymd+" 00:00:00";
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dateList.add(formatter.format(date));
		for(int i=1;i<24+1;i++)
		{
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
		String str = year+"-"+ymd+"-1 00:00:00";
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dateList.add(formatter.format(date));
		int days = getdays(ymd,year);
		for(int i=1;i<days+1;i++)
		{
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
		String str = ymd+"-1-1 00:00:00";
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dateList.add(formatter.format(date));
		for(int i=1;i<13;i++)
		{
			Calendar rightNow = Calendar.getInstance(); 
			rightNow.setTime(date);  
	        rightNow.add(Calendar.MONTH, i); 
	        Date dt = rightNow.getTime();
	        dateList.add(formatter.format(dt));
		}
		return dateList;
	}
	
	private int getdays(int ymd,int year) {
		// TODO Auto-generated method stub
		switch (ymd) { 
        case 1: case 3: case 5:case 7:  case 8:  case 10:  case 12: 
        	return 31;
        case 4:  case 6: case 9:  case 11: 
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
	public List<Energyinfo> queryAllenergyInfo(String dBname, int type) {
		// TODO Auto-generated method stub
		return queryAllenergyMapper.queryAllenergyInfo(dBname, type);
	}

	@Override
	public List<EnergyQeury> findSubEnergyInfoByType(String dBname, String tagnames, String regnames, String type,
			Date date) {
		// TODO Auto-generated method stub
		dBname = dBname+"_data";
		List<EnergyQeury> energyQeuryList = new ArrayList<EnergyQeury>();
		String[] tagnameArray = tagnames.split(",",-1);
		String[] regnameArray = regnames.split(",",-1);
		if(tagnameArray.length != regnameArray.length)
		{
			return null;
		}
		int i=0;
		for(String tagname : tagnameArray)
		{
			float sumvalueFlag = 0;
			int flag=0;
			EnergyQeury energyQeury = new EnergyQeury();
			Map<String,String> energyQeuryMap = new LinkedHashMap<String,String>();
			List<String> dateList = getDateYMD(date,type);
			for(String datestr : dateList)
			{
				String  tagvalueStr = queryAllenergyMapper.queryAllenergyByYMD(dBname,tagname,datestr);
				float tagvlue=0;
				if(tagvalueStr == null || "".equals(tagvalueStr))
				{
					tagvlue = sumvalueFlag;
				}
				else
				{
					tagvlue = Float.parseFloat(tagvalueStr);
				}
				if(flag>0)
				{
					float value = tagvlue-sumvalueFlag;
					if(type.equals("1"))
					{
						energyQeuryMap.put(flag+"月", floatToString(value));
					}
					else if(type.equals("2"))
					{
						energyQeuryMap.put(flag+"日", floatToString(value));
					}
					else
					{
						energyQeuryMap.put(flag+"时", floatToString(value));
					}
				}
				sumvalueFlag=tagvlue;
				flag++;
			}
			energyQeury.setEnergyQeuryMap(energyQeuryMap);
			energyQeury.setDateStr(getDate(date,type));
			energyQeury.setEnergytypename(regnameArray[i]);
			energyQeuryList.add(energyQeury);
			i++;
		}
		return energyQeuryList;
	}

	@Override
	public Energyinfo findObjectById(String dBname,Integer id) {
		// TODO Auto-generated method stub
		Energyinfo energyinfo=queryAllenergyMapper.findObjectById(dBname,id);
		return energyinfo;
	}

	@Override
	public Energyinfo queryAppTotalenergyInfo(String dBname, int energytypeid, int isapptotal) {
		// TODO Auto-generated method stub
		return queryAllenergyMapper.queryAppTotalenergyInfo(dBname,energytypeid,isapptotal);
	}

	@Override
	public Map<String, String> queryAppTotalenergy(String dBname, Energyinfo energyinfo) {
		// TODO Auto-generated method stub
		dBname = dBname+"_data";
		String energytypename = energyinfo.getEnergytypename();
		String tagnames = energyinfo.getTagnames();
		String[] tagnameArry = tagnames.split(",",-1);
		float sumTagvlue = 0;
		for(String tagname : tagnameArry)
		{
			DataInfo dataInfo = queryAllenergyMapper.queryAllenergy(dBname,tagname);
			float tagvlue = Float.parseFloat(dataInfo.getTagvalue());
			sumTagvlue = sumTagvlue+tagvlue;
		}
		Map<String,String> appTotalMap = new HashMap<>();
		appTotalMap.put(energytypename, floatToString(sumTagvlue));
		return appTotalMap;
	}

	@Override
	public List<EnergyQeury> findzsEnergyBytype(String dBname, Energyinfo energyinfo, String type, Date date) {
		// TODO Auto-generated method stub
		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<EnergyQeury> energyQeuryList = new ArrayList<EnergyQeury>();
		String tagnames = energyinfo.getTagnames();
		String[] tagnameArry = tagnames.split(",",-1);
		String datedBname = dBname+"_data";
		for (String tagname : tagnameArry) {
			Date date1 =null;
			Date date2=null;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			EnergyQeury energyQeury = new EnergyQeury();
			Map<String,String> energyQeuryMap = new LinkedHashMap<String,String>();
			//int flag=0;
			List<Reg> reglist=queryAllenergyMapper.findRegByTagname(dBname,tagname);
			energyQeury.setEnergytypename(reglist.get(0).getRegName());
			energyQeury.setEnergyid(reglist.get(0).getDrId());
			 switch (type) {
			case "3":
				date1 = calendar.getTime();
				calendar.add(Calendar.HOUR, 1);
				date2= calendar.getTime();
				break;
			case "2":
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				date1=calendar.getTime();
				calendar.add(Calendar.DATE, 1);
				date2=calendar.getTime();
				break;
			case "1":
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				date1=calendar.getTime();
				calendar.add(Calendar.MONTH, 1);
				date2=calendar.getTime();
				break;
			/*
			 * case "1": calendar.set(Calendar.HOUR_OF_DAY, 0);
			 * calendar.set(Calendar.DAY_OF_MONTH, 1); calendar.set(Calendar.DAY_OF_YEAR,
			 * 1); date1=calendar.getTime(); calendar.add(Calendar.YEAR, 1);
			 * date2=calendar.getTime(); break;
			 */
				
			default:
				break;
			}
		      
		       // List<Regdata> regdatalist = regdataMapper.findObject(datedBname, tagname, date1, date2);
		       
				/*
				 * for (Regdata regdata : regdatalist) { float tagvlue=0; String
				 * tagvalueStr=regdata.getTagvalue(); if(StringUtils.isEmpty(tagvalueStr)) {
				 * tagvlue = sumvalueFlag; } else { tagvlue = Float.parseFloat(tagvalueStr); }
				 * 
				 * if(flag>0) { float value = tagvlue-sumvalueFlag; if(value<0) { value=0; }
				 * String timestr=sdf.format(regdata.getTime()); energyQeuryMap.put(timestr,
				 * String.valueOf(value)); } sumvalueFlag=tagvlue; flag++; }
				 */
			//}else {
				//List<String> dateList = getDateYMD(date,type);
				//for (String datestr : dateList) {
			 System.out.println("date==="+formatter.format(date1)+"==="+formatter.format(date2));
					String  tagvalueStr1 = queryAllenergyMapper.queryAllenergyByYMD(
							datedBname,tagname,formatter.format(date1));
					float tagvlue1=0;
					float tagvlue2=0;
					if(tagvalueStr1 != null && !"".equals(tagvalueStr1))
					{
						tagvlue1 = Float.parseFloat(tagvalueStr1);
					}
					String  tagvalueStr2 = queryAllenergyMapper.queryAllenergyByYMD(
							datedBname,tagname,formatter.format(date2));
					if(tagvalueStr2 != null && !"".equals(tagvalueStr2))
					{
						tagvlue2 = Float.parseFloat(tagvalueStr2);
					}
					float value=tagvlue2-tagvlue1;
					//float tagvlue=0;
					//if(tagvalueStr != null && !"".equals(tagvalueStr))
					//{
						//tagvlue = Float.parseFloat(tagvalueStr);
					//}
					//if(flag>0)
					//{
						//float value = tagvlue-sumvalueFlag;
						//if(value<0)
						//{
							//value=0;
						//}
						if(type.equals("1"))
						{
							energyQeuryMap.put((calendar.get(Calendar.MONTH))+"月", floatToString(value));
						}
						else if(type.equals("2"))
						{
							energyQeuryMap.put((calendar.get(Calendar.DATE)-1)+"日", floatToString(value));
						}
						else
						{
							energyQeuryMap.put((calendar.get(Calendar.HOUR_OF_DAY)-1)+"时", floatToString(value));
						}
					//}
					//sumvalueFlag=tagvlue;
					//flag++;
				//}
			//}
			energyQeury.setStartTime(date1);
			energyQeury.setEndTime(date2);
			energyQeury.setEnergyQeuryMap(energyQeuryMap);
			energyQeury.setDateStr(getDate(date,type));
			energyQeuryList.add(energyQeury);
		}
		return energyQeuryList;
	}
	
	@Override
	public List<EnergyQeury> findzsEnergyBytypeNew(String dBname, Energyinfo energyinfo, String type, Date date) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<EnergyQeury> energyQeuryList = new ArrayList<EnergyQeury>();
		String tagnames = energyinfo.getTagnames();
		String[] tagnameArry = tagnames.split(",",-1);
		String datedBname = dBname+"_data";
		for (String tagname : tagnameArry) {
			Date date1 =null;
			Date date2=null;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			EnergyQeury energyQeury = new EnergyQeury();
			Map<String,String> energyQeuryMap = new LinkedHashMap<String,String>();
			//int flag=0;
			List<Reg> reglist=queryAllenergyMapper.findRegByTagname(dBname,tagname);
			energyQeury.setEnergytypename(reglist.get(0).getRegName());
			energyQeury.setEnergyid(reglist.get(0).getDrId());
			 switch (type) {
			case "3":
				date1 = calendar.getTime();
				calendar.add(Calendar.HOUR, 1);
				date2= calendar.getTime();
				break;
			case "2":
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				date1=calendar.getTime();
				calendar.add(Calendar.DATE, 1);
				date2=calendar.getTime();
				break;
			case "1":
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				date1=calendar.getTime();
				calendar.add(Calendar.MONTH, 1);
				date2=calendar.getTime();
				break;
			/*
			 * case "1": calendar.set(Calendar.HOUR_OF_DAY, 0);
			 * calendar.set(Calendar.DAY_OF_MONTH, 1); calendar.set(Calendar.DAY_OF_YEAR,
			 * 1); date1=calendar.getTime(); calendar.add(Calendar.YEAR, 1);
			 * date2=calendar.getTime(); break;
			 */
				
			default:
				break;
			}
		      
		       // List<Regdata> regdatalist = regdataMapper.findObject(datedBname, tagname, date1, date2);
		       
				/*
				 * for (Regdata regdata : regdatalist) { float tagvlue=0; String
				 * tagvalueStr=regdata.getTagvalue(); if(StringUtils.isEmpty(tagvalueStr)) {
				 * tagvlue = sumvalueFlag; } else { tagvlue = Float.parseFloat(tagvalueStr); }
				 * 
				 * if(flag>0) { float value = tagvlue-sumvalueFlag; if(value<0) { value=0; }
				 * String timestr=sdf.format(regdata.getTime()); energyQeuryMap.put(timestr,
				 * String.valueOf(value)); } sumvalueFlag=tagvlue; flag++; }
				 */
			//}else {
				//List<String> dateList = getDateYMD(date,type);
				//for (String datestr : dateList) {
			    System.out.println("date==="+formatter.format(date1)+"==="+formatter.format(date2));
			    String tablename ="tagdata_"+new SimpleDateFormat("yyyyMMdd").format(date1);
				String  tagvalueStr1 = queryAllenergyMapper.queryAllenergyByYMDNew(
						datedBname,tablename,tagname,formatter.format(date1));
				float tagvlue1=0;
				float tagvlue2=0;
				if(tagvalueStr1 != null && !"".equals(tagvalueStr1))
				{
					tagvlue1 = Float.parseFloat(tagvalueStr1);
				}
				tablename="tagdata_"+new SimpleDateFormat("yyyyMMdd").format(date2);
				String  tagvalueStr2 = queryAllenergyMapper.queryAllenergyByYMDNew(
						datedBname,tablename,tagname,formatter.format(date2));
				if(tagvalueStr2 != null && !"".equals(tagvalueStr2))
				{
					tagvlue2 = Float.parseFloat(tagvalueStr2);
				}
				float value=tagvlue2-tagvlue1;
				//float tagvlue=0;
				//if(tagvalueStr != null && !"".equals(tagvalueStr))
				//{
					//tagvlue = Float.parseFloat(tagvalueStr);
				//}
				//if(flag>0)
				//{
					//float value = tagvlue-sumvalueFlag;
					//if(value<0)
					//{
						//value=0;
					//}
					if(type.equals("1"))
					{
						energyQeuryMap.put((calendar.get(Calendar.MONTH))+"月", floatToString(value));
					}
					else if(type.equals("2"))
					{
						energyQeuryMap.put((calendar.get(Calendar.DATE)-1)+"日", floatToString(value));
					}
					else
					{
						energyQeuryMap.put((calendar.get(Calendar.HOUR_OF_DAY)-1)+"时", floatToString(value));
					}
					//}
					//sumvalueFlag=tagvlue;
					//flag++;
				//}
			//}
			energyQeury.setStartTime(date1);
			energyQeury.setEndTime(date2);
			energyQeury.setEnergyQeuryMap(energyQeuryMap);
			energyQeury.setDateStr(getDate(date,type));
			energyQeuryList.add(energyQeury);
		}
		return energyQeuryList;
	
	}

	public List<EnergyQeury>findSumenergyBytypeMom(String dBname, List<Energyinfo> energytypeinfoList, String type,
												   Date date,Date date2,Integer momType) {
		// TODO Auto-generated method stub
		dBname = dBname+"_data";
		List<EnergyQeury> energyQeuryList = new ArrayList<EnergyQeury>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		for(Energyinfo energyinfo : energytypeinfoList)
		{
			float sumvalueFlag = 0;
			float sumvalueFlag2 = 0;
			int flag=0;
			int flag2=0;
			EnergyQeury energyQeury = new EnergyQeury();
			energyQeury.setEnergyid(energyinfo.getId());
			Map<String,String> energyQeuryMap = new LinkedHashMap<String,String>();
			Map<String,String> energyQeuryMap2 = new LinkedHashMap<String,String>();
			String tagnames = energyinfo.getTagnames();
			String energytypename = energyinfo.getEnergytypename();
			energyQeury.setEnergytypename(energytypename);
			String[] tagnameArry = tagnames.split(",",-1);
			List<String> dateList = getDateYMD(date,type);
			List<String> dataMomList = getDateYMD(date2,type);
			List<String> dateSeven = beforeSeven2(7);
			Collections.reverse(dateSeven);

			float sumTagvalueSizeDate1=0; //第一个时间的tagvalue值的总数
			float sumTagvalueSizeDate2=0; //第2个时间的tagvalue值的总数
			for(String datestr : dateList)
			{
				float sumTagvalue = 0;
				String tabName="";
				try {
					Date dt = sdf.parse(datestr);
					tabName="tagdata_"+sdf2.format(dt);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(String tagname : tagnameArry)
				{
					String  tagvalueStr = "";
					try
					{
						tagvalueStr = queryAllenergyMapper.queryAllenergyByYMDNew(dBname,tabName,tagname,datestr);
					}catch (Exception e) {
						// TODO: handle exception
						tagvalueStr="0";
					}
					float tagvlue=0;
					if(tagvalueStr != null && !"".equals(tagvalueStr))
					{
						tagvlue = Float.parseFloat(tagvalueStr);
					}
					sumTagvalue = sumTagvalue+tagvlue;
				}
				if(flag>0)
				{
					float value = sumTagvalue-sumvalueFlag;
					if(value<0)
					{
						value=0;
					}
					if(type.equals("1"))
					{
						energyQeuryMap.put(flag+"月", floatToString(value));
						sumTagvalueSizeDate1=value+sumTagvalueSizeDate1;
					}
					else if(type.equals("2"))
					{
						energyQeuryMap.put(flag+"日", floatToString(value));
						sumTagvalueSizeDate1=value+sumTagvalueSizeDate1;
					}
					else if(type.equals("4"))
					{
						energyQeuryMap.put(dateSeven.get(flag-1)+"日", floatToString(value));
						sumTagvalueSizeDate1=value+sumTagvalueSizeDate1;
					}
					else
					{
						energyQeuryMap.put(flag+"时", floatToString(value));
						sumTagvalueSizeDate1=value+sumTagvalueSizeDate1;
					}
				}
				flag++;
				sumvalueFlag=sumTagvalue;
			}
			System.out.println("第一个时间的总值"+sumTagvalueSizeDate1);
			for(String datestr : dataMomList)
			{
				float sumTagvalue = 0;
				String tabName="";
				try {
					Date dt = sdf.parse(datestr);
					tabName="tagdata_"+sdf2.format(dt);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(String tagname : tagnameArry)
				{
					String  tagvalueStr = "";
					try
					{
						tagvalueStr = queryAllenergyMapper.queryAllenergyByYMDNew(dBname,tabName,tagname,datestr);
					}catch (Exception e) {
						// TODO: handle exception
						tagvalueStr="0";
					}
					float tagvlue=0;
					if(tagvalueStr != null && !"".equals(tagvalueStr))
					{
						tagvlue = Float.parseFloat(tagvalueStr);
					}
					sumTagvalue = sumTagvalue+tagvlue;
				}
				if(flag2>0)
				{
					float value = sumTagvalue-sumvalueFlag2;
					if(value<0)
					{
						value=0;
					}
					if(type.equals("1"))
					{
						energyQeuryMap2.put(flag2+"月", floatToString(value));
						sumTagvalueSizeDate2=value+sumTagvalueSizeDate2;

					}
					else if(type.equals("2"))
					{
						energyQeuryMap2.put(flag2+"日", floatToString(value));
						sumTagvalueSizeDate2=value+sumTagvalueSizeDate2;
					}
					else if(type.equals("4"))
					{
						energyQeuryMap2.put(dateSeven.get(flag-1)+"日", floatToString(value));
						sumTagvalueSizeDate2=value+sumTagvalueSizeDate2;
					}
					else
					{
						energyQeuryMap2.put(flag2+"时", floatToString(value));
						sumTagvalueSizeDate2=value+sumTagvalueSizeDate2;
					}
				}
				flag2++;
				sumvalueFlag2=sumTagvalue;
			}
			System.out.println("第二个时间的总值"+sumTagvalueSizeDate2);
			float MomSum =0;//同比的总和
			if (momType==1||momType==2){
				float index = sumTagvalueSizeDate1-sumTagvalueSizeDate2;
				if (sumTagvalueSizeDate2!=0&&sumTagvalueSizeDate1!=0){
					MomSum = (((float)((double)index/sumTagvalueSizeDate2)));
				}else {
					MomSum=0;
				}
			}
			energyQeury.setEnergyQeuryMap(energyQeuryMap);
			energyQeury.setEnergyQeuryMap2(energyQeuryMap2);
			energyQeury.setDateStr(getDate(date,type));
			energyQeury.setMomSum(Float.valueOf(floatTo(MomSum)));
			energyQeury.setDateStr2(getDate(date2,type));
			energyQeuryList.add(energyQeury);

		}
		return energyQeuryList;
	}

	@Override
	public List<EnergyQeury> findRegEnergyInfoByType(String dBname, String tagnames, String regnames, String type,
			Date date) {
		// TODO Auto-generated method stub
		dBname = dBname+"_data";
		List<EnergyQeury> energyQeuryList = new ArrayList<EnergyQeury>();
		String[] tagnameArray = tagnames.split(",",-1);
		String[] regnameArray = regnames.split(",",-1);
		if(tagnameArray.length != regnameArray.length)
		{
			return null;
		}
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		for(String tagname : tagnameArray)
		{
			float sumvalueFlag = 0;
			int flag=0;
			EnergyQeury energyQeury = new EnergyQeury();
			Map<String,String> energyQeuryMap = new LinkedHashMap<String,String>();
			List<String> dateList = getDateYMD(date,type);
			for(String datestr : dateList)
			{
				String dateTable="";
				String  tagvalueStr="";
				try {
					Date dt = sdf.parse(datestr);
					dateTable = "tagdata_"+sdf2.format(dt);
					tagvalueStr = queryAllenergyMapper.queryRegEnergyByYMD(dBname,dateTable,tagname,datestr);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					tagvalueStr="";
				}
				float tagvlue=0;
				if(tagvalueStr == null || "".equals(tagvalueStr))
				{
					tagvlue = sumvalueFlag;
				}
				else
				{
					tagvlue = Float.parseFloat(tagvalueStr);
				}
				if(flag>0)
				{
					float value = tagvlue-sumvalueFlag;
					if(type.equals("1"))
					{
						energyQeuryMap.put(flag+"月", floatToString(value));
					}
					else if(type.equals("2"))
					{
						energyQeuryMap.put(flag+"日", floatToString(value));
					}
					else
					{
						energyQeuryMap.put(flag+"时", floatToString(value));
					}
				}
				sumvalueFlag=tagvlue;
				flag++;
			}
			energyQeury.setEnergyQeuryMap(energyQeuryMap);
			energyQeury.setDateStr(getDate(date,type));
			energyQeury.setEnergytypename(regnameArray[i]);
			energyQeuryList.add(energyQeury);
			i++;
		}
		return energyQeuryList;
	}
	
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
	
	private String floatToString(float f)
	{
		String s = String.valueOf((float)(Math.round(f*10))/10);
		return s;
	}


	public String floatTo(float f)
	{
		String s = String.valueOf((float)(Math.round(f*100))/100);
		return s;
	}
}
