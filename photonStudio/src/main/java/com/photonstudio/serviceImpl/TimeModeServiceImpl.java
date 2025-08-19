package com.photonstudio.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.photonstudio.common.CronDateUtils;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.execute.SayHelloJobLogic;
import com.photonstudio.mapper.TimemodeMapper;
import com.photonstudio.mapper.TimemodejobMapper;
import com.photonstudio.pojo.QsLdTgr;
import com.photonstudio.pojo.TaskDefine;
import com.photonstudio.pojo.Timemode;
import com.photonstudio.pojo.Timemodeinfo;
import com.photonstudio.pojo.Timemodejob;
import com.photonstudio.pojo.YmdJob;
import com.photonstudio.quartz.service.QuartzJobService;
import com.photonstudio.service.TimeModeService;

@Service
public class TimeModeServiceImpl implements TimeModeService{
	@Autowired
	private QuartzJobService quartzJobService;
	@Autowired
	private TimemodeMapper timeModeMapper;
	@Autowired
	private TimemodejobMapper timemodejobMapper;

	@Override
	public PageObject<Timemode> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount= timeModeMapper.getRowCount(dBname);
		int startIndex=(pageCurrent-1)*pageSize;
		List<Timemode> list=timeModeMapper.findObject(dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
	
	@Override
	public PageObject<Timemodeinfo> findObjectinfo(String dBname, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount= timeModeMapper.getRowCountinfo(dBname);
		int startIndex=(pageCurrent-1)*pageSize;
		List<Timemodeinfo> list=timeModeMapper.findObjectinfo(dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, Timemode timemode) {
		System.out.println("timemode=="+timemode);
		// TODO Auto-generated method stub
		return timeModeMapper.save(dBname,timemode);
	}
	
	@Override
	public int saveObjectinfo(String dBname, Timemodeinfo timemodeinfo) {
		// TODO Auto-generated method stub
		return timeModeMapper.saveinfo(dBname,timemodeinfo);
	}

	@Override
	public int deleteObject(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		if(ids==null||ids.length==0)throw new ServiceException("请选择");
		int rows=0;
		try {
			timeModeMapper.deleteObjectinfoByModeIds(dBname, ids);
			rows=timeModeMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Timemode timemode) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=timeModeMapper.updateObject(dBname,timemode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public List<Timemodeinfo> findModeinfoByid(String dBname, Integer id) {
		// TODO Auto-generated method stub
		return timeModeMapper.findModeinfoByid(dBname,id);
	}

	@Override
	public int updateObjectinfo(String dBname, Timemodeinfo timemodeinfo) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=timeModeMapper.updateObjectinfo(dBname,timemodeinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectinfo(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		if(ids==null||ids.length==0)throw new ServiceException("请选择");
		int rows=0;
		try {
			rows=timeModeMapper.deleteObjectinfoById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public List<YmdJob> findTimemodejob(String dBname, String yearMonth) {
		// TODO Auto-generated method stub
		List<YmdJob> list=timeModeMapper.findTimemodejob(dBname,yearMonth);
		return list;
	}

	@Override
	public List<Timemode> findAllMode(String dBname) {
		// TODO Auto-generated method stub
		return timeModeMapper.findAllMode(dBname);
	}

	@Override
	public List<QsLdTgr> findAllTg(String dBname) {
		// TODO Auto-generated method stub
		return timeModeMapper.findAllTg(dBname);
	}

	@Override
	public void saveTimemodejob(String dBname, String timemodes,Integer appid) throws Exception {
		// TODO Auto-generated method stub
		String[] arryTimemode = timemodes.split(",",-1);
		System.out.println("timemodes=="+timemodes);
		for(String timemode : arryTimemode)
		{
			String[] timejob = timemode.split("_",-1);
			int flag = Integer.valueOf(timejob[0]);
			String ymd = timejob[1];
			int timemodeid = Integer.valueOf(timejob[2]);
			List<Timemodeinfo> modeinfoList = timeModeMapper.findModeinfoByid(dBname,timemodeid);
			if(modeinfoList.size()<1)
			{
				continue;
			}
			if(flag == 1)//新增
			{
				publicSaveJob(dBname,ymd,timemodeid,modeinfoList);
				startJob(dBname,ymd,appid);
			}
			if(flag == 2)//修改
			{
				//先删除后新增 按照时间yyyymmdd删除当天的老模式
				deletejob(dBname, ymd);
				timeModeMapper.deletemodejobBytime(dBname,ymd);
				
				publicSaveJob(dBname,ymd,timemodeid,modeinfoList);
				startJob(dBname,ymd,appid);
			}
			if(flag == 3)//删除
			{
				deletejob(dBname, ymd);
				timeModeMapper.deletemodejobBytime(dBname,ymd);
			}
		}
	}

	private void publicSaveJob(String dBname, String ymd, int timemodeid, List<Timemodeinfo> modeinfoList) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("timemodeid=="+timemodeid);
		for(Timemodeinfo timemodeinfo : modeinfoList)
		{
			Timemodejob timemodejob = new Timemodejob();
			Integer ldTjId = timemodeinfo.getLdTjId();
			QsLdTgr qsLdTgr = timeModeMapper.findTgByTjid(dBname,ldTjId);
			Integer ldRwId = qsLdTgr.getLdRwId();
//			String timeStr =ymd.substring(0,4)+"-"+ymd.substring(4,6)+"-"+ymd.substring(6,8)+" "+qsLdTgr.getTime();
			String timeStr =ymd+" "+qsLdTgr.getTime();
			Date time = formatter.parse(timeStr);
			timemodejob.setLdTjId(ldTjId);
			timemodejob.setLdRwId(ldRwId);
			timemodejob.setTimemodeid(timemodeid);
			timemodejob.setTime(time);
			timeModeMapper.savejob(dBname,timemodejob);
			
		}
	}
	private void startJob(String dBname,String ymd,Integer appid) {
		List<Timemodejob>joblist= timemodejobMapper.findObject(dBname, null, ymd,null,null);
		if (joblist.size() == 0 || joblist == null)
			return ;
		for (Timemodejob job : joblist) {
			Date date=new Date();
			if(job.getTime().getTime()<date.getTime())continue;
			String cron = CronDateUtils.getCron(job.getTime());
			Map<String, Object> map=new HashMap<>();
			if (!StringUtils.isEmpty(cron)) {
			map.put("dBname", dBname);
			map.put("appid",appid );
			map.put("ldRwId", job.getLdRwId());
			JobKey jobKeys = JobKey.jobKey(String.valueOf(job.getId()), dBname);
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
	}
	private void deletejob(String dBname,String ymd) {
		List<Timemodejob>joblist= timemodejobMapper.findObject(dBname, null, ymd,null,null);
		if (joblist.size() == 0 || joblist == null)return ;
		for (Timemodejob job : joblist) {
			JobKey jobKeys = JobKey.jobKey(String.valueOf(job.getId()), dBname);
			try {
				quartzJobService.deleteJob(jobKeys);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Timemodeinfo> findModeinfoByTjIds(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		List<Timemodeinfo>list=new ArrayList<>();
		try {
			list=timeModeMapper.findModeinfoByTjIds(dBname, ids);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public List<Timemodeinfo> findObjectinfoByIds(String dBname, Integer... ids) {
		// TODO Auto-generated method stub
		List<Timemodeinfo> infolist=new ArrayList<>();
		try {
			infolist=timeModeMapper.findObjectinfoByIds(dBname,ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return infolist;
	}

}
