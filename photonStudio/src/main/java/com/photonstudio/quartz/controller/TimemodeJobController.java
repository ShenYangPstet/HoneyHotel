package com.photonstudio.quartz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.CronDateUtils;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.execute.SayHelloJobLogic;
import com.photonstudio.pojo.TaskDefine;
import com.photonstudio.pojo.Timemodejob;
import com.photonstudio.quartz.service.QuartzJobService;
import com.photonstudio.service.TimemodejobService;

/**
 * Created by EalenXie on 2019/7/10 16:01.
 */
@RestController
@RequestMapping("/timeJob/{dBname}")
public class TimemodeJobController {

	@Resource
	private QuartzJobService quartzJobService;
	@Autowired
	private TimemodejobService timemodejobService;

	/**
	 * 启动 
	 */
	@GetMapping("/startJobByTimemodeid")
	public SysResult startTimeJob(@PathVariable String dBname,  Integer timemodeid,Integer appid) {
		List<Timemodejob> joblist = timemodejobService.findObjectByTimemodeid(dBname,timemodeid);
		//System.out.println(joblist);
		if (joblist.size() == 0 || joblist == null)
			return SysResult.build(50009, "该模式下未配置任务");
		for (Timemodejob job : joblist) {
			String cron = CronDateUtils.getCron(job.getTime());
			Map<String, Object> map=new HashMap<>();
			if (!StringUtils.isEmpty(cron)) {
				//return SysResult.build(50009, "条件名为:“" + qsLdTgr.getLdTjName() + "”的条件时间设置错误");
			map.put("dBname", dBname);
			map.put("appid",appid );
			map.put("ldRwId", job.getLdRwId());
			JobKey jobKeys = JobKey.jobKey(String.valueOf(job.getId()), String.valueOf(job.getLdRwId()));
			// 创建一个定时任务
			TaskDefine task = TaskDefine.builder().jobKey(jobKeys).cronExpression(cron) // 定时任务 的cron表达式
					.jobClass(SayHelloJobLogic.class) // 定时任务 的具体执行逻辑
					.jobDataMap(map)
					.description(job.getLdTjName())// 定时任务 的描述
					.build();
			try {
				quartzJobService.scheduleJob(task);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//return SysResult.build(50009, "创建任务条件名为:" + task.getDescription() + "的任务失败");
			}
			}
		}
		return SysResult.oK();
	}

	/**
	 * 暂停 
	 */
	@GetMapping("/pauseJobByTimemodeid")
	public SysResult pauseTimeJob(@PathVariable String dBname, Integer timemodeid) {
		List<Timemodejob> joblist = timemodejobService.findObjectByTimemodeid(dBname,timemodeid);
		if (joblist.size() == 0)
			return SysResult.build(50009, "该模式未设置任务");

		for (Timemodejob job : joblist) {
			JobKey jobKeys = JobKey.jobKey(String.valueOf(job.getId()), String.valueOf(job.getLdRwId()));
			try {
				quartzJobService.pauseJob(jobKeys);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//return SysResult.build(50009, "暂停任务条件名为:" + qsLdTgr.getLdTjName() + "的任务失败");
			}
		}
		return SysResult.oK();
	}

	/**
	 * 恢复 
	 */
	@GetMapping("/resumeJobByTimemodeid")
	public SysResult resumeTimeJob(@PathVariable String dBname, Integer timemodeid) {
		List<Timemodejob> joblist = timemodejobService.findObjectByTimemodeid(dBname,timemodeid);
		if (joblist.size() == 0)
			return SysResult.build(50009, "该模式未配置任务");

		for (Timemodejob job : joblist) {
			JobKey jobKeys = JobKey.jobKey(String.valueOf(job.getId()), String.valueOf(job.getLdRwId()));
			try {
				quartzJobService.resumeJob(jobKeys);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//return SysResult.build(50009, "恢复任务条件名为:" + job.getLdTjName() + "的任务失败");
			}
		}
		return SysResult.oK();
	}

	/**
	 * 删除 
	 */
	@GetMapping("/deleteJobByTimemodeid")
	public SysResult deleteTimeJob(@PathVariable String dBname, Integer timemodeid) {
		List<Timemodejob> joblist = timemodejobService.findObjectByTimemodeid(dBname,timemodeid);
		if (joblist.size() == 0)
			return SysResult.build(50009, "该模式未配置任务");

		for (Timemodejob job : joblist) {
			JobKey jobKeys = JobKey.jobKey(String.valueOf(job.getId()), String.valueOf(job.getLdRwId()));
			try {
				quartzJobService.deleteJob(jobKeys);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//return SysResult.build(50009, "恢复任务条件名为:" + job.getLdTjName() + "的任务失败");
			}
		}
		return SysResult.oK();

	}

	/**
	 * 修改 cron表达式
	 */
	@GetMapping("/modifyJobCron")
	public SysResult modifyTimeJobCron(@PathVariable String dBname, Timemodejob job) {

		// 这是即将要修改cron的定时任务
		String cron = CronDateUtils.getCron(job.getTime());
		if (StringUtils.isEmpty(cron))
			return SysResult.build(50009, "条件名为:“" + job.getLdTjName() + "”的条件时间设置错误");
		JobKey jobKeys = JobKey.jobKey(String.valueOf(job.getId()), String.valueOf(job.getLdRwId()));
		// 创建一个定时任务
		TaskDefine task = TaskDefine.builder().jobKey(jobKeys).cronExpression(cron) // 定时任务 的cron表达式
				.jobClass(SayHelloJobLogic.class) // 定时任务 的具体执行逻辑
				.description(job.getLdTjName())// 定时任务 的描述
				.build();

		if (quartzJobService.modifyJobCron(task))
			return SysResult.oK();
		else
			return SysResult.build(50009, "修改失败");
	}

	@GetMapping("/findState")
	public SysResult findState(@PathVariable String dBname, Integer id) {
		Timemodejob job = timemodejobService.findObjectById(dBname, id);
		if(job==null)return SysResult.build(50009, "未找到任务");
		JobKey jobKeys = JobKey.jobKey(String.valueOf(job.getId()), String.valueOf(job.getLdRwId()));
		return SysResult.oK(quartzJobService.findState(jobKeys));
	}

}
