package com.photonstudio.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.AppinfoMapper;
import com.photonstudio.pojo.Appinfo;
import com.photonstudio.service.AppinfoService;
@Service
public class AppinfoServiceImpl implements AppinfoService{
	@Autowired
	private AppinfoMapper appinfoMapper;
	@Override
	public PageObject<Appinfo> findObject(String dBname,Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)
			pageCurrent=1;
		if(pageSize==null||pageSize<5)
			pageSize=5;
		        //基于条件查询总记录数并进行验证
				int rowCount=appinfoMapper.getRowCount(dBname);
				//依据条件查询当前页要显示的记录
				int startIndex=(pageCurrent-1)*pageSize;
		List<Appinfo> list=appinfoMapper.findObject(dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
		
	}
	@Override
	public int saveObject(String dBname, Appinfo appinfo) {
		
	int rows ;
	try {
		rows = appinfoMapper.insertObject(dBname,appinfo);
	} catch (Exception e) {
		e.printStackTrace();
		throw new ServiceException("信息保存失败");
	}
		return rows;
	}
	@Override
	public int deleteObject(String dBname,Integer appid) {
		if(appid==null)throw new ServiceException("请选择");
		int rows;
		try {
			rows = appinfoMapper.deleteObject(dBname,appid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}
	@Override
	public Appinfo findObjectById(String dBname,Integer appid) {
		Appinfo appinfo=appinfoMapper.findObjectById(dBname,appid);
		return appinfo;
	}
	@Override
	public int updateObject(String dBname, Appinfo appinfo) {
		int rows;
		try {
			rows = appinfoMapper.updateObject(dBname,appinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public int findAppinfo(String dBname) {
		// TODO Auto-generated method stub
		List<Appinfo> list=appinfoMapper.findAppinfo(dBname);
		if(list.size()==0||list.get(0).getAppdate()==null)return 0;
		 Calendar appcalendar = Calendar.getInstance();
         appcalendar.setTime(list.get(0).getAppdate());
         appcalendar.set(appcalendar.get(Calendar.YEAR), appcalendar.get(Calendar.MONTH), appcalendar.get(Calendar.DAY_OF_MONTH),
					0, 0, 0);
         Date appdate=appcalendar.getTime();
         //System.out.println(appdate);
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        //System.out.println(date);
        long days = (date.getTime() - appdate.getTime()) / (24*3600*1000);
		return (int)days;
	}

}
