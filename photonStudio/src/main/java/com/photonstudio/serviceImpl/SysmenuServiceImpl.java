package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.mapper.SysmenuMapper;
import com.photonstudio.pojo.Sysmenu;
import com.photonstudio.service.SysmenuService;
@Service
public class SysmenuServiceImpl implements SysmenuService {
	@Autowired
	SysmenuMapper sysmenuMapper;
	@Override
	
	public List<Sysmenu> findAll() {
		
		return sysmenuMapper.findAll();
	}
	@Override
	public List<Sysmenu> findmenuByParentId(Integer parentId) {
		
		return sysmenuMapper.findmenuByParentId(parentId);
	}
	@Override
	public int savemenu(Sysmenu menu) {
		int rows;
		rows= sysmenuMapper.insertMenu(menu);
		return rows;
	}
	@Override
	public int updateMenu(Sysmenu menu) {
		if(menu.getParentId()==menu.getId())
			throw new IllegalArgumentException("不能隶属于自己");
		int rows;
		rows=sysmenuMapper.updatemenu(menu);
		return rows;
	}
	@Override
	public int deleteMenuById(Integer id) {
		//1.对参数进行校验
		if(id==null)
		throw new IllegalArgumentException("请先选择");
		//删除子菜单
		findChildren(id);
			
		
		int rows=0;
		try{
		 rows=sysmenuMapper.deleteMenuById(id);
		}catch(Throwable e){
		 e.printStackTrace();
		 throw new ServiceException(e);
		}
		//验证删除结果
		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		//返回结果
		return rows;
	}
	
	
	public  void findChildren(Integer id) {
		List<Integer>list =new ArrayList<>();
		list=sysmenuMapper.findChildrenId(id);
		  if(list==null||list.size()<1) {
		  return;
		 }
		for (Integer ChildrenId : list) {
			findChildren(ChildrenId);
			sysmenuMapper.deleteMenuById(ChildrenId);
		}
		 
	}
	@Override
	public List<Sysmenu> findAllMenu() {
		List<Sysmenu> rootMenu=new ArrayList<>();
		rootMenu=sysmenuMapper.findAll();
		
		// 最后的结果
	    List<Sysmenu> menuList = new ArrayList<Sysmenu>();
	    // 先找到所有的一级菜单
	    for (int i = 0; i < rootMenu.size(); i++) {
	        // 一级菜单没有parentId
	        if (0 == rootMenu.get(i).getParentId()) {
	            menuList.add(rootMenu.get(i));
	        }
	    }
	    // 为一级菜单设置子菜单，getChild是递归调用的
	    for (Sysmenu menu : menuList) {
	        menu.setChildren(getChild(menu.getId(), rootMenu));
	    }
		return menuList;
	}
	
	private List<Sysmenu> getChild(Integer integer, List<Sysmenu> rootMenu) {
	    // 子菜单
	    List<Sysmenu> childList = new ArrayList<>();
	    for (Sysmenu menu : rootMenu) {
	        if (0 != menu.getParentId()) {
	            if (menu.getParentId().equals(integer)) {
	                childList.add(menu);
	            }
	        }
	    }
	    // 把子菜单的子菜单再循环一遍
	    for (Sysmenu menu : childList) {
	    	List<Integer> childrenId = sysmenuMapper.findChildrenId(menu.getId());
	        if (childrenId!=null) {
	            // 递归
	            menu.setChildren(getChild(menu.getId(), rootMenu));
	        }
	    } // 递归退出条件
	    if (childList.size() == 0) {
	        return null;
	    }
	    return childList;
	}
}
