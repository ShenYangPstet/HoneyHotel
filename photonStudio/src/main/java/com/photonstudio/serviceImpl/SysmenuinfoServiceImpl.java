package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.mapper.SysmenuinfoMapper;
import com.photonstudio.pojo.Sysmenuinfo;
import com.photonstudio.service.SysmenuinfoService;
@Service
public class SysmenuinfoServiceImpl implements SysmenuinfoService{
	@Autowired
	private SysmenuinfoMapper sysmenuinfoMapper;
	@Override
	public int saveAll(String dBname,List<Sysmenuinfo> list) {
		try {
			int row=sysmenuinfoMapper.deleteAll(dBname);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("刷新数据失败");
		}
		if(list==null||list.size()==0)return 0;
		int rows=0;
		try {
			rows = sysmenuinfoMapper.insetAll(dBname,list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		
		return rows;
	}
	@Override
	public List<Sysmenuinfo> findObjectByParentId(String dBname, Integer parentId) {
		if(parentId==null)parentId=0;
		List<Sysmenuinfo>list=new ArrayList<>();
		try {
			list=sysmenuinfoMapper.findObject(dBname,parentId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}
	@Override
	public int updateObject(String dBname, Sysmenuinfo sysmenuinfo) {
		if(sysmenuinfo.getParentId()==sysmenuinfo.getId())
			throw new IllegalArgumentException("不能隶属于自己");
		int rows=0;
		try {
			rows = sysmenuinfoMapper.updateObject(dBname,sysmenuinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public List<Sysmenuinfo> findAllMenu(String dBname) {
		List<Sysmenuinfo> rootMenu=new ArrayList<>();
		rootMenu=sysmenuinfoMapper.findObject(dBname, null);
		// 最后的结果
	    List<Sysmenuinfo> menuList = new ArrayList<Sysmenuinfo>();
	    // 先找到所有的一级菜单
	    for (int i = 0; i < rootMenu.size(); i++) {
	        // 一级菜单没有parentId
	        if (0 == rootMenu.get(i).getParentId()) {
	            menuList.add(rootMenu.get(i));
	        }
	    }
	    // 为一级菜单设置子菜单，getChild是递归调用的
	    for (Sysmenuinfo menu : menuList) {
	        menu.setChildren(getChild(menu.getId(), rootMenu,dBname));
	    }
		
		return menuList;
	}
	private List<Sysmenuinfo> getChild(Integer integer, List<Sysmenuinfo> rootMenu,String dBname) {
	    // 子菜单
	    List<Sysmenuinfo> childList = new ArrayList<>();
	    for (Sysmenuinfo menu : rootMenu) {
	        if (0 != menu.getParentId()) {
	            if (menu.getParentId().equals(integer)) {
	                childList.add(menu);
	            }
	        }
	    }
	    if (childList.size() == 0) {
	        return null;
	    }
	    // 把子菜单的子菜单再循环一遍
	    for (Sysmenuinfo menu : childList) {// 没有url子菜单还有子菜单
	    	
	    	List<Sysmenuinfo> children = sysmenuinfoMapper.findObject(dBname, menu.getId());
	        if (children!=null) {
	            // 递归
	            menu.setChildren(getChild(menu.getId(), rootMenu,dBname));
	        }
	    } // 递归退出条件
	   
	    return childList;
	}
	@Override
	public List<Sysmenuinfo> findObjectByGroupid(String dBname, Integer groupid) {
		List<Sysmenuinfo> rootMenu=new ArrayList<>();
		rootMenu=sysmenuinfoMapper.findObjectByGroupid(dBname, groupid, null);
		//System.out.println("rootMenu=="+rootMenu);
		//System.out.println(groupid);
		// 最后的结果
	    List<Sysmenuinfo> menuList = new ArrayList<Sysmenuinfo>();
	    // 先找到所有的一级菜单
	    for (int i = 0; i < rootMenu.size(); i++) {
	        // 一级菜单没有parentId
	        if (0 == rootMenu.get(i).getParentId()) {
	            menuList.add(rootMenu.get(i));
	        }
	    }
	    // 为一级菜单设置子菜单，getChild是递归调用的
	    for (Sysmenuinfo menu : menuList) {
	        menu.setChildren(getChild(menu.getId(), rootMenu,dBname));
	    }
		
		return menuList;
		
	}
	@Override
	public List<Integer> findMenuIdByGroupid(String dBname, Integer groupid) {
		List<Integer>list=new ArrayList<>();
		list=sysmenuinfoMapper.findMenuIdByGroupid(dBname,groupid);
		return list;
	}
	@Override
	public Sysmenuinfo findPicByMenuId(String dBname, Integer id) {
		// TODO Auto-generated method stub
		Sysmenuinfo sysmenuinfo=new Sysmenuinfo();
		try {
			sysmenuinfo=sysmenuinfoMapper.findPicByMenuId(dBname,id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return sysmenuinfo;
	}

}
