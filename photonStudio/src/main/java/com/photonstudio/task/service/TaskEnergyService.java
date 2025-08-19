package com.photonstudio.task.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.mapper.QueryAllenergyMapper;
import com.photonstudio.mapper.TaskEnergyMapper;
import com.photonstudio.pojo.Appenergy;
import com.photonstudio.pojo.Appmanager;
import com.photonstudio.pojo.DataInfo;
import com.photonstudio.pojo.Energyinfo;
import com.photonstudio.pojo.Energyparm;
import com.photonstudio.service.AppinfoService;

@Service
public class TaskEnergyService {

	@Autowired
	private TaskEnergyMapper taskEnergyMapper;
	@Autowired
	private QueryAllenergyMapper queryAllenergyMapper;
	@Autowired
	private AppinfoService appinfoService;
	
	public List<Appmanager> findAppManagerAll()
	{
		List<Appmanager> appmanagerList = taskEnergyMapper.findAppManagerAll();
		return appmanagerList;
	}
	
	public void insertAppenergy(Appenergy appenergy) {
		// TODO Auto-generated method stub
		try
		{
			taskEnergyMapper.insertAppenergy(appenergy);
		}catch (Exception e) {
			// TODO: handle exception
			//System.out.println("insertAppenergy error"+appenergy);
		}
	}

