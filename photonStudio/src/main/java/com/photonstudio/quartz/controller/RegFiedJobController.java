package com.photonstudio.quartz.controller;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.JobStatesUtil;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.execute.RegFiedJobQuartz;
import com.photonstudio.pojo.QsLdRw;
import com.photonstudio.pojo.TaskDefine;
import com.photonstudio.quartz.service.QuartzJobService;
import com.photonstudio.service.QsLdRwService;

@RestController
@RequestMapping("/zsqy/regJob/{dBname}")
public class RegFiedJobController {
	@Autowired
	private QuartzJobService quartzJobService;
	@Autowired
	private QsLdRwService qsLdRwService;
	@RequestMapping("/startJobByLdRwId")
	public SysResult startRegJob(@PathVariable String dBname,Integer ldRwId,Integer appid) {
		QsLdRw qsLdRw=qsLdRwService.findObjectById(dBname,ldRwId);
		if(qsLdRw==null) return SysResult.build(50009, "任务不存在");
		Map<String, Object> map=new HashMap<>();
		map.put("appid", appid);
		map.put("ldRwId", ldRwId);
		map.put("dBname", dBname);
		JobKey jobKeys = JobKey.jobKey(String.valueOf(ldRwId), dBname+"变量条件");
		// 创建一个定时任务
		TaskDefine task = TaskDefine.builder().jobKey(jobKeys).cronExpression("0/3 * * * * ? ") // 定时任务 的cron表达式
				.jobClass(RegFiedJobQuartz.class) // 定时任务 的具体执行逻辑
				.jobDataMap(map)
				.description(qsLdRw.getLdRwMiaoshu())// 定时任务 的描述
				.build();
		try {
			qsLdRw.setIssuspend(0);
			qsLdRwService.updateObject(dBname, qsLdRw);
			quartzJobService.scheduleJob(task);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "创建任务条件名为:" + qsLdRw.getLdRwName() + "的任务失败");
		}
		return SysResult.oK();
		}
	@RequestMapping("/deleteJobByLdRwId")
	public SysResult deleteRegJob(@PathVariable String dBname,Integer ldRwId) {
		JobKey jobKeys = JobKey.jobKey(String.valueOf(ldRwId),dBname+"变量条件");
		QsLdRw qsLdRw=new QsLdRw();
		try {
			qsLdRw.setLdRwId(ldRwId);
			qsLdRw.setIssuspend(1);
			qsLdRwService.updateObject(dBname, qsLdRw);
			quartzJobService.deleteJob(jobKeys);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009," 删除任务失败");
		}
		return SysResult.oK();
	}
	@GetMapping("/findState")
	public SysResult findState(@PathVariable String dBname, Integer ldRwId) {
		JobKey jobKeys = JobKey.jobKey(String.valueOf(ldRwId), dBname+"变量条件");
		String State = quartzJobService.findState(jobKeys);
		return SysResult.oK(JobStatesUtil.getTriggerStatesCN(State));
	}
	}

