package com.photonstudio.execute;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.photonstudio.pojo.QsLdJgr;
import com.photonstudio.service.QsLdJgrService;


/**
 * Created by EalenXie on 2019/7/10 15:30.
 * <p>
 * Job 是 定时任务的具体执行逻辑
 * JobDetail 是 定时任务的定义
 */
@Slf4j
@DisallowConcurrentExecution
public class SayHelloJobLogic implements Job {
	@Autowired
	private QsLdJgrService qsLdJgrService ;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //写你自己的逻辑
    	JobDataMap jdMap=jobExecutionContext.getJobDetail().getJobDataMap();
    	String dBname=jdMap.getString("dBname");
    	Integer ldRwId=jdMap.getInt("ldRwId");
    	Integer appid=jdMap.getInt("appid");
    	List<QsLdJgr>list=qsLdJgrService.findObjectByldRwId(dBname,ldRwId);
		if(list!=null&&list.size()>0) {
			qsLdJgrService.doQsLdJg(dBname,list,appid,null);
		}

    }
}
