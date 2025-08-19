package com.photonstudio.task;

import com.photonstudio.pojo.Appenergy;
import com.photonstudio.pojo.Appmanager;
import com.photonstudio.pojo.Energyinfo;
import com.photonstudio.service.QueryAllenergyService;
import com.photonstudio.task.service.TaskEnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

// @EnableScheduling
// @Configuration
public class TaskEnergy {

	@Autowired
	private TaskEnergyService taskEnergyService;
	@Autowired
	private QueryAllenergyService queryAllenergyService;

	@Scheduled(cron = "* * 1 * * ?")
	public void run() {
		List<Appmanager> appmanagerList = taskEnergyService.findAppManagerAll();
		if(appmanagerList.size()>0) 
		{
			for(Appmanager appmanager : appmanagerList)
			{
				String dbname = appmanager.getAppid()+appmanager.getAppName().toLowerCase();
				System.out.println(dbname);
				//获取每个项目的项目总能耗数据  和前7天每天的项目总能耗数据
				Energyinfo energyinfo = null;
				try
				{
					energyinfo = queryAllenergyService.queryAppTotalenergyInfo(dbname,1,1);
				}catch (Exception e) {
					// TODO: handle exception
				}
				Appenergy appenergy = taskEnergyService.queryAppEnergyInfo(dbname,energyinfo);
				appenergy.setAppid(appmanager.getAppid());
				taskEnergyService.insertAppenergy(appenergy);
			}
		}
    }

}
