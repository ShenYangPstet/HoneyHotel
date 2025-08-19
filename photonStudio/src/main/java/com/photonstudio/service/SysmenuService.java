package com.photonstudio.service;

import java.util.List;

import com.photonstudio.pojo.Sysmenu;

public interface SysmenuService {

	List<Sysmenu> findAll();
	List<Sysmenu> findmenuByParentId(Integer parentId);
	int savemenu(Sysmenu menu);
	int updateMenu(Sysmenu menu);
	int deleteMenuById(Integer id);
	void findChildren(Integer id);
	List<Sysmenu> findAllMenu();
}
