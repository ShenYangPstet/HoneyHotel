package com.photonstudio.config;

import com.photonstudio.common.CronDateUtils;
import com.photonstudio.execute.RegFiedJobQuartz;
import com.photonstudio.pojo.Appmanager;
import com.photonstudio.pojo.Qsmsjbtime;
import com.photonstudio.pojo.TaskDefine;
import com.photonstudio.quartz.service.QuartzJobService;
import com.photonstudio.service.AppmanagerService;
import com.photonstudio.service.QsMsService;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(2)
@Profile("prod")
public class QuartzLineRunner implements CommandLineRunner {
    @Autowired
    private AppmanagerService appmanagerService;
    @Autowired
    private QuartzJobService quartzJobService;
    @Autowired
    private QsMsService qsMsService;

    @Override
    public void run(String... args) throws Exception {
		List<Appmanager>applist=appmanagerService.findObjectByState("1","0");
		for (Appmanager appmanager : applist) 
		{
			try
			{
				String dBname=appmanager.getAppid()+appmanager.getAppName();
				List<Qsmsjbtime> joblist=qsMsService.findQsMsJbtimeAfNow(dBname);
				if(joblist!=null&&joblist.size()>0) 
				{
					for (Qsmsjbtime job : joblist) 
					{
						Date date=new Date();
						if(job.getTime().getTime()<date.getTime())continue;
						String cron = CronDateUtils.getCron(job.getTime());
						Map<String, Object> map=new HashMap<>();
						if (!StringUtils.isEmpty(cron)) 
						{
							//return SysResult.build(50009, "条件名为:“" + qsLdTgr.getLdTjName() + "”的条件时间设置错误");
							map.put("dBname", dBname);
							map.put("appid",appmanager.getAppid() );
							map.put("dzid", job.getDzid());
							JobKey jobKeys = JobKey.jobKey(String.valueOf(job.getJobid()), dBname);
							// 创建一个定时任务
							TaskDefine task = TaskDefine.builder().jobKey(jobKeys).cronExpression(cron) // 定时任务 的cron表达式
									.jobClass(RegFiedJobQuartz.class) // 定时任务 的具体执行逻辑
									.jobDataMap(map)
									.description(job.getDzname())// 定时任务 的描述
									.build();
						
							quartzJobService.scheduleJob(task);
						}
					}
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//return SysResult.build(50009, "创建任务条件名为:" + task.getDescription() + "的任务失败");
			}
		} 
	}
	
}
