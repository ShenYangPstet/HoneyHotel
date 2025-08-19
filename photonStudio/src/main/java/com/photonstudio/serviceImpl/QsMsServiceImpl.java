package com.photonstudio.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.collections4.map.HashedMap;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.photonstudio.common.CronDateUtils;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.execute.RegFiedJobQuartz;
import com.photonstudio.mapper.QsMsMapper;
import com.photonstudio.pojo.Qsmsdz;
import com.photonstudio.pojo.Qsmsjb;
import com.photonstudio.pojo.Qsmsjblog;
import com.photonstudio.pojo.Qsmsjbtime;
import com.photonstudio.pojo.Qsmsjg;
import com.photonstudio.pojo.Qsmsrw;
import com.photonstudio.pojo.TaskDefine;
import com.photonstudio.quartz.service.QuartzJobService;
import com.photonstudio.service.QsMsService;

@Service
public class QsMsServiceImpl implements QsMsService{
	
	@Autowired
	private QsMsMapper qsMsMapper;
	@Autowired
	private QuartzJobService quartzJobService;

	@Override
	public List<Qsmsrw> findMsRw(String dBname, Integer drtypeid) {
		// TODO Auto-generated method stub
		return qsMsMapper.findMsRw(dBname,drtypeid);
	}

	@Override
	public void insertMsRw(String dBname, Qsmsrw qsmsrw) {
		// TODO Auto-generated method stub
		Integer id = qsmsrw.getId();
		if(id == null || id == 0)
		{
			qsMsMapper.insertMsRw(dBname,qsmsrw);
		}
		else
		{
			int row = qsMsMapper.findRowByRwId(dBname,id);
			if(row > 0)
			{
				qsMsMapper.updateMsRw(dBname,qsmsrw);
			}
			else
			{
				qsMsMapper.insertMsRw(dBname,qsmsrw);
			}
		}
	}

	@Override
	public void updateMsRw(String dBname, Qsmsrw qsmsrw) {
		// TODO Auto-generated method stub
		qsMsMapper.updateMsRw(dBname,qsmsrw);
	}

	@Override
	public void deleteMsRw(String dBname, Integer id) {
		// TODO Auto-generated method stub
		qsMsMapper.deleteMsRw(dBname,id);
	}

