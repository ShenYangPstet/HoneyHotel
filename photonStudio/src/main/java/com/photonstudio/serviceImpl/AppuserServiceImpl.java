package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.AppsysuserMapper;
import com.photonstudio.mapper.AppuserMapper;
import com.photonstudio.mapper.AppusergroupMapper;
import com.photonstudio.pojo.Appsysuser;
import com.photonstudio.pojo.Appuser;
import com.photonstudio.pojo.Appusergroup;
import com.photonstudio.service.AppuserService;

@Service
public class AppuserServiceImpl implements AppuserService{
	@Autowired
	private AppuserMapper appuserMapper;
	@Autowired
	private AppusergroupMapper appusergroupMapper;
	@Autowired
	private AppsysuserMapper appsysuserMapper;
	
	@Override
	public List<Appuser> findAll(String dBname,String username) {
		List<Appuser>list=new ArrayList<>();
		try {
			list=appuserMapper.findObject(dBname,username, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}
	@Override
	public PageObject<Appuser> findObject(String dBname,String username ,Integer pageCurrent, Integer pageSize) {
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Appuser> list=new ArrayList<>();
		int rowCount= appuserMapper.getRowCount(dBname,username);
		int startIndex=(pageCurrent-1)*pageSize;
		list=appuserMapper.findObject(dBname, username, startIndex, pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
	@Override
	public int saveObject(String dBname, Appuser appuser) {
		int rows=0;
		try {
			rows=appuserMapper.insertObject(dBname, appuser);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}
	@Override
	public int deleteObject(String dBname,Integer appid ,Integer... ids) {
		int rows=0;
		try {
			rows=appuserMapper.deleteObjectById(dBname, ids);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}
	@Transactional
	@Override
	public int updateObject(String dBname, Appuser appuser,Integer appid) {
		int rows=0;
		int rowss=0;
		Appusergroup appusergroup=new Appusergroup();
		Appsysuser appsysuser=new Appsysuser();
		appusergroup.setAppid(appid).setAppuserground(appuser.getUsergroupid()).setUserid(appuser.getUserid()).setControl(appuser.getUsertype()).setRegShowLevel(appuser.getUsertype());
		if(appuser.getUsergroupid()!=null) {
			//System.out.println(appusergroup);
		
			rowss=appusergroupMapper.updateObjectByAppuser(appusergroup);
	
		}
		if(rowss==0)throw new ServiceException("权限更新失败");
		try {
			appsysuser.setEmail(appuser.getEmail()).setId(appuser.getUserid())
			.setPhonenum(appuser.getPhonenum()).setPermission(appuser.getPermission());
			appsysuserMapper.updateObject(appsysuser);
			rows=appuserMapper.updateObject(dBname, appuser);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public int saveAppuser(String dBname, Appuser appuser, Integer appid,Date licensetime) {
		// TODO Auto-generated method stub
		Appsysuser user = new Appsysuser();
		String md5pass=DigestUtils.md5DigestAsHex("123456".getBytes());
		user.setUsername(appuser.getUsername());
		user.setPassword(md5pass);
		user.setLicensetype("1");
		user.setRole(0);
		user.setLicensetime(licensetime);
		user.setPermission(appuser.getPermission());
		int rows = appsysuserMapper.insertObject(user);
		int userid = appsysuserMapper.getUserId(dBname);
		Appusergroup appusergroup = new Appusergroup();
		appusergroup.setUserid(userid);
		appusergroup.setAppid(appid);
		appusergroup.setControl(appuser.getUsertype());
		appusergroup.setAppuserground(appuser.getUsergroupid());
		appusergroup.setRegShowLevel(appuser.getRegShowLevel());
		rows = appusergroupMapper.insertObject(appusergroup);
		appuser.setUserid(userid);
		rows = appuserMapper.insertObject(dBname, appuser);
		return rows;
	}
	@Override
	public Appuser findObjectByUserid(String dBname, Integer userid) {
		// TODO Auto-generated method stub
		Appuser appuser=new Appuser();
		try {
			appuser=appuserMapper.findObjectByUserid(dBname,userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appuser;
	}

}
