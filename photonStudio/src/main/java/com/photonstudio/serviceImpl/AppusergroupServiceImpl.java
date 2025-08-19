package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.AppuserMapper;
import com.photonstudio.mapper.AppusergroupMapper;
import com.photonstudio.mapper.UsergroupMapper;
import com.photonstudio.pojo.Appuser;
import com.photonstudio.pojo.Appusergroup;
import com.photonstudio.pojo.Usergroup;
import com.photonstudio.service.AppusergroupService;

@Service
public class AppusergroupServiceImpl implements AppusergroupService{
	@Autowired
	private AppusergroupMapper appusergroupMapper;
	@Autowired
	private UsergroupMapper usergroupMapper;
	@Autowired
	private AppuserMapper appuserMapper;

	@Override
	public List<Appusergroup> findObjectByuserid(Integer userid,String region, String city) {
		List<Appusergroup>list=new ArrayList<>();
		try {
			list=appusergroupMapper.findObject(userid,region,city, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}

	@Override
	public PageObject<Appusergroup> findObject(Integer userid, Integer pageCurrent, Integer pageSize) {
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Appusergroup>list=new ArrayList<>();
		int rowCount= appusergroupMapper.getRowCount(userid);
		int startIndex=(pageCurrent-1)*pageSize;
		list=appusergroupMapper.findObject(userid,null,null, startIndex, pageSize);
		List<Appusergroup>appusergrouplist=new ArrayList<>();
		for (Appusergroup appusergroup : list) {
			String dBname=appusergroup.getAppid()+appusergroup.getAppName();
			Usergroup usergroup = usergroupMapper.findObjectById(dBname, appusergroup.getAppuserground());
			if(usergroup==null)continue;
			appusergroup.setUsergroupname(usergroup.getUsergroupname());
			appusergrouplist.add(appusergroup);
		}
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
	@Transactional
	@Override
	public int saveObject(Appusergroup appusergroup) {
		int rows=0;
		System.out.println("appusergroup======"+appusergroup);
		Appuser appuser=new Appuser();
		try {
			rows=appusergroupMapper.insertObject(appusergroup);
			if(appusergroup.getUserid()!=null) {
				appuser.setUserid(appusergroup.getUserid()).setUsername(appusergroup.getUsername())
				.setUsergroupid(appusergroup.getAppuserground()).setUsertype(0)
				.setPhonenum(appusergroup.getPhonenum()).setEmail(appusergroup.getEmail());
				String dBname=appusergroup.getAppid()+appusergroup.getAppName();
				appuserMapper.insertObject(dBname, appuser);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}
	@Transactional
	@Override
	public int deleteObjectById(Appusergroup appusergroup) {
		System.out.println("appusergroup======"+appusergroup);
		int rows=0;
		try {
			rows=appusergroupMapper.deleteObjectById(appusergroup.getId());
			String dBname=appusergroup.getAppid()+appusergroup.getAppName();
			appuserMapper.deleteObjectById(dBname, appusergroup.getUserid());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(Appusergroup appusergroup) {
		System.out.println("appusergroup======"+appusergroup);
		int rows=0;
		Appuser appuser=new Appuser();
		try {
			rows=appusergroupMapper.updateObject(appusergroup);
			if(appusergroup.getAppuserground()!=null) {
				appuser.setUserid(appusergroup.getUserid())
				.setUsergroupid(appusergroup.getAppuserground());
				String dBname=appusergroup.getAppid()+appusergroup.getAppName();
				appuserMapper.updateObject(dBname, appuser);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public List<Appusergroup> findObjectByUserIds(Integer[] userids) {
		// TODO Auto-generated method stub
		List<Appusergroup>list=new ArrayList<>();
		try {
			list=appusergroupMapper.findObjectByUserIds(userids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}

	@Override
	public int updateSkinById(Integer id, String skin) {
		int rows=0;
		try {
			rows=appusergroupMapper.updateSkinById(id,skin);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return rows;
	}
}
