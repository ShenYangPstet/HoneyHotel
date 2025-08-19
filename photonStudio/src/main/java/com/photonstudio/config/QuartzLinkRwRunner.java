package com.photonstudio.config;

import com.photonstudio.execute.LinkFiedJobQuartz;
import com.photonstudio.pojo.Appmanager;
import com.photonstudio.pojo.Qslinkrw;
import com.photonstudio.pojo.TaskDefine;
import com.photonstudio.quartz.service.QuartzJobService;
import com.photonstudio.service.AppmanagerService;
import com.photonstudio.service.QslinkService;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(2)
@Profile("prod")
public class QuartzLinkRwRunner implements CommandLineRunner {
    @Autowired
    private AppmanagerService appmanagerService;
    @Autowired
    private QuartzJobService quartzJobService;
    @Autowired
    private QslinkService qslinkService;

    @Override
    public void run(String... args) throws Exception {
        List<Appmanager> applist = appmanagerService.findObjectByState("1", "0");
        for (Appmanager appmanager : applist) {
            try {
                String dBname = appmanager.getAppid() + appmanager.getAppName();
                List<Qslinkrw> list = qslinkService.findQslinkrwList(dBname);
                if (list != null && list.size() > 0) {
                    for (Qslinkrw qslinkrw : list)
					{
						Map<String, Object> map=new HashMap<>();
						map.put("appid", appmanager.getAppid());
						map.put("rwId", qslinkrw.getRwId());
						map.put("dBname", dBname);
						JobKey jobKeys = JobKey.jobKey(String.valueOf(qslinkrw.getRwId()), dBname+"变量条件");
						TaskDefine task = TaskDefine.builder().jobKey(jobKeys).cronExpression("0/3 * * * * ? ") // 定时任务 的cron表达式
								.jobClass(LinkFiedJobQuartz.class) // 定时任务 的具体执行逻辑
								.jobDataMap(map)
								.description(qslinkrw.getRwName())// 定时任务 的描述
								.build();
						quartzJobService.scheduleJob(task);
						// TODO Auto-generated catch block
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