	@Override
	public PageObject<Qsmsdz> findMsDz(String dBname, Integer rwid,Integer pageCurrent,Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		
		int rowCount = qsMsMapper.getCount(dBname,rwid);
		int startIndex=(pageCurrent-1)*pageSize;
		
		List<Qsmsdz> list = qsMsMapper.findMsDz(dBname,rwid,startIndex,pageSize);
		
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public void insertMsDz(String dBname, Qsmsdz qsmsdz) {
		// TODO Auto-generated method stub
		qsMsMapper.insertMsDz(dBname,qsmsdz);
	}

	@Override
	public void updateMsDz(String dBname, Qsmsdz qsmsdz,Integer appid) {
		// TODO Auto-generated method stub
		Integer dzid = qsmsdz.getId();
		Qsmsdz qsmsdzdb = qsMsMapper.findMsDzById(dBname,dzid);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Qsmsjbtime> qsmsjbtimeList = qsMsMapper.findMSjbtimeBYdzid(dBname,dzid);
		if(!qsmsdz.getTime().equals(qsmsdzdb.getTime())&& qsmsjbtimeList!=null&&
		!qsmsjbtimeList.isEmpty())
		{
			System.out.println("dz time is mod,job restart!");
			deletejob(dBname,qsmsjbtimeList);
			for(Qsmsjbtime qsmsjbtime : qsmsjbtimeList)
			{
				String timeStr =qsmsjbtime.getYmd()+" "+qsmsdz.getTime();
				try {
					qsmsjbtime.setTime(formatter.parse(timeStr));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			qsMsMapper.updateQsmsjbtime(dBname,qsmsjbtimeList);
			startJobModDz(dBname, qsmsjbtimeList, appid);
		}
		qsMsMapper.updateMsDz(dBname,qsmsdz);
	}

	@Override
	public void deleteMsDz(String dBname, Integer id) {
		// TODO Auto-generated method stub
		qsMsMapper.deleteMsDz(dBname,id);
	}

	@Override
	public PageObject<Qsmsjg> findMsJg(String dBname, Integer dzid,Integer pageCurrent,Integer pageSize) {
		// TODO Auto-generated method stub
		
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		
		int rowCount = qsMsMapper.getCountJg(dBname,dzid);
		int startIndex=(pageCurrent-1)*pageSize;
		
		List<Qsmsjg> list = qsMsMapper.findMsJg(dBname,dzid,startIndex,pageSize);
		
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public void insertMsJg(String dBname,Integer dzid,Integer[] regids,String value) {
		// TODO Auto-generated method stub
		for(Integer regid : regids)
		{
			Qsmsjg qsmsjg = new Qsmsjg();
			qsmsjg.setDzid(dzid);
			qsmsjg.setRegid(regid);
			qsmsjg.setValue(value);
			qsMsMapper.insertMsJg(dBname,qsmsjg);
		}
	}

	@Override
	public void updateMsJg(String dBname, Qsmsjg qsmsjg) {
		// TODO Auto-generated method stub
		qsMsMapper.updateMsJg(dBname,qsmsjg);
	}

	@Override
	public void deleteMsJg(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		qsMsMapper.deleteMsJg(dBname,ids);
	}

	@Override
	public List<Qsmsjg> findQsmsjg(String dBname, Integer dzid) {
		// TODO Auto-generated method stub
		return qsMsMapper.findQsmsjg(dBname,dzid);
	}

	@Override
	public void insertQsmsjg(String dBname, Qsmsjg qsmsjg) {
		// TODO Auto-generated method stub
		Integer id = qsmsjg.getId();
		if(id==null || id==0)
		{
			qsMsMapper.insertMsJg(dBname,qsmsjg);
		}
		else
		{
			
			int row = qsMsMapper.findRowByJgId(dBname,id);
			if(row >0 )
			{
				qsMsMapper.updateMsJg(dBname,qsmsjg);
			}
			else
			{
				qsMsMapper.insertMsJg(dBname,qsmsjg);
			}
		}
	}

	@Override
	public Map<String, List<Qsmsrw>> findAllMsRw(String dBname) {
		// TODO Auto-generated method stub
		
		List<Qsmsrw> list = qsMsMapper.findAllMsRw(dBname);
		Map<String,List<Qsmsrw>> mapQsmsrw = new HashedMap<>();
		for(Qsmsrw qsmsrw : list)
		{
			String drtypename = qsmsrw.getDrtypename();
			List<Qsmsrw> listtmp = mapQsmsrw.get(drtypename);
			if(listtmp == null)
			{
			
				List<Qsmsrw> listq = new ArrayList<>();
				listq.add(qsmsrw);
				mapQsmsrw.put(drtypename, listq);
			}
			else
			{
				listtmp.add(qsmsrw);
			}
		}
		return mapQsmsrw;
	}

	@Override
	public void insertMsJb(String dBname, String[] ymds, Integer[] rwids,Integer appid) {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Qsmsjbtime> qsmsjbtimeList = qsMsMapper.findMSjbtime(dBname);
		List<Qsmsdz> qsmsdzList = qsMsMapper.findAllMSdz(dBname);
		Map<String,List<Qsmsjbtime>> qsmsjbtimeMap = new HashedMap<>();
		Map<Integer,List<Qsmsdz>> qsmsdzMap = new HashedMap<>();
		for(Qsmsjbtime qsmsjbtime : qsmsjbtimeList)
		{
			List<Qsmsjbtime> qsmsjbtimeListTmp = qsmsjbtimeMap.get(qsmsjbtime.getYmd());
			if(qsmsjbtimeListTmp == null)
			{
				List<Qsmsjbtime> qsmsjbtimeListYmd = new ArrayList<>();
				qsmsjbtimeListYmd.add(qsmsjbtime);
				qsmsjbtimeMap.put(qsmsjbtime.getYmd(), qsmsjbtimeListYmd);
			}
			else
			{
				qsmsjbtimeMap.get(qsmsjbtime.getYmd()).add(qsmsjbtime);
			}
		}
		for(Qsmsdz qsmsdz : qsmsdzList)
		{
			List<Qsmsdz> qsmsdzListTmp = qsmsdzMap.get(qsmsdz.getRwid());
			if(qsmsdzListTmp == null)
			{
				List<Qsmsdz> qsmsdzListRwid = new ArrayList<>();
				qsmsdzListRwid.add(qsmsdz);
				qsmsdzMap.put(qsmsdz.getRwid(), qsmsdzListRwid);
			}
			else
			{
				qsmsdzMap.get(qsmsdz.getRwid()).add(qsmsdz);
			}
		}
		
		List<Qsmsjb> jbList = new ArrayList<>();
		List<Qsmsjbtime> timeList = new ArrayList<>();
		qsMsMapper.deleteMsJb(dBname, ymds);
		qsMsMapper.deleteMsJbTime(dBname, ymds);
		for(String ymd : ymds)
		{
			List<Qsmsjbtime> jbTimeList = qsmsjbtimeMap.get(ymd);
			if(null != jbTimeList && jbTimeList.size()>0)
			{
				deletejob(dBname, jbTimeList);
			}
			if (ObjectUtil.isNotEmpty(rwids)) {
				for(Integer rwid : rwids)
				{
					Qsmsjb qsmsjb = new Qsmsjb();
					qsmsjb.setYmd(ymd);
					qsmsjb.setRwid(rwid);
					jbList.add(qsmsjb);
					List<Qsmsdz> list = qsmsdzMap.get(rwid);
					if(null != list && list.size()>0)
					{
						for(Qsmsdz qsmsdz : list)
						{
							Qsmsjbtime qsmsjbtime = new Qsmsjbtime();
							qsmsjbtime.setRwid(rwid);
							qsmsjbtime.setDzid(qsmsdz.getId());
							qsmsjbtime.setYmd(ymd);
							String timeStr =ymd+" "+qsmsdz.getTime();
							Date time = null;
							try {
								time = formatter.parse(timeStr);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							qsmsjbtime.setTime(time);
							timeList.add(qsmsjbtime);
						}
					}
				}
			}
		}
		qsMsMapper.insertMsJb(dBname,jbList);
		System.out.println("timeList=="+timeList);
		if(timeList.size()>0)
		{
			qsMsMapper.insertMsJbTime(dBname,timeList);
		}
		startJob(dBname,ymds,appid);
//		for(String ymd : ymds)
//		{
//			qsMsMapper.deleteMsJb(dBname, ymd);
//			List<Qsmsjbtime> qsmsjbtimeList = qsMsMapper.findMSjbtimeBYymd(dBname,ymd);
//			if(qsmsjbtimeList.size()>0)
//			{
//				qsMsMapper.deleteMsJbTime(dBname, ymd);
//				deletejob(dBname, ymd);
//			}
//			for(Integer rwid : rwids)
//			{
//				Qsmsjb qsmsjb = new Qsmsjb();
//				qsmsjb.setYmd(ymd);
//				qsmsjb.setRwid(rwid);
//				qsMsMapper.insertMsJb(dBname,qsmsjb);
//				List<Qsmsdz> list = qsMsMapper.findMsDzByRwid(dBname,rwid);
//				if(list.size()>0)
//				{
//					for(Qsmsdz qsmsdz : list)
//					{
//						Qsmsjbtime qsmsjbtime = new Qsmsjbtime();
//						qsmsjbtime.setRwid(rwid);
//						qsmsjbtime.setDzid(qsmsdz.getId());
//						qsmsjbtime.setYmd(ymd);
//						String timeStr =ymd+" "+qsmsdz.getTime();
//						Date time = null;
//						try {
//							time = formatter.parse(timeStr);
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						qsmsjbtime.setTime(time);
//						qsMsMapper.insertMsJbTime(dBname,qsmsjbtime);
//					}
//				}
//			}
//			startJob(dBname,ymd,appid);
//		}
	}

	@Override
	public Map<String, List<Qsmsrw>> findAllMsJb(String dBname) {
		// TODO Auto-generated method stub
		List<Qsmsjb> qsmsjbList = qsMsMapper.findAllMsJb(dBname);
		Map<String,List<Qsmsrw>> maplist = new HashMap<>();
		if(null != qsmsjbList && qsmsjbList.size()>0)
		{
			for(Qsmsjb qsmsjb : qsmsjbList)
			{
				Qsmsrw qsmsrw = new Qsmsrw();
				qsmsrw.setId(qsmsjb.getRwid());
				qsmsrw.setDrtypeid(qsmsjb.getDrtypeid());
				qsmsrw.setMsname(qsmsjb.getMsname());
				qsmsrw.setColor(qsmsjb.getColor());
				qsmsrw.setType(qsmsjb.getType());
				
				List<Qsmsrw> listtmp = maplist.get(qsmsjb.getYmd());
				if(listtmp == null)
				{
					List<Qsmsrw> listq = new ArrayList<>();
					listq.add(qsmsrw);
					maplist.put(qsmsjb.getYmd(), listq);
				}
				else
				{
					listtmp.add(qsmsrw);
				}
			}
		}
		return maplist;
	}
	
	private void startJob(String dBname,String[] ymds,Integer appid)
	{
		List<Qsmsjbtime> qsmsjbtimeList = qsMsMapper.findMSjbtimeBYymd(dBname,ymds);
		if(qsmsjbtimeList !=null && qsmsjbtimeList.size()<1)
		{
			return;
		}
		for(Qsmsjbtime qsmsjbtime : qsmsjbtimeList)
		{
			if(qsmsjbtime.getTime().getTime() < new Date().getTime())
			{
				continue;
			}
			String cron = CronDateUtils.getCron(qsmsjbtime.getTime());
			Map<String, Object> map=new HashMap<>();
			if(!StringUtils.isEmpty(cron))
			{
				map.put("dBname", dBname);
				map.put("dzid", qsmsjbtime.getDzid());
				map.put("appid", appid);
			}
			JobKey jobKeys = JobKey.jobKey(String.valueOf(qsmsjbtime.getJobid()), dBname);
			// 创建一个定时任务
			TaskDefine task = TaskDefine.builder().jobKey(jobKeys).cronExpression(cron) // 定时任务 的cron表达式
					.jobClass(RegFiedJobQuartz.class) // 定时任务 的具体执行逻辑
					.jobDataMap(map)
					.description(qsmsjbtime.getDzname())// 定时任务 的描述
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
	
	private void startJobModDz(String dBname,List<Qsmsjbtime> qsmsjbtimeList,Integer appid)
	{
		if(qsmsjbtimeList !=null && qsmsjbtimeList.size()<1)
		{
			return;
		}
		for(Qsmsjbtime qsmsjbtime : qsmsjbtimeList)
		{
			if(qsmsjbtime.getTime().getTime() < new Date().getTime())
			{
				continue;
			}
			String cron = CronDateUtils.getCron(qsmsjbtime.getTime());
			Map<String, Object> map=new HashMap<>();
			if(!StringUtils.isEmpty(cron))
			{
				map.put("dBname", dBname);
				map.put("dzid", qsmsjbtime.getDzid());
				map.put("appid", appid);
			}
			JobKey jobKeys = JobKey.jobKey(String.valueOf(qsmsjbtime.getJobid()), dBname);
			// 创建一个定时任务
			TaskDefine task = TaskDefine.builder().jobKey(jobKeys).cronExpression(cron) // 定时任务 的cron表达式
					.jobClass(RegFiedJobQuartz.class) // 定时任务 的具体执行逻辑
					.jobDataMap(map)
					.description(qsmsjbtime.getDzname())// 定时任务 的描述
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
	
	private void deletejob(String dBname,List<Qsmsjbtime> jbTimeList)
	{
		for(Qsmsjbtime qsmsjbtime : jbTimeList)
		{
			JobKey jobKeys = JobKey.jobKey(String.valueOf(qsmsjbtime.getJobid()), dBname);
			try {
				quartzJobService.deleteJob(jobKeys);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Qsmsjbtime> findQsMsJbtimeAfNow(String dBname) {
		// TODO Auto-generated method stub
		return qsMsMapper.findQsMsJbtimeAfNow(dBname);
	}

	@Override
	public PageObject<Qsmsjblog> findMsJbLog(String dBname,Date startTime,Date endTime, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		
		int rowCount = qsMsMapper.getCountLog(dBname,startTime,endTime);
		int startIndex=(pageCurrent-1)*pageSize;
		
		List<Qsmsjblog> list = qsMsMapper.findMsJbLog(dBname,startTime,endTime,startIndex,pageSize);
		
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
		
}
