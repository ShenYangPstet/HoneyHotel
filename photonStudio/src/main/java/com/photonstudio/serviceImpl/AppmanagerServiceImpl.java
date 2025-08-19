package com.photonstudio.serviceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.AppmanagerMapper;
import com.photonstudio.pojo.Appmanager;
import com.photonstudio.pojo.FileUploadProperteis;
import com.photonstudio.service.AppmanagerService;
@Service
public class AppmanagerServiceImpl implements AppmanagerService{
	@Autowired
	private AppmanagerMapper appmanagerMapper;
	@Autowired
	private FileUploadProperteis fileUploadProperteis;
	@Override
	
	public PageObject<Appmanager> findObjectByApptypeid(Integer apptypeid ,Integer userid,
			String region, String city,Integer pageCurrent,Integer pageSize,String state) {
		
		List<Appmanager> list =new ArrayList<>();
		if(pageCurrent==null||pageCurrent<1)
			pageCurrent=1;
		//基于条件查询总记录数并进行验证
		int rowCount=appmanagerMapper.getRowCount(apptypeid,userid,region,city,state);
		//对总记录数进行校验(等于0表示没有记录)

		if(pageSize==null||pageSize<1)pageSize=5;
		//依据条件查询当前页要显示的记录
		int startIndex=(pageCurrent-1)*pageSize;
		list=appmanagerMapper.findObjectByApptypeid(apptypeid, userid,region,city,startIndex, pageSize,state);
		PageObject<Appmanager> pageObject=new PageObject<>();
		//对查询结果进行封装并返回。
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(list);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageCount((rowCount-1)/pageSize+1);
		return pageObject;
	}

	@Override
	public int deleteObjectsById(Integer appid,String dBname) {
		if(appid==null||appid<0)
			throw new IllegalArgumentException("请先选择");
		int rows=0;
		
		try {
			rows = appmanagerMapper.deleteAPPGroupById(appid);
			rows = appmanagerMapper.deleteObjectsById(appid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		//验证删除结果
				if(rows==0)
				throw new ServiceException("记录可能已经不存在");
				//删除对应的数据库
			appmanagerMapper.deleteDBByid(dBname);
		return rows;
	}

	@Override
	public int saveObject(Appmanager appmanager) {
		//1.对参数进行校验
				if(appmanager==null)
				throw new IllegalArgumentException("保存对象不能为空");
				if(StringUtils.isEmpty(appmanager.getAppName()))
			    throw new IllegalArgumentException("项目名不能为空");
				
		int rows;
		appmanager.setAppstate("0");
		appmanager.setState("0");
		rows=appmanagerMapper.insertObject(appmanager);
		System.out.println("插入项目的appid"+appmanager.getAppid());
		//创建对应的数据库
		return rows;
	}

	@Override
	public int updateObject(Appmanager appmanager) {
		
		int rows;
		rows=appmanagerMapper.updateObjectById(appmanager);
		return rows;
	}

	@Override
	public int findCount(String apptypeid) {
		int rows;
		rows=appmanagerMapper.findCount(apptypeid);
		return rows;
	}

	@Override
	@Transactional
	public int initObject(String dBname,Integer appid) {
		System.out.println("库名为："+dBname+".. 项目Id为:"+appid);
		try {
			appmanagerMapper.createDatabase(dBname);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("初始化失败");
			throw new ServiceException("初始化失败");
		}
		 Appmanager appmanager=new Appmanager();
		 appmanager.setAppid(appid);
		 appmanager.setAppstate("1");
		 appmanager.setState("0");
		 String dir=fileUploadProperteis.getUploadFolder()+dBname;
		 File dirFile = new File(dir+"/icon/");
		 if(!dirFile.exists()) {
			 //如果文件不存在,则创建文件夹
			 dirFile.mkdirs();
		 }
		 dirFile=new File(dir+"/picimage/");
		 if(!dirFile.exists()) {
			 //如果文件不存在,则创建文件夹
			 dirFile.mkdirs();
		 }
		 dirFile=new File(dir+"/systemicon/");
		 if(!dirFile.exists()) {
			 //如果文件不存在,则创建文件夹
			 dirFile.mkdirs();
		 }
		 dirFile=new File(dir+"/instructions/");
		 if(!dirFile.exists()) {
			 //如果文件不存在,则创建文件夹
			 dirFile.mkdirs();
		 }
		 int rows= appmanagerMapper.updateObjectById(appmanager);
		if (rows==0)throw new ServiceException("初始化失败");
		
		return rows;
	}

	@Override
	public int removeObject(String state,Integer...appids) {
		if(appids==null||appids.length==0)
			throw new IllegalArgumentException("请先选择");
		int rows=appmanagerMapper.removeObject(state,appids);
;
		
		return rows;
	}

	@Override
	public boolean findCheckappmanager(String appName) {
		
		int rows=0;
		try {
			rows = appmanagerMapper.findObjectByappName(appName);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new ServiceException("重新尝试");
		}
		return rows==0?true:false;
	}

	@Override
	public List<Appmanager> findObjectByUesrid(Integer userid) {
		List<Appmanager>list=new ArrayList<>();
		try {
			list=appmanagerMapper.findObjectByUesrid(userid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("查询错误");
		}
		return list;
	}

	@Override
	public Appmanager findObjectByAppid(Integer appid) {
		Appmanager appmanager=new Appmanager();
		try {
			appmanager=appmanagerMapper.findObjectByAppid(appid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("未找到该项目信息");
		}
		return appmanager;
	}

	@Override
	public List<Appmanager> findObjectByState(String appstate, String state) {
		// TODO Auto-generated method stub
		List<Appmanager> applist=new ArrayList<>();
		try {
			applist = appmanagerMapper.findObjectByState(appstate, state);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return applist;
	}

	@Override
	public List<Appmanager> findAllByApptypeid(Integer userid, String region, String city) {
		// TODO Auto-generated method stub
		List<Appmanager> applist=new ArrayList<>();
		try {
			applist=appmanagerMapper.findObjectByApptypeid(null, userid, region, city, null, null, "0");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return applist;
	}

	@Override
	public List<Appmanager> findAllByAppids(Integer[] appids) {
		// TODO Auto-generated method stub
		List<Appmanager>list=new ArrayList<>();
		try {
			list=appmanagerMapper.findAllByAppids(appids);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public int getRowCountByLicense() {
		// TODO Auto-generated method stub
		int rowCount=0;
		rowCount=appmanagerMapper.getRowCountByLicense();
		return rowCount;
	}

	@Override
	public List<Appmanager> findAllByAppidsAppstate(Integer[] appids,String appstate) {
		// TODO Auto-generated method stub
		List<Appmanager>list=new ArrayList<>();
		try {
			list=appmanagerMapper.findAllByAppidsAppstate(appids,appstate);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}

	
	
}
