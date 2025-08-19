package com.photonstudio.serviceImpl;

import java.util.*;

import cn.hutool.core.util.ObjectUtil;
import com.photonstudio.pojo.*;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.execute.LinkFiedJobQuartz;
import com.photonstudio.mapper.QslinkMapper;
import com.photonstudio.quartz.service.QuartzJobService;
import com.photonstudio.service.QslinkService;

@Service
public class QslinkServiceImpl implements QslinkService{
	
	@Autowired
	private QslinkMapper qslinkMapper;
	@Autowired
	private QuartzJobService quartzJobService;

	@Override
	public List<Qslinktj> findLinkTj(String dBname) {
		// TODO Auto-generated method stub
		return qslinkMapper.findLinkTj(dBname);
	}

	@Override
	public void updateLinkTj(String dBname, Qslinktj qslinktj) {
		// TODO Auto-generated method stub
		qslinkMapper.updateLinkTj(dBname,qslinktj);
	}

	@Override
	public void deleteLinkTj(String dBname, Integer id) {
		// TODO Auto-generated method stub
		qslinkMapper.deleteLinkTj(dBname,id);
	}

	@Override
	public PageObject<Qslinktjinfo> findLinkTjinfo(String dBname, Integer tjId,Integer pageCurrent,Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		
		int rowCount = qslinkMapper.getCountTjinfo(dBname,tjId);
		int startIndex=(pageCurrent-1)*pageSize;
		
		List<Qslinktjinfo> list = qslinkMapper.findLinkTjinfoPage(dBname,tjId,startIndex,pageSize);
		
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public void insertLinkTj(String dBname, Qslinktj qslinktj) {
		// TODO Auto-generated method stub
		qslinkMapper.insertLinkTj(dBname,qslinktj);
	}

	@Override
	public void updateLinkTjinfo(String dBname, Qslinktjinfo qslinktjinfo) {
		// TODO Auto-generated method stub
		qslinkMapper.updateLinkTjinfo(dBname,qslinktjinfo);
	}

	@Override
	public void insertLinkTjinfo(String dBname, Integer tjId, Integer[] regIds, String minValue, String maxValue,
			Integer relation) {
		// TODO Auto-generated method stub
		List<Qslinktjinfo> list=new ArrayList<>();
		for(Integer regId : regIds)
		{
			Qslinktjinfo qslinktjinfo = new Qslinktjinfo();
			qslinktjinfo.setTjId(tjId);
			qslinktjinfo.setRegId(regId);
			qslinktjinfo.setMinValue(minValue);
			qslinktjinfo.setMaxValue(maxValue);
			qslinktjinfo.setRelation(relation);
			list.add(qslinktjinfo);
		}
		qslinkMapper.insertLinkTjinfo(dBname,list);
	}

	@Override
	public void deleteLinkTjinfo(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		qslinkMapper.deleteLinkTjinfo(dBname,ids);
	}

	@Override
	public List<Qslinkjg> findLinkJg(String dBname) {
		// TODO Auto-generated method stub
		return qslinkMapper.findLinkJg(dBname);
	}

	@Override
	public void insertLinkJg(String dBname, Qslinkjg qslinkjg) {
		// TODO Auto-generated method stub
		qslinkMapper.insertLinkJg(dBname, qslinkjg);
	}

	@Override
	public void updateLinkJg(String dBname, Qslinkjg qslinkjg) {
		// TODO Auto-generated method stub
		qslinkMapper.updateLinkJg(dBname, qslinkjg);
	}

	@Override
	public void deleteLinkJg(String dBname, Integer id) {
		// TODO Auto-generated method stub
		qslinkMapper.deleteLinkJg(dBname,id);
	}

	@Override
	public PageObject<Qslinkjginfo> findLinkJginfo(String dBname, Integer jgId, Integer pageCurrent, Integer pageSize) {
		System.out.println("结果id"+jgId);
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		
		int rowCount = qslinkMapper.getCountJginfo(dBname,jgId);
		int startIndex=(pageCurrent-1)*pageSize;
		
		List<Qslinkjginfo> list = qslinkMapper.findLinkJginfo(dBname,jgId,startIndex,pageSize);
		
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public void insertLinkJginfo(String dBname, Qslinkjginfo qslinkjginfo, Integer[] regIds) {
		// TODO Auto-generated method stub
		List<Qslinkjginfo> list = new ArrayList<>();
		if (regIds.length>0){
		for(Integer regId : regIds)
		{
			Qslinkjginfo addqslinkjginfo =new Qslinkjginfo();
			BeanUtils.copyProperties(qslinkjginfo, addqslinkjginfo);
			//qslinkjginfo.setJgId(jgId);
			addqslinkjginfo.setRegId(regId);
			//qslinkjginfo.setValue(value);
			System.out.println(addqslinkjginfo);
			list.add(addqslinkjginfo);
		}
		}else {
			list.add(qslinkjginfo);
		}
		//System.out.println(list);
		qslinkMapper.insertLinkJginfo(dBname,list);
	}

	@Override
	public PageObject<QsLinkLog> findLog(String dBname, Date time, Integer pageCurrent, Integer pageSize,String jgName) {
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;

		int rowCount = qslinkMapper.getLogCount(dBname,time,jgName,null,null);
		int startIndex=(pageCurrent-1)*pageSize;

		List<QsLinkLog> list = qslinkMapper.findLogPage(dBname,time,startIndex,pageSize,jgName);

		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public void updateLinkJginfo(String dBname, Qslinkjginfo qslinkjginfo) {
		// TODO Auto-generated method stub
		qslinkMapper.updateLinkJginfo(dBname,qslinkjginfo);
	}

	@Override
	public void deleteLinkJginfo(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		qslinkMapper.deleteLinkJginfo(dBname,ids);
	}

	@Override
	public PageObject<Qslinkrw> findLinkRw(String dBname, Integer tjId, Integer jgId, Integer pageCurrent,
			Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		
		int rowCount = qslinkMapper.getCountLinkRw(dBname,tjId,jgId);
		int startIndex=(pageCurrent-1)*pageSize;
		
		List<Qslinkrw> list = qslinkMapper.findLinkRw(dBname,tjId,jgId,startIndex,pageSize);
		
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public void insertLinkRw(String dBname,Integer appid, Qslinkrw qslinkrw) {
		// TODO Auto-generated method stub
		qslinkMapper.insertLinkRw(dBname,qslinkrw);
		System.out.println("rwId=="+qslinkrw.getRwId());
		System.out.println("appid=="+appid);
		Map<String, Object> map=new HashMap<>();
		map.put("appid", appid);
		map.put("rwId", qslinkrw.getRwId());
		map.put("dBname", dBname);
		JobKey jobKeys = JobKey.jobKey(String.valueOf(qslinkrw.getRwId()), dBname+"变量条件");
		TaskDefine task = TaskDefine.builder().jobKey(jobKeys).cronExpression("0/3 * * * * ? ") // 定时任务 的cron表达式
				.jobClass(LinkFiedJobQuartz.class) // 定时任务 的具体执行逻辑
				.jobDataMap(map)
				.description(qslinkrw.getRwName())// 定时任务 的描述
				.build();
		try {
			quartzJobService.scheduleJob(task);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateLinkRw(String dBname, Qslinkrw qslinkrw) {
		// TODO Auto-generated method stub
		qslinkMapper.updateLinkRw(dBname,qslinkrw);
	}

	@Override
	public void deleteLinkRw(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		qslinkMapper.deleteLinkRw(dBname,ids);
		for(Integer id : ids)
		{
			JobKey jobKeys = JobKey.jobKey(String.valueOf(id),dBname+"变量条件");
			try {
				quartzJobService.deleteJob(jobKeys);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Qslinktjinfo> exportTjinfo(String dBname, Integer tjId) {
		// TODO Auto-generated method stub
		return qslinkMapper.findLinkTjinfo(dBname, tjId);
	}

	@Override
	public void insertLinkTjinfoImport(String dBname, Qslinktjinfo qslinktjinfo) {
		// TODO Auto-generated method stub
		List<Qslinktjinfo> list=new ArrayList<>();
		list.add(qslinktjinfo);
		qslinkMapper.insertLinkTjinfo(dBname, list);
	}

	@Override
	public List<Qslinkrw> findQslinkrwList(String dBname) {
		// TODO Auto-generated method stub
		return qslinkMapper.findQslinkrwList(dBname);
	}

	@Override
	public List<Qslinkjginfo> exportJginfo(String dBname, Integer jgId) {
		// TODO Auto-generated method stub
		return qslinkMapper.findLinkJginfoByJgid(dBname,jgId);
	}

	@Override
	public void insertLinkJginfoImport(String dBname, Qslinkjginfo qslinkjginfo) {
		// TODO Auto-generated method stub
		List<Qslinkjginfo> list = new ArrayList<Qslinkjginfo>();
		list.add(qslinkjginfo);
		qslinkMapper.insertLinkJginfo(dBname, list);
	}

	@Override
	public List<Qslinkrw> exportRw(String dBname, Integer tjId, Integer jgId) {
		// TODO Auto-generated method stub
		return qslinkMapper.exportRw(dBname,tjId,jgId);
	}

	@Override
	public void insertLinkRwImport(String dBname, List<Qslinkrw> list) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public PageObject<QsLinkLog> findByPage(QsLinkLog dto) {
		PageObject<QsLinkLog> pageObject = new PageObject<>();
		dto.setRange("COUNT(*) as count");
		Integer count = qslinkMapper.findByPage(dto).get(0).getCount();
		dto.setRange("*");
		if (ObjectUtil.isNotNull(dto.getPageSize())
				&& ObjectUtil.isNotNull(dto.getPageCurrent())) {
			Integer startIndex = (dto.getPageCurrent() - 1) * dto.getPageSize();
			dto.setStartIndex(startIndex);
			List<QsLinkLog> list = qslinkMapper.findByPage(dto);
			pageObject = PagesUtil.getPageObject(list, count, dto.getPageSize(), dto.getPageCurrent());
		}
		else {
			List<QsLinkLog> list = qslinkMapper.findByPage(dto);
			pageObject.setRecords(list);
			pageObject.setRowCount(count);
		}
		return pageObject;
	}
	@Override
	public List<Qslinktj> findBytjId(String dBname, Integer [] tjid) {
		return qslinkMapper.findBytjid(dBname,tjid);
	}


	@Override
	public void insertimportQslinktj(String dBname,Qslinktj qslinktj) {
		qslinkMapper.insertLinkTj(dBname,qslinktj);
	}
	@Override
	public int countByQslinktj(String dBname, Integer tjid) {
		return qslinkMapper.countByQslinktj(dBname,tjid);
	}

	@Override
	public List<Qslinkjg> findByid(String dBname, Integer[] ids) {
		return qslinkMapper.findByid(dBname,ids);
	}

	@Override
	public int countByJG(String dBname, Integer jgid) {
		return qslinkMapper.countByJG(dBname,jgid);
	}
}
