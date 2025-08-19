package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.AppmanagerMapper;
import com.photonstudio.mapper.AppsysuserMapper;
import com.photonstudio.mapper.AppuserMapper;
import com.photonstudio.mapper.AppusergroupMapper;
import com.photonstudio.pojo.Appinfo;
import com.photonstudio.pojo.Appmanager;
import com.photonstudio.pojo.Appsysuser;
import com.photonstudio.pojo.Appuser;
import com.photonstudio.pojo.Usertoken;
import com.photonstudio.service.AppsysuserService;
@Service
public class AppsysuserServiceImpl implements AppsysuserService{
	@Autowired
	private AppsysuserMapper appsysuserMapper;
	@Autowired
	private AppusergroupMapper appusergroupMapper;
	@Autowired
	private AppmanagerMapper appmanagerMapper;
	@Autowired
	private AppuserMapper appuserMapper;
	@Override
	public Map<String, Object> findUserByUP(Appsysuser user) {
		String token = null;
		Map<String, Object> map=new HashMap<>();
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		Appsysuser userDB  =appsysuserMapper.findUserByUp(user);
		if(userDB != null) {
			String tokenString="zsqy"+System.currentTimeMillis()+user.getUsername();
			token=DigestUtils.md5DigestAsHex(tokenString.getBytes());
			appsysuserMapper.deleteTokenByUsername(user.getUsername());
			Usertoken usertoken=new Usertoken();
			usertoken.setToken(token);
			usertoken.setUsername(user.getUsername());
			usertoken.setUserid(userDB.getId());
			//System.out.println(usertoken);
			appsysuserMapper.saveToken(usertoken);
			map.put("token", token);
			map.put("user", userDB);
			return map;
		}
		return map;
	}
	@Override
	public Usertoken findUsertokenByToken(String token) {
	  Usertoken usertoken= appsysuserMapper.findUsertokenByToken(token);
	  
	  return usertoken;
	   
		
	}
	@Override
	public Appsysuser findAppsysuserByUsername(String username) {
		Appsysuser user=appsysuserMapper.findAppsysuserByUsername(username);
		
		return user;
	}
	@Override
	public PageObject<Appsysuser> findUserByName(String name,
		Integer	pageCurrent, Integer pageSize) {
		List<Appsysuser> list =new ArrayList<>();
		if(pageCurrent==null||pageCurrent<1)
			pageCurrent=1;
		if(pageSize==null||pageSize<1)pageSize=5;
		//基于条件查询总记录数并进行验证
		int rowCount=appsysuserMapper.getRowCount(name);
		//对总记录数进行校验(等于0表示没有记录)
		if(rowCount==0)
	    throw new ServiceException("记录不存在");
		
		int startIndex=(pageCurrent-1)*pageSize;
		
		list= appsysuserMapper.findUserByName(name, startIndex, pageSize);
		 PageObject<Appsysuser> pageObject=new PageObject<>();
			//对查询结果进行封装并返回。
			pageObject.setRowCount(rowCount);
			pageObject.setRecords(list);
			pageObject.setPageSize(pageSize);
			pageObject.setPageCurrent(pageCurrent);
			pageObject.setPageCount((rowCount-1)/pageSize+1);
			return pageObject;
	}
	@Override
	public int saveObject(Appsysuser user) {
		user.setPassword("123456");
		String md5pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5pass);
		user.setRole(0);
		int rows = 0;
		try {
			rows = appsysuserMapper.insertObject(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}
	@Override
	public int deleteObjectById(Integer... ids) {
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("请先选择");
			//执行删除操作
			int rows=0;
			try{//通过dao访问数据库服务器可能会有异常，此异常也可不再此位置处理
			 rows=appsysuserMapper.deleteObjectById(ids);
			}catch(Throwable e){//网络中断，磁盘坏了，..
			 e.printStackTrace();
			 
			 throw new ServiceException(e);
			}
			//验证删除结果
			if(rows==0)
			throw new ServiceException("记录可能已经不存在");
			//返回结果
			return rows;
	}
	
	@Override
	public int updateObject(Appsysuser user) {
		//if(StringUtils.isEmpty(user.getPassword())) throw new IllegalArgumentException("密码不能为空");
		//String md5pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		//user.setPassword(md5pass);
		int rows=0;
		List<Appmanager>list=new ArrayList<>();
		Appuser appuser= new Appuser();
		appuser.setUserid(user.getId()).setEmail(user.getEmail()).setPhonenum(user.getPhonenum());
		try {
			list=appmanagerMapper.findObjectByUserid(user.getId(), "1", "0");
			//System.out.println("用户项目=="+list);
			for (Appmanager appmanager : list) {
				String dBname=appmanager.getAppid()+appmanager.getAppName();
				appuserMapper.updateObject(dBname, appuser);
			}
			rows = appsysuserMapper.updateObject(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public boolean findCheckUser(String username) {
		int count;
		try {
			count = appsysuserMapper.selectCount(username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("重新尝试");
		}
		return count==0?false:true ;
	}
	@Override
	public String getTokenByUsername(String username) {
		String token = null;
		Appsysuser user=appsysuserMapper.findAppsysuserByUsername(username);
		if(user!=null) {
		String tokenString="zsqy"+System.currentTimeMillis()+username;
		token=DigestUtils.md5DigestAsHex(tokenString.getBytes());
		appsysuserMapper.deleteTokenByUsername(username);
		Usertoken usertoken=new Usertoken();
		usertoken.setToken(token);
		usertoken.setUsername(username);
		//System.out.println(usertoken);
		appsysuserMapper.saveToken(usertoken);
		return token;
		}
		return token;
	}
	@Override
	public List<Appsysuser> findAll() {
		List<Appsysuser>list=new ArrayList<>();
		try {
			list =appsysuserMapper.findUserByName(null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}
	@Override
	public Appinfo queryAppinfoByAppname(String dBname, String appName) {
		// TODO Auto-generated method stub
		return appsysuserMapper.queryAppinfoByAppname(dBname,appName);
	}
	@Override
	public int findUserByPassword(String username, String password) {
		// TODO Auto-generated method stub
		password=DigestUtils.md5DigestAsHex(password.getBytes());
		int rows=0;
		try {
			rows=appsysuserMapper.findUserByPassword(username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return rows;
	}
	@Override
	public int updatePassword(String username, String newpassword) {
		newpassword=DigestUtils.md5DigestAsHex(newpassword.getBytes());
		int rows=0;
		try {
			rows=appsysuserMapper.updatePassword(username,newpassword);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rows;
	}
	

}
