package com.photonstudio.service;

import java.util.List;

import com.photonstudio.pojo.Sysmenuinfo;

public interface SysmenuinfoService {

	int saveAll(String dBname, List<Sysmenuinfo> list);

	List<Sysmenuinfo> findObjectByParentId(String dBname, Integer parentId);

	int updateObject(String dBname, Sysmenuinfo sysmenuinfo);

	List<Sysmenuinfo> findAllMenu(String dBname);

	List<Sysmenuinfo> findObjectByGroupid(String dBname, Integer groupid);

	List<Integer> findMenuIdByGroupid(String dBname, Integer groupid);

	Sysmenuinfo findPicByMenuId(String dBname, Integer id);

}
