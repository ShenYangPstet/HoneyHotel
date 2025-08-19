package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.photonstudio.common.JobStatesUtil;
import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.QsLdTjMapUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.execute.RegFiedJobQuartz;
import com.photonstudio.mapper.QsLdRwMapper;
import com.photonstudio.pojo.QsLdRw;
import com.photonstudio.pojo.TaskDefine;
import com.photonstudio.quartz.service.QuartzJobService;
import com.photonstudio.service.QsLdRwService;

@Service
public class QsLdRwServiceImpl implements  QsLdRwService{
	@Autowired
	private QsLdRwMapper qsLdRwMapper;
	@Autowired
	private QuartzJobService quartzJobService;
	@Override
	public PageObject<QsLdRw> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int startIndex=(pageCurrent-1)*pageSize;
		int rowCount=qsLdRwMapper.getRowCount(dBname);
		List<QsLdRw>list=new ArrayList<>();
		list=qsLdRwMapper.findObject(dBname,startIndex,pageSize);
		for (QsLdRw qsLdRw : list) {
			System.out.println(qsLdRw.getLdRwId());
			JobKey jobKeys = JobKey.jobKey(String.valueOf(qsLdRw.getLdRwId()), dBname+"变量条件");
			String State = quartzJobService.findState(jobKeys);
			qsLdRw.setState(JobStatesUtil.getTriggerStatesCN(State));
		}
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, QsLdRw qsLdRw) {
		int rows=0;
		qsLdRw.setIssuspend(1);
		try {
		rows=qsLdRwMapper.insertObject(dBname,qsLdRw);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer... ids) {
		int rows=0;
		try {
			for (Integer id : ids) {
				deleteRegJob(dBname, id);
				System.out.println("service ids=="+id);
			}
			rows=qsLdRwMapper.deleteObjectById(dBname,ids);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, QsLdRw qsLdRw) {
	int rows=0;
	try {
		rows=qsLdRwMapper.updateObject(dBname,qsLdRw);
	} catch (Exception e) {
		e.printStackTrace();
		throw new ServiceException("更新失败");
	}
		return rows;
	}

	@Override
	public List<QsLdRw> findAllByRwType(String dBname, Integer rwType) {
		List<QsLdRw>list=new ArrayList<>();
		try {
			list=qsLdRwMapper.findObject(dBname, null, null);
			for (QsLdRw qsLdRw : list) {
				System.out.println(qsLdRw.getLdRwId());
				JobKey jobKeys = JobKey.jobKey(String.valueOf(qsLdRw.getLdRwId()), dBname+"变量条件");
				String State = quartzJobService.findState(jobKeys);
				qsLdRw.setState(JobStatesUtil.getTriggerStatesCN(State));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void importObjects(String dBname, List<QsLdRw> list) {
		int rows=0;
		for (QsLdRw qsLdRw : list) {
			if (qsLdRw.getLdRwId()==null||qsLdRw.getLdRwId()<1) {
				qsLdRw.setIssuspend(0);
				qsLdRwMapper.insertObject(dBname, qsLdRw);
			}else {
				rows=qsLdRwMapper.findObjectById(dBname,qsLdRw.getLdRwId());
				if (rows==1) {
					qsLdRwMapper.updateObject(dBname, qsLdRw);
				}else {
					qsLdRw.setIssuspend(0);
					qsLdRwMapper.insertObject(dBname, qsLdRw);
				}
			}
		}
	}

	@Override
	public List<QsLdRw> findMoshiObject(String dBname, Integer rwType) {
		// TODO Auto-generated method stub
		return qsLdRwMapper.findMoshiObject(dBname,rwType);
	}

	@Override
	public QsLdRw findObjectById(String dBname, Integer ldRwId) {
		// TODO Auto-generated method stub
		QsLdRw qsLdRw=new QsLdRw();
		try {
			qsLdRw = qsLdRwMapper.findObjectByldRwId(dBname, ldRwId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qsLdRw;
	}

	@Override
	public boolean startJobByLdRwId(String dBname, Integer ldRwId,Integer appid,QsLdRw qsLdRw) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<>();
		map.put("appid", appid);
		map.put("ldRwId", ldRwId);
		map.put("dBname", dBname);
		JobKey jobKeys = JobKey.jobKey(String.valueOf(ldRwId), dBname+"变量条件");
		// 创建一个定时任务
		TaskDefine task = TaskDefine.builder().jobKey(jobKeys).cronExpression("0/3 * * * * ? ") // 定时任务 的cron表达式
				.jobClass(RegFiedJobQuartz.class) // 定时任务 的具体执行逻辑
				.jobDataMap(map)
				.description(qsLdRw.getLdRwMiaoshu())// 任务 的描述
				.build();
		try {
			qsLdRw.setIssuspend(0);
			qsLdRwMapper.updateObject(dBname, qsLdRw);
			quartzJobService.scheduleJob(task);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public boolean deleteRegJob(String dBname,Integer ldRwId) {
		JobKey jobKeys = JobKey.jobKey(String.valueOf(ldRwId),dBname+"变量条件");
		QsLdTjMapUtil.remove(dBname+"_"+ldRwId);
		try {
			quartzJobService.deleteJob(jobKeys);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<QsLdRw> findObjectByIssuspend(String dBname, Integer issuspend) {
		// TODO Auto-generated method stub
		List<QsLdRw>qsLdRw=new ArrayList<>();
				try {
					qsLdRw=	qsLdRwMapper.findObjectByIssuspend(dBname,issuspend);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return qsLdRw;
	}

	@Override
	public List< QsLdRw> findObjectByIds(String dBname, Integer[] ldrwids) {
		List<QsLdRw> ldrwlist=new ArrayList<>();
		try {
			 ldrwlist=qsLdRwMapper.findObjectByIds(dBname,ldrwids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ldrwlist;
		// TODO Auto-generated method stub
	}

	@Override
	public List<QsLdRw> findAll(String dBname) {
		// TODO Auto-generated method stub
		List<QsLdRw>ldrwlist=new ArrayList<>();
		try {
			ldrwlist=qsLdRwMapper.findObject(dBname, null, null);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ldrwlist;
	}

}
