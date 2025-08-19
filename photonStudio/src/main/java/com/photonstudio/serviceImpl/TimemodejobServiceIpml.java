package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.mapper.TimemodejobMapper;
import com.photonstudio.pojo.Timemodejob;
import com.photonstudio.quartz.service.QuartzJobService;
import com.photonstudio.service.TimemodejobService;
@Service
public class TimemodejobServiceIpml implements TimemodejobService{
	@Autowired
	private TimemodejobMapper timemodejobMapper;
	@Autowired
	private QuartzJobService quartzJobService;
	@Override
	public List<Timemodejob> findObjectByTimemodeid(String dBname, Integer timemodeid) {
		List<Timemodejob> list=new ArrayList<>();
		try {
			list = timemodejobMapper.findObject(dBname,timemodeid,null,null,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询时间任务失败");
			
		}
		return list;
	}

	@Override
	public Timemodejob findObjectById(String dBname, Integer id) {
		// TODO Auto-generated method stub
		Timemodejob job=new Timemodejob();
		try {
			job=timemodejobMapper.findObjectById(dBname,id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return job;
	}

	@Override
	public List<Timemodejob> findObjectByUseTime(String dBname) {
		List<Timemodejob> listjob=new ArrayList<>();
		try {
			listjob=timemodejobMapper.findObjectByUseTime(dBname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listjob;
	}

	@Override
	public int deleteObjectById(String dBname, Integer... ids) {
		// TODO Auto-generated method stub
		int row=0;
		if(ids!=null&&ids.length>0) {
			for (Integer id : ids) {
				deleteTimeJob(dBname,id);
			}
			try {
				row=timemodejobMapper.deleteObjectByIds(dBname,ids);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return row;
		
	}

	private void deleteTimeJob(String dBname, Integer id) {
		// TODO Auto-generated method stub
		JobKey jobKeys = JobKey.jobKey(String.valueOf(id), dBname);
		try {
			quartzJobService.deleteJob(jobKeys);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void deleteObjectByTjIds(String dBname, Integer... ldTjIds) {
		try {
			// TODO Auto-generated method stub
			List<Timemodejob> joblist=new ArrayList<>();
			if(ldTjIds!=null&&ldTjIds.length>0) {
				joblist = timemodejobMapper.findObjectByLdTjId(dBname, ldTjIds);
			}
			if(joblist!=null&&joblist.size()!=0) {
				int i=0;
				Integer[] ids= new Integer[joblist.size()];
				for (Timemodejob timemodejob : joblist) {
					ids[i]=timemodejob.getId();
					i++;
				}
				deleteObjectById(dBname, ids);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}

	@Override
	public void deleteObjectByTimemodeids(String dBname, Integer... timemodeids) {
		// TODO Auto-generated method stub
		try {
			List<Timemodejob> joblist=new ArrayList<>();
			if(timemodeids!=null&&timemodeids.length>0) {
				joblist = timemodejobMapper.findObjectByTimemodeids(dBname, timemodeids);
			}
			if(joblist!=null&&joblist.size()!=0) {
				int i=0;
				Integer[] ids= new Integer[joblist.size()];
				for (Timemodejob timemodejob : joblist) {
					ids[i]=timemodejob.getId();
					i++;
				}
				deleteObjectById(dBname, ids);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void deleteObjectByRwId(String dBname, Integer... ids) {
		// TODO Auto-generated method stub
		try {
			List<Timemodejob> joblist=new ArrayList<>();
			if(ids!=null&&ids.length>0) {
				joblist = timemodejobMapper.findObjectByLdRwIds(dBname, ids);
			}
			if(joblist!=null&&joblist.size()!=0) {
				int i=0;
				Integer[] rwids= new Integer[joblist.size()];
				for (Timemodejob timemodejob : joblist) {
					rwids[i]=timemodejob.getId();
					i++;
				}
				deleteObjectById(dBname, rwids);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
