package com.photonstudio.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.photonstudio.mapper.QsLdTgrMapper;
import com.photonstudio.mapper.TimemodejobMapper;
import com.photonstudio.pojo.DateCalendar;
import com.photonstudio.pojo.QsLdTgr;
import com.photonstudio.pojo.TaskDefine;
import com.photonstudio.pojo.Timemodejob;
import com.photonstudio.quartz.service.QuartzJobService;
import com.photonstudio.service.QsLdTgrService;
import com.photonstudio.service.TimeModeService;
@Service
public class QsLdTgrServiceImpl implements QsLdTgrService {
	@Autowired
	private QsLdTgrMapper qsLdTgrMapper;
	@Autowired
	private TimemodejobMapper timemodejobMapper;
	@Autowired
	private TimeModeService timeModeService;
	@Autowired
	private QuartzJobService quartzJobService;
	@Override
	public PageObject<QsLdTgr> findObject(String dBname, Integer tjType, Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int startIndex=(pageCurrent-1)*pageSize;
		int rowCount=qsLdTgrMapper.getRowCount(dBname,tjType);
		List<QsLdTgr>list=new ArrayList<>();
		list=qsLdTgrMapper.findObject(dBname,tjType,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, QsLdTgr qsLdTgr) {
		int rows=0;
		try {
			rows=qsLdTgrMapper.insertObject(dBname,qsLdTgr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname,Integer tjType ,Integer... ids) {
		
		int rows=0;
		try {
			rows=qsLdTgrMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, QsLdTgr qsLdTgr,Integer appid) {
		System.out.println(qsLdTgr.getTime());
		int rows=0;
		try {
			QsLdTgr qsLdTgrDB=qsLdTgrMapper.findObjectByldTjId(dBname, qsLdTgr.getLdTjId());
			System.out.println(qsLdTgrDB.getTime());
			if(qsLdTgr.getTime()!=null&&!qsLdTgr.getTime().equals(qsLdTgrDB.getTime())) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				List<Timemodejob>joblist=timemodejobMapper.findObjectByLdTjId(dBname,qsLdTgr.getLdTjId());
				if(joblist!=null&&joblist.size()>0) {
				for (Timemodejob timemodejob : joblist) {
					JobKey jobKeys = JobKey.jobKey(String.valueOf(timemodejob.getId()), dBname);
						quartzJobService.deleteJob(jobKeys);
				String timestr = formatter.format(timemodejob.getTime());
				System.out.println("timestr===="+timestr);
				String timestr2=timestr.replace(qsLdTgrDB.getTime(), qsLdTgr.getTime());
				System.out.println("timestr2===="+timestr2);
				timemodejob.setTime(formatter.parse(timestr2));
				timemodejobMapper.updateObject(dBname, timemodejob);
				Date date=new Date();
				if(timemodejob.getTime().getTime()<date.getTime())continue;
				String cron = CronDateUtils.getCron(timemodejob.getTime());
				Map<String, Object> map=new HashMap<>();
				if (!StringUtils.isEmpty(cron)) {
				map.put("dBname", dBname);
				map.put("appid",appid );
				map.put("ldRwId", timemodejob.getLdRwId());
				// 创建一个定时任务
				TaskDefine task = TaskDefine.builder().jobKey(jobKeys).cronExpression(cron) // 定时任务 的cron表达式
						.jobClass(SayHelloJobLogic.class) // 定时任务 的具体执行逻辑
						.jobDataMap(map)
						.description(timemodejob.getLdTjName())// 定时任务 的描述
						.build();
					quartzJobService.scheduleJob(task);
					System.out.println("时间条件更新，创建任务");
				}
				}
				}
			}
			rows=qsLdTgrMapper.updateObject(dBname,qsLdTgr);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public void importObjects(String dBname, Integer tjType, List<QsLdTgr> list) {
		for (QsLdTgr qsLdTgr : list) {
			Integer id=qsLdTgr.getLdTjId();
			qsLdTgr.setTjType(tjType);
			if(id==null||id<1) {
				qsLdTgrMapper.insertObject(dBname, qsLdTgr);
			}else {
				int rows=qsLdTgrMapper.findObjectById(dBname,id);
				if(rows==1) {
					qsLdTgrMapper.updateObject(dBname, qsLdTgr);
				}else {
					qsLdTgrMapper.insertObject(dBname, qsLdTgr);
				}
			}
		}
		
	}

	@Override
	public List<QsLdTgr> findAllByRwType(String dBname, Integer tjType) {
		List<QsLdTgr> list=new ArrayList<>();
		try {
			list=qsLdTgrMapper.findObject(dBname, tjType, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}

	@Override
	public List<DateCalendar> findDate(String yearstr,String monthstr) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int curyear = calendar.get(Calendar.YEAR);
		int curmonth = calendar.get(Calendar.MONTH)+1;
		int curday = calendar.get(Calendar.DATE);
		int year = Integer.valueOf(yearstr);
		int month = Integer.valueOf(monthstr);
		int days = getdays(month,year);
		List<DateCalendar> dateList = new ArrayList<DateCalendar>();
		for(int i=1;i<=days;i++)
		{
			DateCalendar dateCalendar = new DateCalendar();
			if(curyear>year)
			{
				dateCalendar.setDay(i);
				dateCalendar.setState(0);
			}
			else if(curyear<year)
			{
				dateCalendar.setDay(i);
				dateCalendar.setState(1);
			}
			else
			{
				if(curmonth > month)
				{
					dateCalendar.setDay(i);
					dateCalendar.setState(0);
				}
				else if(curmonth < month)
				{
					dateCalendar.setDay(i);
					dateCalendar.setState(1);
				}
				else
				{
					if(i<curday)
					{
						dateCalendar.setDay(i);
						dateCalendar.setState(0);
					}
					else
					{
						dateCalendar.setDay(i);
						dateCalendar.setState(1);
					}
				}
			}
			dateList.add(dateCalendar);
		}
		
		return dateList;
	}
	
	private String getmonthorday(int arg)
	{
		if(arg<10)
		{
			return "0"+arg;
		}
		else
		{
			return String.valueOf(arg);
		}
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
	public int savebydate(String dBname, String tgName, Integer rwid, String dateArry) {
		// TODO Auto-generated method stub
		String[] datearry = dateArry.split(",",-1);
		int rows = 0;
//		if(datearry.length>0)
//		{
//			for(String datestr : datearry)
//			{
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//				Date date = null;
//				try {
//					date = formatter.parse(datestr);
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				QsLdTgr qsLdTgr = new QsLdTgr();
//				qsLdTgr.setLdRwId(rwid);
//				qsLdTgr.setLdTjName(tgName);
//				qsLdTgr.setTime(date);
//				rows=qsLdTgrMapper.insertObject(dBname,qsLdTgr);
//			}
//		}
		
		return rows;
	}

	@Override
	public List<QsLdTgr> findObjectByLdRwIdAndTjType(String dBname, Integer ldRwId, Integer tjType) {
		// TODO Auto-generated method stub
		List<QsLdTgr>list=qsLdTgrMapper.findObjectByLdRwIdAndTjType(dBname,ldRwId,tjType);
		return list;
	}

	@Override
	public QsLdTgr findObjectByldTjId(String dBname, Integer ldTjId) {
		// TODO Auto-generated method stub
		QsLdTgr qsLdTgr=qsLdTgrMapper.findObjectByldTjId(dBname, ldTjId);
		return qsLdTgr;
	}

	@Override
	public void deleteObjectByRwId(String dBname, Integer... ids) {
		// TODO Auto-generated method stub
		try {
			qsLdTgrMapper.deleteObjectByRwId(dBname, ids);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
