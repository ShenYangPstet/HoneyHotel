package com.photonstudio.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.mapper.TaskEnergyMapper;
import com.photonstudio.pojo.Appenergy;
import com.photonstudio.pojo.AppenergyData;
import com.photonstudio.service.AppenergyService;

@Service
public class AppenergyServiceImpl implements AppenergyService{
	
	@Autowired
	private TaskEnergyMapper taskEnergyMapper;

	@Override
	public AppenergyData queryAppenergyData(Integer[] appids) {
		// TODO Auto-generated method stub
		AppenergyData appenergyData = new AppenergyData();
		Map<String,String> beforSevenData =  new HashMap<String, String>();
		Map<Integer,String> appEnergyData = new HashMap<Integer,String>();
		float apptotalEffEnergy = 0;
		float apptotalDrCost = 0;
		float apptotalPeopleCost = 0;
		float apptotalCarbon = 0;
		float apptotalTree = 0;
		float apptotalCost = 0;
		
		float totalOneDayEnergy = 0;
		float totalTwoDayEnergy = 0;
		float totalThreeDayEnergy = 0;
		float totalFourDayEnergy = 0;
		float totalFiveDayEnergy = 0;
		float totalSixDayEnergy = 0;
		float totalSevenDayEnergy = 0;
		List<Appenergy> appenergyList = taskEnergyMapper.queryAppenergyData(appids);
		int size = 0;
		if(appenergyList.size()>0)
		{
			for(Appenergy appenergy : appenergyList)
			{
				totalOneDayEnergy=totalOneDayEnergy+Float.parseFloat(appenergy.getOneDayEnergy());
				totalTwoDayEnergy=totalTwoDayEnergy+Float.parseFloat(appenergy.getTwoDayEnergy());
				totalThreeDayEnergy=totalThreeDayEnergy+Float.parseFloat(appenergy.getThreeDayEnergy());
				totalFourDayEnergy=totalFourDayEnergy+Float.parseFloat(appenergy.getFourDayEnergy());
				totalFiveDayEnergy=totalFiveDayEnergy+Float.parseFloat(appenergy.getFiveDayEnergy());
				totalSixDayEnergy=totalSixDayEnergy+Float.parseFloat(appenergy.getSixDayEnergy());
				totalSevenDayEnergy=totalSevenDayEnergy+Float.parseFloat(appenergy.getSevenDayEnergy());
				
				apptotalEffEnergy=apptotalEffEnergy+Float.parseFloat(appenergy.getTotalEffEnergy());
				apptotalDrCost=apptotalDrCost+Float.parseFloat(appenergy.getAppDrCost());
				apptotalPeopleCost=apptotalPeopleCost+Float.parseFloat(appenergy.getAppPeopleCost());
				apptotalCarbon=apptotalCarbon+Float.parseFloat(appenergy.getAppCarbon());
				apptotalTree=apptotalTree+Float.parseFloat(appenergy.getAppTree());
				apptotalCost=apptotalCost+Float.parseFloat(appenergy.getAppCost());
				
				appEnergyData.put(appenergy.getAppid(), appenergy.getAppTotalEnergy());
				
				size++;
				if(size == appenergyList.size())
				{
					beforSevenData.put(appenergy.getOneDayTime(), String.valueOf(totalOneDayEnergy));
					beforSevenData.put(appenergy.getTwoDayTime(), String.valueOf(totalTwoDayEnergy));
					beforSevenData.put(appenergy.getThreeDayTime(), String.valueOf(totalThreeDayEnergy));
					beforSevenData.put(appenergy.getFourDayTime(), String.valueOf(totalFourDayEnergy));
					beforSevenData.put(appenergy.getFiveDayTime(), String.valueOf(totalFiveDayEnergy));
					beforSevenData.put(appenergy.getSixDayTime(), String.valueOf(totalSixDayEnergy));
					beforSevenData.put(appenergy.getSevenDayTime(), String.valueOf(totalSevenDayEnergy));
				}
			}
		}
		appenergyData.setAppEnergyData(appEnergyData);
		appenergyData.setBeforSevenData(beforSevenData);
		appenergyData.setApptotalCarbon(String.valueOf(apptotalCarbon));
		appenergyData.setApptotalCost(String.valueOf(apptotalCost));
		appenergyData.setApptotalDrCost(String.valueOf(apptotalDrCost));
		appenergyData.setApptotalEffEnergy(String.valueOf(apptotalEffEnergy));
		appenergyData.setApptotalPeopleCost(String.valueOf(apptotalPeopleCost));
		appenergyData.setApptotalTree(String.valueOf(apptotalTree));
		return appenergyData;
	}

}