	public Appenergy queryAppEnergyInfo(String dbname, Energyinfo energyinfo) {
		// TODO Auto-generated method stub
		String dbnameData = dbname+"_data";
		Appenergy appenergy = new Appenergy();
		List<String> dateList = getDateYMD(new Date());
		List<String> dateSeven = beforeSeven2(7);
		Collections.reverse(dateSeven);
		if(energyinfo == null || energyinfo.getTagnames()==null)
		{
			appenergy.setAppTotalEnergy("0.0");
			appenergy.setOneDayTime(dateSeven.get(0));
			appenergy.setOneDayEnergy("0.0");
			appenergy.setTwoDayTime(dateSeven.get(1));
			appenergy.setTwoDayEnergy("0.0");
			appenergy.setThreeDayTime(dateSeven.get(2));
			appenergy.setThreeDayEnergy("0.0");
			appenergy.setFourDayTime(dateSeven.get(3));
			appenergy.setFourDayEnergy("0.0");
			appenergy.setFiveDayTime(dateSeven.get(4));
			appenergy.setFiveDayEnergy("0.0");
			appenergy.setSixDayTime(dateSeven.get(5));
			appenergy.setSixDayEnergy("0.0");
			appenergy.setSevenDayTime(dateSeven.get(6));
			appenergy.setSevenDayEnergy("0.0");
			appenergy.setTotalEffEnergy("0.0");
			appenergy.setAppDrCost("0.0");
			appenergy.setAppPeopleCost("0.0");
			appenergy.setAppCarbon("0.0");
			appenergy.setAppTree("0.0");
			appenergy.setAppCost("0.0");
			return appenergy;
		}
		String tagnames = energyinfo.getTagnames();
		String[] tagnameArry = tagnames.split(",",-1);
		float apptotal = 0;
		for(String tagname : tagnameArry)
		{
			DataInfo dataInfo = null;
			try
			{
				dataInfo = queryAllenergyMapper.queryAllenergy(dbnameData,tagname);
			}catch (Exception e) {
				// TODO: handle exception
				//System.out.println("queryAllenergy error=="+dbnameData+"||"+tagname);
			}
			System.out.println("tagname==="+tagname);
			float tagvlue =0;
			if(dataInfo != null)
			{
				tagvlue = Float.parseFloat(dataInfo.getTagvalue());
			}
			apptotal = apptotal+tagvlue;
		}
		appenergy.setAppTotalEnergy(floatToString(apptotal));
		int day = 1;
		int flag=0;
		float sumvalueFlag = 0;
		for(String datestr : dateList)
		{
			float sumTagvalue = 0;
			for(String tagname : tagnameArry)
			{
				float tagvlue=0;
				try
				{
					String  tagvalueStr = queryAllenergyMapper.queryAllenergyByYMD(dbnameData,tagname,datestr);
					if(tagvalueStr != null && !"".equals(tagvalueStr))
					{
						tagvlue = Float.parseFloat(tagvalueStr);
					}
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println("queryAllenergyByYMD error=="+dbnameData+"||"+tagname+"||"+datestr);
				}
				sumTagvalue = sumTagvalue+tagvlue;
			}
			if(flag>0)
			{
				float value = sumTagvalue-sumvalueFlag;
//				if(value<0)
//				{
//					value=0;
//				}
				if(day == 1)
				{
					appenergy.setOneDayTime(dateSeven.get(day-1));
					appenergy.setOneDayEnergy(floatToString(value));
				}
				if(day == 2)
				{
					appenergy.setTwoDayTime(dateSeven.get(day-1));
					appenergy.setTwoDayEnergy(floatToString(value));
				}
				if(day == 3)
				{
					appenergy.setThreeDayTime(dateSeven.get(day-1));
					appenergy.setThreeDayEnergy(floatToString(value));
				}
				if(day == 4)
				{
					appenergy.setFourDayTime(dateSeven.get(day-1));
					appenergy.setFourDayEnergy(floatToString(value));
				}
				if(day == 5)
				{
					appenergy.setFiveDayTime(dateSeven.get(day-1));
					appenergy.setFiveDayEnergy(floatToString(value));
				}
				if(day == 6)
				{
					appenergy.setSixDayTime(dateSeven.get(day-1));
					appenergy.setSixDayEnergy(floatToString(value));
				}
				if(day == 7)
				{
					appenergy.setSevenDayTime(dateSeven.get(day-1));
					appenergy.setSevenDayEnergy(floatToString(value));
				}
				day++;
			}
			sumvalueFlag=sumTagvalue;
			flag++;
		}
		float day_energy = 0;//每天预计产生能耗
		float dr_cost = 0;//设备成本
		float people_cost = 0;//人力成本
		float carbon_parm = 1;//减少标碳参数
		float tree_parm = 1;//相当于植树参数
		float cost_parm = 1;//折合人民币参数
		int sumday=0;
		try {
			sumday = appinfoService.findAppinfo(dbname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		Energyparm energyparm = null;
		try
		{
			energyparm = taskEnergyMapper.findEnergyparm(dbname);
		}catch (Exception e) {
			// TODO: handle exception
		}
		if(energyparm != null)
		{
			if(energyparm.getDayEnergy()!=null && !energyparm.getDayEnergy().equals(""))
			{
				day_energy = Float.parseFloat(energyparm.getDayEnergy());
			}
			if(energyparm.getDrCost()!=null && !energyparm.getDrCost().equals(""))
			{
				dr_cost = Float.parseFloat(energyparm.getDrCost());
			}
			if(energyparm.getPeopleCost()!=null && !energyparm.getPeopleCost().equals(""))
			{
				people_cost = Float.parseFloat(energyparm.getPeopleCost());
			}
			if(energyparm.getCarbonParm()!=null && !energyparm.getCarbonParm().equals(""))
			{
				carbon_parm = Float.parseFloat(energyparm.getCarbonParm());
			}
			if(energyparm.getTreeParm()!=null && !energyparm.getTreeParm().equals(""))
			{
				tree_parm = Float.parseFloat(energyparm.getTreeParm());
			}
			if(energyparm.getCostParm()!=null && !energyparm.getCostParm().equals(""))
			{
				cost_parm = Float.parseFloat(energyparm.getCostParm());
			}
		}
		
		float totalEffEnergy = day_energy*sumday-apptotal;
		if(totalEffEnergy < 0)
		{
			totalEffEnergy=0;
		}
		float appDrCost = dr_cost;
		float appPeopleCost = people_cost;
		float appCarbon = totalEffEnergy*carbon_parm;
		float appTree = totalEffEnergy*tree_parm;
		float appCost = totalEffEnergy*cost_parm;
		appenergy.setTotalEffEnergy(floatToString(totalEffEnergy));
		appenergy.setAppDrCost(floatToString(appDrCost));
		appenergy.setAppPeopleCost(floatToString(appPeopleCost));
		appenergy.setAppCarbon(floatToString(appCarbon));
		appenergy.setAppTree(floatToString(appTree));
		appenergy.setAppCost(floatToString(appCost));
		return appenergy;
	}
	
	private String floatToString(float f)
	{
		String s = String.valueOf((float)(Math.round(f*10))/10);
		return s;
	}
	
	private List<String> getDateYMD(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		List<String> dateList = new ArrayList<String>();
		dateList = beforeSeven(8);
		Collections.reverse(dateList);
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

}
