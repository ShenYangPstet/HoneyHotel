package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Sysmenu;
import com.photonstudio.pojo.User;

public interface SysmenuMapper {
	List<Sysmenu> findAll();
	void createDatabase(@Param(value = "name") String name);
	void deleteDatabase(@Param(value = "name") String name);
	void createTable();
	void deleteTable(@Param(value = "dbname") String dbname,
			@Param(value = "tablename")String tablename);
	void insert(@Param(value = "dbname") String dbname,
			@Param(value = "tablename")String tablename,
			User user);
	void delete(@Param(value = "dbname") String dbname,
			@Param(value = "id")Integer id);
	void update(@Param(value = "dbname") String dbname,
			@Param(value = "user")User user);
	List<Sysmenu> findmenuByParentId(@Param(value = "parentId")Integer parentId);
	int insertMenu(Sysmenu menu);
	int updatemenu(Sysmenu menu);
	int deleteMenuById(@Param(value = "ids")Integer... ids);
	List<Integer> findChildrenId(@Param(value = "id")Integer id);
	
}
