package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Appinfo;
import com.photonstudio.pojo.Appsysuser;

import com.photonstudio.pojo.Usertoken;

public interface AppsysuserMapper {

	

	Appsysuser findUserByUp(Appsysuser user);

	void saveToken(Usertoken usertoken);

	Usertoken findUsertokenByToken(@Param(value = "token")String token);

	Appsysuser findAppsysuserByUsername(@Param(value = "username")String username);

	List<Appsysuser> findUserByName(@Param(value = "name")String name,
			@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);

	int insertObject(Appsysuser user);

	int deleteObjectById(@Param(value = "ids")Integer...ids);

	int updateObject(Appsysuser user);

	int getRowCount(@Param("name")String name);

	void deleteTokenByUsername(@Param("username")String username);

	int selectCount(@Param("username")String username);

	Appinfo queryAppinfoByAppname(@Param("dBname")String dBname, @Param("appName")String appName);

	int findUserByPassword(@Param("username")String username,@Param("password") String password);

	int updatePassword(@Param("username")String username,@Param("newpassword") String newpassword);

	int getUserId(@Param("dBname")String dBname);


}
